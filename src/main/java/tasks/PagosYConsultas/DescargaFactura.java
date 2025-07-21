package tasks.PagosYConsultas;

import interactions.Click.ClickElementByText;
import interactions.Click.ClickTextoQueContengaX;
import interactions.input.IngresarMontoConTecladoNumerico;
import interactions.validations.ValidarCantidadFacturas;
import interactions.validations.ValidarTexto;
import interactions.validations.ValidarTextoQueContengaX;
import interactions.validations.ValidateInformationText;
import interactions.wait.WaitFor;
import interactions.wait.WaitForResponse;
import models.User;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Enter;
import utils.EvidenciaUtils;
import utils.TestDataProvider;

import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static net.serenitybdd.screenplay.Tasks.instrumented;
import static userinterfaces.PagosYConsultasPage.*;
import static utils.Constants.*;

public class DescargaFactura implements Task {
    private final User user = TestDataProvider.getRealUser();
    private static final String paso1 = "Seleccionar Descarga tu factura";
    private static final String paso2 = "Verificar número de celular";
    private static final String paso3 = "Validar 6 facturas disponibles";
    private static final String paso4 = "Descargar factura";
    private static final String paso5 = "Ingresar contraseña";
    private static final String paso6 = "Verificar factura descargada";

    @Override
    public <T extends Actor> void performAs(T actor) {

        EvidenciaUtils.registrarCaptura(paso1);

        // Seleccionar "Descarga tu factura"
        actor.attemptsTo(
                ClickTextoQueContengaX.elTextoContiene(DESCARGA_TU_FACTURA),
                WaitForResponse.withText(POSTPAGO)
        );

        // Verificar número de celular en pantalla
        actor.attemptsTo(
                ValidarTexto.validarTexto(POSTPAGO),
                ValidarTexto.validarTexto(DESCARGA_TU_FACTURA),
                ValidarTextoQueContengaX.elTextoContiene(user.getNumero().replace(" ", "")),
                ValidarTextoQueContengaX.elTextoContiene(ENCUENTRA_LAS_ULTIMAS_FACTURAS)
        );
        EvidenciaUtils.registrarCaptura(paso2);

        // Validar que se encuentren disponibles 6 facturas
        actor.attemptsTo(
                ValidarCantidadFacturas.disponibles(6)
        );

        EvidenciaUtils.registrarCaptura(paso3);

        // Descargar factura (primera disponible)
        actor.attemptsTo(
                WaitFor.aTime(2000),
                Click.on(ICON_DESCARGAR_FACTURA),
                WaitForResponse.withText(ESTE_ARCHIVO_ESTA_PROTEGIDO)
        );
        EvidenciaUtils.registrarCaptura(paso4);

        // Ingreso de contraseña
        actor.attemptsTo(
                ValidarTexto.validarTexto(ESTE_ARCHIVO_ESTA_PROTEGIDO),
                Click.on(TXT_CONTRASENA_FACTURA),
            WaitFor.aTime(5000),
                Enter.theValue(user.getContrasena()).into(TXT_CONTRASENA_FACTURA));
          //  IngresarMontoConTecladoNumerico.elValor(user.getContrasena()));



        EvidenciaUtils.registrarCaptura(paso5);

        actor.attemptsTo(
                ClickElementByText.clickElementByText(ABRIR)
        );


        // Verificar apertura de factura
        actor.attemptsTo(
                WaitFor.aTime(3000)
        );

        actor.should(seeThat(
                ValidateInformationText.validateInformationText(URL_FACTURA_DESCARGADA))
        );

        EvidenciaUtils.registrarCaptura(paso6);
    }

    public static Performable descargarFactura() {
        return instrumented(DescargaFactura.class);
    }
}
