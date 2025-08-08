package tasks.MediosDePagos;

import interactions.Click.ClickTextoQueContengaX;
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
import static utils.Constants.*;

public class TarjetaCreditoDebito implements Task {
    private static final String paso1 = "Validar formulario tarjeta crédito/débito";
    private static final String paso2 = "Llenar datos de la tarjeta";
    private static final String paso3 = "Validar numero de cuotas +";
    private static final String paso4 = "Validar numero de cuotas -";

    @Override
    public <T extends Actor> void performAs(T actor) {

        // VALIDAR FORMULARIO INICIAL
        actor.attemptsTo(
                WaitForResponse.withText(TARJETAS_NACIONALES),
                ValidarTextoQueContengaX.elTextoContiene(TARJETAS_NACIONALES),
                ValidarTextoQueContengaX.elTextoContiene(NUMERO_TARJETA)
        );

        EvidenciaUtils.registrarCaptura(paso1);

        // LLENAR DATOS DE LA TARJETA
        actor.attemptsTo(
                Enter.theValue("5306 9156 7890 1234").into(TXT_NUMERO_TARJETA),
                Enter.theValue("Pepito Perez").into(TXT_NOMBRE_TARJETA),
                WaitFor.aTime(1000)
        );

        // VALIDAR TIPOS DE DOCUMENTO (los 4 disponibles)
        validarTiposDocumento(actor);

        // COMPLETAR RESTO DEL FORMULARIO
        actor.attemptsTo(
                Enter.theValue("12345674").into(TXT_NUMERO_DOCUMENTO),
                Enter.theValue("12/30").into(TXT_FECHA_EXPIRACION),
                Enter.theValue("123").into(TXT_CVV),
                Enter.theValue("pepitoperez@gmail.com").into(TXT_EMAIL));

        EvidenciaUtils.registrarCaptura(paso2);

        actor.attemptsTo(
                ScrollHastaTexto.conTexto(PAGAR),
                Enter.theValue("3109871234").into(TXT_TELEFONO),
                WaitFor.aTime(1000)
        );

        // AJUSTAR NÚMERO DE CUOTAS
        actor.attemptsTo(
                Click.on(BTN_MAS_CUOTAS),
                Click.on(BTN_MAS_CUOTAS),
                WaitFor.aTime(500)
        );

        EvidenciaUtils.registrarCaptura(paso3);

        actor.attemptsTo(
                Click.on(BTN_MENOS_CUOTAS),
                WaitFor.aTime(500)
        );

        EvidenciaUtils.registrarCaptura(paso4);


        // VALIDAR QUE EL BOTÓN PAGAR ESTÉ HABILITADO
        actor.attemptsTo(
                ValidarTexto.validarTexto(GUARDAR_DATOS_TARJETA),
                ValidarTexto.validarTexto(PAGAR)
        );

        EvidenciaUtils.registrarCaptura("Formulario completado");
    }

    private <T extends Actor> void validarTiposDocumento(T actor) {
        String[] tiposDocumento = {
                CEDULA_CIUDADANIA,
                CEDULA_EXTRANJERIA,
                PASAPORTE,
                NUMERO_IDENTIFICACION_TRIBUTARIA
        };

        for (String tipoDoc : tiposDocumento) {
            actor.attemptsTo(
                    Click.on(DROPDOWN_TIPO_DOCUMENTO),
                    WaitFor.aTime(1000),
                    ValidarTexto.validarTexto(CEDULA_CIUDADANIA),
                    ValidarTexto.validarTexto(CEDULA_EXTRANJERIA),
                    ValidarTexto.validarTexto(PASAPORTE),
                    ValidarTexto.validarTexto(NUMERO_IDENTIFICACION_TRIBUTARIA),
                    ClickTextoQueContengaX.elTextoContiene(tipoDoc),
                    WaitFor.aTime(1000)
            );

            EvidenciaUtils.registrarCaptura("Tipo documento seleccionado: " + tipoDoc);
        }
    }

    public static Performable validarRedireccion() {
        return instrumented(TarjetaCreditoDebito.class);
    }
}