package exceptions;

import static userinterfaces.LoginPage.TXT_NO_PERMITIR;
import static utils.AndroidObject.androidDriver;

import interactions.wait.WaitFor;
import io.appium.java_client.AppiumBy;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.questions.Presence;

public class Excepciones {

  public void ExClickElTextoContiene(Actor actor, String text) {
    if (Presence.of(TXT_NO_PERMITIR).answeredBy(actor)) {
      androidDriver(actor)
          .findElement(AppiumBy.androidUIAutomator("new UiSelector().text(\"No permitir\")"))
          .click();
      actor.attemptsTo(WaitFor.aTime(1000));
    }
    androidDriver(actor)
        .findElement(AppiumBy.androidUIAutomator("new UiSelector().textContains(\"" + text + "\")"))
        .click();
  }
}
