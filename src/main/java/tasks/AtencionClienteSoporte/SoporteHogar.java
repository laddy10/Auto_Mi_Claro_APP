package tasks.AtencionClienteSoporte;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static userinterfaces.AtencionClienteSoportePage.ICON_WHATSAPP;
import static utils.Constants.*;

import interactions.Click.ClickTextoQueContengaX;
import interactions.validations.ValidarTextoQueContengaX;
import interactions.wait.WaitForResponse;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.questions.Presence;
import utils.EvidenciaUtils;

public class SoporteHogar implements Task {

    private static final String paso1 = "Ingresar a Soporte Hogar";
    private static final String paso2 = "Seleccionar WhatsApp y validar redirección";

    @Override
    public <T extends Actor> void performAs(T actor) {
        EvidenciaUtils.registrarCaptura(paso1);

        actor.attemptsTo(
                ClickTextoQueContengaX.elTextoContiene(SOPORTE_HOGAR), WaitForResponse.withText(WHATSAPP));
    }

    public static Performable ingresar() {
        return instrumented(SoporteHogar.class);
    }

    public static class SeleccionarWhatsappYValidar implements Task {
        @Override
        public <T extends Actor> void performAs(T actor) {
            EvidenciaUtils.registrarCaptura("Seleccionar WhatsApp y validar redirección");

            // Condicional: verificar si aparece el ícono de WhatsApp
            if (!Presence.of(ICON_WHATSAPP).viewedBy(actor).resolveAll().isEmpty()) {
                actor.attemptsTo(
                        ValidarTextoQueContengaX.elTextoContiene(SELECCIONAR_APLICACION),
                        Click.on(ICON_WHATSAPP),
                        ValidarTextoQueContengaX.elTextoContiene(WHATSAPP));
            } else {
                // Si no aparece el ícono, valida que redirija directamente a WhatsApp
                actor.attemptsTo(ValidarTextoQueContengaX.elTextoContiene(WHATSAPP));
            }
        }

        public static Performable validar() {
            return instrumented(SeleccionarWhatsappYValidar.class);
        }
    }

    public static Performable seleccionarWhatsappYValidar() {
        return SeleccionarWhatsappYValidar.validar();
    }
}