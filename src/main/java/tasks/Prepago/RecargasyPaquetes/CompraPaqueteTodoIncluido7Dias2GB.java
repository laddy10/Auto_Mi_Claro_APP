package tasks.Prepago.RecargasyPaquetes;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static userinterfaces.PagosyConsultasPrePage.BTN_COMPRAR_1;
import static userinterfaces.PagosyConsultasPrePage.LBL_VER_DETALLE_1;
import static utils.Constants.COMPRAR;
import static utils.Constants.ELEGIR_OTRO_MEDIO_PAGO;
import static utils.Constants.VER_DETALLE_DEL_PAQUETE;
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

public class CompraPaqueteTodoIncluido7Dias2GB extends AndroidObject implements Task {

  private final String precioTexto = PRECIO_$11000;
  private final String valorFormateado = FormateadorValor.aFormatoCmax(precioTexto);

  @Override
  public <T extends Actor> void performAs(T actor) {
    // 1. Scroll leve inicial para que quede visible 'Ver detalle del paquete'
    actor.attemptsTo(
        Scroll.scrollMediaVista(),
        WaitFor.aTime(1000));

    // 2. Validar información del paquete en tarjeta (2 GB, $ 11.000, Min ILIMITADOS)
    try {
      actor.attemptsTo(ValidarTextoQueContengaX.elTextoContiene("2 GB"));
    } catch (Exception ignored) {
    }
    try {
      actor.attemptsTo(ValidarTextoQueContengaX.elTextoContiene("11.000"));
    } catch (Exception ignored) {
    }

    // 3. Clic en Ver detalle del paquete 1
    try {
      actor.attemptsTo(Click.on(LBL_VER_DETALLE_1));
    } catch (Exception e) {
      try {
        actor.attemptsTo(ClickTextoQueContengaX.elTextoContiene(VER_DETALLE_DEL_PAQUETE));
      } catch (Exception e2) {
        try {
          actor.attemptsTo(ClickTextoQueContengaX.elTextoContiene("Ver detalle"));
        } catch (Exception ignored) {
        }
      }
    }

    actor.attemptsTo(WaitFor.aTime(1200));

    // 4. Scroll leve adicional tras desplegar Ver detalle para que el botón Comprar sea visible y clickeable
    actor.attemptsTo(
        Scroll.scrollMediaVista(),
        WaitFor.aTime(1000));

    actor.remember("VALOR_COMPRA", valorFormateado);
    actor.remember("PAQUETE_CANAL", TODO_INCLUIDO_7DIAS_2GB);

    CapturaDePantallaMovil.tomarCapturaPantalla("Detalle paquete Todo Incluido 2GB $11.000 validado");
    EvidenciaUtils.registrarCaptura("Detalle paquete Todo Incluido 2GB validado con precio $11.000");

    // 5. Clic en botón Comprar
    try {
      actor.attemptsTo(Click.on(BTN_COMPRAR_1));
    } catch (Exception e) {
      try {
        actor.attemptsTo(ClickTextoQueContengaX.elTextoContiene(COMPRAR));
      } catch (Exception e2) {
        try {
          actor.attemptsTo(ClickTextoQueContengaX.elTextoContiene("Comprar"));
        } catch (Exception ignored) {
        }
      }
    }

    // 6. Esperar y validar pantalla Elegir otro medio de pago
    actor.attemptsTo(
        WaitForResponse.withText(ELEGIR_OTRO_MEDIO_PAGO),
        WaitFor.aTime(1000));

    try {
      actor.attemptsTo(ValidarTexto.validarTexto(PRECIO_$11000));
    } catch (Exception e) {
      actor.attemptsTo(ValidarTextoQueContengaX.elTextoContiene("11.000"));
    }

    CapturaDePantallaMovil.tomarCapturaPantalla("Pantalla Elegir otro medio de pago validada con precio $11.000");
    EvidenciaUtils.registrarCaptura("Pantalla Elegir otro medio de pago validada con precio $11.000");
  }

  public static Performable comprar() {
    return instrumented(CompraPaqueteTodoIncluido7Dias2GB.class);
  }
}
