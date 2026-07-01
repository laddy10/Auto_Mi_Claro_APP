package interactions.Click;

import static net.serenitybdd.screenplay.Tasks.instrumented;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import java.util.List;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Interaction;
import org.openqa.selenium.WebElement;
import utils.AndroidObject;

public class ClickTextoCercanoA implements Interaction {

    private final String anchorText;
    private final String targetText;

    public ClickTextoCercanoA(String anchorText, String targetText) {
        this.anchorText = anchorText;
        this.targetText = targetText;
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        AppiumDriver<?> driver = AndroidObject.androidDriver(actor);

        WebElement ancla =
                driver.findElement(
                        new MobileBy.ByAndroidUIAutomator(
                                "new UiSelector().textContains(\"" + anchorText + "\")"));
        int anclaY = ancla.getLocation().getY();

        List<? extends WebElement> candidatos =
                driver.findElements(
                        new MobileBy.ByAndroidUIAutomator(
                                "new UiSelector().textContains(\"" + targetText + "\")"));

        WebElement masCercano = null;
        int menorDistancia = Integer.MAX_VALUE;

        for (WebElement candidato : candidatos) {
            int distancia = candidato.getLocation().getY() - anclaY;
            if (distancia >= 0 && distancia < menorDistancia) {
                menorDistancia = distancia;
                masCercano = candidato;
            }
        }

        if (masCercano == null) {
            throw new RuntimeException(
                    "No se encontró '" + targetText + "' debajo de '" + anchorText + "'");
        }

        masCercano.click();
    }

    public static Interaction anclaEn(String anchorText, String targetText) {
        return instrumented(ClickTextoCercanoA.class, anchorText, targetText);
    }
}