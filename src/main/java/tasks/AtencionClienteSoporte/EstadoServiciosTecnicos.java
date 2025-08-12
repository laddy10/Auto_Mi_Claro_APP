package tasks.AtencionClienteSoporte;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static userinterfaces.AtencionClienteSoportePage.*;
import static utils.Constants.*;

import interactions.Click.ClickElementByText;
import interactions.Click.ClickTextoQueContengaX;
import interactions.validations.ValidarTexto;
import interactions.wait.WaitFor;
import interactions.wait.WaitForResponse;
import models.User;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Enter;
import utils.AndroidObject;
import utils.EvidenciaUtils;
import utils.TestDataProvider;

public class EstadoServiciosTecnicos implements Task {

  private final User user = TestDataProvider.getRealUser();

  @Override
  public <T extends Actor> void performAs(T actor) {
    EvidenciaUtils.registrarCaptura("Ingresar a Estado Servicios Técnicos");

    actor.attemptsTo(
        ClickTextoQueContengaX.elTextoContiene(ESTADO_SERVICIOS_TECNICOS),
        WaitForResponse.withText(POSTPAGO));
  }

  public static Performable ingresar() {
    return instrumented(EstadoServiciosTecnicos.class);
  }

  public static class SeleccionarLineaYVerDetalle implements Task {
    private final User user = TestDataProvider.getRealUser();

    @Override
    public <T extends Actor> void performAs(T actor) {
      EvidenciaUtils.registrarCaptura("Seleccionar línea y ver detalle");

      AndroidObject.scrollCorto2(actor, user.getNumero() + " " + VER_DETALLE);

      actor.attemptsTo(
          ClickTextoQueContengaX.elTextoContiene(user.getNumero()),
          WaitForResponse.withText(ORDENES_DE_SERVICIO));
    }

    public static Performable ejecutar() {
      return instrumented(SeleccionarLineaYVerDetalle.class);
    }
  }

  public static class ConsultarPorNumeroDocumento implements Task {
    private final User user = TestDataProvider.getRealUser();

    @Override
    public <T extends Actor> void performAs(T actor) {
      EvidenciaUtils.registrarCaptura("Consultar por número de documento");

      actor.attemptsTo(
          Click.on(COMBO_CRITERIO_BUSQUEDA),
          ClickTextoQueContengaX.elTextoContiene(NUMERO_DE_DOCUMENTO),
          Enter.theValue(user.getContrasena()).into(TXT_INGRESA_NUMERO),
          Click.on(BTN_CONSULTAR),
          WaitFor.aTime(3000),
          ValidarTexto.validarTexto(NO_SE_ENCONTRO_INFORMACION),
          ClickElementByText.clickElementByText(ACEPTAR_2));
    }

    public static Performable ejecutar() {
      return instrumented(ConsultarPorNumeroDocumento.class);
    }
  }

  public static class ConsultarPorNumeroCelular implements Task {
    private final User user = TestDataProvider.getRealUser();

    @Override
    public <T extends Actor> void performAs(T actor) {
      EvidenciaUtils.registrarCaptura("Consultar por número de celular");

      actor.attemptsTo(
          Click.on(COMBO_CRITERIO_BUSQUEDA),
          ClickTextoQueContengaX.elTextoContiene(NUMERO_DE_CELULAR),
          Enter.theValue(user.getNumero()).into(TXT_INGRESA_NUMERO),
          Click.on(BTN_CONSULTAR),
          WaitFor.aTime(3000),
          WaitForResponse.withText(NO_SE_ENCONTRO_INFORMACION),
          ValidarTexto.validarTexto(NO_SE_ENCONTRO_INFORMACION),
          ClickElementByText.clickElementByText(ACEPTAR_2));
    }

    public static Performable ejecutar() {
      return instrumented(ConsultarPorNumeroCelular.class);
    }
  }

  public static class ConsultarPorImei implements Task {
    private final User user = TestDataProvider.getRealUser();

    @Override
    public <T extends Actor> void performAs(T actor) {
      EvidenciaUtils.registrarCaptura("Consultar por IMEI");

      actor.attemptsTo(
          Click.on(COMBO_CRITERIO_BUSQUEDA),
          ClickTextoQueContengaX.elTextoContiene(IMEI),
          Enter.theValue(user.getImei()).into(TXT_INGRESA_NUMERO),
          Click.on(BTN_CONSULTAR),
          WaitFor.aTime(3000),
          WaitForResponse.withText(NO_SE_ENCONTRO_INFORMACION),
          ValidarTexto.validarTexto(NO_SE_ENCONTRO_INFORMACION),
          ClickElementByText.clickElementByText(ACEPTAR_2));
    }

    public static Performable ejecutar() {
      return instrumented(ConsultarPorImei.class);
    }
  }

  public static Performable seleccionarLineaYVerDetalle() {
    return SeleccionarLineaYVerDetalle.ejecutar();
  }

  public static Performable consultarPorNumeroDocumento() {
    return ConsultarPorNumeroDocumento.ejecutar();
  }

  public static Performable consultarPorNumeroCelular() {
    return ConsultarPorNumeroCelular.ejecutar();
  }

  public static Performable consultarPorImei() {
    return ConsultarPorImei.ejecutar();
  }
}
