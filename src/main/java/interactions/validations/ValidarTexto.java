package interactions.validations;

import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Interaction;
import utils.AndroidObject;

import static net.serenitybdd.screenplay.Tasks.instrumented;

public class ValidarTexto extends AndroidObject implements Interaction {

  private String text;

  public ValidarTexto(String text) {
    this.text = text;
  }

  @Override
  @Step("Valida que el texto '#text' este presente en pantalla")
  public <T extends Actor> void performAs(T actor) {
    ValidarTexto(actor, text);
  }

  public static Interaction validarTexto(String text) {
    return instrumented(ValidarTexto.class, text);
  }
}
