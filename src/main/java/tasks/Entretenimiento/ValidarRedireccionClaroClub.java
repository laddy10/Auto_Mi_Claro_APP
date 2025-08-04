package tasks.Entretenimiento;


import interactions.Click.ClickTextoQueContengaX;
import interactions.validations.ValidarTexto;
import interactions.validations.ValidarTextoQueContengaX;
import interactions.wait.WaitFor;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import utils.EvidenciaUtils;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static userinterfaces.EntretenimientoPage.BTN_CLOSE;
import static userinterfaces.LoginPage.BTN_ACEPTAR;
import static utils.Constants.*;
import static utils.Constants.ACEPTAR_2;
import static utils.Constants.CONTINUAR;

public class ValidarRedireccionClaroClub implements Task {
    private static final String paso = "Validar redireccion Claro Club";

    @Override
    public <T extends Actor> void performAs(T actor) {

        actor.attemptsTo(
                WaitFor.aTime(4000),
                Click.on(BTN_CLOSE),
                ValidarTextoQueContengaX.elTextoContiene("Categorías")
        );
        EvidenciaUtils.registrarCaptura(paso);
    }

    public static Performable validar() {
        return instrumented(ValidarRedireccionClaroClub.class);
    }
}
