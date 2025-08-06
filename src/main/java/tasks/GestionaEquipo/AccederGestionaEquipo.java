package tasks.GestionaEquipo;

import interactions.Click.ClickTextoQueContengaX;
import interactions.Scroll.ScrollHastaTexto;
import interactions.validations.ValidarTexto;
import interactions.wait.WaitFor;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.waits.WaitUntil;
import utils.EvidenciaUtils;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isNotPresent;
import static userinterfaces.GestionaEquipoPage.*;
import static utils.Constants.*;

/**
 * Task para acceder al módulo de Gestiona tu Equipo
 */
public class AccederGestionaEquipo implements Task {

    private static final String paso = "Acceder a Gestiona tu Equipo";

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                ScrollHastaTexto.conTexto(PAGOS_Y_CONSULTAS),
                ClickTextoQueContengaX.elTextoContiene(VER_MAS),
                ScrollHastaTexto.conTexto(GESTIONA_TU_EQUIPO),
                ClickTextoQueContengaX.elTextoContiene(GESTIONA_TU_EQUIPO),
                WaitFor.aTime(3000),
                ValidarTexto.validarTexto(ELIGE_NUMERO_CUENTA_LINEA),
                ValidarTexto.validarTexto(POSTPAGO),
                ClickTextoQueContengaX.elTextoContiene(VER_DETALLE),
                WaitUntil.the(LBL_ESPERA_UN_MOMENTO, isNotPresent()).forNoMoreThan(30).seconds()
        );

        EvidenciaUtils.registrarCaptura(paso);
    }

    public static Performable acceder() {
        return instrumented(AccederGestionaEquipo.class);
    }
}