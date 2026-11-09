package tasks.MediosDePagos;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static userinterfaces.MediosPagoPage.*;
import static utils.Constants.*;
import static utils.ConstantsPaquetes.PRECIO_$6500;

import interactions.Click.ClickTextoQueContengaX;
import interactions.validations.ValidarTextoQueContengaX;
import interactions.wait.WaitFor;
import interactions.wait.WaitForTextContains;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import java.util.List;
import models.User;
import net.serenitybdd.core.pages.WebElementFacade;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import utils.AndroidObject;
import utils.CapturaDePantallaMovil;
import utils.EvidenciaUtils;
import utils.TestDataProvider;

public class DiligenciarFormularioDaviplata extends AndroidObject implements Task {

  private final User user = TestDataProvider.getRealUser();

  @Override
  public <T extends Actor> void performAs(T actor) {
    // 1. Validar direccionamiento al portal de Daviplata
    actor.attemptsTo(
        WaitForTextContains.withAnyTextContains(
            "Daviplata",
            "DAVIplata",
            "Ingresa el documento asociado",
            "código de seguridad"),
        WaitFor.aTime(3000));

    try {
      actor.attemptsTo(
          ValidarTextoQueContengaX.elTextoContiene(INGRESA_DOCUMENTO_ASOCIADO));
    } catch (Exception e) {
      actor.attemptsTo(
          ValidarTextoQueContengaX.elTextoContiene("código de seguridad"));
    }

    // Validar valor a pagar en portal Daviplata
    try {
      actor.attemptsTo(
          ValidarTextoQueContengaX.elTextoContiene(PRECIO_$6500));
    } catch (Exception e) {
      actor.attemptsTo(
          ValidarTextoQueContengaX.elTextoContiene("6.500"));
    }

    CapturaDePantallaMovil.tomarCapturaPantalla("Portal Daviplata cargado con valor $6.500");
    EvidenciaUtils.registrarCaptura("Portal Daviplata cargado con valor $6.500");

    // 2. Ingresar número de documento
    String numDocumento =
        (user.getCedula() != null && !user.getCedula().isEmpty())
            ? user.getCedula()
            : "99321987";

    ingresarNumeroDocumento(actor, numDocumento);

    CapturaDePantallaMovil.tomarCapturaPantalla("Número de documento ingresado en Daviplata");
    EvidenciaUtils.registrarCaptura("Número de documento ingresado en Daviplata: " + numDocumento);

    // 3. Clic en botón Continuar
    actor.attemptsTo(WaitFor.aTime(1500));
    clicBotonContinuar(actor);

    // 4. Validar redireccionamiento a pantalla donde solicita el código de 6 dígitos
    validarPantallaCodigoDaviplata(actor);
  }

  private void validarPantallaCodigoDaviplata(Actor actor) {
    actor.attemptsTo(
        WaitForTextContains.withAnyTextContains(
            "6 dígitos",
            "codigo de 6 dígitos",
            "código de 6 dígitos",
            "expira en",
            "Anular transacción"),
        WaitFor.aTime(3000));

    boolean pantallaCodigoVisible = false;
    try {
      actor.attemptsTo(
          ValidarTextoQueContengaX.elTextoContiene("6 dígitos"));
      pantallaCodigoVisible = true;
    } catch (Exception e) {
      try {
        actor.attemptsTo(
            ValidarTextoQueContengaX.elTextoContiene("expira en"));
        pantallaCodigoVisible = true;
      } catch (Exception ex) {
        AndroidDriver driver = androidDriver(actor);
        List<WebElement> els =
            driver.findElements(
                By.xpath(
                    "//*[contains(@text, '6 dígitos') or contains(@text, 'codigo de 6') or contains(@text, 'expira en') or contains(@text, 'Anular transacción')]"));
        if (!els.isEmpty()) {
          pantallaCodigoVisible = true;
        }
      }
    }

    if (!pantallaCodigoVisible) {
      throw new AssertionError(
          "No se visualizó la pantalla de solicitud de código de 6 dígitos en Daviplata");
    }

    CapturaDePantallaMovil.tomarCapturaPantalla("Pantalla solicitud de código Daviplata validada exitosamente");
    EvidenciaUtils.registrarCaptura(
        "Pantalla solicitud de código Daviplata validada exitosamente: Ingresa el codigo de 6 dígitos");
  }

