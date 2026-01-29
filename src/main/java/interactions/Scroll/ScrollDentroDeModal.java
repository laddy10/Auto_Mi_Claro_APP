package interactions.Scroll;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import java.time.Duration;
import java.util.Collections;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Interaction;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import org.openqa.selenium.Rectangle;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;

public class ScrollDentroDeModal implements Interaction {

  private final WebElement contenedor;
  private final int intentos;

  private ScrollDentroDeModal(WebElement contenedor, int intentos) {
    this.contenedor = contenedor;
    this.intentos = intentos;
  }

  public static ScrollDentroDeModal en(WebElement contenedor, int intentos) {
    return new ScrollDentroDeModal(contenedor, intentos);
  }

  @Override
  public <T extends Actor> void performAs(T actor) {

    WebDriver driver = BrowseTheWeb.as(actor).getDriver();

    if (!(driver instanceof AppiumDriver)) {
      return;
    }

    AppiumDriver<?> appium = (AppiumDriver<?>) driver;
    Rectangle rect = contenedor.getRect();

    int startX = rect.getX() + rect.getWidth() / 2;
    int startY = rect.getY() + (int) (rect.getHeight() * 0.75);
    int endY = rect.getY() + (int) (rect.getHeight() * 0.25);

    // 🟢 Intento 1: W3C PointerInput (preferido)
    try {
      PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
      Sequence swipe = new Sequence(finger, 1);

      swipe.addAction(
          finger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), startX, startY));
      swipe.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
      swipe.addAction(
          finger.createPointerMove(
              Duration.ofMillis(600), PointerInput.Origin.viewport(), startX, endY));
      swipe.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

      appium.perform(Collections.singletonList(swipe));

    } catch (Exception w3cEx) {

      // 🟡 Fallback TouchAction
      try {
        new TouchAction<>(appium)
            .press(PointOption.point(startX, startY))
            .waitAction(WaitOptions.waitOptions(Duration.ofMillis(600)))
            .moveTo(PointOption.point(startX, endY))
            .release()
            .perform();
      } catch (Exception ignored) {
        // No rompemos el flujo
      }
    }

    try {
      Thread.sleep(500);
    } catch (InterruptedException ignored) {
    }
  }
}
