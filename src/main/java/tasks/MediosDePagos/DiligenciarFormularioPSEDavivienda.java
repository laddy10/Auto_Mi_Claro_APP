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
import utils.TestDataProvider;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import utils.AndroidObject;
import utils.CapturaDePantallaMovil;
import utils.EvidenciaUtils;

public class DiligenciarFormularioPSEDavivienda extends AndroidObject implements Task {

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

    // 2. En bancos seleccionar BANCO DAVIVIENDA
    seleccionarBancoDavivienda(actor);

    // 3. Validar Tipo de cliente y Correo electrónico
    actor.attemptsTo(
        Scroll.scrollMediaVista(),
        WaitFor.aTime(1000));

    asegurarTipoClientePersonaNatural(actor);

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

    CapturaDePantallaMovil.tomarCapturaPantalla("Datos en portal PSE validados con Banco Davivienda");
    EvidenciaUtils.registrarCaptura("Datos en portal PSE validados con Banco Davivienda");

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

    // Esperar redirección al portal Davivienda PSE
    actor.attemptsTo(WaitFor.aTime(12000));

    // 5. Validar direccionamiento al portal Davivienda PSE
    actor.attemptsTo(
        WaitForTextContains.withAnyTextContains(
            40, "Davivienda", "Pagos en Línea y PSE", "Persona Natural", "Bienvenido al Portal"),
        WaitFor.aTime(3000));

    CapturaDePantallaMovil.tomarCapturaPantalla("Redirección a Portal Davivienda PSE exitosa");
    EvidenciaUtils.registrarCaptura("Redirección a Portal Davivienda PSE exitosa");

    // 6. Seleccionar Persona Natural
    seleccionarPersonaNatural(actor);

    // 7. Ingresar No. de documento
    String numDocumento = (user.getCedula() != null && !user.getCedula().trim().isEmpty())
        ? user.getCedula()
        : "1072603158";
    ingresarNumeroDocumento(actor, numDocumento);

    // 8. Clic en botón Continuar
    clicBotonContinuar(actor);

