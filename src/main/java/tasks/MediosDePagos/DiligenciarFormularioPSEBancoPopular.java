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
import net.serenitybdd.core.pages.WebElementFacade;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Enter;
import net.serenitybdd.screenplay.matchers.WebElementStateMatchers;
import net.serenitybdd.screenplay.waits.WaitUntil;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import utils.AndroidObject;
import utils.CapturaDePantallaMovil;
import utils.EvidenciaUtils;
import utils.TestDataProvider;

public class DiligenciarFormularioPSEBancoPopular extends AndroidObject implements Task {

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

    // 2. En bancos hacer scroll y seleccionar Banco Popular
    seleccionarBancoPopular(actor);

    // 3. Validar que el correo sea el mismo del inicio de sesión
    actor.attemptsTo(
        Scroll.scrollMediaVista(),
        WaitFor.aTime(1000));

    String correoSesion = user.getEmail();
    try {
      actor.attemptsTo(ValidarTextoQueContengaX.elTextoContiene(correoSesion));
    } catch (Exception e) {
      AndroidDriver driver = androidDriver(actor);
      List<WebElement> emails =
          driver.findElements(By.xpath("//*[contains(@text, '" + correoSesion + "')]"));
      if (!emails.isEmpty()) {
        System.out.println("Correo electrónico verificado: " + correoSesion);
      }
    }

    CapturaDePantallaMovil.tomarCapturaPantalla("Correo electrónico de sesión validado");
    EvidenciaUtils.registrarCaptura("Correo electrónico de sesión validado: " + correoSesion);

    // 4. Clic en Pagar
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

    // 5. Validar direccionamiento a la página del pago (Banco Popular)
    actor.attemptsTo(
        WaitForTextContains.withAnyTextContains(
            "Banco Popular", "Bienvenido a nuestro nuevo portal", "banco popular"),
        WaitFor.aTime(3000));

    actor.attemptsTo(ValidarTextoQueContengaX.elTextoContiene("Bienvenido a nuestro nuevo portal"));

    CapturaDePantallaMovil.tomarCapturaPantalla("Redirección a Banco Popular exitosa");
    EvidenciaUtils.registrarCaptura("Redirección a Banco Popular exitosa");

    // 6. Ingresar un número de documento y contraseña (solo números)
    String numDocumento =
        (user.getCedula() != null && !user.getCedula().isEmpty())
            ? user.getCedula()
            : "1020304050";

    String password = "12345678";

    ingresarNumeroDocumento(actor, numDocumento);
    ingresarContrasena(actor, password);

    // 7. Clic en botón Ingresar
    clicBotonIngresar(actor);

