package interactions.Click;

import static net.serenitybdd.screenplay.Tasks.instrumented;

import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Interaction;
import utils.AndroidObject;

public class ClickElementByText extends AndroidObject implements Interaction {

  private String Text;

  public ClickElementByText(String Text) {
    this.Text = Text;
  }

  @Override
  @Step("Busca el texto de '#Text' y le da click.")
  public <T extends Actor> void performAs(T actor) {
    ClickByText(actor, Text);
  }

  public static Interaction clickElementByText(String Text) {
    return instrumented(ClickElementByText.class, Text);
  }
}
