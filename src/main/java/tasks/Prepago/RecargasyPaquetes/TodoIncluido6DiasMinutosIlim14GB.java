package tasks.Prepago.RecargasyPaquetes;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static userinterfaces.PagosyConsultasPrePage.*;
import static utils.Constants.*;
import static utils.ConstantsPaquetes.*;

import interactions.Click.ClickTextoQueContengaX;
import interactions.Scroll.Scroll;
import interactions.validations.ValidarTexto;
import interactions.validations.ValidarTextoQueContengaX;
import interactions.wait.WaitFor;
import interactions.wait.WaitForResponse;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import utils.AndroidObject;
import utils.CapturaDePantallaMovil;
import utils.EvidenciaUtils;
import utils.FormateadorValor;

public class TodoIncluido6DiasMinutosIlim14GB extends AndroidObject implements Task {

    private final String precioTexto = PRECIO_$9000;
    private final String valorFormateado = FormateadorValor.aFormatoCmax(precioTexto);

    @Override
    public <T extends Actor> void performAs(T actor) {

        // Desplazamiento inicial y selección del segmento
        actor.attemptsTo(
                Scroll.scrollUnaVista(),
                Scroll.scrollUnaVista(),
                Scroll.scrollUnaVista(),
                Scroll.scrollUnaVista(),
                Scroll.scrollUnaVista(),
                ClickTextoQueContengaX.elTextoContiene(ULTIMO),
                Scroll.scrollUnaVista(),
                Scroll.scrollUnaVista(),
                Scroll.scrollUnaVista(),
                Scroll.scrollUnaVista(),
                Scroll.scrollUnaVista(),
                ClickTextoQueContengaX.elTextoContiene(ULTIMO),
                Scroll.scrollUnaVista(),
                Scroll.scrollUnaVista(),
                Scroll.scrollUnaVista(),
                Scroll.scrollUnaVista(),
                Scroll.scrollUnaVista(),
                ClickTextoQueContengaX.elTextoContiene(ULTIMO),
                ValidarTexto.validarTexto(precioTexto));

        // Ingreso al detalle del paquete
        actor.attemptsTo(
                ClickTextoQueContengaX.elTextoContiene(VER_DETALLE_DEL_PAQUETE),

                // Validación del nombre del paquete
                ValidarTexto.validarTexto(TODO_INCLUIDO_6DIAS_1_4GB));

        // Guardar datos para validaciones posteriores en CMAX
        actor.remember("VALOR_COMPRA", valorFormateado);
        actor.remember("PAQUETE_CANAL", TODO_INCLUIDO_6DIAS_1_4GB);

        // Evidencia visual
        CapturaDePantallaMovil.tomarCapturaPantalla("captura_pantalla");
        EvidenciaUtils.registrarCaptura("Detalle paquete Todo Incluido 6 días 1.4GB validado");

        // Scroll leve para hacer visible el botón Comprar
        actor.attemptsTo(
                Scroll.scrollMediaVista(),
                WaitFor.aTime(1000));

        // Flujo de compra
        try {
            actor.attemptsTo(Click.on(BTN_COMPRAR_1));
        } catch (Exception e) {
            try {
                actor.attemptsTo(
                        Scroll.scrollMediaVista(),
                        Click.on(BTN_COMPRAR_1));
            } catch (Exception e2) {
                try {
                    actor.attemptsTo(ClickTextoQueContengaX.elTextoContiene(COMPRAR));
                } catch (Exception e3) {
                    actor.attemptsTo(ClickTextoQueContengaX.elTextoContiene("Comprar"));
                }
            }
        }

        actor.attemptsTo(
                WaitForResponse.withText(ELEGIR_OTRO_MEDIO_PAGO),
                WaitFor.aTime(1000));

        try {
            actor.attemptsTo(ValidarTexto.validarTexto(precioTexto));
        } catch (Exception e) {
            actor.attemptsTo(ValidarTextoQueContengaX.elTextoContiene("9.000"));
        }

        EvidenciaUtils.registrarCaptura("Redirección a elegir medio de pago con precio $9.000");
    }

    public static Performable todoIncluido6DiasMinutosIlim14GB() {
        return instrumented(TodoIncluido6DiasMinutosIlim14GB.class);
    }
}
