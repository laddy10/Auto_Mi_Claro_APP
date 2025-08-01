package tasks.PagosYConsultas.DetalleDeTuPlan;

import interactions.Click.ClickTextoQueContengaX;
import interactions.wait.WaitForResponse;
import models.User;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import utils.AndroidObject;
import utils.EvidenciaUtils;
import utils.TestDataProvider;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static utils.Constants.*;

public class SeleccionarLineaPostpago implements Task {

    private static final User user = TestDataProvider.getRealUser();
    private static final String paso1 = "Seleccionar línea postpago " + user.getNumero();

    @Override
    public <T extends Actor> void performAs(T actor) {

        AndroidObject.scrollCorto2(actor, LINEA + " " + user.getNumero() + " " + VER_DETALLE);

        EvidenciaUtils.registrarCaptura(paso1);

        actor.attemptsTo(
                ClickTextoQueContengaX.elTextoContiene(user.getNumero()),
                WaitForResponse.withAnyText(FAMILIA_Y_AMIGOS_OPCION, ADELANTA_SALDO_LABEL, COMPRA_POR_CATEGORIA)
        );
    }

    public static Performable seleccionarLinea() {
        return instrumented(SeleccionarLineaPostpago.class);
    }
}