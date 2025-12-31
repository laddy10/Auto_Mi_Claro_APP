package listeners;

import io.appium.java_client.AppiumDriver;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import net.serenitybdd.core.Serenity;
import net.thucydides.core.model.DataTable;
import net.thucydides.core.model.Story;
import net.thucydides.core.model.TestOutcome;
import net.thucydides.core.steps.ExecutedStepDescription;
import net.thucydides.core.steps.StepFailure;
import net.thucydides.core.steps.StepListener;
import net.thucydides.core.webdriver.ThucydidesWebDriverSupport;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import utils.OllamaClient;
import utils.PageSourceParser;

/**
 * Listener de Serenity optimizado para análisis con Ollama
 *
 * <p>VERSIÓN MEJORADA CON: ✅ Análisis de similitud de textos ✅ Detección inteligente de
 * localizadores similares ✅ Sugerencias de código flexible
 *
 * <p>Compatible con: Serenity BDD 2.0.71, Cucumber API 2.x, Java 11
 */
public class OllamaStepListener implements StepListener {

  private static final boolean OLLAMA_ENABLED =
      Boolean.parseBoolean(System.getProperty("ollama.enabled", "true"));
  private static final String PAGE_SOURCE_DIR = "target/ollama/pagesources";
  private static final String REPORTS_DIR = "target/ollama/reports";

  private final OllamaClient ollamaClient;
  private boolean ollamaAvailable = false;
  private boolean alreadyAnalyzedCurrentTest = false;
  private String currentTestName = "";

  public OllamaStepListener() {
    System.out.println("[OLLAMA] Inicializando listener optimizado...");
    System.out.println(
        "[OLLAMA-DEBUG] ollama.enabled = "
            + System.getProperty("ollama.enabled", "false (default)"));

    this.ollamaClient = new OllamaClient();

    // Crear directorios necesarios
    try {
      Files.createDirectories(Paths.get(PAGE_SOURCE_DIR));
      Files.createDirectories(Paths.get(REPORTS_DIR));
    } catch (IOException e) {
      System.err.println("[OLLAMA] Error al crear directorios: " + e.getMessage());
    }

    // Verificar disponibilidad de Ollama
    if (OLLAMA_ENABLED) {
      this.ollamaAvailable = checkOllamaAvailability();

      if (ollamaAvailable) {
        System.out.println("✅ [OLLAMA] Disponible y listo");
        System.out.println("   - Solo se ejecutará en fallos de localizadores");
        System.out.println("   - Máximo una vez por test");
        System.out.println("   - Para deshabilitar: ollama.enabled=false");
      } else {
        System.out.println("⚠️ [OLLAMA] No disponible");
        System.out.println("   Para habilitar: ollama run phi3");
      }
    } else {
      System.out.println("ℹ️ [OLLAMA] Deshabilitado (ollama.enabled=false)");
    }
  }

  @Override
  public void stepFailed(StepFailure failure) {
    if (!OLLAMA_ENABLED || !ollamaAvailable) {
      return;
    }

    if (alreadyAnalyzedCurrentTest) {
      return;
    }

    if (!isLocatorError(failure)) {
      return;
    }

    alreadyAnalyzedCurrentTest = true;

    String stepName =
        (failure.getDescription() != null)
            ? failure.getDescription().getName()
            : "Paso desconocido";

    String errorMsg =
        (failure.getException() != null)
            ? failure.getException().getMessage()
            : "Sin mensaje de error";

    System.out.println("\n╔════════════════════════════════════════════════════╗");
    System.out.println("║  🔍 LOCALIZADOR NO ENCONTRADO - ANALIZANDO CON IA  ║");
    System.out.println("╚════════════════════════════════════════════════════╝");
    System.out.println("Test: " + currentTestName);
    System.out.println("Step: " + stepName);
    System.out.println("");

    analyzeLocatorFailure(stepName, errorMsg);
  }

