package interactions.Click;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Interaction;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ClickLineaCuenta implements Interaction {

  private final String numeroLinea;

  public ClickLineaCuenta(String numeroLinea) {
    this.numeroLinea = numeroLinea;
  }

  @Override
  public <T extends Actor> void performAs(T actor) {

    WebDriverWait wait = new WebDriverWait(BrowseTheWeb.as(actor).getDriver(), 15);

    By locator =
        By.xpath(
            "//android.widget.Button[@resource-id='btn_account' and "
                + "contains(@text,'"
                + numeroLinea
                + "')]");

    WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));

    element.click();
  }

  public static ClickLineaCuenta conNumero(String numeroLinea) {
    return new ClickLineaCuenta(numeroLinea);
  }
}
