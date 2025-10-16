package interactions.Click;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;

import java.time.Duration;
import java.util.Collections;

import static net.serenitybdd.screenplay.Tasks.instrumented;

public class ClickEnCoordenadas implements Task {

    private final int x;
    private final int y;

    public ClickEnCoordenadas(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        WebDriver driver = BrowseTheWeb.as(actor).getDriver();

        if (!(driver instanceof AppiumDriver)) {
            throw new IllegalStateException("El driver actual no es un AppiumDriver.");
        }

        AppiumDriver<?> appiumDriver = (AppiumDriver<?>) driver;

        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
        Sequence tap = new Sequence(finger, 1);

        // Mueve y hace tap en la coordenada indicada
        tap.addAction(finger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), x, y));
        tap.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
        tap.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

        appiumDriver.perform(Collections.singletonList(tap));
    }

    public static ClickEnCoordenadas en(int x, int y) {
        return instrumented(ClickEnCoordenadas.class, x, y);
    }
}