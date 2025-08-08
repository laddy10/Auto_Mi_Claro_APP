package tasks.Prepago.RecargasyPaquetes;

import interactions.Click.ClickTextoQueContengaX;
import interactions.comunes.Atras;
import interactions.validations.ValidarTexto;
import interactions.validations.ValidarTextoQueContengaX;
import interactions.wait.WaitForResponse;
import models.User;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import utils.EvidenciaUtils;
import utils.TestDataProvider;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static userinterfaces.PagosYConsultasPage.BTN_PAGAR;
import static userinterfaces.PagosYConsultasPage.CHECK_ACEPTAR_TERMINOS_CONDICIONES2;
import static utils.Constants.*;
import static utils.Constants.OPERACION_EXITOSA;

public class ResumenCompra implements Task {

    private static final User user = TestDataProvider.getRealUser();
    private static final String paso1 = "Validar resumen de la compra";
    private static final String paso2 = "Ingresar y validar términos y condiciones";
    private static final String paso3 = "Aceptar términos y condiciones";

    @Override
    public <T extends Actor> void performAs(T actor) {

        // VALIDAR RESUMEN DE LA COMPRA
        EvidenciaUtils.registrarCaptura(paso1);

        actor.attemptsTo(
                ValidarTexto.validarTexto(RESUMEN_COMPRA),
                ValidarTexto.validarTexto(VIGENCIA_LABEL),
                ValidarTexto.validarTexto(user.getDuracionPaqueteArmar()),
                ValidarTextoQueContengaX.elTextoContiene(DATOS),
                ValidarTexto.validarTexto(user.getCantidadDatosArmar()),
                ValidarTexto.validarTexto(MIN_ILIMITADOS)
        );

        // INGRESAR A TÉRMINOS Y CONDICIONES
        actor.attemptsTo(
                ClickTextoQueContengaX.elTextoContiene(TERMINOS_Y_CONDICIONES_RECARGAS),
                WaitForResponse.withText(TERMINOS_CONDICIONES_MI_CLARO)
        );

        EvidenciaUtils.registrarCaptura(paso2);

        actor.attemptsTo(
                ValidarTexto.validarTexto(TERMINOS_CONDICIONES_MI_CLARO),
                ValidarTextoQueContengaX.elTextoContiene(COMPRA_DE_PAQUETES_Y_RECARGAS),
                ValidarTextoQueContengaX.elTextoContiene(LABEL_PAQUETES),
                ValidarTextoQueContengaX.elTextoContiene(LABEL_RECARGAS)
        );

        // REGRESAR Y PROCEDER AL PAGO
        actor.attemptsTo(
                Atras.irAtras(),
                WaitForResponse.withText(RESUMEN_COMPRA),
                Click.on(CHECK_ACEPTAR_TERMINOS_CONDICIONES2)
        );

        EvidenciaUtils.registrarCaptura(paso3);

        actor.attemptsTo(
                Click.on(BTN_PAGAR),
                WaitForResponse.withAnyText(PORTAL_PAGOS_CLARO, OPERACION_EXITOSA,CODIGO_SEGURIDAD_SMS)
        );
    }

    public static Performable validar() {
        return instrumented(ResumenCompra.class);
    }
}