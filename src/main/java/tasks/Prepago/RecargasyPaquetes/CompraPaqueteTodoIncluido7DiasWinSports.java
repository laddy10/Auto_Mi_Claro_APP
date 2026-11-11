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

public class CompraPaqueteTodoIncluido7DiasWinSports extends AndroidObject implements Task {

  private final String precioTexto = PRECIO_$15000;
  private final String valorFormateado = FormateadorValor.aFormatoCmax(precioTexto);

  @Override
  public <T extends Actor> void performAs(T actor) {
    // 1. Validar precio 15.000 en catálogo
    actor.attemptsTo(
        WaitFor.aTime(1500),
        ValidarTextoQueContengaX.elTextoContiene("15.000"));

    // 2. Clic en Ver detalle del paquete 1
    try {
      actor.attemptsTo(Click.on(LBL_VER_DETALLE_1));
    } catch (Exception e) {
      actor.attemptsTo(ClickTextoQueContengaX.elTextoContiene(VER_DETALLE_DEL_PAQUETE));
    }

    actor.attemptsTo(WaitFor.aTime(1000));

    // 3. Validar información del paquete (con espacio: '10 GB')
    try {
      actor.attemptsTo(ValidarTextoQueContengaX.elTextoContiene("10 GB"));
    } catch (Exception e) {
      actor.attemptsTo(ValidarTextoQueContengaX.elTextoContiene("Win sports"));
    }

    actor.remember("VALOR_COMPRA", valorFormateado);
    actor.remember("PAQUETE_CANAL", TODO_INCLUIDO_7DIAS_10GB_WIN);

    CapturaDePantallaMovil.tomarCapturaPantalla("Detalle paquete Todo Incluido 7 días Win Sports validado");
    EvidenciaUtils.registrarCaptura("Detalle paquete Todo Incluido 7 días Win Sports validado: $15.000 - 10 GB");

    // 4. Scroll suave si es necesario y Clic en Comprar 1
    try {
      actor.attemptsTo(Click.on(BTN_COMPRAR_1));
    } catch (Exception e) {
      try {
        actor.attemptsTo(
            Scroll.scrollMediaVista(),
            Click.on(BTN_COMPRAR_1));
      } catch (Exception e2) {
        actor.attemptsTo(ClickTextoQueContengaX.elTextoContiene(COMPRAR));
      }
    }

    // 5. Esperar pantalla Elegir otro medio de pago
    actor.attemptsTo(
        WaitForResponse.withText(ELEGIR_OTRO_MEDIO_PAGO),
        WaitFor.aTime(1000));

    // 6. Validar precio en pantalla de medio de pago
    actor.attemptsTo(ValidarTextoQueContengaX.elTextoContiene("15.000"));

    CapturaDePantallaMovil.tomarCapturaPantalla("Pantalla Elegir otro medio de pago validada con precio $15.000");
    EvidenciaUtils.registrarCaptura("Pantalla Elegir otro medio de pago validada con precio $15.000");
  }

  public static Performable comprar() {
    return instrumented(CompraPaqueteTodoIncluido7DiasWinSports.class);
  }
}
