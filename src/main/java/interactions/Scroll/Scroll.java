package interactions.Scroll;

import static net.serenitybdd.screenplay.Tasks.instrumented;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Interaction;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.targets.Target;
import net.thucydides.core.annotations.Step;
import utils.AndroidObject;

public class Scroll extends AndroidObject implements Interaction {

  private final boolean mediaVista;

  public Scroll() {
    this.mediaVista = false;
  }

  public Scroll(boolean mediaVista) {
    this.mediaVista = mediaVista;
  }

  public static Performable to(Target xpath) {
    return instrumented(Scroll.class);
  }

  @Override
  @Step("Realiza Scroll hacia abajo")
  public <T extends Actor> void performAs(T actor) {
    if (mediaVista) {
      UnScrollAbajoCorto(actor);
    } else {
      UnScrollAbajo(actor);
    }
  }

  /** Scroll completo (comportamiento original, sin cambios). */
  public static Interaction scrollUnaVista() {
    return instrumented(Scroll.class, false);
  }

  /** Scroll corto: la mitad del recorrido de scrollUnaVista(). */
  public static Interaction scrollMediaVista() {
    return instrumented(Scroll.class, true);
  }
}