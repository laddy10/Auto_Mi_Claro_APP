package interactions.Click;

import static net.serenitybdd.screenplay.Tasks.instrumented;

import io.appium.java_client.MobileBy;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Interaction;
import net.thucydides.core.annotations.Step;
import utils.AndroidObject;

public class ClickElementById extends AndroidObject implements Interaction {

    private final String resourceId;

    public ClickElementById(String resourceId) {
        this.resourceId = resourceId;
    }

    @Override
    @Step("Busca el elemento con id '#resourceId' y le da click.")
    public <T extends Actor> void performAs(T actor) {
        androidDriver(actor)
                .findElement(MobileBy.id(resourceId))
                .click();
    }

    public static Interaction clickElementById(String resourceId) {
        return instrumented(ClickElementById.class, resourceId);
    }
}