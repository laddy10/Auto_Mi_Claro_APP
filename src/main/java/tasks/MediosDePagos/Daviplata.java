package tasks.MediosDePagos;

import interactions.Click.ClickTextoQueContengaX;
import interactions.validations.ValidarTexto;
import interactions.validations.ValidarTextoQueContengaX;
import interactions.wait.WaitFor;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import utils.EvidenciaUtils;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static userinterfaces.MediosPagoPage.*;
import static utils.Constants.*;

public class Daviplata implements Task {

    private static final String paso1 = "Validar formulario Daviplata";
    private static final String paso2 = "Validar tipos de documento";
    private static final String paso3 = "Documento: Cédula de extranjería";
    private static final String paso4 = "Documento: Cédula de ciudadanía";

    @Override
    public <T extends Actor> void performAs(T actor) {

        // VALIDAR FORMULARIO DAVIPLATA
        EvidenciaUtils.registrarCaptura(paso1);

        actor.attemptsTo(
                ValidarTextoQueContengaX.elTextoContiene(INGRESA_DOCUMENTO_ASOCIADO),
                ValidarTextoQueContengaX.elTextoContiene(CODIGO_SEGURIDAD_SMS),
                ValidarTexto.validarTexto(TIPO_DE_DOCUMENTO),
                ValidarTexto.validarTexto(NUMERO_DE_DOCUMENTO)
        );


        // Volver a desplegar para validar la segunda opción
        actor.attemptsTo(
                Click.on(BTN_TIPO_DOCUMENTO_DAVIPLATA),
                WaitFor.aTime(1000));

        EvidenciaUtils.registrarCaptura(paso2);

        actor.attemptsTo(
                ClickTextoQueContengaX.elTextoContiene(CEDULA_DE_EXTRANJERIA),
                WaitFor.aTime(1000)
        );

        EvidenciaUtils.registrarCaptura(paso3);

        // Regresar a Cédula de ciudadanía para continuar
        actor.attemptsTo(
                Click.on(BTN_TIPO_DOCUMENTO_DAVIPLATA_2),
                WaitFor.aTime(1000),
                ClickTextoQueContengaX.elTextoContiene(CEDULA_DE_CIUDADANIA),
                WaitFor.aTime(1000)
        );

        EvidenciaUtils.registrarCaptura(paso4);

    }

    public static Performable validarRedireccion() {
        return instrumented(Daviplata.class);
    }
}