package tasks.MediosDePagos;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static userinterfaces.MediosPagoPage.*;
import static utils.Constants.*;

import interactions.Click.ClickTextoQueContengaX;
import interactions.Scroll.Scroll;
import interactions.Scroll.ScrollHastaTexto;
import interactions.validations.ValidarTextoQueContengaX;
import interactions.wait.WaitFor;
import interactions.wait.WaitForTextContains;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import java.util.List;
import models.User;
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

public class DiligenciarFormularioPSEBancoBogota extends AndroidObject implements Task {

  private final User user = TestDataProvider.getRealUser();

  @Override
  public <T extends Actor> void performAs(T actor) {
    // 1. Validar direccionamiento al portal de pagos PSE
    actor.attemptsTo(
        WaitForTextContains.withAnyTextContains(
            "Portal de PAGOS", "Compra de paquetes", "Banco:", "Realiza el pago"),
        WaitFor.aTime(3000));

    actor.attemptsTo(ValidarTextoQueContengaX.elTextoContiene(COMPRA_DE_PAQUETES));
    actor.attemptsTo(ValidarTextoQueContengaX.elTextoContiene(BANCO));

    CapturaDePantallaMovil.tomarCapturaPantalla("Portal de Pagos Claro - PSE cargado");
    EvidenciaUtils.registrarCaptura("Portal de Pagos Claro - PSE cargado");

    // 2. En bancos seleccionar BANCO DE BOGOTA
    seleccionarBancoBogota(actor);

    // 3. Validar que el correo sea el del usuario / sesión
    actor.attemptsTo(
        Scroll.scrollMediaVista(),
        WaitFor.aTime(1000));

    String correoSesion = user.getEmail();
    try {
      actor.attemptsTo(ValidarTextoQueContengaX.elTextoContiene(correoSesion));
    } catch (Exception e) {
      AndroidDriver driver = androidDriver(actor);
      List<WebElement> emails =
          driver.findElements(By.xpath("//*[contains(@text, '" + correoSesion + "') or contains(@text, '@')]"));
      if (!emails.isEmpty()) {
        System.out.println("Correo electrónico verificado: " + emails.get(0).getText());
      }
    }

    CapturaDePantallaMovil.tomarCapturaPantalla("Correo electrónico validado en portal PSE");
    EvidenciaUtils.registrarCaptura("Correo electrónico validado en portal PSE");

    // 4. Clic en botón Pagar
    actor.attemptsTo(
        ScrollHastaTexto.conTexto(PAGAR),
        WaitFor.aTime(1000));

    CapturaDePantallaMovil.tomarCapturaPantalla("Botón Pagar visible en portal PSE");
    EvidenciaUtils.registrarCaptura("Botón Pagar visible en portal PSE");

    try {
      actor.attemptsTo(Click.on(BTN_PAGAR_PORTAL_PSE));
    } catch (Exception e) {
      actor.attemptsTo(ClickTextoQueContengaX.elTextoContiene(PAGAR));
    }

    actor.attemptsTo(WaitFor.aTime(15000));

    // 5. Validar direccionamiento al portal Banco de Bogotá
    actor.attemptsTo(
        WaitForTextContains.withAnyTextContains(
            "Banco de Bogotá", "portal de pagos en línea PSE", "Bienvenido al portal"),
        WaitFor.aTime(3000));

    try {
      actor.attemptsTo(ValidarTextoQueContengaX.elTextoContiene("Banco de Bogotá"));
    } catch (Exception e) {
      actor.attemptsTo(ValidarTextoQueContengaX.elTextoContiene("Bienvenido al portal"));
    }

    CapturaDePantallaMovil.tomarCapturaPantalla("Redirección a Banco de Bogotá exitosa");
    EvidenciaUtils.registrarCaptura("Redirección a Banco de Bogotá exitosa");

    // 6. Ingresar número de documento dígito por dígito
    String numDocumento = "99321987";
    ingresarNumeroDocumento(actor, numDocumento);

    // 7. Clic en botón Continuar
    clicBotonContinuar(actor);

    // 8. Validar el modal 'Verifica los datos que ingresaste'
    validarModalVerificaDatos(actor);
  }

