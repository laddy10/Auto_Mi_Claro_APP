package interactions.wait;

import io.appium.java_client.MobileBy;
import java.util.Arrays;
import java.util.List;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Interaction;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class WaitForTextContains implements Interaction {

  private final List<String> partialTexts;
  private final int timeout;
  private static final int DEFAULT_TIMEOUT = 60;

  public WaitForTextContains(List<String> partialTexts, int timeout) {
    this.partialTexts = partialTexts;
    this.timeout = timeout;
  }

  @Override
  public <T extends Actor> void performAs(T actor) {
    long startTime = System.currentTimeMillis();
    boolean found = false;

    while ((System.currentTimeMillis() - startTime) < timeout * 1000 && !found) {
      try {
        for (String partialText : partialTexts) {
          // 1. Búsqueda por textContains en UiAutomator
          String uiAutomatorQuery =
              String.format("new UiSelector().textContains(\"%s\")", partialText);
          List<WebElement> elements =
              BrowseTheWeb.as(actor)
                  .getDriver()
                  .findElements(MobileBy.AndroidUIAutomator(uiAutomatorQuery));

          if (!elements.isEmpty()) {
            found = true;
            break;
          }

          // 2. Búsqueda por descriptionContains en UiAutomator (común en WebViews y Custom Tabs)
          try {
            String uiDescQuery =
                String.format("new UiSelector().descriptionContains(\"%s\")", partialText);
            elements =
                BrowseTheWeb.as(actor)
                    .getDriver()
                    .findElements(MobileBy.AndroidUIAutomator(uiDescQuery));
            if (!elements.isEmpty()) {
              found = true;
              break;
            }
          } catch (Exception ignored) {
          }

          // 3. Búsqueda por XPath (@text o @content-desc)
          try {
            elements =
                BrowseTheWeb.as(actor)
                    .getDriver()
                    .findElements(
                        By.xpath(
                            "//*[contains(@text, '"
                                + partialText
                                + "') or contains(@content-desc, '"
                                + partialText
                                + "')]"));
            if (!elements.isEmpty()) {
              found = true;
              break;
            }
          } catch (Exception ignored) {
          }
        }

        if (!found) {
          Thread.sleep(500);
        }
      } catch (Exception ignored) {
        // Continúa intentando
      }
    }

    if (!found) {
      throw new RuntimeException(
          String.format(
              "No se encontró ningún texto que contenga alguno de estos valores: %s en el tiempo dado (%d segundos)",
              partialTexts, timeout));
    }
  }

  // Método para un solo texto con timeout personalizado
  public static WaitForTextContains withTextContains(String partialText, int timeoutSeconds) {
    return new WaitForTextContains(Arrays.asList(partialText), timeoutSeconds);
  }

  // Método para un solo texto con timeout por defecto
  public static WaitForTextContains withTextContains(String partialText) {
    return new WaitForTextContains(Arrays.asList(partialText), DEFAULT_TIMEOUT);
  }

  // Método para múltiples textos con timeout personalizado
  public static WaitForTextContains withAnyTextContains(
      List<String> partialTexts, int timeoutSeconds) {
    return new WaitForTextContains(partialTexts, timeoutSeconds);
  }

  // Método para múltiples textos con timeout por defecto
  public static WaitForTextContains withAnyTextContains(List<String> partialTexts) {
    return new WaitForTextContains(partialTexts, DEFAULT_TIMEOUT);
  }

  // Método para múltiples textos como parámetros variables con timeout por defecto
  public static WaitForTextContains withAnyTextContains(String... partialTexts) {
    return new WaitForTextContains(Arrays.asList(partialTexts), DEFAULT_TIMEOUT);
  }

  // Método para múltiples textos como parámetros variables con timeout personalizado
  public static WaitForTextContains withAnyTextContains(
      int timeoutSeconds, String... partialTexts) {
    return new WaitForTextContains(Arrays.asList(partialTexts), timeoutSeconds);
  }
}
