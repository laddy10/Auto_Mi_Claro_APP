package tasks.AtencionClienteSoporte;

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

public class EstadoServiciosTecnicosPRE implements Task {

    private final User user = TestDataProvider.getRealUser();

    @Override
    public <T extends Actor> void performAs(T actor) {
        EvidenciaUtils.registrarCaptura("Ingresar a Estado Servicios Técnicos");

        actor.attemptsTo(
                ClickTextoQueContengaX.elTextoContiene(ESTADO_SERVICIOS_TECNICOS),
                WaitForResponse.withText(PREPAGO));
    }

    public static Performable ingresar() {
        return instrumented(EstadoServiciosTecnicos.class);
    }

    public static class SeleccionarLineaYVerDetalle implements Task {
        private final User user = TestDataProvider.getRealUser();

        @Override
        public <T extends Actor> void performAs(T actor) {
            EvidenciaUtils.registrarCaptura("Seleccionar línea y ver detalle");
            actor.attemptsTo(
                    ClickTextoQueContengaX.elTextoContiene(PREPAGO));
            AndroidObject.scrollCorto2(actor, user.getNumeroPrepago() + " " + VER_DETALLE);

            actor.attemptsTo(
                    ClickTextoQueContengaX.elTextoContiene(user.getNumeroPrepago()),
                    WaitForResponse.withText(ORDENES_DE_SERVICIO));
        }

        public static Performable ejecutar() {
            return instrumented(SeleccionarLineaYVerDetalle.class);
        }
    }
    public static Performable seleccionarLineaYVerDetalle() {
        return SeleccionarLineaYVerDetalle.ejecutar();
    }
}
