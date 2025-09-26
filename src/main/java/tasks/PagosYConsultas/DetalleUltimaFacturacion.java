package tasks.PagosYConsultas;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static utils.Constants.*;

import interactions.Click.ClickElementByText;
import interactions.Click.ClickTextoQueContengaX;
import interactions.Scroll.ScrollHastaTexto;
import interactions.validations.ValidarTexto;
import interactions.validations.ValidarTextoQueContengaX;
import interactions.wait.WaitForResponse;
import models.User;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import utils.AndroidObject;
import utils.EvidenciaUtils;
import utils.TestDataProvider;

public class DetalleUltimaFacturacion implements Task {
  private final User user = TestDataProvider.getRealUser();
  private static final String paso1 = "Seleccionar Detalle última facturación";
  private static final String paso2 = "Validar información en popup";
  private static final String paso3 = "Validar detalle completo de facturación";
  private static final String paso4 = "Validar opciones disponibles";

  @Override
  public <T extends Actor> void performAs(T actor) {

    // Paso 1: Llegar a la sección de últimos pagos
    actor.attemptsTo(ScrollHastaTexto.conTexto(ULTIMOS_PAGOS));
    EvidenciaUtils.registrarCaptura(paso1);

    // Seleccionar "Detalle última facturación"
    actor.attemptsTo(
            ClickTextoQueContengaX.elTextoContiene(DETALLE_ULTIMA_FACTURACION),
            WaitForResponse.withText(ACEPTAR_2));

    // 🔹 Validar si aparece popup "TU_FACTURA_ESTARA_DISPONIBLE"
    if (isElementoVisible(actor, TU_FACTURA_ESTARA_DISPONIBLE)) {
      actor.attemptsTo(
              ValidarTexto.validarTexto(TU_FACTURA_ESTARA_DISPONIBLE),
              ValidarTexto.validarTexto(ACEPTAR_2));

      EvidenciaUtils.registrarCaptura(paso2);
      return; // 🚪 Finaliza aquí el caso, no continúa a pasos 3 y 4
    }

    // 🔹 Si no aparece popup, continuar flujo normal
    actor.attemptsTo(
            ClickElementByText.clickElementByText(ACEPTAR_2),
            WaitForResponse.withText(GERENCIA_DCT));

    actor.attemptsTo(
            ValidarTextoQueContengaX.elTextoContiene(GERENCIA_DCT),
            ValidarTextoQueContengaX.elTextoContiene(PAGA_AQUI),
            ValidarTextoQueContengaX.elTextoContiene(VALOR_TOTAL_A_PAGAR),
            ValidarTextoQueContengaX.elTextoContiene(FECHA_LIMITE_PAGO),
            ValidarTextoQueContengaX.elTextoContiene(MES_FACTURADO),
            ValidarTextoQueContengaX.elTextoContiene(NUMERO_DE_MOVIL),
            ValidarTextoQueContengaX.elTextoContiene(user.getNumero().replace(" ", "")));

    EvidenciaUtils.registrarCaptura(paso3);

    actor.attemptsTo(ScrollHastaTexto.conTexto(CHATEA_CON_NOSOTROS));
    EvidenciaUtils.registrarCaptura(paso4);
  }

  // 🔹 Método auxiliar para validar si un texto está visible en pantalla
  private boolean isElementoVisible(Actor actor, String texto) {
    try {
      WebElement elemento =
              AndroidObject.androidDriver(actor)
                      .findElement(By.xpath("//*[contains(@text,'" + texto + "')]"));
      return elemento.isDisplayed();
    } catch (Exception e) {
      return false;
    }
  }

  public static Performable validarDetalleUltimaFacturacion() {
    return instrumented(DetalleUltimaFacturacion.class);
  }
}