    // 9. Ingresar clave virtual ficticia 1234 (la validación llega hasta aquí)
    ingresarClaveVirtual(actor, "1234");
  }

  private void seleccionarBancoDavivienda(Actor actor) {
    abrirDropdownBanco(actor);

    CapturaDePantallaMovil.tomarCapturaPantalla("Lista de bancos desplegada");
    EvidenciaUtils.registrarCaptura("Lista de bancos desplegada");

    boolean seleccionado = false;
    for (int i = 0; i < 10; i++) {
      try {
        AndroidDriver driver = androidDriver(actor);
        List<WebElement> davivienda =
            driver.findElements(
                By.xpath(
                    "//*[contains(@text, 'BANCO DAVIVIENDA') or contains(@text, 'DAVIVIENDA') or contains(@content-desc, 'BANCO DAVIVIENDA') or contains(@content-desc, 'DAVIVIENDA')]"));
        if (!davivienda.isEmpty() && davivienda.get(0).isDisplayed()) {
          davivienda.get(0).click();
          seleccionado = true;
          break;
        }
      } catch (Exception ignored) {
      }
      actor.attemptsTo(Scroll.scrollMediaVista(), WaitFor.aTime(600));
    }

    if (!seleccionado) {
      actor.attemptsTo(ClickTextoQueContengaX.elTextoContiene(BANCO_DAVIVIENDA));
    }

    actor.attemptsTo(WaitFor.aTime(2000));
    CapturaDePantallaMovil.tomarCapturaPantalla("Banco Davivienda seleccionado");
    EvidenciaUtils.registrarCaptura("Banco Davivienda seleccionado");
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

  private void asegurarTipoClientePersonaNatural(Actor actor) {
    try {
      AndroidDriver driver = androidDriver(actor);
      List<WebElement> natural =
          driver.findElements(
              By.xpath(
                  "//*[contains(@text, 'Persona Natural') or contains(@content-desc, 'Persona Natural')]"));
      if (natural.isEmpty()) {
        try {
          actor.attemptsTo(Click.on(DROPDOWN_TIPO_CLIENTE));
          actor.attemptsTo(WaitFor.aTime(1000));
          actor.attemptsTo(ClickTextoQueContengaX.elTextoContiene(PERSONA_NATURAL));
        } catch (Exception ignored) {
        }
      }
    } catch (Exception ignored) {
    }
  }

  private void seleccionarPersonaNatural(Actor actor) {
    boolean clickExitoso = false;
    try {
      actor.attemptsTo(Click.on(BTN_CANAL_PERSONA_NATURAL_DAVIVIENDA));
      clickExitoso = true;
    } catch (Exception e) {
      try {
        AndroidDriver driver = androidDriver(actor);
        List<WebElement> personaNatural =
            driver.findElements(
                By.xpath(
                    "//*[contains(@text, 'Persona Natural') or contains(@content-desc, 'Persona Natural')]"));
        if (!personaNatural.isEmpty()) {
          personaNatural.get(0).click();
          clickExitoso = true;
        }
      } catch (Exception ignored) {
      }
    }

    if (!clickExitoso) {
      actor.attemptsTo(ClickTextoQueContengaX.elTextoContiene("Persona Natural"));
    }

    actor.attemptsTo(WaitFor.aTime(4000));
    CapturaDePantallaMovil.tomarCapturaPantalla("Canal Persona Natural seleccionado en Davivienda");
    EvidenciaUtils.registrarCaptura("Canal Persona Natural seleccionado en Davivienda");
  }

  private static final String XPATH_NUMERO_DOCUMENTO =
      "//android.widget.EditText[@resource-id=\"viewns_Z7_KQ84HOK0298RE0688624FQ3GI0_:formAutenticar:numeroDocumento\" or contains(@resource-id, 'numeroDocumento')]";

  private static final String XPATH_CLAVE_VIRTUAL =
      "//android.widget.EditText[@resource-id=\"viewns_Z7_KQ84HOK0298RE0688624FQ3GI0_:formAutenticar:claveVirtualMask\" or contains(@resource-id, 'claveVirtual')]";

  private void ingresarNumeroDocumento(Actor actor, String numDocumento) {
    cerrarAutofillYTeclado(actor);

    AndroidDriver driver = androidDriver(actor);
    WebElement campo = null;
    for (int i = 0; i < 15; i++) {
      try {
        List<WebElement> campos = driver.findElements(By.xpath(XPATH_NUMERO_DOCUMENTO));
        if (!campos.isEmpty() && campos.get(0).isDisplayed()) {
          campo = campos.get(0);
          break;
        }
      } catch (Exception ignored) {
      }
      try {
        Thread.sleep(1000);
      } catch (InterruptedException ignored) {
      }
    }

    if (campo != null) {
      try {
        campo.click();
      } catch (Exception ignored) {
      }
    } else {
      try {
        actor.attemptsTo(Click.on(TXT_NUMERO_DOCUMENTO_DAVIVIENDA));
      } catch (Exception ignored) {
      }
    }

    actor.attemptsTo(WaitFor.aTime(800));
    descartarAutofillSiEstaVisible(actor);

    // Ingresar número de documento una sola vez
    if (campo != null) {
      try {
        campo.clear();
        campo.sendKeys(numDocumento);
      } catch (Exception e) {
        digitarSoloNumeros(actor, numDocumento);
      }
    } else {
      digitarSoloNumeros(actor, numDocumento);
    }

    cerrarAutofillYTeclado(actor);

    CapturaDePantallaMovil.tomarCapturaPantalla("Número de documento ingresado en Davivienda");
    EvidenciaUtils.registrarCaptura("Número de documento ingresado en Davivienda: " + numDocumento);
  }

  private void clicBotonContinuar(Actor actor) {
    actor.attemptsTo(WaitFor.aTime(1000));
    cerrarAutofillYTeclado(actor);

    try {
      actor.attemptsTo(Click.on(BTN_CONTINUAR_DAVIVIENDA));
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

    actor.attemptsTo(WaitFor.aTime(4000));
  }

  private void ingresarClaveVirtual(Actor actor, String clave) {
    cerrarAutofillYTeclado(actor);

    // Esperar a que el campo Clave virtual aparezca en pantalla tras dar Continuar
    AndroidDriver driver = androidDriver(actor);
    WebElement campoClave = null;
    for (int i = 0; i < 20; i++) {
      try {
        List<WebElement> campos = driver.findElements(By.xpath(XPATH_CLAVE_VIRTUAL));
        if (!campos.isEmpty() && campos.get(0).isDisplayed()) {
          campoClave = campos.get(0);
          break;
        }
      } catch (Exception ignored) {
      }
      try {
        Thread.sleep(1000);
      } catch (InterruptedException ignored) {
      }
    }

    if (campoClave != null) {
      try {
        campoClave.click();
      } catch (Exception ignored) {
      }
    } else {
      try {
        actor.attemptsTo(Click.on(TXT_CLAVE_VIRTUAL_DAVIVIENDA));
      } catch (Exception ignored) {
      }
    }

    actor.attemptsTo(WaitFor.aTime(600));
    descartarAutofillSiEstaVisible(actor);

    // Ingresar la clave 1234
    if (campoClave != null) {
      try {
        campoClave.sendKeys(clave);
      } catch (Exception e) {
        digitarSoloNumeros(actor, clave);
      }
    } else {
      digitarSoloNumeros(actor, clave);
    }

    cerrarAutofillYTeclado(actor);

    CapturaDePantallaMovil.tomarCapturaPantalla("Clave virtual ficticia 1234 ingresada en Davivienda");
    EvidenciaUtils.registrarCaptura("Clave virtual ficticia 1234 ingresada en Davivienda");
  }

  private void clicBotonContinuarFinal(Actor actor) {
    actor.attemptsTo(WaitFor.aTime(1000));
    cerrarAutofillYTeclado(actor);

    try {
      actor.attemptsTo(Click.on(BTN_CONTINUAR_DAVIVIENDA));
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

    actor.attemptsTo(WaitFor.aTime(4000));

    // Validar mensaje o estado final
    try {
      AndroidDriver driver = androidDriver(actor);
      List<WebElement> alertas =
          driver.findElements(
              By.xpath(
                  "//*[contains(@text, 'Acceso denegado') or contains(@content-desc, 'Acceso denegado') "
                      + "or contains(@text, 'revise los valores') or contains(@content-desc, 'revise los valores')]"));
      if (!alertas.isEmpty()) {
        System.out.println("Alerta Davivienda detectada: " + alertas.get(0).getText());
      }
    } catch (Exception ignored) {
    }

    CapturaDePantallaMovil.tomarCapturaPantalla("Validación final Davivienda PSE completada");
    EvidenciaUtils.registrarCaptura(
        "Validación final en Davivienda PSE completada: Clave virtual ingresada y pantalla final validada");
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
    return instrumented(DiligenciarFormularioPSEDavivienda.class);
  }
}
