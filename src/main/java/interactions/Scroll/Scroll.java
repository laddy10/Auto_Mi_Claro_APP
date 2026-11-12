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
  private final boolean cuartoVista;

  public Scroll() {
    this.mediaVista = false;
    this.cuartoVista = false;
  }

  public Scroll(boolean mediaVista) {
    this.mediaVista = mediaVista;
    this.cuartoVista = false;
  }

  public Scroll(boolean mediaVista, boolean cuartoVista) {
    this.mediaVista = mediaVista;
    this.cuartoVista = cuartoVista;
  }

  public static Performable to(Target xpath) {
    return instrumented(Scroll.class);
  }

  @Override
  @Step("Realiza Scroll hacia abajo")
  public <T extends Actor> void performAs(T actor) {
    if (cuartoVista) {
      UnScrollAbajoMuyCorto(actor);
    } else if (mediaVista) {
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

  /** Scroll muy corto: la mitad del recorrido de scrollMediaVista(). */
  public static Interaction scrollCuartoVista() {
    return instrumented(Scroll.class, false, true);
  }
}
