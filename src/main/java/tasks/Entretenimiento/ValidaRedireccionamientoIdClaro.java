package tasks.Entretenimiento;

import interactions.validations.ValidarTextoQueContengaX;
import interactions.wait.WaitFor;
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
                WaitFor.aTime(6000), // Espera breve para asegurar carga
                ValidarTextoQueContengaX.elTextoContiene("Identificación de Cliente")
        );
        EvidenciaUtils.registrarCaptura(paso);
    }

    public static Performable validar() {
        return instrumented(ValidaRedireccionamientoIdClaro.class);
    }
}
