package tasks.Prepago.RecargasyPaquetes;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static userinterfaces.PagosyConsultasPrePage.BTN_COMPRAR_2;
import static userinterfaces.PagosyConsultasPrePage.LBL_VER_DETALLE_2;
import static utils.Constants.*;
import static utils.ConstantsPaquetes.NAVEGACION_2GB_7DIAS;
import static utils.ConstantsPaquetes.NAVEGACION_2GB_7DIAS_ANTERIOR;
import static utils.ConstantsPaquetes.PRECIO_$9000;

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

public class Navegacion2GBWhatsappFacebookTwitter7Dias extends AndroidObject implements Task {

    private final String precioTexto = PRECIO_$9000;
    private final String valorFormateado = FormateadorValor.aFormatoCmax(precioTexto);

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                Scroll.scrollUnaVista(),
                Scroll.scrollUnaVista(),
                ClickTextoQueContengaX.elTextoContiene(ULTIMO));

        actor.attemptsTo(
                Scroll.scrollUnaVista(),
                ValidarTexto.validarTexto(precioTexto),
                ClickTextoQueContengaX.elTextoContiene(VER_DETALLE_DEL_PAQUETE),
                WaitFor.aTime(1500));

        try {
            actor.attemptsTo(ValidarTexto.validarTexto(NAVEGACION_2GB_7DIAS));
        } catch (Exception e) {
            try {
                actor.attemptsTo(ValidarTextoQueContengaX.elTextoContiene("WhatsApp, Facebook y X"));
            } catch (Exception ex) {
                actor.attemptsTo(ValidarTexto.validarTexto(NAVEGACION_2GB_7DIAS_ANTERIOR));
            }
        }

        actor.remember("VALOR_COMPRA", valorFormateado);
        actor.remember("PAQUETE_CANAL", NAVEGACION_2GB_7DIAS);

        CapturaDePantallaMovil.tomarCapturaPantalla("captura_pantalla");
        EvidenciaUtils.registrarCaptura("Validar paquete navegación 2GB 7 días");

        actor.attemptsTo(
                ClickTextoQueContengaX.elTextoContiene(COMPRAR),
                WaitForResponse.withText(ELEGIR_OTRO_MEDIO_PAGO),
                ValidarTexto.validarTexto(precioTexto));

        EvidenciaUtils.registrarCaptura("Redirección a elegir medio de pago");
    }

    public static Performable navegacion2GBWhatsappFacebookTwitter7Dias() {
        return instrumented(Navegacion2GBWhatsappFacebookTwitter7Dias.class);
    }
}
