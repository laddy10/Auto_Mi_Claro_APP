package tasks.PagosYConsultas.DetalleDeTuPlan;

import io.appium.java_client.AppiumDriver;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;
import net.serenitybdd.screenplay.Actor;
import net.thucydides.core.util.SystemEnvironmentVariables;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Rectangle;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Pause;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.support.ui.WebDriverWait;
import tasks.Login.GestionCuenta;
import tasks.PagosYConsultas.AccederMenuPagosYConsultas;
import utils.AndroidObject;
import utils.EvidenciaUtils;

/** Interacciones nativas de Roaming basadas en elementos, no en coordenadas del dispositivo. */
public final class RoamingScreen implements AutoCloseable {
  static final By SWITCH = By.xpath(
      "//*[@class='android.widget.ToggleButton' or @class='android.widget.Switch'"
          + " or @class='android.widget.SwitchCompat' or @class='androidx.appcompat.widget.SwitchCompat']");
  static final By SESSION_EXPIRED = By.xpath(
      "//*[contains(@text,'hemos cerrado tu sesi')"
          + " or contains(@text,'sesión se ha cerrado')"
          + " or contains(@content-desc,'hemos cerrado tu sesi')]");
  private final AppiumDriver<?> driver;
  private final Actor actor;
  private final long implicitWait;

  public RoamingScreen(Actor actor) {
    this.actor = actor;
    driver = AndroidObject.androidDriver(actor);
    implicitWait = SystemEnvironmentVariables.createEnvironmentVariables()
        .getPropertyAsInteger("webdriver.timeouts.implicitlywait", 10000);
    // Los sondeos de avisos transitorios no deben bloquear por el timeout implícito.
    driver.manage().timeouts().implicitlyWait(0, TimeUnit.MILLISECONDS);
  }

  public void open(String line) {
    tap(text("Activar Roaming"));
    waitFor(text("Postpago"), false);
    WebElement row = waitFor(By.xpath(
        "//*[contains(translate(@text,' ',''),'" + line + "')"
            + " or contains(translate(@content-desc,' ',''),'" + line + "')]"), true);
    tapLine(row);
    waitFor(SWITCH, false);
    state();
    EvidenciaUtils.registrarCaptura("Administrar Roaming de la linea " + line);
  }

  public boolean state() {
    final String[] last = {null};
    final long[] since = {0};
    String result = new WebDriverWait(driver, 30).ignoring(StaleElementReferenceException.class)
        .withMessage("No se pudo determinar el estado de Roaming")
        .until(ignored -> {
          checkSession();
          List<? extends WebElement> switches = driver.findElements(SWITCH);
          if (switches.size() != 1 || !switches.get(0).isDisplayed()) {
            since[0] = 0;
            return null;
          }
          String checked = switches.get(0).getAttribute("checked");
          if (!"true".equals(checked) && !"false".equals(checked)) {
            since[0] = 0;
            return null;
          }
          if (since[0] == 0 || !checked.equals(last[0])) {
            last[0] = checked;
            since[0] = System.nanoTime();
          }
          return System.nanoTime() - since[0] >= Duration.ofSeconds(5).toNanos()
              ? checked : null;
        });
    return Boolean.parseBoolean(result);
  }

  public void tapText(String value) {
    tap(text(value));
  }

  public void tapSwitch() {
    tap(SWITCH);
  }

  public void tap(By locator) {
    tap(waitFor(locator, false));
  }

  public WebElement waitFor(By locator, boolean scroll) {
    int[] swipes = {0};
    Rectangle[] previous = {null};
    return new WebDriverWait(driver, 60).ignoring(StaleElementReferenceException.class)
        .withMessage("No se encontro el control de Roaming: " + locator)
        .until(ignored -> {
          checkSession();
          for (WebElement element : driver.findElements(locator)) {
            if (element.isDisplayed() && element.isEnabled() && element.getRect().getHeight() > 5) {
              Rectangle bounds = element.getRect();
              if (bounds.equals(previous[0])) {
                return element;
              }
              previous[0] = bounds;
              return null;
            }
          }
          previous[0] = null;
          if (scroll && swipes[0]++ < 10) {
            swipe();
          }
          return null;
        });
  }

