package utils;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Parser mejorado de page source XML con priorización inteligente
 *
 * <p>VERSIÓN MEJORADA: ✅ Prioriza textos con números (versiones, fechas) ✅ Prioriza resource-ids
 * únicos ✅ Filtra elementos vacíos o poco útiles
 */
public class PageSourceParser {

  /**
   * Extrae localizadores relevantes del XML con priorización inteligente
   *
   * @param xmlFile Archivo XML del page source
   * @param maxLocators Número máximo de localizadores a retornar
   * @return Lista de localizadores priorizados
   */
  public static List<String> extractRelevantLocators(File xmlFile, int maxLocators) {
    try {
      DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
      DocumentBuilder builder = factory.newDocumentBuilder();
      Document doc = builder.parse(xmlFile);

      // TreeMap mantiene orden por prioridad (mayor primero)
      Map<Integer, List<String>> prioritizedLocators = new TreeMap<>(Collections.reverseOrder());

      NodeList nodes = doc.getElementsByTagName("*");

      System.out.println("[PageSourceParser] Analizando " + nodes.getLength() + " nodos...");

      for (int i = 0; i < nodes.getLength(); i++) {
        Node node = nodes.item(i);

        if (node.getNodeType() == Node.ELEMENT_NODE) {
          NamedNodeMap attributes = node.getAttributes();

          if (attributes != null && attributes.getLength() > 0) {
            String locator = buildLocatorString(attributes);

            if (locator != null && !locator.trim().isEmpty()) {
              int priority = calculatePriority(locator, attributes);

              // Agrupar por prioridad (puede haber varios con misma prioridad)
              prioritizedLocators.computeIfAbsent(priority, k -> new ArrayList<>()).add(locator);
            }
          }
        }
      }

      // Aplanar el mapa manteniendo orden de prioridad
      List<String> result =
          prioritizedLocators.values().stream()
              .flatMap(List::stream)
              .limit(maxLocators)
              .collect(Collectors.toList());

      System.out.println("[PageSourceParser] ✅ Extraídos " + result.size() + " localizadores");

      // Debug: Mostrar los primeros 5
      if (!result.isEmpty()) {
        System.out.println("[PageSourceParser] Top 5 localizadores:");
        for (int i = 0; i < Math.min(5, result.size()); i++) {
          System.out.println("  " + (i + 1) + ". " + result.get(i));
        }
      }

      return result;

    } catch (Exception e) {
      System.err.println("[PageSourceParser] ❌ Error: " + e.getMessage());
      e.printStackTrace();
      return Collections.emptyList();
    }
  }

  /** ✅ CLAVE: Calcula prioridad del localizador Mayor puntaje = más relevante */
  private static int calculatePriority(String locator, NamedNodeMap attributes) {
    int priority = 100; // Base

    String text = getAttributeValue(attributes, "text");
    String resourceId = getAttributeValue(attributes, "resource-id");
    String contentDesc = getAttributeValue(attributes, "content-desc");
    String clickable = getAttributeValue(attributes, "clickable");

    // ✅ PRIORIDAD MUY ALTA: Texto con números (versiones, fechas)
    if (text != null && !text.isEmpty()) {
      priority += 200;

      // CRÍTICO: Detectar versiones y fechas
      if (containsNumbers(text)) {
        priority += 300; // ¡MUY ALTA PRIORIDAD!
        System.out.println(
            "[PageSourceParser] 🎯 Texto con números: \""
                + text
                + "\" (prioridad: "
                + priority
                + ")");
      }

      // Texto largo es más específico
      if (text.length() > 10) {
        priority += 50;
      }

      // Palabras clave relevantes
      if (containsKeywords(text)) {
        priority += 100;
      }
    }

    // ✅ PRIORIDAD ALTA: resource-id único
    if (resourceId != null && !resourceId.isEmpty()) {
      priority += 150;

      // IDs más específicos
      if (resourceId.contains("version")
          || resourceId.contains("info")
          || resourceId.contains("title")
          || resourceId.contains("label")) {
        priority += 100;
      }
    }

    // ✅ PRIORIDAD MEDIA: content-desc
    if (contentDesc != null && !contentDesc.isEmpty()) {
      priority += 80;
    }

    // ✅ PRIORIDAD MEDIA: elementos clickeables
    if ("true".equals(clickable)) {
      priority += 70;
    }

    // ❌ PENALIZACIÓN: Elementos sin información útil
    if (text == null || text.isEmpty()) {
      priority -= 100;
    }

    // ❌ PENALIZACIÓN: Textos genéricos
    if (text != null && (text.equals("true") || text.equals("false") || text.length() < 2)) {
      priority -= 150;
    }

    return priority;
  }

  /** Construye el string del localizador desde atributos */
  private static String buildLocatorString(NamedNodeMap attributes) {
    StringBuilder locator = new StringBuilder();

    // Orden de prioridad de atributos
    String[] priorityAttributes = {
      "text", "resource-id", "content-desc", "class", "clickable", "enabled", "index"
    };

    for (String attrName : priorityAttributes) {
      String value = getAttributeValue(attributes, attrName);

      if (value != null && !value.isEmpty() && !value.equals("false")) {
        if (locator.length() > 0) {
          locator.append(", ");
        }
        locator.append(attrName).append("=\"").append(value).append("\"");
      }
    }

    return locator.toString();
  }

  /** Obtiene valor de un atributo */
  private static String getAttributeValue(NamedNodeMap attributes, String attrName) {
    Node attrNode = attributes.getNamedItem(attrName);
    return attrNode != null ? attrNode.getNodeValue() : null;
  }

  /** ✅ CRÍTICO: Verifica si contiene números */
  private static boolean containsNumbers(String str) {
    if (str == null) return false;
    return str.matches(".*\\d+.*");
  }

  /** ✅ Verifica si contiene palabras clave relevantes */
  private static boolean containsKeywords(String text) {
    if (text == null) return false;

    String lowerText = text.toLowerCase();
    String[] keywords = {
      "ver",
      "versión",
      "version",
      "fecha",
      "date",
      "título",
      "title",
      "nombre",
      "name",
      "botón",
      "button",
      "aceptar",
      "continuar",
      "confirmar",
      "inicio",
      "home",
      "perfil",
      "profile",
      "consultar",
      "buscar",
      "search"
    };

    for (String keyword : keywords) {
      if (lowerText.contains(keyword)) {
        return true;
      }
    }

    return false;
  }
}