  private boolean isLocatorError(StepFailure failure) {
    if (failure.getException() == null) {
      return false;
    }

    Throwable exception = failure.getException();
    String errorMessage =
        exception.getMessage() != null ? exception.getMessage().toLowerCase() : "";

    boolean isNoSuchElement = exception instanceof NoSuchElementException;
    boolean isLocatorInMessage =
        errorMessage.contains("unable to locate")
            || errorMessage.contains("no such element")
            || errorMessage.contains("element not found")
            || errorMessage.contains("could not find element");

    return isNoSuchElement || isLocatorInMessage;
  }

  /** ✅ MÉTODO PRINCIPAL - Analiza fallo de localizador con IA */
  private void analyzeLocatorFailure(String stepName, String errorMessage) {
    long startTime = System.currentTimeMillis();

    try {
      System.out.println("[OLLAMA] 1/5 Capturando page source...");

      String pageSource = capturePageSource();
      if (pageSource == null || pageSource.isEmpty()) {
        System.out.println("[OLLAMA] ⚠️  No se pudo capturar page source");
        return;
      }

      System.out.println("[OLLAMA] 2/5 Guardando XML (" + pageSource.length() + " caracteres)...");
      String xmlFileName = savePageSource(pageSource, stepName);

      System.out.println("[OLLAMA] 3/5 Extrayendo localizadores...");
      File xmlFile = new File(xmlFileName);
      List<String> availableLocators = PageSourceParser.extractRelevantLocators(xmlFile, 20);

      if (availableLocators.isEmpty()) {
        System.out.println("[OLLAMA] ⚠️  No se encontraron localizadores");
        return;
      }

      // ✅ NUEVO: Análisis de similitud ANTES de llamar a la IA
      System.out.println("[OLLAMA] 4/5 Analizando similitudes...");
      String searchedText = extractSearchedText(errorMessage);
      String similarityAnalysis = analyzeSimilarity(searchedText, availableLocators);

      System.out.println(similarityAnalysis); // Mostrar en consola

      System.out.println(
          "[OLLAMA] 5/5 Consultando a IA (" + availableLocators.size() + " localizadores)...");
      String prompt =
          buildLocatorFailurePrompt(stepName, errorMessage, availableLocators, searchedText);

      String analysis;
      try {
        String rawAnalysis = ollamaClient.ask(prompt);
        analysis = similarityAnalysis + "\n" + rawAnalysis;
      } catch (IOException e) {
        System.err.println("[OLLAMA] ⚠️  Timeout - mostrando análisis sin IA");
        analysis =
            similarityAnalysis + "\n" + generateFallbackAnalysis(availableLocators, errorMessage);
      }

      long duration = System.currentTimeMillis() - startTime;

      System.out.println("\n╔════════════════════════════════════════════════════╗");
      System.out.println("║           🤖 ANÁLISIS DE IA (OLLAMA)               ║");
      System.out.println("╚════════════════════════════════════════════════════╝");
      System.out.println(analysis);
      System.out.println("════════════════════════════════════════════════════");
      System.out.println("⏱️  Tiempo de análisis: " + (duration / 1000) + " segundos\n");

      attachAnalysisToSerenityReport(stepName, errorMessage, analysis);
      saveAnalysisReport(stepName, errorMessage, analysis);

    } catch (Exception e) {
      System.err.println("❌ [OLLAMA] Error: " + e.getMessage());
    }
  }

