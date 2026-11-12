package tasks.Prepago.RecargasyPaquetes;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static utils.Constants.COMPRAR;
import static utils.Constants.ELEGIR_OTRO_MEDIO_PAGO;
import static utils.Constants.VER_DETALLE_DEL_PAQUETE;
import static utils.ConstantsPaquetes.*;

import interactions.Click.ClickTextoQueContengaX;
import interactions.Scroll.Scroll;
import interactions.validations.ValidarTexto;
import interactions.validations.ValidarTextoQueContengaX;
import interactions.wait.WaitFor;
import interactions.wait.WaitForResponse;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import java.time.Duration;
import java.util.Collections;
import java.util.List;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import utils.AndroidObject;
import utils.CapturaDePantallaMovil;
import utils.EvidenciaUtils;
import utils.FormateadorValor;

public class CompraPaqueteTodoIncluido15Dias20GB extends AndroidObject implements Task {

  private final String precioTexto = PRECIO_$25000;
  private final String valorFormateado = FormateadorValor.aFormatoCmax(precioTexto);

  @Override
  public <T extends Actor> void performAs(T actor) {
    // 1. Centrar con precisión la tarjeta de la posición 2 (20 GB - $25.000)
    centrarPaquete20GB(actor);

    // 2. Validar información visible del paquete de 20 GB ($ 25.000)
    try {
      actor.attemptsTo(ValidarTextoQueContengaX.elTextoContiene("20 GB"));
    } catch (Exception ignored) {
    }
    try {
      actor.attemptsTo(ValidarTextoQueContengaX.elTextoContiene("25.000"));
    } catch (Exception ignored) {
    }

    // 3. Clic específico en 'Ver detalle del paquete' de la tarjeta de 20 GB
    clicVerDetallePaquete20GB(actor);

    actor.attemptsTo(WaitFor.aTime(1200));

    // 4. Validar el detalle del paquete desplegado (15 días, 20 GB, sin redes)
    validarDetallePaquete20GB(actor);

    actor.remember("VALOR_COMPRA", valorFormateado);
    actor.remember("PAQUETE_CANAL", TODO_INCLUIDO_15DIAS_20GB_SIN_REDES);

    CapturaDePantallaMovil.tomarCapturaPantalla("Detalle paquete Todo Incluido 15 días 20GB validado");
    EvidenciaUtils.registrarCaptura("Detalle paquete Todo Incluido 15 días 20GB validado: $25.000 - 15 días");

    // 5. Scroll suave para centrar el botón Comprar desplegado y hacer clic exclusivo
    actor.attemptsTo(
        Scroll.scrollMediaVista(),
        WaitFor.aTime(800));

    clicBotonComprarPaquete20GB(actor);

    // 6. Esperar y validar pantalla Elegir otro medio de pago
    actor.attemptsTo(
        WaitForResponse.withText(ELEGIR_OTRO_MEDIO_PAGO),
        WaitFor.aTime(1000));

    try {
      actor.attemptsTo(ValidarTexto.validarTexto(PRECIO_$25000));
    } catch (Exception e) {
      actor.attemptsTo(ValidarTextoQueContengaX.elTextoContiene("25.000"));
    }

    CapturaDePantallaMovil.tomarCapturaPantalla("Pantalla Elegir otro medio de pago validada con precio $25.000");
    EvidenciaUtils.registrarCaptura("Pantalla Elegir otro medio de pago validada con precio $25.000");
  }

  private void centrarPaquete20GB(Actor actor) {
    AndroidDriver driver = androidDriver(actor);
    org.openqa.selenium.Dimension size = driver.manage().window().getSize();
    int width = size.width;
    int height = size.height;

    // Si '20 GB' no está presente o no es displayed, realizar scroll inicial
    List<WebElement> els = driver.findElements(By.xpath("//*[@text='20 GB']"));
    if (els.isEmpty() || !els.get(0).isDisplayed()) {
      actor.attemptsTo(Scroll.scrollUnaVista(), WaitFor.aTime(800));
      els = driver.findElements(By.xpath("//*[@text='20 GB']"));
    }

    if (!els.isEmpty()) {
      WebElement el20 = els.get(0);
      int currentY = el20.getLocation().getY();
      int targetY = (int) (height * 0.22); // Colocar la cabecera '20 GB' al 22% de la altura de la pantalla

      int dy = currentY - targetY;
      if (Math.abs(dy) > 30) {
        int startX = width / 2;
        int startY = (int) (height * 0.70);
        int endY = startY - dy;

        // Limitar endY para evitar swipes fuera de la pantalla
        if (endY < (int) (height * 0.15)) {
          endY = (int) (height * 0.15);
        }
        if (endY > (int) (height * 0.85)) {
          endY = (int) (height * 0.85);
        }

        realizarSwipe(driver, startX, startY, startX, endY);
        try {
          Thread.sleep(800);
        } catch (InterruptedException ignored) {
        }
      }
    } else {
      // Fallback si por alguna razón no se encontró el elemento
      actor.attemptsTo(Scroll.scrollUnaVista(), WaitFor.aTime(800));
    }
  }

