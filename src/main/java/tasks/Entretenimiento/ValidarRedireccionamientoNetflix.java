package tasks.Entretenimiento;

import interactions.Click.ClickTextoQueContengaX;
import interactions.Scroll.ScrollHastaTexto;
import interactions.validations.ValidarTextoQueContengaX;
import interactions.wait.WaitFor;
import models.User;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.waits.WaitUntil;
import utils.AndroidObject;
import utils.EvidenciaUtils;
import utils.TestDataProvider;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isNotPresent;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isVisible;
import static userinterfaces.EntretenimientoPage.*;
import static userinterfaces.PagosYConsultasPage.BTN_TRES_PUNTOS_MAS;
import static utils.Constants.*;

public class ValidarRedireccionamientoNetflix implements Task {

    private static final User user = TestDataProvider.getRealUser();
    private static final String paso = "Esperar desaparición del texto 'Espera un momento'";
    private static final String paso2 = "Ingresar al menú de tres puntos y seleccionar 'Acerca de'";
    private static final String paso3 = "Validar versión de mini app Netflix";
    private static final String paso4 = "Seleccionar la línea postpago Y Hacer scroll a la línea del usuario y ver detalle";

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                WaitUntil.the(LBL_ESPERA_UN_MOMENTO, isNotPresent()).forNoMoreThan(30).seconds()

        );
        EvidenciaUtils.registrarCaptura(paso);
        //**********************************************************************************
        actor.attemptsTo(
                Click.on(BTN_TRES_PUNTOS_MAS)
        );
        actor.attemptsTo(
                ClickTextoQueContengaX.elTextoContiene("Acerca de"),
                WaitFor.aTime(5000)
        );
        EvidenciaUtils.registrarCaptura(paso2);
        //**********************************************************************************
        actor.attemptsTo(
                ValidarTextoQueContengaX.elTextoContiene("Ver")
        );
        EvidenciaUtils.registrarCaptura(paso3);
        //**********************************************************************************
        actor.attemptsTo(
                Click.on(BTN_VOLVER)
        );
        //**********************************************************************************
        actor.attemptsTo(
                ClickTextoQueContengaX.elTextoContiene(POSTPAGO)
        );
        AndroidObject.scrollCorto2(actor, LINEA + " " + user.getNumero() + " " + VER_DETALLE);
        actor.attemptsTo(
                ClickTextoQueContengaX.elTextoContiene(user.getNumero())
        );
        EvidenciaUtils.registrarCaptura(paso4);
    }

    public static Performable validar() {
        return instrumented(ValidarRedireccionamientoNetflix.class);
    }
}
