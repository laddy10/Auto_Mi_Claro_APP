package interactions.mobile;

import static net.serenitybdd.screenplay.Tasks.instrumented;

import java.time.Duration;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Interaction;
import net.serenitybdd.screenplay.targets.Target;
import org.openqa.selenium.TimeoutException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClickIfPresent implements Interaction {

  private static final Logger LOGGER = LoggerFactory.getLogger(ClickIfPresent.class);
  private final Target target;
  private final Duration timeout;

  private ClickIfPresent(Target target, Duration timeout) {
    this.target = target;
    this.timeout = timeout;
  }

  public static ClickIfPresent on(Target target) {
    return instrumented(ClickIfPresent.class, target, Duration.ofSeconds(2));
  }

  public static ClickIfPresent on(Target target, Duration timeout) {
    return instrumented(ClickIfPresent.class, target, timeout);
  }

  @Override
  public <T extends Actor> void performAs(T actor) {
    try {
      LOGGER.debug("Checking if element '{}' is present", target);

      // Intenta encontrar y hacer click
      target.resolveFor(actor).click();
      LOGGER.info("Clicked on optional element: {}", target);

      Thread.sleep(500);

    } catch (TimeoutException | org.openqa.selenium.NoSuchElementException e) {
      LOGGER.debug("Optional element '{}' not found. Continuing...", target);
    } catch (Exception e) {
      LOGGER.debug("Could not interact with optional element: {}", target);
    }
  }
}
