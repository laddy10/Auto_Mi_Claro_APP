package tasks.AtencionClienteSoporte;

import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static net.serenitybdd.screenplay.Tasks.instrumented;
import static userinterfaces.AtencionClienteSoportePage.*;
import static utils.Constants.*;

import interactions.Click.ClickTextoQueContengaX;
import interactions.validations.ValidarElemento;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.questions.Presence;
import utils.EvidenciaUtils;

public class PuntosDeAtencion implements Task {

  private static final String paso1 = "Ingresar a Puntos de atención";

  @Override
  public <T extends Actor> void performAs(T actor) {
    EvidenciaUtils.registrarCaptura(paso1);

    actor.attemptsTo(ClickTextoQueContengaX.elTextoContiene(PUNTOS_DE_ATENCION));
  }

  public static Performable ingresar() {
    return instrumented(PuntosDeAtencion.class);
  }

  public static class ManejarPermisosUbicacion implements Task {
    @Override
    public <T extends Actor> void performAs(T actor) {
      EvidenciaUtils.registrarCaptura("Manejar permisos de ubicación");

      // Primera condicional: verificar mensaje de ubicación
      if (!Presence.of(BTN_SI_PERMITIR).viewedBy(actor).resolveAll().isEmpty()) {
        actor.attemptsTo(Click.on(BTN_SI_PERMITIR));

        // Segunda condicional: verificar si aparece nuevamente el mensaje
        if (!Presence.of(BTN_SI_PERMITIR).viewedBy(actor).resolveAll().isEmpty()) {
          actor.attemptsTo(Click.on(BTN_SI_PERMITIR));
        }
      }
    }

    public static Performable ejecutar() {
      return instrumented(ManejarPermisosUbicacion.class);
    }
  }

  public static class VerificarMapa implements Task {
    @Override
    public <T extends Actor> void performAs(T actor) {
      EvidenciaUtils.registrarCaptura("Verificar que esté presente el mapa");

      actor.should(seeThat(ValidarElemento.esVisible(MAPA_UBICACION)));
    }

    public static Performable ejecutar() {
      return instrumented(VerificarMapa.class);
    }
  }

  public static Performable manejarPermisosUbicacion() {
    return ManejarPermisosUbicacion.ejecutar();
  }

  public static Performable verificarMapa() {
    return VerificarMapa.ejecutar();
  }
}