  /** ✅ NUEVO: Analiza similitud entre el texto buscado y los disponibles */
  private String analyzeSimilarity(String searchedText, List<String> availableLocators) {
    if (searchedText == null || searchedText.isEmpty()) {
      return "\n📋 No se pudo extraer el texto buscado para comparar\n";
    }

    StringBuilder analysis = new StringBuilder();
    analysis.append("\n🔍 ANÁLISIS DE SIMILITUD:\n");
    analysis.append("Texto buscado: \"").append(searchedText).append("\"\n\n");

    boolean foundSimilar = false;

    // Buscar localizadores con textos similares
    for (int i = 0; i < availableLocators.size(); i++) {
      String locator = availableLocators.get(i);

      if (locator.contains("text=")) {
        String locatorText = extractTextFromLocator(locator);

        if (!locatorText.isEmpty()) {
          int similarity = calculateSimilarity(searchedText, locatorText);

          if (similarity > 70) {
            analysis.append(
                String.format(
                    "   ✅ %d%% similar (localizador #%d): %s\n", similarity, i + 1, locator));
            foundSimilar = true;
          } else if (similarity > 50) {
            analysis.append(
                String.format(
                    "   ⚠️  %d%% similar (localizador #%d): %s\n", similarity, i + 1, locator));
            foundSimilar = true;
          }
        }
      }
    }

    if (!foundSimilar) {
      analysis.append("   ❌ No se encontraron textos similares (>50% similitud)\n");
    }

    return analysis.toString();
  }

  /** ✅ NUEVO: Extrae el texto de un localizador */
  private String extractTextFromLocator(String locator) {
    try {
      int start = locator.indexOf("text=\"");
      if (start == -1) return "";

      start += 6; // Saltar 'text="'
      int end = locator.indexOf("\"", start);
      if (end == -1) return "";

      return locator.substring(start, end);
    } catch (Exception e) {
      return "";
    }
  }

  /** ✅ NUEVO: Calcula similitud entre dos strings (0-100) */
  private int calculateSimilarity(String s1, String s2) {
    if (s1 == null || s2 == null) return 0;
    if (s1.isEmpty() || s2.isEmpty()) return 0;

    // Normalizar para comparación
    s1 = s1.toLowerCase().trim();
    s2 = s2.toLowerCase().trim();

    if (s1.equals(s2)) return 100;

    // Similitud basada en palabras comunes
    String[] words1 = s1.split("\\s+");
    String[] words2 = s2.split("\\s+");

    int commonWords = 0;
    for (String w1 : words1) {
      for (String w2 : words2) {
        if (w1.equalsIgnoreCase(w2)) {
          commonWords++;
          break;
        }
      }
    }

    int totalWords = Math.max(words1.length, words2.length);
    if (totalWords == 0) return 0;

    return (int) ((commonWords * 100.0) / totalWords);
  }

  /** ✅ MEJORADO: Construye prompt con análisis de similitud */
  private String buildLocatorFailurePrompt(
      String stepName, String errorMessage, List<String> availableLocators, String searchedText) {
    StringBuilder prompt = new StringBuilder();

    prompt.append("Eres un experto en automatización mobile con Appium.\n\n");

    // ✅ AGREGAR CONTEXTO DE SIMILITUD
    prompt.append(
        "⚠️ IMPORTANTE: El texto buscado puede tener variaciones pequeñas (fechas, números, versiones).\n");
    prompt.append("Busca localizadores SIMILARES incluso si no son 100% idénticos.\n\n");

    prompt.append("PROBLEMA:\n");
    String locatorInfo = extractLocatorFromError(errorMessage);
    prompt.append("Localizador que falló: ").append(locatorInfo).append("\n\n");

    if (searchedText != null && !searchedText.isEmpty()) {
      prompt.append("Texto específico buscado: \"").append(searchedText).append("\"\n");
      prompt.append("👉 Busca textos similares con fechas/números/versiones diferentes\n\n");
    }

    prompt.append("LOCALIZADORES DISPONIBLES EN LA PANTALLA:\n");

    int limit = Math.min(availableLocators.size(), 20);
    for (int i = 0; i < limit; i++) {
      prompt.append((i + 1)).append(". ").append(availableLocators.get(i)).append("\n");
    }

    prompt.append("\nTAREA (responde en máximo 10 líneas):\n");
    prompt.append("1. ¿Hay algún localizador SIMILAR al buscado?\n");
    prompt.append("2. Indica el número del localizador de la lista\n");
    prompt.append("3. Explica qué cambió (fecha, versión, número, etc)\n");
    prompt.append("4. Sugiere código Java con localizador flexible\n\n");

    prompt.append("EJEMPLO DE RESPUESTA IDEAL:\n");
    prompt.append("SIMILAR: Localizador #5 tiene 90% similitud\n");
    prompt.append("DIFERENCIA: Fecha cambió de 2025-11-10 a 2025-11-11\n");
    prompt.append("CÓDIGO:\n");
    prompt.append("new UiSelector().textMatches(\"Ver 1\\\\.4\\\\.9 .*\")\n");

    return prompt.toString();
  }

