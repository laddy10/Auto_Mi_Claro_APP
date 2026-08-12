package utils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.logging.Logger;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.util.Units;
import org.apache.poi.xwpf.usermodel.*;

public class WordAppium {

  private static final Logger LOGGER = Logger.getLogger(WordAppium.class.getName());

  private static final String TEMPLATE_PATH =
      System.getProperty("user.dir")
          + File.separator
          + "ruta"
          + File.separator
          + "PlantillaInforme.docx";
  private static final String CAPTURAS_DIR = "Capturas/";
  private static final String REPORTES_DIR =
      System.getProperty("user.dir") + File.separator + "reportes";

  // Carpeta y archivo donde ErrorScreenshotHooks deja la captura del fallo.
  private static final String ERROR_DIR = "Error";
  private static final String ERROR_FILE = "error.png";

  private static final DateTimeFormatter FORMATTER =
      DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

  private static final ResourceBundle messages = ResourceBundle.getBundle("messages");
  private static final Properties STEP_MESSAGES = new Properties();

  static {
    try (InputStream input =
        WordAppium.class.getClassLoader().getResourceAsStream("messages.properties")) {
      if (input != null) {
        STEP_MESSAGES.load(input);
        LOGGER.info("messages.properties cargado correctamente.");
      } else {
        LOGGER.warning("messages.properties no encontrado.");
      }
    } catch (IOException e) {
      LOGGER.warning("Error al cargar messages.properties: " + e.getMessage());
    }
  }

  public static void generarReporte(
      String nombreEscenario,
      String[] pasosEjecutados,
      String numero,
      String duracionFormato,
      String pasoFallido,
      String estadoFinal) {

    boolean fallo = "FAILED".equalsIgnoreCase(estadoFinal);

    File[] capturas = new File(CAPTURAS_DIR).listFiles();
    // Si no hay capturas Y el caso NO falló, no hay nada que reportar.
    // Si falló, seguimos igual para dejar la sección de error en el reporte.
    if ((capturas == null || capturas.length == 0) && !fallo) {
      LOGGER.warning("No hay capturas para procesar.");
      return;
    }
    if (capturas == null) {
      capturas = new File[0];
    }

    new File(REPORTES_DIR).mkdirs();
    String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
    String nombreArchivo =
        "Prueba_" + nombreEscenario.replaceAll("\\s+", "_") + "_" + timestamp + ".docx";
    String rutaDestino = REPORTES_DIR + File.separator + nombreArchivo;
    System.out.println("📄 Guardando en: " + rutaDestino);
    try (FileInputStream fis = new FileInputStream(TEMPLATE_PATH);
        XWPFDocument doc = new XWPFDocument(fis);
        FileOutputStream fos = new FileOutputStream(rutaDestino)) {

      reemplazarTexto(doc, "{{ESCENARIO}}", nombreEscenario);
      reemplazarTexto(doc, "{{FECHA}}", FORMATTER.format(LocalDateTime.now()));
      reemplazarTexto(doc, "{{LINEA}}", numero);
      reemplazarTexto(doc, "{{DURACION}}", duracionFormato);
      reemplazarTexto(doc, "{{ESTADO}}", fallo ? "FALLIDO" : "EXITOSO");

      agregarPasosYCapturas(doc, pasosEjecutados, capturas);

      // 🔴 Nuevo: sección de error (descripción + captura) cuando el caso falla.
      if (fallo) {
        agregarSeccionError(doc, pasoFallido, EstadoPrueba.descripcionError);
      }

      doc.write(fos);
      LOGGER.info("Reporte generado correctamente: " + rutaDestino);

    } catch (IOException | InvalidFormatException e) {
      LOGGER.severe("Error generando el reporte Word: " + e.getMessage());
    }

    eliminarCapturas(capturas);
  }

  private static void agregarPasosYCapturas(XWPFDocument doc, String[] pasos, File[] capturas)
      throws IOException, InvalidFormatException {
    for (String paso : pasos) {
      XWPFParagraph p = doc.createParagraph();
      p.setSpacingBefore(200);
      XWPFRun run = p.createRun();
      run.setText(paso);
      run.setFontSize(12);

      XWPFParagraph espacio = doc.createParagraph();
      XWPFRun espacioRun = espacio.createRun();
      espacioRun.setText("");

      File imagen = buscarCapturaDePaso(paso, capturas);
      if (imagen != null) {
        XWPFParagraph imgP = doc.createParagraph();
        XWPFRun imgRun = imgP.createRun();
        try (FileInputStream is = new FileInputStream(imagen)) {
          imgRun.addPicture(
              is, Document.PICTURE_TYPE_PNG, imagen.getName(), Units.toEMU(150), Units.toEMU(270));
        }
      } else {
        XWPFRun noImgRun = doc.createParagraph().createRun();
        noImgRun.setText("(No se encontró imagen para este paso)");
      }
    }
  }

