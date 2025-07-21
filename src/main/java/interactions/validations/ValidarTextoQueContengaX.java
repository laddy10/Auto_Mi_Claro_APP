package interactions.validations;

import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Interaction;
import utils.AndroidObject;

import static net.serenitybdd.screenplay.Tasks.instrumented;

public class ValidarTextoQueContengaX extends AndroidObject implements Interaction {

    private String text;

    public ValidarTextoQueContengaX(String text) {
        this.text = text;
    }

    @Override
    @Step("Valida que el texto '#text' este contenido en otro texto visible.")
    public <T extends Actor> void performAs(T actor) {
        ElTextoContiene(actor, text);
    }

    public static Interaction elTextoContiene(String text) {
        return instrumented(ValidarTextoQueContengaX.class, text);
    }
}