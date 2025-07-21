package tasks.PagosYConsultas;

import interactions.Click.ClickTextoQueContengaX;
import interactions.Scroll.ScrollHastaTexto;
import interactions.validations.ValidarTexto;
import interactions.validations.ValidarTextoQueContengaX;
import interactions.wait.WaitForResponse;
import models.User;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import utils.EvidenciaUtils;
import utils.TestDataProvider;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static utils.Constants.*;

public class PagosAutomaticos implements Task {
    private final User user = TestDataProvider.getRealUser();
    private static final String paso1 = "Seleccionar Pagos automáticos";
    private static final String paso2 = "Validar direccionamiento Pagos automáticos";
    private static final String paso3 = "Validar cuentas Claro móvil asociadas";
    private static final String paso4 = "Ingresar a Ver historial ";
    private static final String paso5 = "Validar redirección Ver historial";

    @Override
    public <T extends Actor> void performAs(T actor) {

        EvidenciaUtils.registrarCaptura(paso1);

        // Seleccionar "Pagos automáticos"
        actor.attemptsTo(
                ClickTextoQueContengaX.elTextoContiene(PAGOS_AUTOMATICOS),
                WaitForResponse.withText(SELECCIONA_QUE_SERVICIO_DESEAS_INSCRIBIR)
        );

        EvidenciaUtils.registrarCaptura(paso2);

        // Validar mensaje de inscripción y cuentas activas
        actor.attemptsTo(
                ValidarTextoQueContengaX.elTextoContiene(SELECCIONA_QUE_SERVICIO_DESEAS_INSCRIBIR),
                ValidarTextoQueContengaX.elTextoContiene(VER_HISTORIAL),
                ValidarTextoQueContengaX.elTextoContiene(SELECCIONAR_TODAS),
                ScrollHastaTexto.conTexto(user.getNumero().replace(" ", "")),
                ValidarTextoQueContengaX.elTextoContiene(user.getNumero().replace(" ", ""))
        );

        EvidenciaUtils.registrarCaptura(paso3);


        // Validar redirección Ver historial
        actor.attemptsTo(
                ScrollHastaTexto.conTexto(VER_HISTORIAL));

        EvidenciaUtils.registrarCaptura(paso4);

        actor.attemptsTo(
                ClickTextoQueContengaX.elTextoContiene(VER_HISTORIAL),
                WaitForResponse.withText(HISTORIAL_DE_PAGOS),
                ValidarTexto.validarTexto(HISTORIAL_DE_PAGOS),
                ValidarTextoQueContengaX.elTextoContiene(PAGOS_REALIZADOS),
                ValidarTextoQueContengaX.elTextoContiene(TARJETAS_INSCRITAS)
        );

        EvidenciaUtils.registrarCaptura(paso5);
    }

    public static Performable validarPagosAutomaticos() {
        return instrumented(PagosAutomaticos.class);
    }
}