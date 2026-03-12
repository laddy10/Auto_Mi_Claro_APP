package interactions.Click;

import static net.serenitybdd.screenplay.Tasks.instrumented;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import java.time.Duration;
import java.util.Collections;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Interaction;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import utils.AndroidObject;

public class ClickTextoRobusto implements Interaction {

  private final String texto;
  private final boolean clickDerecha;

  public ClickTextoRobusto(String texto, boolean clickDerecha) {
    this.texto = texto;
    this.clickDerecha = clickDerecha;
  }

  /** Click en el centro del elemento encontrado por texto. */
  public static ClickTextoRobusto en(String texto) {
    return instrumented(ClickTextoRobusto.class, texto, false);
  }

  /**
   * Click en el borde derecho (90%) del elemento encontrado por texto. Útil para listas donde la
   * acción está a la derecha (flechas, "Ver más", etc).
   */
  public static ClickTextoRobusto enLaDerecha(String texto) {
    return instrumented(ClickTextoRobusto.class, texto, true);
  }

  @Override
  public <T extends Actor> void performAs(T actor) {
    AppiumDriver<?> driver = AndroidObject.androidDriver(actor);

    WebElement elemento = null;
    try {
      // Busca elemento que contenga el texto
      elemento =
          driver.findElement(
              new MobileBy.ByAndroidUIAutomator(
                  "new UiSelector().textContains(\"" + texto + "\")"));
    } catch (Exception e) {
      System.out.println("❌ Elemento con texto '" + texto + "' no encontrado.");
      return;
    }

    if (elemento != null) {
      String zona = clickDerecha ? "DERECHA (90%)" : "CENTRO";
      System.out.println("👆 Forzando TAP en " + zona + " de: " + texto);
      clickEnCoordenadas(driver, elemento);
    }
  }

  private void clickEnCoordenadas(AppiumDriver<?> driver, WebElement elemento) {
    try {
      Point location = elemento.getLocation();
      Dimension size = elemento.getSize();

      // X: Si es derecha, usa el 90% del ancho. Si no, el 50%.
      int xOffset = clickDerecha ? (int) (size.getWidth() * 0.90) : (size.getWidth() / 2);
      int centerX = location.getX() + xOffset;

      // Y: Siempre el centro vertical
      int centerY = location.getY() + (size.getHeight() / 2);

      System.out.println("📍 Coordenadas: X=" + centerX + ", Y=" + centerY);

      PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
      Sequence tap = new Sequence(finger, 1);
      tap.addAction(
          finger.createPointerMove(
              Duration.ZERO, PointerInput.Origin.viewport(), centerX, centerY));
      tap.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
      tap.addAction(
          finger.createPointerMove(
              Duration.ofMillis(150), PointerInput.Origin.viewport(), centerX, centerY));
      tap.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

      driver.perform(Collections.singletonList(tap));
      System.out.println("✅ Tap ejecutado.");

    } catch (Exception e) {
      System.out.println("❌ Falló click coordenadas: " + e.getMessage());
    }
  }
}
