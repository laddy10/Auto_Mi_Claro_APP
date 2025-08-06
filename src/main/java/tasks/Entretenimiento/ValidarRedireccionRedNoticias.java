/*package tasks.Entretenimiento;

import interactions.Click.ClickTextoQueContengaX;
import interactions.Scroll.ScrollHastaTexto;
import interactions.validations.ValidarTexto;
import interactions.validations.ValidarTextoQueContengaX;
import interactions.wait.WaitFor;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.waits.WaitUntil;
import utils.EvidenciaUtils;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isNotPresent;
import static userinterfaces.EntretenimientoPage.*;
import static utils.Constants.*;


  //Task para validar redirección RED + NOTICIAS

public class ValidarRedireccionRedNoticias implements Task {

    private static final String paso = "Validar Redirección RED + NOTICIAS";

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                ClickTextoQueContengaX.elTextoContiene(VER_DETALLE),
                WaitFor.aTime(3000),
                WaitUntil.the(LBL_ESPERA_UN_MOMENTO, isNotPresent()).forNoMoreThan(30).seconds(),
                WaitFor.aTime(5000)
        );

        EvidenciaUtils.registrarCaptura(paso);
    }

    public static Performable validar() {
        return instrumented(ValidarRedireccionRedNoticias.class);
    }
}Captura(paso);
    }

public static Performable acceder() {
    return instrumented(AccederEntretenimiento.class);
}
}
*/