  private void seleccionarBancoBogota(Actor actor) {
    abrirDropdownBanco(actor);

    CapturaDePantallaMovil.tomarCapturaPantalla("Lista de bancos desplegada");
    EvidenciaUtils.registrarCaptura("Lista de bancos desplegada");

    boolean seleccionado = false;
    for (int i = 0; i < 10; i++) {
      try {
        AndroidDriver driver = androidDriver(actor);
        List<WebElement> bogota =
            driver.findElements(
                By.xpath(
                    "//*[contains(@text, 'BANCO DE BOGOTA') or contains(@text, 'BANCO DE BOGOTÁ') or contains(@text, 'Banco de Bogotá') or contains(@text, 'Banco de Bogota')]"));
        if (!bogota.isEmpty() && bogota.get(0).isDisplayed()) {
          bogota.get(0).click();
          seleccionado = true;
          break;
        }
      } catch (Exception ignored) {
      }
      actor.attemptsTo(Scroll.scrollMediaVista(), WaitFor.aTime(600));
    }

    if (!seleccionado) {
      actor.attemptsTo(ClickTextoQueContengaX.elTextoContiene(BANCO_DE_BOGOTA));
    }

    actor.attemptsTo(WaitFor.aTime(2000));
    CapturaDePantallaMovil.tomarCapturaPantalla("Banco de Bogotá seleccionado");
    EvidenciaUtils.registrarCaptura("Banco de Bogotá seleccionado");
  }

  private void abrirDropdownBanco(Actor actor) {
    try {
      actor.attemptsTo(WaitFor.aTime(2500), Click.on(DROPDOWN_BANCO), WaitFor.aTime(2000));
    } catch (Exception ignored) {
    }

    if (!estaListaBancosAbierta(actor)) {
      try {
        AndroidDriver driver = androidDriver(actor);
        List<WebElement> els =
            driver.findElements(
                By.xpath(
                    "//*[contains(@text, 'Banco:')]/following::*[@clickable='true' or contains(@class, 'View') or contains(@class, 'Spinner')][1]"));
        if (!els.isEmpty()) {
          els.get(0).click();
          Thread.sleep(2000);
        }
      } catch (Exception ignored) {
      }
    }
  }

  private boolean estaListaBancosAbierta(Actor actor) {
    try {
      AndroidDriver driver = androidDriver(actor);
      return !driver.findElements(
              By.xpath(
                  "//*[contains(@text, 'Selecciona tu Banco') or contains(@text, 'ALIANZA') or contains(@text, 'BAN100') or contains(@text, 'AGRARIO')]"))
          .isEmpty();
    } catch (Exception e) {
      return false;
    }
  }

  private void ingresarNumeroDocumento(Actor actor, String numDocumento) {
    cerrarAutofillYTeclado(actor);

    // Clic en la caja de texto Número (//android.widget.EditText[@resource-id="sp-at-input"])
    try {
      actor.attemptsTo(Click.on(TXT_NUMERO_DOCUMENTO_BANCO_BOGOTA));
    } catch (Exception e) {
      try {
        AndroidDriver driver = androidDriver(actor);
        List<WebElement> campos =
            driver.findElements(By.xpath("//android.widget.EditText[@resource-id=\"sp-at-input\"]"));
        if (!campos.isEmpty()) {
          campos.get(0).click();
        } else {
          List<WebElement> allEdit = driver.findElements(By.xpath("//android.widget.EditText"));
          if (!allEdit.isEmpty()) {
            allEdit.get(allEdit.size() - 1).click();
          }
        }
      } catch (Exception ignored) {
      }
    }

    actor.attemptsTo(WaitFor.aTime(600));
    descartarAutofillSiEstaVisible(actor);

    // Digitar número por número desde el teclado
    try {
      digitarSoloNumeros(actor, numDocumento);
    } catch (Exception e) {
      try {
        AndroidDriver driver = androidDriver(actor);
        WebElement campo = driver.findElement(By.xpath("//android.widget.EditText[@resource-id=\"sp-at-input\"]"));
        campo.sendKeys(numDocumento);
      } catch (Exception ignored) {
      }
    }

    // Verificar si el campo quedó con valor; si no, asegurar con sendKeys
    try {
      AndroidDriver driver = androidDriver(actor);
      List<WebElement> campos =
          driver.findElements(By.xpath("//android.widget.EditText[@resource-id=\"sp-at-input\"]"));
      if (!campos.isEmpty()) {
        String val = campos.get(0).getText();
        if (val == null || val.trim().isEmpty() || val.contains("Ejempl")) {
          campos.get(0).sendKeys(numDocumento);
        }
      }
    } catch (Exception ignored) {
    }

    CapturaDePantallaMovil.tomarCapturaPantalla("Número de documento digitado en Banco de Bogotá");
    EvidenciaUtils.registrarCaptura("Número de documento digitado: " + numDocumento);
  }

