package tasks.MediosDePagos;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static userinterfaces.MediosPagoPage.*;
import static utils.Constants.*;

import interactions.Click.ClickTextoQueContengaX;
import interactions.Scroll.Scroll;
import interactions.validations.ValidarTexto;
import interactions.validations.ValidarTextoQueContengaX;
import interactions.wait.WaitFor;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.touch.offset.PointOption;
import java.util.List;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;
import utils.AndroidObject;
import utils.CapturaDePantallaMovil;
import utils.EvidenciaUtils;

public class OtrosMediosDePagoPSE extends AndroidObject implements Task {

  private static final String paso1 = "Validar direccionamiento y menu desplegable medios de pago";
  private static final String PASO_PSE = "Por medio de PSE";

  @Override
  public <T extends Actor> void performAs(T actor) {

    actor.attemptsTo(
        WaitFor.aTime(3000),
        ValidarTexto.validarTexto(COMPRA_DE_PAQUETES),
        ValidarTextoQueContengaX.elTextoContiene(VALOR_A_PAGAR),
        ValidarTextoQueContengaX.elTextoContiene(NUMERO_FACTURA_RECARGAS),
        ValidarTextoQueContengaX.elTextoContiene(DESCRIPCION_COMPRA),
        ValidarTextoQueContengaX.elTextoContiene(ESCOGE_MEDIO_PAGO),
        WaitFor.aTime(2500),
        Scroll.scrollUnaVista(),
        Click.on(DROPDOWN_MEDIO_PAGO),
        WaitFor.aTime(2000));

    CapturaDePantallaMovil.tomarCapturaPantalla("Medios de pago desplegados");
    EvidenciaUtils.registrarCaptura(paso1);

    seleccionarOpcionPSE(actor);

    CapturaDePantallaMovil.tomarCapturaPantalla("Opción PSE seleccionada");
    EvidenciaUtils.registrarCaptura(PASO_PSE);

    clicContinuar(actor);

    actor.attemptsTo(
        WaitFor.aTime(6000),
        DiligenciarFormularioPSEBancoPopular.diligenciar());
  }

  private void seleccionarOpcionPSE(Actor actor) {
    AndroidDriver driver = androidDriver(actor);
    boolean clickExitoso = false;

    String xpathGriver =
        "//android.view.ViewGroup[@resource-id=\"com.clarocolombia.miclaro:id/griver_layout_refresh\"]/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.webkit.WebView/android.webkit.WebView/android.view.View[2]/android.view.View/android.view.View[2]/android.view.View/android.view.View[1]/android.view.View/android.view.View[1]";

    for (int i = 0; i < 5; i++) {
      try {
        List<WebElement> els = driver.findElements(By.xpath(xpathGriver));
        if (!els.isEmpty()) {
          try {
            els.get(0).click();
            clickExitoso = true;
            break;
          } catch (Exception e) {
            Point loc = els.get(0).getLocation();
            Dimension sz = els.get(0).getSize();
            int cx = loc.getX() + (sz.getWidth() / 2);
            int cy = loc.getY() + (sz.getHeight() / 2);
            new TouchAction(driver)
                .tap(PointOption.point(cx, cy))
                .perform();
            clickExitoso = true;
            break;
          }
        }
      } catch (Exception ignored) {
      }
      try {
        Thread.sleep(800);
      } catch (InterruptedException ignored) {
      }
    }

    if (!clickExitoso) {
      try {
        List<WebElement> els =
            driver.findElements(
                By.xpath("//*[contains(@text, 'PSE') or contains(@text, 'NEQUI') or contains(@content-desc, 'PSE')]"));
        if (!els.isEmpty()) {
          els.get(0).click();
          clickExitoso = true;
        }
      } catch (Exception ignored) {
      }
    }

    if (!clickExitoso) {
      try {
        actor.attemptsTo(Click.on(BTN_OPCION_PSE_DROPDOWN));
      } catch (Exception e) {
        try {
          actor.attemptsTo(ClickTextoQueContengaX.elTextoContiene("PSE"));
        } catch (Exception ignored) {
        }
      }
    }

    actor.attemptsTo(WaitFor.aTime(2000));
  }

  private void clicContinuar(Actor actor) {
    boolean clickExitoso = false;
    AndroidDriver driver = androidDriver(actor);

    try {
      List<WebElement> btns =
          driver.findElements(By.xpath("//android.widget.Button[contains(@text, 'Continuar')]"));
      if (!btns.isEmpty() && btns.get(0).isDisplayed()) {
        btns.get(0).click();
        clickExitoso = true;
      }
    } catch (Exception ignored) {
    }

    if (!clickExitoso) {
      try {
        actor.attemptsTo(Click.on(BTN_CONTINUAR_PORTAL_PAGOS));
        clickExitoso = true;
      } catch (Exception e) {
        try {
          actor.attemptsTo(
              Scroll.scrollMediaVista(),
              ClickTextoQueContengaX.elTextoContiene(CONTINUAR));
          clickExitoso = true;
        } catch (Exception ignored) {
        }
      }
    }
  }

  public static Performable validarOtrosMediosPSE() {
    return instrumented(OtrosMediosDePagoPSE.class);
  }
}

