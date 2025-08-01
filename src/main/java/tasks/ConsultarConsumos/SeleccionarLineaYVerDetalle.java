package tasks.ConsultarConsumos;


import interactions.Click.ClickTextoQueContengaX;
import interactions.Scroll.ScrollHastaTexto;
import interactions.wait.WaitFor;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.waits.WaitUntil;
import utils.EvidenciaUtils;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isNotPresent;
import static userinterfaces.ConsultarConsumosPage.*;
import static utils.Constants.*;

public class SeleccionarLineaYVerDetalle implements Task {

    private static final String paso = "Seleccionar Línea y Ver Detalle";

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                ScrollHastaTexto.conTexto(LINEA_310_263_2840),
                ClickTextoQueContengaX.elTextoContiene(VER_DETALLE),
                WaitUntil.the(LBL_ESPERA_UN_MOMENTO, isNotPresent()).forNoMoreThan(30).seconds(),
                WaitFor.aTime(3000)
        );

        EvidenciaUtils.registrarCaptura(paso);
    }

    public static Performable seleccionar() {
        return instrumented(SeleccionarLineaYVerDetalle.class);
    }
}