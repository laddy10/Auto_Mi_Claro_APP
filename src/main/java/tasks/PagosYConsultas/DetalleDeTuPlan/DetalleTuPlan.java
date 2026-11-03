package tasks.PagosYConsultas.DetalleDeTuPlan;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static userinterfaces.PagosYConsultasPage.BTN_DETALLE_DE_TU_PLAN;
import static userinterfaces.PagosYConsultasPage.BTN_TRES_PUNTOS_MAS;
import static utils.Constants.*;
import static utils.ConstantsMiniVersiones.Versiones.MINI_VERSION_DETALLE_DE_TU_PLAN_CONSTANT;

import interactions.Click.ClickTextoQueContengaX;
import interactions.Scroll.Scroll;
import interactions.comunes.Atras;
import interactions.validations.ValidarTexto;
import interactions.validations.ValidarTextoQueContengaX;
import interactions.validations.VerificarVersionModulo;
import interactions.wait.WaitFor;
import interactions.wait.WaitForResponse;
import models.User;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import utils.AndroidObject;
import utils.EvidenciaUtils;
import utils.TestDataProvider;

public class DetalleTuPlan implements Task {
  private static final User user = TestDataProvider.getRealUser();
  private static final String paso1 = "Seleccionar Detalle de tu plan";
  private static final String paso2 =
      "Seleccionar línea postpago " + user.getNumeroFamiliayAmigos();
  private static final String paso3 = "Validar versión de miniprograma";
  private static final String paso4 = "Validar información detalle del plan";
  private static final String paso5 = "Validar opciones disponibles";

  @Override
  public <T extends Actor> void performAs(T actor) {

    EvidenciaUtils.registrarCaptura(paso1);

    // Seleccionar "Detalle de tu plan"
      WaitFor.aTime(2000);
  if(isElementoVisible(actor, DETALLE_DE_TU_PLAN)) {
    actor.attemptsTo(
            ClickTextoQueContengaX.elTextoContiene(DETALLE_DE_TU_PLAN),
            WaitForResponse.withText(POSTPAGO));
  }else{
    actor.attemptsTo(Click.on(BTN_DETALLE_DE_TU_PLAN));
  }

    EvidenciaUtils.registrarCaptura(paso2);

    // Seleccionar línea postpago
    AndroidObject.scrollCorto2(
        actor, LINEA + " " + user.getNumeroFamiliayAmigos() + " " + VER_DETALLE);

    actor.attemptsTo(
        ClickTextoQueContengaX.elTextoContiene(user.getNumeroFamiliayAmigos()),
        WaitForResponse.withText(DETALLE_DE_TU_PLAN));

    // Validar versión de miniprograma
    actor.attemptsTo(
        WaitFor.aTime(2000),
        Click.on(BTN_TRES_PUNTOS_MAS),
        ClickTextoQueContengaX.elTextoContiene(ACERCA_DE),
        WaitForResponse.withText(DETALLE_DE_TU_PLAN_POSTPAGO),
        ValidarTexto.validarTexto(DETALLE_DE_TU_PLAN_POSTPAGO),
        ValidarTexto.validarTexto(DECLARACION_SERVICIO),
        ValidarTextoQueContengaX.elTextoContiene(VER));

    EvidenciaUtils.registrarCaptura(paso3);

    actor.attemptsTo(
        VerificarVersionModulo.conLaEsperada(MINI_VERSION_DETALLE_DE_TU_PLAN_CONSTANT));

    // Validar información detalle del plan
    actor.attemptsTo(
        Atras.irAtras(),
        WaitFor.aTime(4000),
        ValidarTexto.validarTexto(CONSULTA_LA_FACTURA_DE_TU_PLAN_POSTPAGO),
        ValidarTexto.validarTexto(VER_PAGAR_FACTURA),
        // ValidarTexto.validarTexto(TU_PLAN),
        // ValidarTextoQueContengaX.elTextoContiene(CARGO_FIJO_MENSUAL_IVA_INCLUIDO),

        // Validar datos del plan
        ValidarTexto.validarTexto(DATOS),
        // ValidarTextoQueContengaX.elTextoContiene(TU_PLAN_INCLUYE),
        // ValidarTextoQueContengaX.elTextoContiene(VER_DETALLE_DE_REDES),

        // Validar voz
        ValidarTexto.validarTexto(VOZ),
        ValidarTextoQueContengaX.elTextoContiene(SEGUNDOS_PARA_LLAMAR));

    EvidenciaUtils.registrarCaptura(paso4);

    actor.attemptsTo(
        // Validar SMS
        ValidarTexto.validarTexto(SMS),
        Scroll.scrollUnaVista(),

        // Validar Larga Distancia Internacional
        // ValidarTexto.validarTexto(LARGA_DISTANCIA_INTERNACIONAL),

        // Validar texto botones
        Scroll.scrollUnaVista(),
        ValidarTexto.validarTexto(DESCRIPCION_DEL_PLAN),
        ValidarTexto.validarTexto(FAMILIA_Y_AMIGOS),
        ValidarTexto.validarTexto(APLICACIONES_ELEGIBLES),
        ValidarTexto.validarTexto(PAQUETES_ADICIONALES),
        ValidarTexto.validarTexto(ADMINISTRAR_ROAMING));

    EvidenciaUtils.registrarCaptura(paso5);
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

  public static Performable validarDetalleTuPlan() {
    return instrumented(DetalleTuPlan.class);
  }
}