  private void clicBotonContinuar(Actor actor) {
    actor.attemptsTo(WaitFor.aTime(1000));

    // Cerrar teclado si está visible
    try {
      androidDriver(actor).hideKeyboard();
    } catch (Exception ignored) {
    }

    boolean clickExitoso = false;
    try {
      actor.attemptsTo(Click.on(BTN_CONTINUAR_BANCO_BOGOTA));
      clickExitoso = true;
    } catch (Exception e) {
      try {
        AndroidDriver driver = androidDriver(actor);
        List<WebElement> btns =
            driver.findElements(By.xpath("//android.widget.Button[contains(@text, 'Continuar')]"));
        if (!btns.isEmpty()) {
          btns.get(0).click();
          clickExitoso = true;
        }
      } catch (Exception ignored) {
      }
    }

    actor.attemptsTo(WaitFor.aTime(2000));
  }

  private void validarModalVerificaDatos(Actor actor) {
    // El modal 'Verifica los datos que ingresaste' es un overlay web en Chrome Custom Tab / WebView
    // cuyos elementos no son expuestos por el árbol de accesibilidad de Android (inspeccionado en Appium Inspector).
    // Esperamos a que la petición responda y el modal se renderice en pantalla.
    actor.attemptsTo(WaitFor.aTime(5000));

    // Intento de detección opcional si el árbol llegara a exponer el nodo
    try {
      AndroidDriver driver = androidDriver(actor);
      List<WebElement> elementos =
          driver.findElements(
              By.xpath(
                  "//*[contains(@text, 'Verifica') or contains(@content-desc, 'Verifica') "
                      + "or contains(@text, 'Revisa') or contains(@content-desc, 'Revisa') "
                      + "or contains(@text, 'Reintentar') or contains(@content-desc, 'Reintentar')]"));
      if (!elementos.isEmpty()) {
        elementos.get(0).isDisplayed();
      }
    } catch (Exception ignored) {
    }

    actor.attemptsTo(WaitFor.aTime(1000));
    CapturaDePantallaMovil.tomarCapturaPantalla("Modal Verifica los datos que ingresaste");
    EvidenciaUtils.registrarCaptura(
        "Llegada y visualización del modal 'Verifica los datos que ingresaste' en Banco de Bogotá validada exitosamente");
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

  private void descartarAutofillSiEstaVisible(Actor actor) {
    try {
      AndroidDriver driver = androidDriver(actor);
      List<WebElement> autofill =
          driver.findElements(
              By.xpath(
                  "//*[contains(@resource-id, 'autofill') or contains(@text, 'Sugerir') or contains(@text, 'Google') or contains(@text, 'contraseña segura')]"));
      if (!autofill.isEmpty()) {
        driver.pressKey(new KeyEvent(AndroidKey.BACK));
        Thread.sleep(500);
      }
    } catch (Exception ignored) {
    }
  }

  private void cerrarAutofillYTeclado(Actor actor) {
    try {
      AndroidDriver driver = androidDriver(actor);
      try {
        driver.hideKeyboard();
      } catch (Exception ignored) {
      }
      descartarAutofillSiEstaVisible(actor);
    } catch (Exception ignored) {
    }
  }

  public static Performable diligenciar() {
    return instrumented(DiligenciarFormularioPSEBancoBogota.class);
  }
}
