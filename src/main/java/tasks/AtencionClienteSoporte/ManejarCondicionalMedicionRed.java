package tasks.AtencionClienteSoporte;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static userinterfaces.AtencionClienteSoportePage.LBL_CONECTATE_PLAN_DATOS;
import static utils.Constants.*;

import interactions.Click.ClickElementByText;
import interactions.Click.ClickTextoQueContengaX;
import interactions.validations.ValidarTexto;
import interactions.validations.ValidarTextoQueContengaX;
import interactions.wait.WaitFor;
import interactions.wait.WaitForResponse;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.questions.Presence;
import utils.EvidenciaUtils;

public class ManejarCondicionalMedicionRed implements Task {

    private static final String paso1 = "Verificar información en pantalla";
    private static final String paso2 = "Clic en Iniciar test";
    private static final String paso3 = "Manejar condicional - Conectarse al plan de datos";
    private static final String paso4 = "Validar información de test de velocidad";

    @Override
    public <T extends Actor> void performAs(T actor) {
        EvidenciaUtils.registrarCaptura(paso1);

        actor.attemptsTo(ValidarTextoQueContengaX.elTextoContiene(ESTAMOS_LISTOS_INICIAR_MEDICION));

        EvidenciaUtils.registrarCaptura(paso2);

        actor.attemptsTo(ClickTextoQueContengaX.elTextoContiene(INICIAR_TEST));

        // Condicional: verificar si aparece mensaje de conectarse al plan de datos
        if (!Presence.of(LBL_CONECTATE_PLAN_DATOS).viewedBy(actor).resolveAll().isEmpty()) {

            EvidenciaUtils.registrarCaptura(paso3);

            actor.attemptsTo(
                    ValidarTexto.validarTexto(CONECTATE_TU_PLAN_DATOS),
                    ValidarTexto.validarTexto(NO_PUEDES_REALIZAR_MEDICION),
                    ClickElementByText.clickElementByText(CERRAR));
        } else {
            // Si no aparece el mensaje, esperar 10 segundos y validar información
            actor.attemptsTo(
                    WaitFor.aTime(10000),
                    WaitForResponse.withText(TEST_FINALIZADO),
                    ValidarTexto.validarTexto(TEST_FINALIZADO),
                    ValidarTextoQueContengaX.elTextoContiene(VELOCIDAD_SUBIDA),
                    ValidarTextoQueContengaX.elTextoContiene(VELOCIDAD_DESCARGA),
                    ValidarTextoQueContengaX.elTextoContiene(LATENCIA),
                    ValidarTextoQueContengaX.elTextoContiene(FLUCTUACION_FASE));

            EvidenciaUtils.registrarCaptura(paso4);
        }
    }

    public static Performable verificarYManejar() {
        return instrumented(ManejarCondicionalMedicionRed.class);
    }
}
