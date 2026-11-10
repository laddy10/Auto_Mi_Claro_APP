package tasks.Prepago.RecargasyPaquetes;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static userinterfaces.PagosyConsultasPrePage.*;
import static utils.Constants.*;
import static utils.ConstantsPaquetes.*;

import interactions.Click.ClickTextoQueContengaX;
import interactions.Scroll.Scroll;
import interactions.validations.ValidarTexto;
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
                ClickTextoQueContengaX.elTextoContiene(ULTIMO),
                Scroll.scrollUnaVista(),
                Scroll.scrollUnaVista(),
                Scroll.scrollUnaVista(),
                Scroll.scrollUnaVista(),
                ClickTextoQueContengaX.elTextoContiene(ULTIMO),
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

        // Flujo de compra
        actor.attemptsTo(
                Click.on(BTN_COMPRAR_1),
                WaitForResponse.withText(ELEGIR_OTRO_MEDIO_PAGO),
                ValidarTexto.validarTexto(precioTexto));

        EvidenciaUtils.registrarCaptura("Redirección a elegir medio de pago con precio $9.000");
    }

    public static Performable todoIncluido6DiasMinutosIlim14GB() {
        return instrumented(TodoIncluido6DiasMinutosIlim14GB.class);
    }
}
