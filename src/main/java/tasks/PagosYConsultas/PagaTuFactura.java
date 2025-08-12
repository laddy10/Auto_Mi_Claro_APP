package tasks.PagosYConsultas;

import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static net.serenitybdd.screenplay.Tasks.instrumented;
import static org.hamcrest.core.IsEqual.equalTo;
import static userinterfaces.LoginPage.LBL_ENCABEZADO_USUARIO;
import static userinterfaces.PagosYConsultasPage.*;
import static userinterfaces.VersionesMiniProgramaPage.MINI_VERSION_PAGA_TU_FACTURA_TARGET;
import static utils.Constants.*;
import static utils.Constants.DECLARACION_SERVICIO;
import static utils.ConstantsMiniVersiones.Versiones.MINI_VERSION_PAGA_TU_FACTURA_CONSTANT;

import interactions.Click.ClickTextoQueContengaX;
import interactions.comunes.Atras;
import interactions.validations.ValidarTexto;
import interactions.validations.ValidarTextoQueContengaX;
import interactions.validations.ValidateInformationText;
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

public class PagaTuFactura implements Task {
    private static final User user = TestDataProvider.getRealUser();
    private static final String paso = "Ingreso a Paga y consulta tus facturas";
    private static final String paso2 = "Seleccionar la linea " + user.getNumero();
    private static final String paso3 = "Validar Mini Versión";
    private static final String paso4 = "Validar datos de pago: Referencia, fechas y valor";

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                ClickTextoQueContengaX.elTextoContiene(PAGA_TU_FACTURA),
                WaitForResponse.withText(POSTPAGO));

        EvidenciaUtils.registrarCaptura(paso);

        actor.attemptsTo(ClickTextoQueContengaX.elTextoContiene(POSTPAGO));

        AndroidObject.scrollCorto2(actor, LINEA + " " + user.getNumero() + " " + VER_DETALLE);

        EvidenciaUtils.registrarCaptura(paso2);

        actor.attemptsTo(
                ClickTextoQueContengaX.elTextoContiene(user.getNumero()),
                WaitForResponse.withText(PAGAR_FACTURA),
                Click.on(BTN_TRES_PUNTOS_MAS),
                ClickTextoQueContengaX.elTextoContiene(ACERCA_DE),
                WaitFor.aTime(1000),
                ValidarTexto.validarTexto(PAGA_TU_FACTURA),
                ValidarTexto.validarTexto(DECLARACION_SERVICIO),
                ValidarTextoQueContengaX.elTextoContiene(VER));

        EvidenciaUtils.registrarCaptura(paso3);


        actor.should(seeThat(ValidateInformationText.validateInformationText(MINI_VERSION_PAGA_TU_FACTURA_TARGET),
                equalTo(MINI_VERSION_PAGA_TU_FACTURA_CONSTANT)));


        actor.attemptsTo(
                Atras.irAtras(),
                WaitForResponse.withText(PAGAR_FACTURA),
                ValidarTextoQueContengaX.elTextoContiene(REFERENCIA_PAGO),
                ValidarTextoQueContengaX.elTextoContiene(FECHA_DE_CARGA),
                ValidarTextoQueContengaX.elTextoContiene(VALOR_A_PAGAR));

        EvidenciaUtils.registrarCaptura(paso4);

        List<WebElementFacade> lblfechapagooportuno = LBL_FECHA_PAGO_OPORTUNO.resolveAllFor(actor);
        if (!lblfechapagooportuno.isEmpty()) {
            actor.attemptsTo(
                    ValidarTextoQueContengaX.elTextoContiene(FECHA_PAGO_OPORTUNO));

        }
    }

    public static Performable pagaTuFactura() {
        return instrumented(PagaTuFactura.class);
    }
}
