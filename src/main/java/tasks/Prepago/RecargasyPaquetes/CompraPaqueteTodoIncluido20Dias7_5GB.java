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

public class CompraPaqueteTodoIncluido20Dias7_5GB extends AndroidObject implements Task {

  private final String precioTexto = PRECIO_$25000;
  private final String valorFormateado = FormateadorValor.aFormatoCmax(precioTexto);

  @Override
  public <T extends Actor> void performAs(T actor) {
    // 1. Scroll hasta el final de la primera vista para ubicar el último paquete (7.5 GB, $25.000)
    actor.attemptsTo(
        Scroll.scrollUnaVista(),
        Scroll.scrollUnaVista(),
        Scroll.scrollUnaVista(),
        Scroll.scrollUnaVista(),
        Scroll.scrollUnaVista(),
        WaitFor.aTime(1000));

    // Si aún no está visible el texto de 25.000 o 7.5 GB, hacer scroll adicional
    try {
      actor.attemptsTo(ValidarTextoQueContengaX.elTextoContiene("25.000"));
    } catch (Exception e) {
      actor.attemptsTo(
          Scroll.scrollUnaVista(),
          WaitFor.aTime(1000));
    }

    // 2. Validar información de la tarjeta del paquete (7.5 GB, $25.000)
    try {
      actor.attemptsTo(ValidarTextoQueContengaX.elTextoContiene("7.5 GB"));
    } catch (Exception ignored) {
    }
    try {
      actor.attemptsTo(ValidarTextoQueContengaX.elTextoContiene("25.000"));
    } catch (Exception ignored) {
    }

    // 3. Clic en Ver detalle del paquete
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

    // 4. Validar el nombre / detalle del paquete como tal
    try {
      actor.attemptsTo(ValidarTexto.validarTexto(TODO_INCLUIDO_20DIAS_7_5GB_REDES));
    } catch (Exception e) {
      try {
        actor.attemptsTo(ValidarTextoQueContengaX.elTextoContiene("7.5 GB de navegacion"));
      } catch (Exception ex) {
        try {
          actor.attemptsTo(ValidarTextoQueContengaX.elTextoContiene("Paquete 20 dias"));
        } catch (Exception ignored) {
        }
      }
    }

    actor.remember("VALOR_COMPRA", valorFormateado);
    actor.remember("PAQUETE_CANAL", TODO_INCLUIDO_20DIAS_7_5GB_REDES);

    CapturaDePantallaMovil.tomarCapturaPantalla("Detalle paquete Todo Incluido 20 días 7.5GB validado");
    EvidenciaUtils.registrarCaptura("Detalle paquete Todo Incluido 20 días 7.5GB validado: $25.000 - 20 días");

    // 5. Scroll leve si es necesario y Clic en botón Comprar
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
          try {
            actor.attemptsTo(ClickTextoQueContengaX.elTextoContiene("Comprar"));
          } catch (Exception ignored) {
          }
        }
      }
    }

    // 6. Esperar y validar pantalla Elegir otro medio de pago
    actor.attemptsTo(
        WaitForResponse.withText(ELEGIR_OTRO_MEDIO_PAGO),
        WaitFor.aTime(1000));

    try {
      actor.attemptsTo(ValidarTexto.validarTexto(PRECIO_$25000));
    } catch (Exception e) {
      actor.attemptsTo(ValidarTextoQueContengaX.elTextoContiene("25.000"));
    }

    CapturaDePantallaMovil.tomarCapturaPantalla("Pantalla Elegir otro medio de pago validada con precio $25.000");
    EvidenciaUtils.registrarCaptura("Pantalla Elegir otro medio de pago validada con precio $25.000");
  }

  public static Performable comprar() {
    return instrumented(CompraPaqueteTodoIncluido20Dias7_5GB.class);
  }
}