  public boolean visible(By locator) {
    return driver.findElements(locator).stream().anyMatch(WebElement::isDisplayed);
  }

  public void checkSession() {
    if (visible(SESSION_EXPIRED)) {
      throw new SessionExpiredException();
    }
  }

  public void confirmActive() {
    if (!state()) {
      throw new IllegalStateException("La aplicacion no confirmo Roaming activo");
    }
    EvidenciaUtils.registrarCaptura("Servicio Roaming activo antes de desactivarlo");
  }

  public void finishActivation() {
    tapText("No, gracias");
    tapText("Continuar");
    tapText("Aceptar");
    confirmActive();
  }

  public void activateIndefinite() {
    tapSwitch();
    tapText("Activar por tiempo indefinido");
    finishActivation();
    waitFor(text("Indefinido"), false);
  }

  static LocalDate expiryDate(LocalDate today, String configured) {
    LocalDate result = configured == null ? today.plusDays(1) : LocalDate.parse(configured);
    if (!result.isAfter(today) || result.isAfter(today.plusMonths(1))) {
      throw new IllegalArgumentException(
          "La fecha de Roaming debe ser futura y estar dentro del proximo mes");
    }
    return result;
  }

  public void activateWithExpiry() {
    LocalDate today = LocalDate.parse(driver.getDeviceTime().substring(0, 10));
    LocalDate expiry = expiryDate(today, System.getProperty("roaming.sa098.expiry"));
    tapSwitch();
    tap(By.xpath("//*[starts-with(@text,'Activar con fecha')"
        + " or starts-with(@content-desc,'Activar con fecha')]"));
    if (expiry.getMonth() != today.getMonth()) {
      tap(By.xpath("//*[@resource-id='android:id/next'"
          + " or @content-desc='Next month' or @content-desc='Mes siguiente'"
          + " or @content-desc='Siguiente mes']"));
    }
    tapText(Integer.toString(expiry.getDayOfMonth()));
    tapText("Aceptar");
    finishActivation();
    String formatted = expiry.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    waitFor(By.xpath("//*[contains(@text,'" + formatted
        + "') or contains(@content-desc,'" + formatted + "')]"), false);
    EvidenciaUtils.registrarCaptura("Roaming con vigencia definida hasta " + formatted);
  }

  public void prepareInactive(String line) {
    if (state()) {
      EvidenciaUtils.registrarCaptura("Preparar precondicion: Roaming desactivado");
      DesactivarServicioRoaming.desactivar(this);
      verifyAfterReopen(line);
    }
    EvidenciaUtils.registrarCaptura("Precondicion confirmada: Roaming desactivado");
  }

  public void waitUntilClear(By locator) {
    new WebDriverWait(driver, 30).ignoring(StaleElementReferenceException.class)
        .withMessage("El aviso o modal de Roaming no desaparecio: " + locator)
        .until(ignored -> {
          checkSession();
          return !visible(locator);
        });
  }

  public boolean waitUntilOff(By error) {
    final long[] since = {0};
    String result = new WebDriverWait(driver, 30).ignoring(StaleElementReferenceException.class)
        .withMessage("Roaming no confirmo su desactivacion")
        .until(ignored -> {
          checkSession();
          if (visible(error)) {
            return "error";
          }
          List<? extends WebElement> switches = driver.findElements(SWITCH);
          if (switches.size() != 1 || !"false".equals(switches.get(0).getAttribute("checked"))) {
            since[0] = 0;
            return null;
          }
          if (since[0] == 0) {
            since[0] = System.nanoTime();
          }
          return System.nanoTime() - since[0] >= Duration.ofSeconds(5).toNanos()
              ? "off" : null;
        });
    return "off".equals(result);
  }

