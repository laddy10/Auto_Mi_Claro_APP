package tasks.AtencionClienteSoporte;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static utils.Constants.*;

import interactions.Click.ClickTextoQueContengaX;
import interactions.wait.WaitForResponse;
import models.User;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import utils.AndroidObject;
import utils.EvidenciaUtils;
import utils.TestDataProvider;

public class ConsultarPQRPre implements Task {

    private static final String paso1 = "Ingresar a Consultar PQR";

    @Override
    public <T extends Actor> void performAs(T actor) {
        EvidenciaUtils.registrarCaptura(paso1);

        actor.attemptsTo(
                ClickTextoQueContengaX.elTextoContiene(CONSULTAR_PQR),
                WaitForResponse.withText(PREPAGO));
    }

    public static Performable ingresar() {
        return instrumented(ConsultarPQR.class);
    }

    public static class SeleccionarLineaYVerDetalle implements Task {
        private final User user = TestDataProvider.getRealUser();

        @Override
        public <T extends Actor> void performAs(T actor) {
            EvidenciaUtils.registrarCaptura("Seleccionar línea y ver detalle - PQR");
            actor.attemptsTo(
                    ClickTextoQueContengaX.elTextoContiene(PREPAGO));
            AndroidObject.scrollCorto2(actor, LINEA + " " + user.getNumeroPrepago() + " " + VER_DETALLE);

            actor.attemptsTo(
                    ClickTextoQueContengaX.elTextoContiene(user.getNumeroPrepago()),
                    WaitForResponse.withText(CLARO_COLOMBIA_PQR));
        }

        public static Performable ejecutar() {
            return instrumented(SeleccionarLineaYVerDetalle.class);
        }
    }

    public static Performable seleccionarLineaYVerDetalle() {
        return SeleccionarLineaYVerDetalle.ejecutar();
    }
}