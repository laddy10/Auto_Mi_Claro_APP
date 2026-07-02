package tasks.Entretenimiento.ValidarRedirecciones;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static userinterfaces.EntretenimientoPage.*;
import static userinterfaces.LoginPage.TXT_USERNAME;
import static utils.Constants.*;

import interactions.Click.ClickElementByText;
import interactions.Click.ClickTextoQueContengaX;
import interactions.validations.ValidarElemento;
import interactions.validations.ValidarTexto;
import interactions.wait.WaitElement;
import interactions.wait.WaitFor;
import models.User;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Enter;
import net.serenitybdd.screenplay.questions.Presence;
import net.serenitybdd.screenplay.targets.Target;
import org.openqa.selenium.By;
import utils.EvidenciaUtils;
import utils.TestDataProvider;

public class ValidarRedireccionClaroMusicaApp implements Task {

  private final User user = TestDataProvider.getRealUser();
  private static final String paso = "Validar redirección a la aplicación Claro Música";

  @Override
  public <T extends Actor> void performAs(T actor) {

    // Esperar a que la app cargue
    try {
      Thread.sleep(12000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    actor.attemptsTo(WaitFor.aTime(1000)); // medio segundo antes de validar

    if (isVisible(actor, LBL_PERMITIR_NOTIFICACIONES)) {
      actor.attemptsTo(ClickElementByText.clickElementByText(NO_PERMITIR));
      EvidenciaUtils.registrarCaptura("Condición: No permitir notificaciones");
    }

    // ✅ Nueva condición: Si aparece "Aceptar y continuar", hacer clic
    if (isVisible(actor, BTN_ACEPTAR_CONTINUAR)) {
      actor.attemptsTo(Click.on(BTN_ACEPTAR_CONTINUAR), WaitFor.aTime(1000));
      EvidenciaUtils.registrarCaptura("Condición: Aceptar y continuar");
    }

    // Si aparece el mensaje de alerta, hacer clic en confirmar
    if (isVisible(actor, LBL_MENSAJE_ALERT)) {
      actor.attemptsTo(Click.on(BTN_ALERT_CONFIRM));
      EvidenciaUtils.registrarCaptura("Condición: Mensaje de alerta confirmado");
    } else {
      actor.attemptsTo(WaitFor.aTime(1000));
    }

    // ✅ Nueva condición:Si ingresa a una sesión verifica el mensaje y continúa
    if (isVisible(actor, MSJ_ALERTA_INGRESO)) {
      if (isVisible(actor, BTN_ENTENDIDO)) {
        actor.attemptsTo(ClickTextoQueContengaX.elTextoContiene(ENTENDIDO));
        EvidenciaUtils.registrarCaptura("Condición: Alerta de ingreso - Entendido");
      }
      actor.attemptsTo((Performable) ValidarElemento.esVisible(LOGO_CLARO_MUSICA));
      EvidenciaUtils.registrarCaptura("Condición: Sesión ya iniciada - Logo Claro Música visible");
    }

    // Validar elementos visibles en Claro Música si no hay sesiòn iniciada

    if (isVisible(actor, BTN_ESCUCHA_GRATIS)) {
      actor.attemptsTo(
              ValidarTexto.validarTexto("Escucha gratis"), ValidarTexto.validarTexto("Entrar"));
      EvidenciaUtils.registrarCaptura("Condición: Pantalla 'Escucha gratis' visible");
    }

    if (isVisible(actor, TXT_ABRIR_CON)) {
      actor.attemptsTo(ClickElementByText.clickElementByText(SOLO_UNA_VEZ));
      EvidenciaUtils.registrarCaptura("Condición: Abrir con - Solo una vez");
    }

    if (isVisible(actor, LBL_PERMITIR_ACTIVIDAD)) {
      actor.attemptsTo(ClickElementByText.clickElementByText(CANCELAR));
      EvidenciaUtils.registrarCaptura("Condición: Permitir actividad - Cancelar");
    }

    if (isVisible(actor, TXT_ABRIR)) {
      actor.attemptsTo(ClickElementByText.clickElementByText(ABRIR2));
      EvidenciaUtils.registrarCaptura("Condición: Abrir aplicación");
    }/*

    if (isVisible(actor, TXT_BIENVENIDO)) {
      actor.attemptsTo(ClickElementByText.clickElementByText(ACEPTAR_CONTINUAR));
      EvidenciaUtils.registrarCaptura("Condición: Pantalla de bienvenida");
    }

    if (isVisible(actor, TXT_ENTRAR)) {
      actor.attemptsTo(ClickElementByText.clickElementByText(ENTRAR));
      EvidenciaUtils.registrarCaptura("Condición: Botón Entrar");
    }

    if (isVisible(actor, TXT_INGRESA_CON_NUMERO)) {
      actor.attemptsTo(
              Click.on(BTN_CORREO),
              WaitElement.isEnable(TBX_CORREO),
              Enter.theValue(user.getEmailClaroMusica()).into(TBX_CORREO),
              WaitElement.isEnable(TBX_CONTRASENA),
              Enter.theValue(user.getpasswordClaroMusica()).into(TBX_CONTRASENA),
              Click.on(BTN_ENTRAR_CLARO_MUSICA),
              WaitElement.isEnable(LOGO_CLARO_MUSICA),
              WaitFor.aTime(3000));
      EvidenciaUtils.registrarCaptura("Condición: Login con correo Claro Música");
    }

    if (isVisible(actor, LBL_PERMITIR_NOTIFICACIONES)) {
      actor.attemptsTo(ClickElementByText.clickElementByText(PERMITIR));
      EvidenciaUtils.registrarCaptura("Condición: Permitir notificaciones");
    }

    if (isVisible(actor, TXT_BIENVENIDO_GRATIS)) {
      actor.attemptsTo(ClickElementByText.clickElementByText(ENTENDIDO));
      EvidenciaUtils.registrarCaptura("Condición: Bienvenido gratis - Entendido");
    }

    if (isVisible(actor, TXT_BIENVENIDO_ILIMITADO)) {
      actor.attemptsTo(ClickElementByText.clickElementByText(ENTENDIDO));
      EvidenciaUtils.registrarCaptura("Condición: Bienvenido ilimitado - Entendido");
    }

    actor.attemptsTo(
            ValidarTexto.validarTexto("Inicio"),
            ValidarTexto.validarTexto("Mi Música"),
            ValidarTexto.validarTexto("Buscar"),
            ValidarTexto.validarTexto("Radios"));

    EvidenciaUtils.registrarCaptura(paso);*/
  }

  private <T extends Actor> boolean isVisible(T actor, Target element) {
    return !Presence.of(element).viewedBy(actor).resolveAll().isEmpty();
  }

  public static Performable validar() {
    return instrumented(ValidarRedireccionClaroMusicaApp.class);
  }
}