  /** ✅ NUEVO: Extrae el texto específico que se buscó del mensaje de error */
  private String extractSearchedText(String errorMessage) {
    try {
      // Buscar patrón: textContains("...")
      int start = errorMessage.indexOf("textContains(\"");
      if (start != -1) {
        start += 14;
        int end = errorMessage.indexOf("\")", start);
        if (end != -1) {
          return errorMessage.substring(start, end);
        }
      }

      // Buscar patrón: text("...")
      start = errorMessage.indexOf("text(\"");
      if (start != -1) {
        start += 6;
        int end = errorMessage.indexOf("\")", start);
        if (end != -1) {
          return errorMessage.substring(start, end);
        }
      }

      return null;
    } catch (Exception e) {
      return null;
    }
  }

  private String capturePageSource() {
    try {
      WebDriver driver = ThucydidesWebDriverSupport.getDriver();

      if (driver == null) {
        System.out.println("[OLLAMA-DEBUG] Driver es null");
        return null;
      }

      WebDriver unwrappedDriver = driver;

      if (driver.getClass().getName().contains("WebDriverFacade")) {
        try {
          java.lang.reflect.Field field = driver.getClass().getDeclaredField("proxiedWebDriver");
          field.setAccessible(true);
          unwrappedDriver = (WebDriver) field.get(driver);
          System.out.println(
              "[OLLAMA-DEBUG] Driver desenvuelto: " + unwrappedDriver.getClass().getSimpleName());
        } catch (Exception e) {
          System.out.println("[OLLAMA-DEBUG] No se pudo desenvolver: " + e.getMessage());
        }
      }

      if (!(unwrappedDriver instanceof AppiumDriver)) {
        System.out.println(
            "[OLLAMA-DEBUG] Driver no es AppiumDriver: "
                + unwrappedDriver.getClass().getSimpleName());
        return null;
      }

      String pageSource = unwrappedDriver.getPageSource();

      if (pageSource == null || pageSource.trim().isEmpty()) {
        System.out.println("[OLLAMA-DEBUG] Page source vacío");
        return null;
      }

      System.out.println("[OLLAMA-DEBUG] ✅ Page source OK: " + pageSource.length() + " caracteres");
      return pageSource;

    } catch (Exception e) {
      System.err.println("[OLLAMA-DEBUG] Error: " + e.getMessage());
      return null;
    }
  }

  private String savePageSource(String pageSource, String stepName) throws IOException {
    String sanitizedName = stepName.replaceAll("[^a-zA-Z0-9]", "_");
    String timestamp = String.valueOf(System.currentTimeMillis());
    String filename =
        String.format("%s/failure_%s_%s.xml", PAGE_SOURCE_DIR, sanitizedName, timestamp);

    Files.write(Paths.get(filename), pageSource.getBytes());
    System.out.println("[OLLAMA-DEBUG] XML guardado: " + filename);
    return filename;
  }

  private String extractLocatorFromError(String errorMessage) {
    if (errorMessage.contains("Element info:")) {
      int start = errorMessage.indexOf("Element info:");
      int end = errorMessage.indexOf("\n", start);
      if (end == -1) end = errorMessage.length();
      return errorMessage.substring(start, Math.min(end, start + 200));
    }
    return "No especificado";
  }

