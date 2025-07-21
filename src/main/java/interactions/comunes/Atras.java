package interactions.comunes;

import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Interaction;
import utils.AndroidObject;

import static net.serenitybdd.screenplay.Tasks.instrumented;

public class Atras extends AndroidObject implements Interaction {

    @Override
    @Step("Regresa atrás en la aplicacion.")
    public <T extends Actor> void performAs(T actor) {
        Atras(actor);
    }

    public static Interaction irAtras() {
        return instrumented(Atras.class);
    }
}
