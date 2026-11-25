package utils;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

/**
 * Lógica pura (sin Appium) para ubicar una opción (p. ej. "Ver más") que pertenece a un módulo
 * identificado por su título (p. ej. "Pagos y consultas"), a partir del page source.
 *
 * <p>Decide una de cuatro acciones:
 *
 * <ul>
 *   <li>TOCAR: la opción del módulo está completa dentro de la zona segura → tocar (x, y).
 *   <li>DESPLAZAR: hay que mover el contenido {@code desplazamiento} px (positivo = contenido sube).
 *   <li>BUSCAR_MODULO: el título del módulo no está en pantalla.
 *   <li>NO_ENCONTRADO: el título está arriba de la zona segura y aun así la opción no aparece.
 * </ul>
 *
 * <p>Zona segura: área visible del contenedor con scroll del módulo, descontando la barra superior
 * (top_bar) y la barra de navegación inferior (bottomBlurView / nav_view) más un margen para el
 * botón flotante de Cupones.
 */
public final class LocalizadorOpcionEnModulo {

    public enum Accion {
        TOCAR,
        DESPLAZAR,
        BUSCAR_MODULO,
        NO_ENCONTRADO
    }

    /** Resultado del análisis del page source. */
    public static final class Plan {
        public final Accion accion;
        public final int x;
        public final int y;
        public final int desplazamiento;
        public final int zonaSuperior;
        public final int zonaInferior;
        /** Posición Y (top) del título del módulo; -1 si no se encontró. Sirve para detectar avance. */
        public final int tituloY;
        public final String detalle;

        private Plan(
                Accion accion, int x, int y, int desplazamiento, int zonaSup, int zonaInf, int tituloY,
                String detalle) {
            this.accion = accion;
            this.x = x;
            this.y = y;
            this.desplazamiento = desplazamiento;
            this.zonaSuperior = zonaSup;
            this.zonaInferior = zonaInf;
            this.tituloY = tituloY;
            this.detalle = detalle;
        }

        @Override
        public String toString() {
            return accion + " x=" + x + " y=" + y + " desplazamiento=" + desplazamiento
                    + " zona=[" + zonaSuperior + "," + zonaInferior + "] tituloY=" + tituloY + " " + detalle;
        }
    }

    private static final Pattern BOUNDS = Pattern.compile("\\[(-?\\d+),(-?\\d+)]\\[(-?\\d+),(-?\\d+)]");
    private static final Pattern XML_INVALIDO =
            Pattern.compile("[^\\x09\\x0A\\x0D\\x20-\\uD7FF\\uE000-\\uFFFD\\x{10000}-\\x{10FFFF}]");

    /** Ids de barras que se superponen al contenido por arriba. */
    private static final String[] OVERLAYS_SUPERIORES = {":id/top_bar"};
    /** Ids de barras que se superponen al contenido por abajo. */
    private static final String[] OVERLAYS_INFERIORES = {":id/bottomBlurView", ":id/nav_view"};

    /** Margen inferior (fracción del alto) para no tocar el botón flotante de Cupones. */
    private static final double MARGEN_INFERIOR = 0.05;
    private static final double MARGEN_SUPERIOR = 0.02;
    /** Distancia máxima título → opción (fracción del alto). Más allá, es de otro módulo. */
    private static final double DISTANCIA_MAXIMA = 0.45;
    /** Posición objetivo de la opción dentro de la zona segura al desplazar (0 = arriba, 1 = abajo). */
    private static final double POSICION_OBJETIVO = 0.6;
    private static final int DESPLAZAMIENTO_MINIMO = 15;
    private static final int TOLERANCIA_PX = 5;

    private LocalizadorOpcionEnModulo() {}

