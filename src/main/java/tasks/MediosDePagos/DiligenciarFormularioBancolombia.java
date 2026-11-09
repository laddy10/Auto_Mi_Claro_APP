package tasks.MediosDePagos;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static userinterfaces.MediosPagoPage.*;
import static utils.Constants.*;

import interactions.Click.ClickTextoQueContengaX;
import interactions.validations.ValidarTextoQueContengaX;
import interactions.wait.WaitFor;
import interactions.wait.WaitForTextContains;
import io.appium.java_client.android.AndroidDriver;
import java.util.List;
import net.serenitybdd.core.pages.WebElementFacade;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Enter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import utils.AndroidObject;
import utils.CapturaDePantallaMovil;
import utils.EvidenciaUtils;

public class DiligenciarFormularioBancolombia extends AndroidObject implements Task {

  private static final int TIMEOUT_CARGA_BANCOLOMBIA = 60;
  private static final int TIMEOUT_CLAVE_PRINCIPAL = 30;
  private static final String USUARIO_PRUEBA = "Pruebas1234";

  @Override
  public <T extends Actor> void performAs(T actor) {
    // 1. Esperar redirección al portal de Bancolombia
    actor.attemptsTo(
        WaitForTextContains.withAnyTextContains(
            "Autenticación Bancolombia",
            "Te damos la bienvenida",
            "Bancolombia"),
        WaitFor.aTime(3000));

    actor.attemptsTo(
        ValidarTextoQueContengaX.elTextoContiene("Te damos la bienvenida"));

    CapturaDePantallaMovil.tomarCapturaPantalla("Portal de Bancolombia iniciado");
    EvidenciaUtils.registrarCaptura("Portal de Bancolombia iniciado");

    // 2. Esperar que el campo Usuario esté visible y habilitado
    esperarCampoUsuarioBancolombia(actor);

    // 3. Ingresar Usuario
    ingresarUsuario(actor, USUARIO_PRUEBA);

    CapturaDePantallaMovil.tomarCapturaPantalla("Usuario ingresado en Bancolombia");
    EvidenciaUtils.registrarCaptura("Usuario ingresado en Bancolombia: " + USUARIO_PRUEBA);

    // 4. Clic en Continuar
    clicBotonContinuar(actor);

    // 5. Esperar transición a pantalla de Clave Principal
    boolean clavePrincipalVisible = esperarClavePrincipal(actor, TIMEOUT_CLAVE_PRINCIPAL);

    // Si aún sigue en bienvenida, reintentar una sola vez el botón Continuar
    if (!clavePrincipalVisible) {
      if (sigueEnBienvenida(actor)) {
        EvidenciaUtils.registrarCaptura(
            "Bancolombia permaneció en la pantalla de bienvenida. Se reintenta una vez el botón Continuar.");
        clicBotonContinuar(actor);
        clavePrincipalVisible = esperarClavePrincipal(actor, TIMEOUT_CLAVE_PRINCIPAL);
      }
    }

    if (!clavePrincipalVisible) {
      CapturaDePantallaMovil.tomarCapturaPantalla("Bancolombia no cargó pantalla de Clave Principal");
      throw new IllegalStateException(
          "Bancolombia no mostró la pantalla de Clave principal dentro del tiempo esperado.");
    }

    // 6. Validación funcional final: Pantalla de Clave Principal
    actor.attemptsTo(
        ValidarTextoQueContengaX.elTextoContiene("Clave principal"));

    CapturaDePantallaMovil.tomarCapturaPantalla("Pantalla de Clave Principal Bancolombia validada");
    EvidenciaUtils.registrarCaptura("Pantalla de Clave Principal Bancolombia validada");
  }

  private void esperarCampoUsuarioBancolombia(Actor actor) {
    AndroidDriver driver = androidDriver(actor);
    boolean disponible = false;

    for (int i = 0; i < TIMEOUT_CARGA_BANCOLOMBIA; i++) {
      try {
        List<WebElement> campos = driver.findElements(By.className("android.widget.EditText"));
        if (!campos.isEmpty() && campos.get(0).isDisplayed()) {
          disponible = true;
          break;
        }
      } catch (Exception ignored) {
      }
      try {
        Thread.sleep(1000);
      } catch (InterruptedException ignored) {
      }
    }

    if (!disponible) {
      CapturaDePantallaMovil.tomarCapturaPantalla("Portal Bancolombia no habilitó campo Usuario");
      throw new IllegalStateException(
          "El portal de Bancolombia no terminó de cargar el campo Usuario después de "
              + TIMEOUT_CARGA_BANCOLOMBIA
              + " segundos.");
    }
  }

  private void ingresarUsuario(Actor actor, String usuario) {
    try {
      AndroidDriver driver = androidDriver(actor);
      List<WebElement> campos = driver.findElements(By.className("android.widget.EditText"));
      if (!campos.isEmpty()) {
        campos.get(0).click();
        Thread.sleep(500);
        campos.get(0).sendKeys(usuario);
      } else {
        actor.attemptsTo(
            Click.on(TXT_USUARIO_BANCOLOMBIA),
            Enter.theValue(usuario).into(TXT_USUARIO_BANCOLOMBIA));
      }
    } catch (Exception e) {
      try {
        WebElementFacade campo = TXT_CAMPO_USUARIO_BANCOLOMBIA.resolveFor(actor);
        campo.click();
        campo.sendKeys(usuario);
      } catch (Exception ignored) {
      }
    }

    // Ocultar teclado si quedó desplegado
    try {
      androidDriver(actor).hideKeyboard();
    } catch (Exception ignored) {
    }
  }

  private void clicBotonContinuar(Actor actor) {
    try {
      actor.attemptsTo(Click.on(BTN_CONTINUAR_BANCOLOMBIA));
    } catch (Exception e) {
      try {
        AndroidDriver driver = androidDriver(actor);
        List<WebElement> btns =
            driver.findElements(
                By.xpath("//android.widget.Button[@text='Continuar' or contains(@text, 'Continuar')]"));
        if (!btns.isEmpty()) {
          btns.get(0).click();
        } else {
          actor.attemptsTo(ClickTextoQueContengaX.elTextoContiene("Continuar"));
        }
      } catch (Exception ignored) {
      }
    }
  }

  private boolean esperarClavePrincipal(Actor actor, int timeoutSegundos) {
    AndroidDriver driver = androidDriver(actor);
    for (int i = 0; i < timeoutSegundos; i++) {
      try {
        List<WebElement> els =
            driver.findElements(
                By.xpath("//*[contains(@text, 'Clave principal') or contains(@text, 'cajero automático')]"));
        if (!els.isEmpty() && els.get(0).isDisplayed()) {
          return true;
        }
      } catch (Exception ignored) {
      }
      try {
        Thread.sleep(1000);
      } catch (InterruptedException ignored) {
      }
    }
    return false;
  }

  private boolean sigueEnBienvenida(Actor actor) {
    try {
      AndroidDriver driver = androidDriver(actor);
      return !driver.findElements(By.xpath("//*[contains(@text, 'Te damos la bienvenida')]")).isEmpty();
    } catch (Exception e) {
      return false;
    }
  }

  public static Performable diligenciar() {
    return instrumented(DiligenciarFormularioBancolombia.class);
  }
}