  public void verifyAfterReopen(String line) {
    verifyWithSessionRecovery(() -> {
      driver.navigate().back();
      waitFor(text("Postpago"), false);
      selectLineAndVerifyOff(line);
    }, () -> {
      EvidenciaUtils.registrarCaptura("Sesion expirada al verificar Roaming; reingreso unico");
      try {
        actor.attemptsTo(GestionCuenta.segunCuenta(), AccederMenuPagosYConsultas.acceder());
      } finally {
        driver.manage().timeouts().implicitlyWait(0, TimeUnit.MILLISECONDS);
      }
      tap(text("Activar Roaming"));
      waitFor(text("Postpago"), false);
      selectLineAndVerifyOff(line);
    });
  }

  static void verifyWithSessionRecovery(Runnable verification, Runnable recovery) {
    try {
      verification.run();
    } catch (SessionExpiredException e) {
      recovery.run();
    }
  }

  static final class SessionExpiredException extends IllegalStateException {
    SessionExpiredException() {
      super("Mi Claro cerro la sesion. No es posible confirmar el estado de Roaming; ingresa nuevamente.");
    }
  }

  private void selectLineAndVerifyOff(String line) {
    By row = By.xpath("//*[contains(translate(@text,' ',''),'" + line + "')"
        + " or contains(translate(@content-desc,' ',''),'" + line + "')]");
    tapLine(waitFor(row, true));
    waitFor(SWITCH, false);
    if (state()) {
      throw new IllegalStateException("Roaming sigue activo al reabrir la linea " + line);
    }
    EvidenciaUtils.registrarCaptura("Roaming desactivado tras reabrir la linea " + line);
  }

  static By text(String value) {
    return By.xpath("//*[@text='" + value + "' or @content-desc='" + value + "']");
  }

  static int[] center(Rectangle bounds, Dimension screen) {
    int left = Math.max(0, bounds.getX());
    int top = Math.max(0, bounds.getY());
    int right = Math.min(screen.getWidth(), bounds.getX() + bounds.getWidth());
    int bottom = Math.min(screen.getHeight(), bounds.getY() + bounds.getHeight());
    if (right <= left || bottom <= top) {
      throw new IllegalStateException("El control de Roaming esta fuera de la pantalla");
    }
    return new int[] {left + (right - left) / 2, top + (bottom - top) / 2};
  }

  private void tap(WebElement element) {
    int[] point = center(element.getRect(), driver.manage().window().getSize());
    tapPoint(point);
  }

  private void tapLine(WebElement row) {
    Rectangle bounds = row.getRect();
    int[] point = center(bounds, driver.manage().window().getSize());
    // El arbol del WebView agrupa la tarjeta; su accion "Administrar" esta a la derecha.
    point[0] = bounds.getX() + bounds.getWidth() * 85 / 100;
    tapPoint(point);
  }

  private void tapPoint(int[] point) {
    PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "roaming");
    Sequence tap = new Sequence(finger, 1);
    tap.addAction(finger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), point[0], point[1]));
    tap.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
    tap.addAction(new Pause(finger, Duration.ofMillis(150)));
    tap.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
    driver.perform(Collections.singletonList(tap));
  }

  private void swipe() {
    Dimension size = driver.manage().window().getSize();
    PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "roaming-scroll");
    Sequence swipe = new Sequence(finger, 1);
    swipe.addAction(finger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(),
        size.width / 2, size.height * 3 / 4));
    swipe.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
    swipe.addAction(finger.createPointerMove(Duration.ofMillis(600), PointerInput.Origin.viewport(),
        size.width / 2, size.height / 3));
    swipe.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
    driver.perform(Collections.singletonList(swipe));
  }

  @Override
  public void close() {
    driver.manage().timeouts().implicitlyWait(implicitWait, TimeUnit.MILLISECONDS);
  }
}