  /** ✅ MEJORADO: Análisis fallback cuando Ollama no responde */
  private String generateFallbackAnalysis(List<String> availableLocators, String errorMessage) {
    StringBuilder fallback = new StringBuilder();

    fallback.append("⚠️  Análisis de IA no disponible (timeout)\n\n");
    fallback.append("📋 LOCALIZADORES DISPONIBLES:\n\n");

    int limit = Math.min(availableLocators.size(), 10);
    for (int i = 0; i < limit; i++) {
      fallback
          .append("  ")
          .append(i + 1)
          .append(". ")
          .append(availableLocators.get(i))
          .append("\n");
    }

    fallback.append("\n💡 SUGERENCIA:\n");
    fallback.append("Revisa manualmente el XML en: ").append(PAGE_SOURCE_DIR).append("/\n");
    fallback.append("Busca textos similares al que intentaste localizar\n");

    return fallback.toString();
  }

  private void attachAnalysisToSerenityReport(
      String stepName, String errorMessage, String analysis) {
    StringBuilder report = new StringBuilder();
    report.append("## 🤖 Análisis de Localizador con IA\n\n");
    report.append("**Step**: ").append(stepName).append("\n\n");
    report.append("### Análisis:\n```\n");
    report.append(analysis);
    report.append("\n```\n");

    Serenity.recordReportData().withTitle("🔍 Análisis Ollama").andContents(report.toString());
  }

  private void saveAnalysisReport(String stepName, String errorMessage, String analysis) {
    try {
      String sanitizedName = stepName.replaceAll("[^a-zA-Z0-9]", "_");
      String timestamp = String.valueOf(System.currentTimeMillis());
      String filename =
          String.format("%s/analysis_%s_%s.md", REPORTS_DIR, sanitizedName, timestamp);

      StringBuilder report = new StringBuilder();
      report.append("# Análisis de Fallo\n\n");
      report.append("**Fecha**: ").append(new java.util.Date()).append("\n");
      report.append("**Step**: ").append(stepName).append("\n\n");
      report.append("## Análisis:\n\n").append(analysis).append("\n");

      Files.write(Paths.get(filename), report.toString().getBytes());
      System.out.println("[OLLAMA] 📄 Reporte: " + filename);

    } catch (IOException e) {
      System.err.println("[OLLAMA] Error al guardar reporte");
    }
  }

  private boolean checkOllamaAvailability() {
    try {
      String response = ollamaClient.ask("test");
      return response != null && !response.isEmpty();
    } catch (IOException e) {
      return false;
    }
  }

  // ===================================================================
  // Implementación de StepListener
  // ===================================================================

  @Override
  public void testSuiteStarted(Class<?> storyClass) {}

  @Override
  public void testSuiteStarted(Story story) {}

  @Override
  public void testSuiteFinished() {}

  @Override
  public void testStarted(String description, String id) {
    currentTestName = description;
    alreadyAnalyzedCurrentTest = false;
  }

  @Override
  public void testStarted(String description) {
    testStarted(description, "");
  }

  @Override
  public void testFinished(TestOutcome result) {}

  @Override
  public void testRetried() {}

  @Override
  public void stepStarted(ExecutedStepDescription description) {}

  @Override
  public void skippedStepStarted(ExecutedStepDescription description) {}

  @Override
  public void stepFinished() {}

  @Override
  public void stepIgnored() {}

  @Override
  public void stepPending() {}

  @Override
  public void stepPending(String message) {}

  @Override
  public void lastStepFailed(StepFailure stepFailure) {}

  @Override
  public void testFailed(TestOutcome testOutcome, Throwable cause) {}

  @Override
  public void testIgnored() {}

  @Override
  public void testSkipped() {}

  @Override
  public void testPending() {}

  @Override
  public void testIsManual() {}

  @Override
  public void notifyScreenChange() {}

  @Override
  public void useExamplesFrom(DataTable table) {}

  @Override
  public void addNewExamplesFrom(DataTable table) {}

  @Override
  public void exampleStarted(Map<String, String> data) {}

  @Override
  public void exampleFinished() {}

  @Override
  public void assumptionViolated(String message) {}

  @Override
  public void testRunFinished() {}
}
