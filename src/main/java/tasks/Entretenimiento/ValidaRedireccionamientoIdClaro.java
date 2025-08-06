package tasks.Entretenimiento;

import interactions.validations.ValidarTextoQueContengaX;
import interactions.wait.WaitFor;
import interactions.wait.WaitForResponse;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import utils.EvidenciaUtils;

import static net.serenitybdd.screenplay.Tasks.instrumented;

public class ValidaRedireccionamientoIdClaro implements Task {

    private static final String paso = "Validar pantalla de Identificación de Cliente (Disney+)";

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                // Espera breve para asegurar carga
                WaitForResponse.withText("Identificación de Cliente"),
                ValidarTextoQueContengaX.elTextoContiene("Identificación de Cliente")
        );
        EvidenciaUtils.registrarCaptura(paso);
    }

    public static Performable validar() {
        return instrumented(ValidaRedireccionamientoIdClaro.class);
    }
}
