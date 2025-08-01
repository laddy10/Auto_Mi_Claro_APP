package tasks.PagosYConsultas.DetalleDeTuPlan;

import interactions.Click.ClickTextoQueContengaX;
import interactions.validations.ValidarTexto;
import interactions.validations.ValidarTextoQueContengaX;
import interactions.wait.WaitFor;
import interactions.wait.WaitForResponse;
import models.User;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import utils.EvidenciaUtils;
import utils.TestDataProvider;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static userinterfaces.PagosYConsultasPage.CHECK_TIKTOK;
import static utils.Constants.*;

public class ComprarAplicaciones implements Task {

    private static final User user = TestDataProvider.getRealUser();
    private static final String paso1 = "Ingresar a Comprar aplicaciones";
    private static final String paso2 = "Validar direccionamiento Comprar aplicaciones";
    private static final String paso3 = "Seleccionar aplicación y guardar";
    private static final String paso4 = "Direccionamiento exitoso";

    @Override
    public <T extends Actor> void performAs(T actor) {

        // COMPRAR APLICACIONES
        EvidenciaUtils.registrarCaptura(paso1);

        actor.attemptsTo(
                ClickTextoQueContengaX.elTextoContiene(COMPRAR_APLICACIONES),
                WaitForResponse.withText(APLICACIONES_ADICIONALES)
        );

        // VALIDAR DIRECCIONAMIENTO COMPRAR APLICACIONES

        actor.attemptsTo(
                ValidarTexto.validarTexto(APLICACIONES_ELEGIBLES),
                ValidarTextoQueContengaX.elTextoContiene(user.getNumero()),
                ValidarTexto.validarTexto(APLICACIONES_ADICIONALES),
                ValidarTexto.validarTexto(GUARDAR)
        );

        EvidenciaUtils.registrarCaptura(paso2);

        actor.attemptsTo(
                Click.on(CHECK_TIKTOK),
                WaitFor.aTime(1000));

        EvidenciaUtils.registrarCaptura(paso3);


        actor.attemptsTo(
                ClickTextoQueContengaX.elTextoContiene(GUARDAR),
                ValidarTexto.validarTexto(user.getNumero()),
                ValidarTextoQueContengaX.elTextoContiene("Aceptar"),
                ValidarTextoQueContengaX.elTextoContiene(ACTIVA_APPS_ADICIONALES),
                ValidarTextoQueContengaX.elTextoContiene(LABEL_COSTO_ADICIONAL),
                ValidarTextoQueContengaX.elTextoContiene(LABEL_CARGO_FACTURA)
        );

        EvidenciaUtils.registrarCaptura(paso4
        );

    }

    public static Performable ingresarYValidar() {
        return instrumented(ComprarAplicaciones.class);
    }
}