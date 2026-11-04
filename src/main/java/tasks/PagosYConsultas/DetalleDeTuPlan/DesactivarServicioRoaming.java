package tasks.PagosYConsultas.DetalleDeTuPlan;

import interactions.Click.ClickTextoQueContengaX;
import interactions.validations.ValidarTextoQueContengaX;
import interactions.wait.WaitFor;
import interactions.wait.WaitForResponse;
import models.User;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import utils.EvidenciaUtils;
import utils.TestDataProvider;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static userinterfaces.PagosyConsultasPrePage.BTON_ACTIVAR_ROAMING;
import static utils.Constants.*;

public class DesactivarServicioRoaming implements Task {
    private static final String NUMERO_LINEA = "310 263 7102";
    private static final User user = TestDataProvider.getRealUser();
    private static final String paso1 = "Hacer clic en Activar Roaming";
    private static final String paso2 = "Ingreso a menu activa roaming";
    private static final String paso3 = "Seleccionar la linea postpago " + NUMERO_LINEA;
    private static final String paso4 = "Activar roaming";
    private static final String paso5 = "";
    private static final String paso6 = "";
    private static final String paso7 = "";
    private static final String paso8 = "";
    private static final String paso9 = "";


    @Override
    public <T extends Actor> void performAs(T actor) {

        // 1. Hacer clic en Activar Roaming
        EvidenciaUtils.registrarCaptura(paso1);

        actor.attemptsTo(
                ClickTextoQueContengaX.elTextoContiene(ACTIVAR_ROAMING),
                WaitForResponse.withText(ACTIVA_ROAMING));

        EvidenciaUtils.registrarCaptura(paso2);


        actor.attemptsTo(
                ClickTextoQueContengaX.elTextoContiene(NUMERO_LINEA),
                WaitForResponse.withText("Activa Roaming"));
        EvidenciaUtils.registrarCaptura(paso3);

        actor.attemptsTo(
                Click.on(BTON_ACTIVAR_ROAMING),
                WaitForResponse.withText("Elige")
        );
        EvidenciaUtils.registrarCaptura(paso4);
        actor.attemptsTo(
                ClickTextoQueContengaX.elTextoContiene("Activar con fecha"),
                WaitFor.aTime(3000),
                ClickTextoQueContengaX.elTextoContiene("30")
        );
        EvidenciaUtils.registrarCaptura(paso5);
        actor.attemptsTo(
                ClickTextoQueContengaX.elTextoContiene(ACEPTAR_2),
                WaitForResponse.withText("Fecha")
        );
        actor.attemptsTo(
                ValidarTextoQueContengaX.elTextoContiene("Ver paquetes"));
        EvidenciaUtils.registrarCaptura(paso6); // Deseas comprar mas paquetes
        actor.attemptsTo(

                ClickTextoQueContengaX.elTextoContiene("No, gracias")
        );
        EvidenciaUtils.registrarCaptura(paso7); // Ahorra comprando paquetes
        actor.attemptsTo(ClickTextoQueContengaX.elTextoContiene("Continuar"));
        EvidenciaUtils.registrarCaptura(paso8); //Confirmacion de compra de paquete
        actor.attemptsTo(ClickTextoQueContengaX.elTextoContiene("Aceptar"));
        EvidenciaUtils.registrarCaptura(paso9); //Pantalla de confirmacion de roaming activado
        actor.attemptsTo(
                Click.on(BTON_ACTIVAR_ROAMING),
                WaitFor.aTime(5000),
                ClickTextoQueContengaX.elTextoContiene("Desactivar"),
                WaitForResponse.withText("Servicio Roaming")

        );

    }

    public static Performable desactivarServicioRoaming() {
        return instrumented(DesactivarServicioRoaming.class);
    }

}