    public static Plan calcular(String pageSource, String modulo, String opcion, int altoPantalla) {
        Document doc = parsear(pageSource);
        List<Element> nodos = todosLosNodos(doc);

        Element titulo = buscarTitulo(nodos, modulo, opcion, altoPantalla);
        if (titulo == null) {
            return new Plan(Accion.BUSCAR_MODULO, 0, 0, 0, 0, altoPantalla, -1,
                    "No se ve el título del módulo '" + modulo + "'");
        }
        int[] bTitulo = bounds(titulo);

        // Zona segura
        int zonaSup = 0;
        int zonaInf = altoPantalla;
        Element scroll = ancestroScrollable(titulo);
        if (scroll != null) {
            int[] bs = bounds(scroll);
            zonaSup = Math.max(zonaSup, bs[1]);
            zonaInf = Math.min(zonaInf, bs[3]);
        }
        for (Element e : nodos) {
            String id = e.getAttribute("resource-id");
            if (id.isEmpty() || !esVisible(e)) continue;
            int[] b = bounds(e);
            if (b == null) continue;
            if (terminaEnAlguno(id, OVERLAYS_SUPERIORES) && b[3] < altoPantalla / 2) {
                zonaSup = Math.max(zonaSup, b[3]);
            }
            if (terminaEnAlguno(id, OVERLAYS_INFERIORES) && b[1] > altoPantalla / 2) {
                zonaInf = Math.min(zonaInf, b[1]);
            }
        }
        zonaSup += (int) (altoPantalla * MARGEN_SUPERIOR);
        zonaInf -= (int) (altoPantalla * MARGEN_INFERIOR);

        // Opción del módulo: subiendo por los ancestros del título, el primero que contenga la opción
        // por debajo del título (o en la misma fila) delimita el módulo.
        int[] bOpcion = buscarOpcionDelModulo(titulo, bTitulo, opcion, altoPantalla);

        if (bOpcion == null) {
            // La opción aún no está renderizada: subir el título al borde superior de la zona segura.
            int delta = bTitulo[1] - zonaSup;
            if (delta <= DESPLAZAMIENTO_MINIMO) {
                return new Plan(Accion.NO_ENCONTRADO, 0, 0, 0, zonaSup, zonaInf, bTitulo[1],
                        "El módulo '" + modulo + "' está arriba y no muestra la opción '" + opcion + "'");
            }
            return new Plan(Accion.DESPLAZAR, 0, 0, delta, zonaSup, zonaInf, bTitulo[1],
                    "Opción aún no visible; se sube el título del módulo");
        }

        int cx = (bOpcion[0] + bOpcion[2]) / 2;
        int cy = (bOpcion[1] + bOpcion[3]) / 2;
        boolean dentro = bOpcion[1] >= zonaSup && bOpcion[3] <= zonaInf;
        if (dentro) {
            return new Plan(Accion.TOCAR, cx, cy, 0, zonaSup, zonaInf, bTitulo[1], "Opción en zona segura");
        }
        int objetivo = zonaSup + (int) ((zonaInf - zonaSup) * POSICION_OBJETIVO);
        int delta = cy - objetivo;
        if (Math.abs(delta) < DESPLAZAMIENTO_MINIMO) {
            return new Plan(Accion.TOCAR, cx, cy, 0, zonaSup, zonaInf, bTitulo[1], "Opción casi centrada");
        }
        return new Plan(Accion.DESPLAZAR, cx, cy, delta, zonaSup, zonaInf, bTitulo[1],
                "Opción fuera de zona segura " + texto(bOpcion));
    }

    // ---------------------------------------------------------------- helpers

    private static int[] buscarOpcionDelModulo(
            Element titulo, int[] bTitulo, String opcion, int altoPantalla) {
        int distanciaMax = (int) (altoPantalla * DISTANCIA_MAXIMA);
        Node actual = titulo.getParentNode();
        while (actual instanceof Element) {
            int[] mejor = null;
            for (Element e : descendientes((Element) actual)) {
                if (e == titulo || !igualTexto(e, opcion) || !esVisible(e)) continue;
                int[] b = bounds(e);
                if (b == null || b[1] < bTitulo[1] - TOLERANCIA_PX) continue; // por encima → otro módulo
                if (mejor == null || b[1] < mejor[1] || (b[1] == mejor[1] && b[0] < mejor[0])) mejor = b;
            }
            if (mejor != null) {
                return (mejor[1] - bTitulo[3] <= distanciaMax) ? mejor : null;
            }
            actual = actual.getParentNode();
        }
        return null;
    }

