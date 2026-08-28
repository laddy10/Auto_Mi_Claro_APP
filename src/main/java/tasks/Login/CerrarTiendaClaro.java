package tasks.Login;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static userinterfaces.LoginPage.BTN_CERRAR_TIENDA_CLARO;
import static userinterfaces.LoginPage.LBL_TIENDA_CLARO;

import interactions.Click.ClickElementById;
import interactions.comunes.Atras;
import interactions.wait.WaitFor;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.questions.Presence;
import net.serenitybdd.screenplay.targets.Target;
import utils.AndroidObject;
import utils.EvidenciaUtils;

/**
 * EMERGENCIA: Al abrir la app con la sesión ya iniciada, se abre la Tienda Claro
 * ("Tecnología que te transforma") sobre el home, bloqueando el flujo de los casos.
 * Esta task detecta esa pantalla y la cierra con el botón X (iv_close). NO usa
 * navigate().back() porque en este contexto eso cierra la app.
 */
public class CerrarTiendaClaro extends AndroidObject implements Task {

    private static final String IV_CLOSE = "com.clarocolombia.miclaro:id/iv_close";
    private static final int MAX_INTENTOS = 3;
    private static final String paso = "Se cierra la Tienda Claro para continuar el caso";

    @Override
    public <T extends Actor> void performAs(T actor) {
        if (!tiendaVisible(actor)) {
            return; // No hay tienda: no interrumpe el flujo normal
        }

        for (int intento = 1; intento <= MAX_INTENTOS && tiendaVisible(actor); intento++) {
            if (isVisible(actor, BTN_CERRAR_TIENDA_CLARO)) {
                actor.attemptsTo(
                        Atras.irAtras(),
                        WaitFor.aTime(1500));
            } else {
                // Botón X aún no renderizado; da un respiro y reintenta la detección
                actor.attemptsTo(WaitFor.aTime(1000));
            }
        }

        EvidenciaUtils.registrarCaptura(paso);
    }

    private <T extends Actor> boolean tiendaVisible(T actor) {
        return isVisible(actor, LBL_TIENDA_CLARO);
    }

    public <T extends Actor> boolean isVisible(T actor, Target element) {
        try {
            return !Presence.of(element).viewedBy(actor).resolveAll().isEmpty();
        } catch (Exception e) {
            return false;
        }
    }

    public static Performable cerrarTiendaClaro() {
        return instrumented(CerrarTiendaClaro.class);
    }
}