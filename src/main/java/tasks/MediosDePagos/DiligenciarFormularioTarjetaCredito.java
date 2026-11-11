package tasks.MediosDePagos;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static userinterfaces.MediosPagoPage.*;
import static utils.Constants.*;

import interactions.Click.ClickTextoQueContengaX;
import interactions.Scroll.Scroll;
import interactions.validations.ValidarTexto;
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
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import utils.AndroidObject;
import utils.CapturaDePantallaMovil;
import utils.EvidenciaUtils;
import utils.TestDataProvider;

public class DiligenciarFormularioTarjetaCredito extends AndroidObject implements Task {

  private final User user = TestDataProvider.getRealUser();

  @Override
  public <T extends Actor> void performAs(T actor) {
    // Esperar a que cargue el formulario
    actor.attemptsTo(
        WaitForTextContains.withAnyTextContains("Agregar tarjeta", TARJETAS_NACIONALES, NUMERO_TARJETA),
        WaitFor.aTime(4000));

    try {
      actor.attemptsTo(ValidarTextoQueContengaX.elTextoContiene("Agregar tarjeta"));
    } catch (Exception e) {
      actor.attemptsTo(ValidarTextoQueContengaX.elTextoContiene(TARJETAS_NACIONALES));
    }

    CapturaDePantallaMovil.tomarCapturaPantalla("Formulario de tarjeta de crédito cargado");
    EvidenciaUtils.registrarCaptura("Formulario de tarjeta de crédito cargado");

    // Llenar datos de la tarjeta con digitación humana número por número
    actor.attemptsTo(WaitFor.aTime(1500), Click.on(TXT_NUMERO_TARJETA));
    actor.attemptsTo(WaitFor.aTime(1000));

    digitarSoloNumeros(actor, NUMERO_TARJETA_FICTICIO);
    actor.attemptsTo(WaitFor.aTime(1500));

    // Cerrar tooltip o popup de autofill / teclado antes de continuar
    cerrarAutofillYTeclado(actor);
    actor.attemptsTo(WaitFor.aTime(1000));

    // Llenar nombre del titular
    actor.attemptsTo(
            WaitFor.aTime(1000),
            Enter.theValue(NOMBRE_FICTICIO).into(TXT_NOMBRE_APELLIDO),
            WaitFor.aTime(1000),
            Click.on(DROPDOWN_TIPO_DOCUMENTO),
            WaitFor.aTime(1000),
            ClickTextoQueContengaX.elTextoContiene(CEDULA_CIUDADANIA),
            WaitFor.aTime(1000)
    );


    // Número de documento
    actor.attemptsTo(WaitFor.aTime(1000), Click.on(TXT_NUMERO_DOCUMENTO));
    digitarSoloNumeros(actor, NUMERO_CEDULA_FICTICIO);

    // Fecha de expiración
    actor.attemptsTo(WaitFor.aTime(1000), Click.on(TXT_FECHA_EXPIRACION));
    digitarSoloNumeros(actor, FECHA_EXPIRACION_FICTICIA);

    // CVC
    actor.attemptsTo(WaitFor.aTime(1000), Click.on(TXT_CVV));
    digitarSoloNumeros(actor, CVC_FICTICIO);

    CapturaDePantallaMovil.tomarCapturaPantalla("Datos básicos de tarjeta ingresados");
    EvidenciaUtils.registrarCaptura("Datos básicos de tarjeta ingresados");

    // Continuar con datos adicionales
    actor.attemptsTo(
        WaitFor.aTime(1500),
        Scroll.scrollMediaVista(),
        WaitFor.aTime(1000),
        Enter.theValue(CORREO_FICTICIO).into(TXT_EMAIL),
        WaitFor.aTime(1000));

    try {
      if (user.getNumeroPrepago() != null && !user.getNumeroPrepago().isEmpty()) {
        actor.attemptsTo(Enter.theValue(user.getNumeroPrepago()).into(TXT_TELEFONO));
      }
    } catch (Exception ignored) {
    }

    // Modificar número de cuotas (Aumentar a 2 o 3, luego volver a 1)
    actor.attemptsTo(
        Click.on(BTN_MAS_CUOTAS),
        WaitFor.aTime(1000),
        Click.on(BTN_MAS_CUOTAS),
        WaitFor.aTime(1000),
        Click.on(BTN_MENOS_CUOTAS),
        WaitFor.aTime(1000),
        Click.on(BTN_MENOS_CUOTAS),
        WaitFor.aTime(1000));

    // Interactuar con radio button asociar claro pay / guardar tarjeta
    try {
      actor.attemptsTo(Click.on(RDO_ASOCIAR_CLPAY), WaitFor.aTime(1000));
    } catch (Exception ignored) {
      try {
        actor.attemptsTo(Click.on(CHK_GUARDAR_TARJETA), WaitFor.aTime(1000));
      } catch (Exception ignored2) {
      }
    }

    CapturaDePantallaMovil.tomarCapturaPantalla("Datos adicionales completados y cuotas validadas");
    EvidenciaUtils.registrarCaptura("Datos adicionales completados y cuotas validadas");

    // Validar botón Pagar habilitado
    actor.attemptsTo(
            Scroll.scrollMediaVista(),
            ValidarTexto.validarTexto(PAGAR));

    CapturaDePantallaMovil.tomarCapturaPantalla("Botón Pagar validado");
    EvidenciaUtils.registrarCaptura("Botón Pagar validado");
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
      try {
        List<WebElement> autofill =
            driver.findElements(
                By.xpath(
                    "//*[contains(@resource-id, 'autofill') or contains(@text, 'Escanear') or contains(@text, 'GUEVARA') or contains(@text, 'LADDY')]"));
        if (!autofill.isEmpty()) {
          driver.pressKey(new KeyEvent(AndroidKey.BACK));
          Thread.sleep(600);
        }
      } catch (Exception ignored) {
      }

      // Tocar en el texto superior para quitar el foco de la tarjeta
      try {
        List<WebElement> titulos =
            driver.findElements(
                By.xpath("//*[@text='Agregar tarjeta' or contains(@text, 'Solo se permite')]"));
        if (!titulos.isEmpty()) {
          titulos.get(0).click();
          Thread.sleep(500);
        }
      } catch (Exception ignored) {
      }
    } catch (Exception ignored) {
    }
  }

  private void ingresarNombreTitular(Actor actor, String nombre) {
    try {
      AndroidDriver driver = androidDriver(actor);
      // Asegurarse de que el elemento esté habilitado
      for (int i = 0; i < 5; i++) {
        List<WebElement> els =
            driver.findElements(By.xpath("//android.widget.EditText[@resource-id=\"NOMBRE_TARJETA\"]"));
        if (!els.isEmpty() && els.get(0).isEnabled()) {
          break;
        }
        cerrarAutofillYTeclado(actor);
        Thread.sleep(800);
      }
    } catch (Exception ignored) {
    }

    try {
      actor.attemptsTo(
          Click.on(TXT_NOMBRE_TARJETA),
          WaitFor.aTime(500),
          Enter.theValue(nombre).into(TXT_NOMBRE_TARJETA));
    } catch (Exception e) {
      cerrarAutofillYTeclado(actor);
      actor.attemptsTo(WaitFor.aTime(800));
      try {
        WebElementFacade campo = TXT_NOMBRE_TARJETA.resolveFor(actor);
        campo.click();
        campo.type(nombre);
      } catch (Exception ex) {
        try {
          AndroidDriver driver = androidDriver(actor);
          WebElement campo =
              driver.findElement(By.xpath("//android.widget.EditText[@resource-id=\"NOMBRE_TARJETA\"]"));
          campo.click();
          campo.sendKeys(nombre);
        } catch (Exception ignored) {
        }
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
    return instrumented(DiligenciarFormularioTarjetaCredito.class);
  }

  public static Performable transaccionTarjetaCreditoPost() {
    return instrumented(DiligenciarFormularioTarjetaCredito.class);
  }
}