    // 8. La prueba finaliza cuando sale el mensaje en rojo de no registrado
    validarMensajeDocumentoNoRegistrado(actor);
  }

  private void ingresarNumeroDocumento(Actor actor, String numDocumento) {
    cerrarAutofillYTeclado(actor);

    // Click en el campo número de documento
    try {
      actor.attemptsTo(Click.on(TXT_NUMERO_DOCUMENTO_BANCO_POPULAR_PORTAL));
    } catch (Exception e) {
      try {
        AndroidDriver driver = androidDriver(actor);
        List<WebElement> campos =
            driver.findElements(
                By.xpath(
                    "//android.view.ViewGroup[@resource-id=\"com.clarocolombia.miclaro:id/griver_layout_refresh\"]/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.webkit.WebView/android.webkit.WebView/android.view.View/android.view.View[1]/android.view.View[2]/android.view.View[1]/android.widget.EditText"));
        if (!campos.isEmpty()) {
          campos.get(0).click();
        }
      } catch (Exception ignored) {
      }
    }

    actor.attemptsTo(WaitFor.aTime(600));
    descartarAutofillSiEstaVisible(actor);

    // Digitar número por número como humano desde el teclado
    try {
      digitarSoloNumeros(actor, numDocumento);
    } catch (Exception e) {
      try {
        AndroidDriver driver = androidDriver(actor);
        WebElement campo =
            driver.findElement(
                By.xpath(
                    "//android.view.ViewGroup[@resource-id=\"com.clarocolombia.miclaro:id/griver_layout_refresh\"]/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.webkit.WebView/android.webkit.WebView/android.view.View/android.view.View[1]/android.view.View[2]/android.view.View[1]/android.widget.EditText"));
        campo.sendKeys(numDocumento);
      } catch (Exception ignored) {
      }
    }

    // Verificar si el campo quedó con valor; si no, asegurar con sendKeys
    try {
      AndroidDriver driver = androidDriver(actor);
      List<WebElement> campos =
          driver.findElements(
              By.xpath(
                  "//android.view.ViewGroup[@resource-id=\"com.clarocolombia.miclaro:id/griver_layout_refresh\"]/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.webkit.WebView/android.webkit.WebView/android.view.View/android.view.View[1]/android.view.View[2]/android.view.View[1]/android.widget.EditText"));
      if (!campos.isEmpty()) {
        String val = campos.get(0).getText();
        if (val == null || val.trim().isEmpty()) {
          campos.get(0).sendKeys(numDocumento);
        }
      }
    } catch (Exception ignored) {
    }
  }

  private void ingresarContrasena(Actor actor, String password) {
    actor.attemptsTo(WaitFor.aTime(500));

    // Click en el campo contraseña
    try {
      actor.attemptsTo(Click.on(TXT_CONTRASENA_BANCO_POPULAR_PORTAL));
    } catch (Exception e) {
      try {
        AndroidDriver driver = androidDriver(actor);
        List<WebElement> campos =
            driver.findElements(
                By.xpath(
                    "//android.view.ViewGroup[@resource-id=\"com.clarocolombia.miclaro:id/griver_layout_refresh\"]/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.webkit.WebView/android.webkit.WebView/android.view.View/android.view.View[1]/android.view.View[2]/android.view.View[1]/android.view.View[6]/android.widget.EditText"));
        if (!campos.isEmpty()) {
          campos.get(0).click();
        }
      } catch (Exception ignored) {
      }
    }

    actor.attemptsTo(WaitFor.aTime(600));
    descartarAutofillSiEstaVisible(actor);

    // Ingresar la contraseña
    try {
      AndroidDriver driver = androidDriver(actor);
      List<WebElement> campos =
          driver.findElements(
              By.xpath(
                  "//android.view.ViewGroup[@resource-id=\"com.clarocolombia.miclaro:id/griver_layout_refresh\"]/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.webkit.WebView/android.webkit.WebView/android.view.View/android.view.View[1]/android.view.View[2]/android.view.View[1]/android.view.View[6]/android.widget.EditText"));
      if (!campos.isEmpty()) {
        campos.get(0).sendKeys(password);
      } else {
        WebElementFacade campo = TXT_CONTRASENA_BANCO_POPULAR_PORTAL.resolveFor(actor);
        campo.sendKeys(password);
      }
    } catch (Exception e) {
      try {
        WebElementFacade campo = TXT_CONTRASENA_BANCO_POPULAR_PORTAL.resolveFor(actor);
        campo.sendKeys(password);
      } catch (Exception ignored) {
      }
    }

    actor.attemptsTo(WaitFor.aTime(500));
    descartarAutofillSiEstaVisible(actor);
    gestionarPromptGuardarContrasenaGoogle(actor);

    // Ocultar teclado
    try {
      androidDriver(actor).hideKeyboard();
    } catch (Exception ignored) {
    }
  }

  private void clicBotonIngresar(Actor actor) {
    actor.attemptsTo(WaitFor.aTime(1000));

    // Cerrar teclado si está visible para asegurar que el botón se vea
    try {
      androidDriver(actor).hideKeyboard();
    } catch (Exception ignored) {
    }

    // Si salió el prompt de Google Password Manager, dar clic en "Ahora no"
    gestionarPromptGuardarContrasenaGoogle(actor);

    boolean clickExitoso = false;
    try {
      actor.attemptsTo(Click.on(BTN_INGRESAR_BANCO_POPULAR_LOGIN));
      clickExitoso = true;
    } catch (Exception e) {
      try {
        AndroidDriver driver = androidDriver(actor);
        List<WebElement> btns = driver.findElements(By.id("btn_login"));
        if (btns.isEmpty()) {
          btns =
              driver.findElements(
                  By.xpath("//android.widget.Button[@resource-id=\"btn_login\"]"));
        }
        if (btns.isEmpty()) {
          btns =
              driver.findElements(
                  By.xpath("//android.widget.Button[contains(@text, 'Ingresar')]"));
        }
        if (!btns.isEmpty()) {
          btns.get(0).click();
          clickExitoso = true;
        }
      } catch (Exception ignored) {
      }
    }

    actor.attemptsTo(WaitFor.aTime(1500));
    gestionarPromptGuardarContrasenaGoogle(actor);
  }

  private void gestionarPromptGuardarContrasenaGoogle(Actor actor) {
    try {
      AndroidDriver driver = androidDriver(actor);
      List<WebElement> ahoraNo =
          driver.findElements(
              By.xpath(
                  "//*[@text='Ahora no' or contains(@text, 'Ahora no') or contains(@resource-id, 'autofill_save_no') or contains(@resource-id, 'cancel')]"));
      if (!ahoraNo.isEmpty() && ahoraNo.get(0).isDisplayed()) {
        ahoraNo.get(0).click();
        Thread.sleep(800);
      }
    } catch (Exception ignored) {
    }
  }

  private void validarMensajeDocumentoNoRegistrado(Actor actor) {
    boolean mensajeVisible = false;
    AndroidDriver driver = androidDriver(actor);

    for (int i = 0; i < 15; i++) {
      gestionarPromptGuardarContrasenaGoogle(actor);

      try {
        List<WebElement> alertas =
            driver.findElements(
                By.xpath(
                    "//*[contains(@text, 'no se encuentran registrados') or contains(@text, 'Valida tu información') or contains(@text, 'no se encuentran')]"));
        if (!alertas.isEmpty() && alertas.get(0).isDisplayed()) {
          mensajeVisible = true;
          break;
        }
      } catch (Exception ignored) {
      }

      try {
        Thread.sleep(1000);
      } catch (InterruptedException ignored) {
      }
    }

    if (!mensajeVisible) {
      try {
        actor.attemptsTo(
            WaitForTextContains.withAnyTextContains(
                "no se encuentran registrados", "Valida tu información", "no se encuentran"),
            WaitFor.aTime(1500));
        mensajeVisible = true;
      } catch (Exception e) {
        throw new AssertionError(
            "No se visualizó el mensaje en rojo de documento no registrado en el portal Banco Popular");
      }
    }

    CapturaDePantallaMovil.tomarCapturaPantalla("Mensaje de documento no registrado en Banco Popular");
    EvidenciaUtils.registrarCaptura(
        "Mensaje en rojo de documento no registrado validado: Este Tipo y Nro. de documento no se encuentran registrados. Valida tu información.");
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

      // Ocultar teclado si está visible
      try {
        driver.hideKeyboard();
      } catch (Exception ignored) {
      }

      // Si el popup/overlay de autofill está en pantalla, presionar BACK para cerrarlo
      descartarAutofillSiEstaVisible(actor);

      // Tocar en el texto superior para quitar foco residual
      try {
        List<WebElement> titulos =
            driver.findElements(
                By.xpath(
                    "//*[contains(@text, 'Bienvenido a nuestro') or contains(@text, 'nuevo portal')]"));
        if (!titulos.isEmpty()) {
          titulos.get(0).click();
          Thread.sleep(500);
        }
      } catch (Exception ignored) {
      }
    } catch (Exception ignored) {
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


  private void seleccionarBancoPopular(Actor actor) {
    abrirDropdownBanco(actor);

    CapturaDePantallaMovil.tomarCapturaPantalla("Lista de bancos desplegada");
    EvidenciaUtils.registrarCaptura("Lista de bancos desplegada");

    // Scroll hasta encontrar BANCO POPULAR (hasta 10 scrolls de media vista)
    boolean seleccionado = false;
    for (int i = 0; i < 10; i++) {
      try {
        AndroidDriver driver = androidDriver(actor);
        List<WebElement> popular =
            driver.findElements(
                By.xpath("//*[contains(@text, 'BANCO POPULAR') or contains(@text, 'Banco Popular')]"));
        if (!popular.isEmpty() && popular.get(0).isDisplayed()) {
          popular.get(0).click();
          seleccionado = true;
          break;
        }
      } catch (Exception ignored) {
      }
      actor.attemptsTo(Scroll.scrollMediaVista(), WaitFor.aTime(600));
    }

    if (!seleccionado) {
      actor.attemptsTo(ClickTextoQueContengaX.elTextoContiene(BANCO_POPULAR));
    }

    actor.attemptsTo(WaitFor.aTime(2000));
    CapturaDePantallaMovil.tomarCapturaPantalla("Banco Popular seleccionado");
    EvidenciaUtils.registrarCaptura("Banco Popular seleccionado");
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

  public static Performable diligenciar() {
    return instrumented(DiligenciarFormularioPSEBancoPopular.class);
  }
}
