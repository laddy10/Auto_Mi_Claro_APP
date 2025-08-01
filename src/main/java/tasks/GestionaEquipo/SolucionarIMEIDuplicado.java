package tasks.GestionaEquipo;

import interactions.Click.ClickTextoQueContengaX;
import interactions.validations.ValidarTexto;
import interactions.wait.WaitFor;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import utils.EvidenciaUtils;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static utils.Constants.*;

public class SolucionarIMEIDuplicado implements Task {

    private static final String paso = "Solucionar IMEI Duplicado";

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                ClickTextoQueContengaX.elTextoContiene(SOLUCIONAR_EQUIPO_IMEI_DUPLICADO),
                WaitFor.aTime(2000),
                ValidarTexto.validarTexto(SOLUCIONAR_EQUIPO_IMEI_DUPLICADO_TITULO),
                ValidarTexto.validarTexto(REGISTRAR_LINEA_IMEI),
                ValidarTexto.validarTexto(GESTIONAR_LINEAS_IMEI)
        );

        EvidenciaUtils.registrarCaptura(paso);
    }

    public static Performable solucionar() {
        return instrumented(SolucionarIMEIDuplicado.class);
    }
}