  private void ingresarNumeroDocumento(Actor actor, String numDocumento) {
    // Click en el campo número de documento
    try {
      actor.attemptsTo(Click.on(TXT_NUMERO_DOCUMENTO_DAVIPLATA));
    } catch (Exception e) {
      try {
        AndroidDriver driver = androidDriver(actor);
        List<WebElement> campos = driver.findElements(By.className("android.widget.EditText"));
        if (!campos.isEmpty()) {
          campos.get(0).click();
        } else {
          actor.attemptsTo(Click.on(TXT_INGRESAR_NUMERO_DOCUMENTO_DAVIPLATA));
        }
      } catch (Exception ignored) {
      }
    }

    actor.attemptsTo(WaitFor.aTime(800));

    // Digitar número por número desde el teclado
    try {
      digitarSoloNumeros(actor, numDocumento);
    } catch (Exception e) {
      try {
        AndroidDriver driver = androidDriver(actor);
        List<WebElement> campos = driver.findElements(By.className("android.widget.EditText"));
        if (!campos.isEmpty()) {
          campos.get(0).sendKeys(numDocumento);
        }
      } catch (Exception ignored) {
      }
    }

    // Verificar si el campo quedó con valor; si no, asegurar con sendKeys
    try {
      AndroidDriver driver = androidDriver(actor);
      List<WebElement> campos = driver.findElements(By.className("android.widget.EditText"));
      if (!campos.isEmpty()) {
        String val = campos.get(0).getText();
        if (val == null || val.trim().isEmpty()) {
          campos.get(0).sendKeys(numDocumento);
        }
      }
    } catch (Exception ignored) {
    }

    // Ocultar teclado
    try {
      androidDriver(actor).hideKeyboard();
    } catch (Exception ignored) {
    }
  }

  private void clicBotonContinuar(Actor actor) {
    try {
      actor.attemptsTo(Click.on(BTN_CONTINUAR_DAVIPLATA));
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

  private <T extends Actor> void digitarSoloNumeros(T actor, String valor) {
    for (char caracter : valor.toCharArray()) {
      if (Character.isDigit(caracter)) {
        digitarDesdeTeclado(actor, String.valueOf(caracter));
      }
    }
  }

  private void digitarDesdeTeclado(Actor actor, String digito) {
    try {
      AndroidDriver driver = androidDriver(actor);
      char c = digito.charAt(0);
      AndroidKey key;
      switch (c) {
        case '0': key = AndroidKey.DIGIT_0; break;
        case '1': key = AndroidKey.DIGIT_1; break;
        case '2': key = AndroidKey.DIGIT_2; break;
        case '3': key = AndroidKey.DIGIT_3; break;
        case '4': key = AndroidKey.DIGIT_4; break;
        case '5': key = AndroidKey.DIGIT_5; break;
        case '6': key = AndroidKey.DIGIT_6; break;
        case '7': key = AndroidKey.DIGIT_7; break;
        case '8': key = AndroidKey.DIGIT_8; break;
        case '9': key = AndroidKey.DIGIT_9; break;
        default: return;
      }
      driver.pressKey(new KeyEvent(key));
      try {
        Thread.sleep(120);
      } catch (InterruptedException ignored) {
      }
    } catch (Exception e) {
      AndroidObject.digitarDesdeTeclado(digito);
    }
  }

  public static Performable diligenciar() {
    return instrumented(DiligenciarFormularioDaviplata.class);
  }
}
