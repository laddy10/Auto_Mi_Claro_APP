package tasks.PagosYConsultas.Portabilidad;

import interactions.Click.ClickTextoQueContengaX;
import interactions.validations.ValidarTexto;
import interactions.wait.WaitForResponse;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import utils.EvidenciaUtils;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static utils.Constants.*;

public class EstadoPortabilidad implements Task {

    private static final String paso2 = "Ingresar a Estado de portabilidad";
    private static final String paso3 = "Validar redirección a formulario";

    @Override
    public <T extends Actor> void performAs(T actor) {

        // Ingresar a Estado de portabilidad
        EvidenciaUtils.registrarCaptura(paso2);

        actor.attemptsTo(
                ClickTextoQueContengaX.elTextoContiene(ESTADO_DE_PORTABILIDAD),
                WaitForResponse.withText(CONSULTA_EL_ESTADO_TU_PORTACION)
        );

        // Validar redirección a formulario
        EvidenciaUtils.registrarCaptura(paso3);

        actor.attemptsTo(
                ValidarTexto.validarTexto(CONSULTA_EL_ESTADO_TU_PORTACION),
                ValidarTexto.validarTexto(NUMERO_CELULAR_QUE_SOLICITASTE),
                ValidarTexto.validarTexto(NUMERO_DE_DOCUMENTO_REGISTRADO),
                ValidarTexto.validarTexto(CONSULTAR)
        );
    }

    public static Performable validarEstadoYRedireccion() {
        return instrumented(EstadoPortabilidad.class);
    }
}