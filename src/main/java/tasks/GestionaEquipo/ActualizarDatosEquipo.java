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

public class ActualizarDatosEquipo implements Task {

    private static final String paso = "Actualizar Datos de Equipo";

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                ClickTextoQueContengaX.elTextoContiene(ACTUALIZAR_DATOS_EQUIPO),
                WaitFor.aTime(2000),
                ValidarTexto.validarTexto(ACTUALIZAR_DATOS_EQUIPO_TITULO)
        );

        EvidenciaUtils.registrarCaptura(paso);
    }

    public static Performable actualizar() {
        return instrumented(ActualizarDatosEquipo.class);
    }
}