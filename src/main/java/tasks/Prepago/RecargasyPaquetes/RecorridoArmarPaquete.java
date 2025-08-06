package tasks.Prepago.RecargasyPaquetes;

import interactions.Click.ClickTextoQueContengaX;
import interactions.wait.WaitFor;
import interactions.wait.WaitForResponse;
import models.User;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import utils.AndroidObject;
import utils.TestDataProvider;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static userinterfaces.PagosYConsultasPage.*;
import static userinterfaces.PagosyConsultasPrePage.DROPDOWN_DATOS;
import static userinterfaces.PagosyConsultasPrePage.DROPDOWN_DURACION;
import static utils.Constants.*;

public class RecorridoArmarPaquete implements Task {

    private static final User user = TestDataProvider.getRealUser();

    @Override
    public <T extends Actor> void performAs(T actor) {

        // Ir a home
        actor.attemptsTo(
                Click.on(ICON_HOME),
                WaitForResponse.withText(POSTPAGO)
        );

        // Seleccionar prepago
        actor.attemptsTo(
                ClickTextoQueContengaX.elTextoContiene(PREPAGO),
                WaitFor.aTime(1000)
        );

        // Seleccionar línea prepago
        AndroidObject.scrollCorto2(actor, LINEA + " " + user.getNumeroPrepago() + " " + ELEGIR);

        actor.attemptsTo(
                ClickTextoQueContengaX.elTextoContiene(user.getNumeroPrepago()),
                WaitForResponse.withText(ARMA_TU_PAQUETE)
        );

        // Clic en Arma tu paquete
        actor.attemptsTo(
                ClickTextoQueContengaX.elTextoContiene(ARMA_TU_PAQUETE),
                WaitForResponse.withText(SELECCIONA_LA_DURACION)
        );

        // Seleccionar duración
        actor.attemptsTo(
                Click.on(DROPDOWN_DURACION),
                WaitForResponse.withText(ELIGE_DURACION_PAQUETE),
                ClickTextoQueContengaX.elTextoContiene(user.getDuracionPaqueteArmar()),
                WaitFor.aTime(1000)
        );

        // Seleccionar cantidad de datos
        actor.attemptsTo(
                Click.on(DROPDOWN_DATOS),
                WaitForResponse.withText(ELIGE_GIGAS_NECESITAS),
                ClickTextoQueContengaX.elTextoContiene(user.getCantidadDatosArmar()),
                WaitFor.aTime(1000)
        );

        // Armar paquete
        actor.attemptsTo(
                ClickTextoQueContengaX.elTextoContiene(ARMAR_PAQUETE),
                WaitForResponse.withText(PAQUETE_ARMADO_PARA_LINEA)
        );

        // Ir a pagar
        actor.attemptsTo(
                ClickTextoQueContengaX.elTextoContiene(IR_A_PAGAR),
                WaitForResponse.withText(ELEGIR_OTRO_MEDIO_PAGO)
        );
    }

    public static Performable navegar() {
        return instrumented(RecorridoArmarPaquete.class);
    }
}