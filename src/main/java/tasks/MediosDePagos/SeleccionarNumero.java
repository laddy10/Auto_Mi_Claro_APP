package tasks.MediosDePagos;

import interactions.Click.ClickTextoQueContengaX;
import interactions.Click.TocarLineaWebView;
import interactions.comunes.Atras;
import interactions.validations.ValidarTexto;
import interactions.validations.ValidarTextoQueContengaX;
import interactions.validations.VerificarVersionModulo;
import interactions.wait.WaitFor;
import interactions.wait.WaitForResponse;
import models.User;
import net.serenitybdd.core.pages.WebElementFacade;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import utils.AndroidObject;
import utils.EvidenciaUtils;
import utils.TestDataProvider;

import java.util.List;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static userinterfaces.PagosYConsultasPage.*;
import static utils.Constants.*;
import static utils.ConstantsMiniVersiones.Versiones.MINI_VERSION_PAGA_TU_FACTURA_CONSTANT;

public class SeleccionarNumero implements Task {
    private static final User user = TestDataProvider.getRealUser();
    private static final String paso = "Ingreso a Paga y consulta tus facturas";
    private static final String paso2 = "Seleccionar la linea " + user.getNumeroPagaTuFactura();
    private static final String paso3 = "Validar Mini Versión";
    private static final String paso4 = "Validar datos de pago: Referencia, fechas y valor";

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                ClickTextoQueContengaX.elTextoContiene(PAGA_TU_FACTURA),
                WaitForResponse.withText(POSTPAGO));

        EvidenciaUtils.registrarCaptura(paso);

        actor.attemptsTo(ClickTextoQueContengaX.elTextoContiene(POSTPAGO));

        TocarLineaWebView.desplazarSinInercia(actor, LINEA + " " + user.getNumeroPagaTuFactura());

        EvidenciaUtils.registrarCaptura(paso2);

        actor.attemptsTo(
                TocarLineaWebView.conTexto(LINEA + " " + user.getNumeroPagaTuFactura()),
                WaitForResponse.withText(PAGAR_FACTURA));

        TocarLineaWebView.verificarLineaAbierta(actor, user.getNumeroPagaTuFactura());

        actor.attemptsTo(
                Click.on(BTN_TRES_PUNTOS_MAS),
                ClickTextoQueContengaX.elTextoContiene(ACERCA_DE),
                WaitFor.aTime(1000),
                ValidarTexto.validarTexto(PAGA_TU_FACTURA),
                ValidarTexto.validarTexto(DECLARACION_SERVICIO),
                ValidarTextoQueContengaX.elTextoContiene(VER));

        EvidenciaUtils.registrarCaptura(paso3);

        actor.attemptsTo(
                VerificarVersionModulo.conLaEsperada(MINI_VERSION_PAGA_TU_FACTURA_CONSTANT),
                Atras.irAtras(),
                WaitForResponse.withText(PAGAR_FACTURA),
                ValidarTextoQueContengaX.elTextoContiene(REFERENCIA_PAGO),
                ValidarTextoQueContengaX.elTextoContiene(FECHA_DE_CARGA),
                ValidarTextoQueContengaX.elTextoContiene(VALOR_A_PAGAR));

        EvidenciaUtils.registrarCaptura(paso4);

        List<WebElementFacade> lblfechapagooportuno = LBL_FECHA_PAGO_OPORTUNO.resolveAllFor(actor);
        if (!lblfechapagooportuno.isEmpty()) {
            actor.attemptsTo(ValidarTextoQueContengaX.elTextoContiene(FECHA_PAGO_OPORTUNO));
        }
    }

    public static Performable seleccionarNumero() {
        return instrumented(SeleccionarNumero.class);
    }
}

