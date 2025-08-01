package tasks.PagosYConsultas.DetalleDeTuPlan;

import interactions.Click.ClickTextoQueContengaX;
import interactions.Scroll.ScrollHastaTexto;
import interactions.wait.WaitForResponse;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import utils.EvidenciaUtils;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static utils.Constants.*;

public class IngresarVerFactura implements Task {

    private static final String paso1 = "Hacer clic en Ver factura";

    @Override
    public <T extends Actor> void performAs(T actor) {

        actor.attemptsTo(
                ScrollHastaTexto.conTexto(VER_FACTURA));

        EvidenciaUtils.registrarCaptura(paso1);

        actor.attemptsTo(
                ClickTextoQueContengaX.elTextoContiene(VER_FACTURA),
                WaitForResponse.withText(PAGAR_FACTURA)
        );
    }

    public static Performable ingresarVerFactura() {
        return instrumented(IngresarVerFactura.class);
    }
}