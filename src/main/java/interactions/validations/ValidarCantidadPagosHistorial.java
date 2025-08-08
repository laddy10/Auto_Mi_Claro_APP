package interactions.validations;

import static net.serenitybdd.screenplay.Tasks.instrumented;

import io.appium.java_client.AppiumBy;
import java.util.List;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Interaction;
import org.openqa.selenium.WebElement;
import utils.AndroidObject;

public class ValidarCantidadPagosHistorial implements Interaction {
  private final int cantidadMinima;

  public ValidarCantidadPagosHistorial(int cantidad) {
    this.cantidadMinima = cantidad;
  }

  public static ValidarCantidadPagosHistorial minimo(int cantidad) {
    return instrumented(ValidarCantidadPagosHistorial.class, cantidad);
  }

  @Override
  public <T extends Actor> void performAs(T actor) {
    // Contar elementos que contengan "Canal de pago:" (indica registro de pago)
    List<WebElement> registrosPagos =
        AndroidObject.androidDriver(actor)
            .findElements(
                AppiumBy.androidUIAutomator("new UiSelector().textContains(\"Canal de pago:\")"));

    int cantidadPagos = registrosPagos.size();
    System.out.println("Registros de pagos encontrados: " + cantidadPagos);

    if (cantidadPagos < cantidadMinima) {
      throw new AssertionError(
          "Se esperaban al menos "
              + cantidadMinima
              + " registros de pago, pero se encontraron "
              + cantidadPagos);
    }

    System.out.println(
        "✅ Validación exitosa: Se encontraron " + cantidadPagos + " registros de pago");
  }
}
