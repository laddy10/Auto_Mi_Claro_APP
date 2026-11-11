package tasks.Prepago.RecargasyPaquetes;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static userinterfaces.PagosyConsultasPrePage.BTN_COMPRAR_2;
import static userinterfaces.PagosyConsultasPrePage.LBL_VER_DETALLE_2;
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
import utils.CapturaDePantallaMovil;
import utils.EvidenciaUtils;
import utils.FormateadorValor;

public class CompraPaquete1000Minutos implements Task {

  private final String precioTexto = PRECIO_$18500;
  private final String valorFormateado = FormateadorValor.aFormatoCmax(precioTexto);

  @Override
  public <T extends Actor> void performAs(T actor) {
    // 1. Scroll para ubicar el paquete de 1000 Minutos ($18.500)
    actor.attemptsTo(
        Scroll.scrollUnaVista(),
        WaitFor.aTime(1000));

    // 2. Validar precio en catálogo
    try {
      actor.attemptsTo(ValidarTexto.validarTexto(PRECIO_$18500));
    } catch (Exception e) {
      actor.attemptsTo(ValidarTextoQueContengaX.elTextoContiene("18.500"));
    }

    // 3. Clic en Ver detalle del paquete 2
    try {
      actor.attemptsTo(Click.on(LBL_VER_DETALLE_2));
    } catch (Exception e) {
      actor.attemptsTo(ClickTextoQueContengaX.elTextoContiene(VER_DETALLE_DEL_PAQUETE));
    }

    actor.attemptsTo(WaitFor.aTime(1000));

    // 4. Validar información del paquete: 1000 minutos, 20 días
    try {
      actor.attemptsTo(ValidarTexto.validarTexto(PAQUETE_1000M_20DIAS));
    } catch (Exception e) {
      actor.attemptsTo(ValidarTextoQueContengaX.elTextoContiene("1000 minutos"));
    }

    actor.remember("VALOR_COMPRA", valorFormateado);
    actor.remember("PAQUETE_CANAL", PAQUETE_1000M_20DIAS);

    CapturaDePantallaMovil.tomarCapturaPantalla("Detalle paquete 1000 minutos validado");
    EvidenciaUtils.registrarCaptura("Detalle paquete 1000 minutos validado: $18.500 - 20 días");

    // 5. Clic en Comprar
    try {
      actor.attemptsTo(Click.on(BTN_COMPRAR_2));
    } catch (Exception e) {
      actor.attemptsTo(ClickTextoQueContengaX.elTextoContiene(COMPRAR));
    }

    // 6. Esperar pantalla Elegir otro medio de pago
    actor.attemptsTo(
        WaitForResponse.withText(ELEGIR_OTRO_MEDIO_PAGO),
        WaitFor.aTime(1000));

    // 7. Validar precio en pantalla de medio de pago
    try {
      actor.attemptsTo(ValidarTexto.validarTexto(PRECIO_$18500));
    } catch (Exception e) {
      actor.attemptsTo(ValidarTextoQueContengaX.elTextoContiene("18.500"));
    }

    CapturaDePantallaMovil.tomarCapturaPantalla("Pantalla Elegir otro medio de pago validada con precio $18.500");
    EvidenciaUtils.registrarCaptura("Pantalla Elegir otro medio de pago validada con precio $18.500");
  }

  public static Performable comprar() {
    return instrumented(CompraPaquete1000Minutos.class);
  }
}
