package tasks.Entretenimiento.ValidarRedirecciones;

import interactions.Click.ClickTextoQueContengaX;
import interactions.validations.ValidarElemento;
import interactions.wait.WaitFor;
import interactions.validations.ValidarTexto;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.questions.Presence;
import net.serenitybdd.screenplay.targets.Target;
import utils.EvidenciaUtils;

import javax.swing.text.html.parser.Element;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static userinterfaces.EntretenimientoPage.*;
import static utils.Constants.*;

public class ValidarRedireccionClaroMusicaApp implements Task {

    private static final String paso = "Validar redirección a la aplicación Claro Música";

    @Override
    public <T extends Actor> void performAs(T actor) {

        // Esperar a que la app cargue
        try {
            Thread.sleep(12000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        actor.attemptsTo(WaitFor.aTime(1000)); // medio segundo antes de validar

        // ✅ Nueva condición: Si aparece "Aceptar y continuar", hacer clic
        if (isVisible(actor, BTN_ACEPTAR_CONTINUAR)) {
            actor.attemptsTo(
                    Click.on(BTN_ACEPTAR_CONTINUAR),
                    WaitFor.aTime(1000)
            );
        }

        // Si aparece el mensaje de alerta, hacer clic en confirmar
        if (isVisible(actor, LBL_MENSAJE_ALERT)) {
            actor.attemptsTo(
                    Click.on(BTN_ALERT_CONFIRM)
            );
        } else {
            actor.attemptsTo(WaitFor.aTime(1000));
        }

        // ✅ Nueva condición:Si ingresa a una sesión verifica el mensaje y continúa
        if (isVisible(actor, MSJ_ALERTA_INGRESO)) {
            if (isVisible(actor, BTN_ENTENDIDO)) {
                actor.attemptsTo(
                        ClickTextoQueContengaX.elTextoContiene(ENTENDIDO)
                );
            }
            actor.attemptsTo(
                    (Performable) ValidarElemento.esVisible(LOGO_CLARO_MUSICA)
            );
        }

        // Validar elementos visibles en Claro Música si no hay sesiòn iniciada

        if(isVisible(actor, BTN_ESCUCHA_GRATIS)) {
            actor.attemptsTo(
                    ValidarTexto.validarTexto("Escucha gratis"),
                    ValidarTexto.validarTexto("Entrar")
            );
        }
        EvidenciaUtils.registrarCaptura(paso);
    }

    private <T extends Actor> boolean isVisible(T actor, Target element) {
        return !Presence.of(element).viewedBy(actor).resolveAll().isEmpty();
    }

    public static Performable validar() {
        return instrumented(ValidarRedireccionClaroMusicaApp.class);
    }
}