  /** Agrega al final del reporte la descripción del error y la captura del fallo (Error/error.png). */
  private static void agregarSeccionError(
      XWPFDocument doc, String pasoFallido, String descripcionError) {

    XWPFParagraph titulo = doc.createParagraph();
    titulo.setSpacingBefore(300);
    XWPFRun tRun = titulo.createRun();
    tRun.setText("RESULTADO: FALLIDO");
    tRun.setBold(true);
    tRun.setFontSize(14);
    tRun.setColor("C00000");

    if (pasoFallido != null && !pasoFallido.trim().isEmpty()) {
      XWPFParagraph p1 = doc.createParagraph();
      XWPFRun r1 = p1.createRun();
      r1.setBold(true);
      r1.setText("Paso donde falló: ");
      XWPFRun r1b = p1.createRun();
      r1b.setText(pasoFallido);
    }

    XWPFParagraph p2 = doc.createParagraph();
    XWPFRun r2 = p2.createRun();
    r2.setBold(true);
    r2.setText("Descripción del error:");

    XWPFParagraph p2b = doc.createParagraph();
    XWPFRun r2b = p2b.createRun();
    String desc =
        (descripcionError == null || descripcionError.trim().isEmpty())
            ? "No se capturó el detalle del error."
            : recortar(descripcionError, 2000);
    r2b.setText(desc);
    r2b.setColor("C00000");

    File errorImg = new File(ERROR_DIR + File.separator + ERROR_FILE);
    if (errorImg.exists()) {
      XWPFParagraph pImgTit = doc.createParagraph();
      pImgTit.setSpacingBefore(150);
      XWPFRun rImgTit = pImgTit.createRun();
      rImgTit.setBold(true);
      rImgTit.setText("Captura del error:");

      XWPFParagraph imgP = doc.createParagraph();
      XWPFRun imgRun = imgP.createRun();
      try (FileInputStream is = new FileInputStream(errorImg)) {
        imgRun.addPicture(
            is, Document.PICTURE_TYPE_PNG, errorImg.getName(), Units.toEMU(150), Units.toEMU(270));
      } catch (Exception e) {
        LOGGER.warning("No se pudo insertar la captura del error: " + e.getMessage());
      }
    } else {
      XWPFRun noImg = doc.createParagraph().createRun();
      noImg.setText("(No se encontró la captura del error en " + errorImg.getPath() + ")");
    }
  }

  private static String recortar(String s, int max) {
    if (s == null) {
      return "";
    }
    s = s.trim();
    return s.length() <= max ? s : s.substring(0, max) + "… (mensaje recortado)";
  }

  private static File buscarCapturaDePaso(String paso, File[] capturas) {
    String normalizado = paso.toLowerCase().replaceAll("[^a-z0-9]", "_");
    for (File f : capturas) {
      if (f.getName().toLowerCase().contains(normalizado)) {
        return f;
      }
    }
    return null;
  }

  private static String obtenerDescripcionPaso(String paso) {
    String key = paso.toLowerCase().replaceAll("[^a-z0-9]", "_");
    if (STEP_MESSAGES.containsKey(key)) {
      return STEP_MESSAGES.getProperty(key);
    }
    for (String k : STEP_MESSAGES.stringPropertyNames()) {
      if (key.contains(k)) {
        return STEP_MESSAGES.getProperty(k);
      }
    }
    return paso;
  }

  private static void eliminarCapturas(File[] capturas) {
    for (File captura : capturas) {
      try {
        Files.deleteIfExists(captura.toPath());
      } catch (IOException e) {
        LOGGER.warning("No se pudo eliminar la captura: " + captura.getName());
      }
    }
  }

  private static void reemplazarTexto(XWPFDocument doc, String marcador, String valor) {
    for (XWPFParagraph p : doc.getParagraphs()) {
      for (XWPFRun r : p.getRuns()) {
        String text = r.getText(0);
        if (text != null && text.contains(marcador)) {
          r.setText(text.replace(marcador, valor), 0);
        }
      }
    }

    for (XWPFTable t : doc.getTables()) {
      for (XWPFTableRow row : t.getRows()) {
        for (XWPFTableCell cell : row.getTableCells()) {
          for (XWPFParagraph p : cell.getParagraphs()) {
            for (XWPFRun r : p.getRuns()) {
              String text = r.getText(0);
              if (text != null && text.contains(marcador)) {
                r.setText(text.replace(marcador, valor), 0);
              }
            }
          }
        }
      }
    }
  }

  public static void inicializarPlantillaReporte() {
    try {
      String origen =
          System.getProperty("user.dir")
              + File.separator
              + "ruta"
              + File.separator
              + "PlantillaInforme.docx";
      String destino =
          System.getProperty("user.dir")
              + File.separator
              + "ruta"
              + File.separator
              + "InformeFinal.docx";
      Files.copy(Paths.get(origen), Paths.get(destino), StandardCopyOption.REPLACE_EXISTING);
      LOGGER.info("Plantilla copiada correctamente.");
    } catch (IOException e) {
      LOGGER.severe("Error al copiar la plantilla: " + e.getMessage());
    }
  }
}
