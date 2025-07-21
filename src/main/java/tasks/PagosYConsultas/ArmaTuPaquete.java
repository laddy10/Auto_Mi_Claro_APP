package tasks.PagosYConsultas;

import interactions.Click.ClickElementByText;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
// TODO: Agregar imports necesarios según el flujo específico

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static utils.Constants.*;

public class ArmaTuPaquete implements Task {
    // TODO: Definir pasos específicos según la información que proporciones

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                ClickElementByText.clickElementByText("ARMA TU PAQUETE")
                // TODO: Agregar pasos específicos
        );
    }

    public static Performable armaTuPaquete() {
        return instrumented(ArmaTuPaquete.class);
    }
}