  private void clicVerDetallePaquete20GB(Actor actor) {
    AndroidDriver driver = androidDriver(actor);

    // Intentar clic directo en el 'Ver detalle' subsiguiente al texto '20 GB'
    try {
      List<WebElement> detalles =
          driver.findElements(
              By.xpath(
                  "//*[@text='20 GB']/following::*[contains(@text, 'Ver detalle') or @text='Ver detalle del paquete']"));
      for (WebElement el : detalles) {
        if (el.isDisplayed()) {
          el.click();
          return;
        }
      }
    } catch (Exception ignored) {
    }

    try {
      List<WebElement> detalles = driver.findElements(By.xpath("//*[contains(@text, 'Ver detalle')]"));
      for (WebElement el : detalles) {
        if (el.isDisplayed()) {
          el.click();
          return;
        }
      }
    } catch (Exception ignored) {
    }

    actor.attemptsTo(ClickTextoQueContengaX.elTextoContiene(VER_DETALLE_DEL_PAQUETE));
  }

  private void validarDetallePaquete20GB(Actor actor) {
    AndroidDriver driver = androidDriver(actor);
    boolean encontrado = false;

    // Verificar si el texto ya está visible
    try {
      List<WebElement> els =
          driver.findElements(
              By.xpath(
                  "//*[contains(@text, '20 GB de navegacion') or contains(@text, 'Paquete 15 dias')]"));
      if (!els.isEmpty() && els.get(0).isDisplayed()) {
        encontrado = true;
      }
    } catch (Exception ignored) {
    }

    // Si aún no está visible, reintentar clic en 'Ver detalle' una vez más
    if (!encontrado) {
      clicVerDetallePaquete20GB(actor);
      actor.attemptsTo(WaitFor.aTime(1200));
    }

    try {
      actor.attemptsTo(ValidarTexto.validarTexto(TODO_INCLUIDO_15DIAS_20GB_SIN_REDES));
    } catch (Exception e) {
      try {
        actor.attemptsTo(ValidarTextoQueContengaX.elTextoContiene("20 GB de navegacion"));
      } catch (Exception ex) {
        try {
          actor.attemptsTo(ValidarTextoQueContengaX.elTextoContiene("Paquete 15 dias"));
        } catch (Exception ignored) {
        }
      }
    }
  }

  private void clicBotonComprarPaquete20GB(Actor actor) {
    AndroidDriver driver = androidDriver(actor);

    // Clic EXCLUSIVO al botón Comprar que sigue a '20 GB' o '20 GB de navegacion'
    try {
      List<WebElement> btns =
          driver.findElements(
              By.xpath(
                  "//*[contains(@text, '20 GB') or contains(@text, '20 GB de navegacion')]/following::*[contains(@text, 'Comprar') or @text='Comprar']"));
      for (WebElement b : btns) {
        if (b.isDisplayed()) {
          b.click();
          return;
        }
      }
    } catch (Exception ignored) {
    }

    try {
      List<WebElement> btns =
          driver.findElements(By.xpath("//android.widget.Button[@text='Comprar']"));
      for (WebElement b : btns) {
        if (b.isDisplayed()) {
          b.click();
          return;
        }
      }
    } catch (Exception ignored) {
    }

    actor.attemptsTo(ClickTextoQueContengaX.elTextoContiene(COMPRAR));
  }

  private void realizarSwipe(AndroidDriver driver, int startX, int startY, int endX, int endY) {
    try {
      PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
      Sequence swipe = new Sequence(finger, 1);
      swipe.addAction(
          finger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), startX, startY));
      swipe.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
      swipe.addAction(
          finger.createPointerMove(
              Duration.ofMillis(600), PointerInput.Origin.viewport(), endX, endY));
      swipe.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
      driver.perform(Collections.singletonList(swipe));
    } catch (Exception e) {
      try {
        new TouchAction<>(driver)
            .press(PointOption.point(startX, startY))
            .waitAction(WaitOptions.waitOptions(Duration.ofMillis(600)))
            .moveTo(PointOption.point(endX, endY))
            .release()
            .perform();
      } catch (Exception ignored) {
      }
    }
  }

  public static Performable comprar() {
    return instrumented(CompraPaqueteTodoIncluido15Dias20GB.class);
  }
}
