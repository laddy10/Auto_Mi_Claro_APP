package tasks.PagosYConsultas.DetalleDeTuPlan;

import interactions.Click.ClickTextoQueContengaX;
import interactions.validations.ValidarTexto;
import interactions.validations.ValidarTextoQueContengaX;
import interactions.wait.WaitForResponse;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import utils.EvidenciaUtils;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static utils.Constants.*;

public class AgendarTurnos implements Task {

    private static final String paso1 = "Hacer clic en Agendar turnos";
    private static final String paso2 = "Validar pantalla inicial Agendar turnos";
    private static final String paso3 = "Validar Solicitar Cita";
    private static final String paso4 = "Validar Cancelar o Modificar Cita";
    private static final String paso5 = "Validar Consultar Citas";

    @Override
    public <T extends Actor> void performAs(T actor) {

        EvidenciaUtils.registrarCaptura(paso1);

        actor.attemptsTo(
                ClickTextoQueContengaX.elTextoContiene(AGENDAR_TURNOS),
                WaitForResponse.withAnyText(BIENVENIDO_SISTEMA_AGENDAMIENTO)
        );

        // Validar pantalla inicial
        EvidenciaUtils.registrarCaptura(paso2);

        actor.attemptsTo(
                ValidarTextoQueContengaX.elTextoContiene(BIENVENIDO_SISTEMA_AGENDAMIENTO),
                ValidarTextoQueContengaX.elTextoContiene(SOLICITUD_CONSULTA_MODIFICACION),
                ValidarTexto.validarTexto(CITA_PRESENCIAL),
                ValidarTexto.validarTexto(CITA_VIRTUAL)
        );

        // 1. SOLICITAR CITA - DIRECCIONAMIENTO CORRECTO - VOLVER

        actor.attemptsTo(
                ClickTextoQueContengaX.elTextoContiene(SOLICITAR_CITA),
                WaitForResponse.withAnyText(VOLVER)
        );

        EvidenciaUtils.registrarCaptura(paso3);

        // Validar direccionamiento correcto de Solicitar Cita
        actor.attemptsTo(
                ValidarTextoQueContengaX.elTextoContiene(AGENDAMIENTO_WEB),
                ValidarTextoQueContengaX.elTextoContiene(INGRESE_DATOS_SOLICITADOS_AGENDAR),
                ClickTextoQueContengaX.elTextoContiene(TIPO_DOCUMENTO),
                ClickTextoQueContengaX.elTextoContiene(NUMERO_DOCUMENTO),
                ClickTextoQueContengaX.elTextoContiene(NOMBRE),
                ClickTextoQueContengaX.elTextoContiene(TELEFONO_CELULAR),
                ClickTextoQueContengaX.elTextoContiene(CORREO_ELECTRONICO),

                // Volver
                ClickTextoQueContengaX.elTextoContiene(VOLVER),
                WaitForResponse.withText(BIENVENIDO_SISTEMA_AGENDAMIENTO)
        );

        // 2. CANCELAR O MODIFICAR CITA - VOLVER

        actor.attemptsTo(
                ClickTextoQueContengaX.elTextoContiene(CANCELAR_O_MODIFICAR_CITA),
                WaitForResponse.withText(VOLVER));

        EvidenciaUtils.registrarCaptura(paso4);

        // Validar direccionamiento correcto de Cancelar o Modificar Cita
        actor.attemptsTo(
                ValidarTextoQueContengaX.elTextoContiene(AGENDAMIENTO_WEB),
                ValidarTextoQueContengaX.elTextoContiene(INGRESE_DATOS_SOLICITADOS_CANCELAR),
                ClickTextoQueContengaX.elTextoContiene(TIPO_DOCUMENTO),
                ClickTextoQueContengaX.elTextoContiene(NUMERO_DOCUMENTO),
                ClickTextoQueContengaX.elTextoContiene(NUMERO_DE_CITA),

                // Volver
                ClickTextoQueContengaX.elTextoContiene(VOLVER),
                WaitForResponse.withText(BIENVENIDO_SISTEMA_AGENDAMIENTO)
        );

        // 3. CONSULTAR CITAS - VOLVER

        actor.attemptsTo(
                ClickTextoQueContengaX.elTextoContiene(CONSULTAR_CITAS),
                WaitForResponse.withAnyText(VOLVER));

        EvidenciaUtils.registrarCaptura(paso5);

        // Validar direccionamiento correcto de Consultar Citas
        actor.attemptsTo(
                ValidarTextoQueContengaX.elTextoContiene(AGENDAMIENTO_WEB),
                ValidarTextoQueContengaX.elTextoContiene(INGRESE_DATOS_SOLICITADOS_CONSULTAR),
                ClickTextoQueContengaX.elTextoContiene(TIPO_DOCUMENTO),
                ClickTextoQueContengaX.elTextoContiene(NUMERO_DOCUMENTO),
                ClickTextoQueContengaX.elTextoContiene(ESTADO_DE_LA_CITA),

                // Volver
                ClickTextoQueContengaX.elTextoContiene(VOLVER),
                WaitForResponse.withText(BIENVENIDO_SISTEMA_AGENDAMIENTO)
        );
    }

    public static Performable validarDireccionamiento() {
        return instrumented(AgendarTurnos.class);
    }
}