    /**
     * Busca el título del módulo. Si hay varios nodos con ese texto (p. ej. copias ocultas de otro
     * fragmento), prefiere el que está dentro de un contenedor con scroll y, entre ellos, el más
     * cercano a la pantalla visible.
     */
    private static Element buscarTitulo(
            List<Element> nodos, String modulo, String opcion, int altoPantalla) {
        List<Element> exactos = new ArrayList<>();
        List<Element> parciales = new ArrayList<>();
        String m = modulo.trim().toLowerCase();
        for (Element e : nodos) {
            if (!esVisible(e) || bounds(e) == null) continue;
            String t = e.getAttribute("text").trim();
            if (t.isEmpty() || t.equalsIgnoreCase(opcion)) continue;
            if (t.equalsIgnoreCase(modulo.trim())) exactos.add(e);
            else if (t.toLowerCase().contains(m)) parciales.add(e);
        }
        Element mejor = elegirTitulo(exactos, altoPantalla);
        return mejor != null ? mejor : elegirTitulo(parciales, altoPantalla);
    }

    private static Element elegirTitulo(List<Element> candidatos, int altoPantalla) {
        Element mejor = null;
        long mejorPuntaje = Long.MAX_VALUE;
        for (Element e : candidatos) {
            int[] b = bounds(e);
            int fuera = b[1] < 0 ? -b[1] : Math.max(0, b[1] - altoPantalla); // px fuera de pantalla
            long puntaje = (ancestroScrollable(e) != null ? 0 : 1_000_000L) + fuera;
            if (puntaje < mejorPuntaje) {
                mejorPuntaje = puntaje;
                mejor = e;
            }
        }
        return mejor;
    }

    private static Element ancestroScrollable(Element e) {
        Node n = e.getParentNode();
        while (n instanceof Element) {
            if ("true".equals(((Element) n).getAttribute("scrollable"))) return (Element) n;
            n = n.getParentNode();
        }
        return null;
    }

    private static boolean igualTexto(Element e, String texto) {
        return e.getAttribute("text").trim().equalsIgnoreCase(texto.trim());
    }

    private static boolean esVisible(Element e) {
        String d = e.getAttribute("displayed");
        return d.isEmpty() || "true".equals(d);
    }

    private static boolean terminaEnAlguno(String id, String[] sufijos) {
        for (String s : sufijos) if (id.endsWith(s)) return true;
        return false;
    }

    static int[] bounds(Element e) {
        Matcher m = BOUNDS.matcher(e.getAttribute("bounds"));
        if (!m.matches()) return null;
        return new int[] {
                Integer.parseInt(m.group(1)), Integer.parseInt(m.group(2)),
                Integer.parseInt(m.group(3)), Integer.parseInt(m.group(4))
        };
    }

    private static String texto(int[] b) {
        return "[" + b[0] + "," + b[1] + "][" + b[2] + "," + b[3] + "]";
    }

    private static List<Element> descendientes(Element raiz) {
        List<Element> lista = new ArrayList<>();
        NodeList nl = raiz.getElementsByTagName("*");
        for (int i = 0; i < nl.getLength(); i++) lista.add((Element) nl.item(i));
        return lista;
    }

    private static List<Element> todosLosNodos(Document doc) {
        return descendientes(doc.getDocumentElement());
    }

    private static Document parsear(String xml) {
        try {
            String limpio = XML_INVALIDO.matcher(xml).replaceAll("");
            DocumentBuilderFactory f = DocumentBuilderFactory.newInstance();
            f.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
            DocumentBuilder b = f.newDocumentBuilder();
            return b.parse(new InputSource(new StringReader(limpio)));
        } catch (Exception e) {
            throw new IllegalStateException("No se pudo interpretar el page source", e);
        }
    }
}