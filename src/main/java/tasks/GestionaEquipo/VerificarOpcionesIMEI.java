package tasks.GestionaEquipo;

import interactions.validations.ValidarTexto;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import utils.EvidenciaUtils;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static utils.Constants.*;

public class VerificarOpcionesIMEI implements Task {

    private static final String paso = "Verificar Opciones IMEI Duplicado";

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                ValidarTexto.validarTexto(REGISTRAR_LINEA_IMEI),
                ValidarTexto.validarTexto(GESTIONAR_LINEAS_IMEI)
        );

        EvidenciaUtils.registrarCaptura(paso);
    }

    public static Performable verificar() {
        return instrumented(VerificarOpcionesIMEI.class);
    }
}