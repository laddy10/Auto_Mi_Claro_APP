package tasks.PagosYConsultas.DetalleDeTuPlan;

import interactions.Click.ClickTextoQueContengaX;
import interactions.validations.ValidarTexto;
import interactions.validations.ValidarTextoQueContengaX;
import interactions.wait.WaitFor;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import utils.EvidenciaUtils;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static utils.Constants.*;

public class AdministrarAplicacionesIncluidasPermitido implements Task {

    private static final String NUMERO_LINEA = "310 263 3858";
    private static final String paso1 = "Ingresar Administra las aplicaciones incluidas";
    private static final String paso2 = "Validar direccionamiento administrar aplicaciones";

    @Override
    public <T extends Actor> void performAs(T actor) {

        // ADMINISTRA LAS APLICACIONES INCLUIDAS
        EvidenciaUtils.registrarCaptura(paso1);

        actor.attemptsTo(
                ClickTextoQueContengaX.elTextoContiene(ADMINISTRA_LAS_APLICACIONES_INCLUIDAS),
                WaitFor.aTime(2000)
        );

        // VALIDAR DIRECCIONAMIENTO - Plan que permite
        EvidenciaUtils.registrarCaptura(paso2);

        actor.attemptsTo(
                ValidarTexto.validarTexto(APLICACIONES_ELEGIBLES),
                ValidarTextoQueContengaX.elTextoContiene(NUMERO_LINEA),
                ValidarTexto.validarTexto(APLICACIONES_GRATIS_YA_INSTALADAS),
                ValidarTexto.validarTexto(APLICACIONES_QUE_PUEDES_INSCRIBIR)
        );
    }

    public static Performable administrarYValidar() {
        return instrumented(AdministrarAplicacionesIncluidasPermitido.class);
    }
}