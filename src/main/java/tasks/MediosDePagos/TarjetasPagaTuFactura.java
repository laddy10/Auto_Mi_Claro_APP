package tasks.MediosDePagos;

import interactions.Click.ClickTextoQueContengaX;
import interactions.Scroll.Scroll;
import interactions.Scroll.ScrollHastaTexto;
import interactions.validations.ValidarTexto;
import interactions.validations.ValidarTextoQueContengaX;
import interactions.wait.WaitFor;
import interactions.wait.WaitForResponse;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Enter;
import utils.EvidenciaUtils;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static userinterfaces.MediosPagoPage.*;
import static userinterfaces.MediosPagoPage.BTN_MAS_CUOTAS;
import static userinterfaces.MediosPagoPage.BTN_MENOS_CUOTAS;
import static userinterfaces.MediosPagoPage.DROPDOWN_TIPO_DOCUMENTO;
import static userinterfaces.MediosPagoPage.TXT_CVV;
import static userinterfaces.MediosPagoPage.TXT_EMAIL;
import static userinterfaces.MediosPagoPage.TXT_FECHA_EXPIRACION;
import static userinterfaces.MediosPagoPage.TXT_TELEFONO;
import static utils.Constants.*;

public class TarjetasPagaTuFactura implements Task {

    private static final String paso1 = "Validar formulario tarjeta crédito/débito";
    private static final String paso2 = "Llenar datos de la tarjeta";
    private static final String paso3 = "Validar numero de cuotas +";
    private static final String paso4 = "Validar numero de cuotas -";

    @Override
    public <T extends Actor> void performAs(T actor) {

        // VALIDAR FORMULARIO INICIAL
        actor.attemptsTo(
                WaitForResponse.withText(AGREGAR_TARJETA),
                ValidarTextoQueContengaX.elTextoContiene(NUMERO_TARJETA));

        EvidenciaUtils.registrarCaptura(paso1);

        // LLENAR DATOS DE LA TARJETA
        actor.attemptsTo(
                Enter.theValue("5306 9156 7890 1234").into(TXT_NUMERO_TARJETA2),
                Enter.theValue("Pruebas Claro").into(TXT_NOMBRE_TARJETA2),
                WaitFor.aTime(1000));
        // VALIDAR TIPOS DE DOCUMENTO (los 4 disponibles)
        validarTiposDocumento(actor);

        // COMPLETAR RESTO DEL FORMULARIO
        actor.attemptsTo(
                Enter.theValue("12345674").into(TXT_NUMERO_DOCUMENTO2),
                Enter.theValue("12/30").into(TXT_FECHA_VENCIMIENTO),
                Enter.theValue("123").into(TXT_CVV2),
                Enter.theValue("pruebas@gmail.com").into(TXT_EMAIL2));
        EvidenciaUtils.registrarCaptura(paso2);

        actor.attemptsTo(
                Scroll.scrollUnaVista(),
                Enter.theValue("3109871234").into(TXT_TELEFONO2),
                WaitFor.aTime(1000));

        // AJUSTAR NÚMERO DE CUOTAS
        actor.attemptsTo(Click.on(BTN_MAS_CUOTAS), Click.on(BTN_MAS_CUOTAS), WaitFor.aTime(500));

        EvidenciaUtils.registrarCaptura(paso3);

        actor.attemptsTo(Click.on(BTN_MENOS_CUOTAS), WaitFor.aTime(500));

        EvidenciaUtils.registrarCaptura(paso4);

        // VALIDAR QUE EL BOTÓN PAGAR ESTÉ HABILITADO
        actor.attemptsTo(
                ValidarTexto.validarTexto(PAGAR));

        EvidenciaUtils.registrarCaptura("Formulario completado");
    }

    private <T extends Actor> void validarTiposDocumento(T actor) {
        String[] tiposDocumento = {
                CEDULA_CIUDADANIA, CEDULA_EXTRANJERIA2, PASAPORTE, NUMERO_IDENTIFICACION_TRIBUTARIA
        };

        for (String tipoDoc : tiposDocumento) {
            actor.attemptsTo(
                    Click.on(DROPDOWN_TIPO_DOCUMENTO2),
                    WaitFor.aTime(1000),
                    ValidarTexto.validarTexto(CEDULA_CIUDADANIA),
                    ValidarTexto.validarTexto(CEDULA_EXTRANJERIA),
                    ValidarTexto.validarTexto(PASAPORTE),
                    ValidarTexto.validarTexto(NUMERO_IDENTIFICACION_TRIBUTARIA),
                    ClickTextoQueContengaX.elTextoContiene(tipoDoc),
                    WaitFor.aTime(1000));

            EvidenciaUtils.registrarCaptura("Tipo documento seleccionado: " + tipoDoc);
        }
    }

    public static Performable validarRedireccion() {
        return instrumented(TarjetasPagaTuFactura.class);
    }

}
