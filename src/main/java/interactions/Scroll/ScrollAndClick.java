package interactions.Scroll;

import static net.serenitybdd.screenplay.Tasks.instrumented;

import io.appium.java_client.MobileBy;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Interaction;
import net.thucydides.core.annotations.Step;
import utils.AndroidObject;

public class ScrollAndClick extends AndroidObject implements Interaction {

  private final String text;

  public ScrollAndClick(String text) {
    this.text = text;
  }

  @Override
  @Step("Scroll y click en el texto: #text")
  public <T extends Actor> void performAs(T actor) {
    try {
      androidDriver(actor)
          .findElement(
              new MobileBy.ByAndroidUIAutomator(
                  "new UiScrollable(new UiSelector().scrollable(true)).scrollIntoView("
                      + "new UiSelector().textContains(\""
                      + text
                      + "\"));"))
          .click();
    } catch (Exception e) {
      ClickElTextoContiene(actor, text);
    }
  }

  public static ScrollAndClick scrollAndClick(String text) {
    return instrumented(ScrollAndClick.class, text);
  }
}
