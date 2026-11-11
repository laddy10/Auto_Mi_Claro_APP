package tasks.Prepago.RecargasyPaquetes;

import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static net.serenitybdd.screenplay.Tasks.instrumented;
import static net.serenitybdd.screenplay.actors.OnStage.theActorInTheSpotlight;
import static userinterfaces.PagosYConsultasPage.CHECK_ACEPTAR_TERMINOS_CONDICIONES2;
import static userinterfaces.PagosyConsultasPrePage.PAG_TERMINOS_Y_CONDICIONES;
import static utils.Constants.RESUMEN_COMPRA;
import static utils.Constants.TERMINOS_Y_CONDICIONES;

import interactions.Click.ClickTextoQueContengaX;
import interactions.Scroll.Scroll;
import interactions.comunes.Atras;
import interactions.validations.ValidarElemento;
import interactions.wait.WaitFor;
import interactions.wait.WaitForResponse;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import utils.AndroidObject;
import utils.CapturaDePantallaMovil;
import utils.EvidenciaUtils;

public class TerminosYCondicionesPaquetes extends AndroidObject implements Task {

  @Override
  public <T extends Actor> void performAs(T actor) {
    EvidenciaUtils.registrarCaptura("Ingresar a términos y condiciones");

    actor.attemptsTo(ClickTextoQueContengaX.elTextoContiene(TERMINOS_Y_CONDICIONES));

    theActorInTheSpotlight()
        .should(seeThat(ValidarElemento.esVisible(PAG_TERMINOS_Y_CONDICIONES)));

    CapturaDePantallaMovil.tomarCapturaPantalla("captura_pantalla");
    EvidenciaUtils.registrarCaptura("Visualización documento de términos y condiciones");

    actor.attemptsTo(Scroll.scrollUnaVista());

    CapturaDePantallaMovil.tomarCapturaPantalla("captura_pantalla");
    EvidenciaUtils.registrarCaptura("Scroll en términos y condiciones");

    actor.attemptsTo(
        Atras.irAtras(),
        WaitForResponse.withText(RESUMEN_COMPRA),
        WaitFor.aTime(1000),
        Click.on(CHECK_ACEPTAR_TERMINOS_CONDICIONES2));

    CapturaDePantallaMovil.tomarCapturaPantalla("captura_pantalla");
    EvidenciaUtils.registrarCaptura("Aceptar términos y condiciones en resumen de compra");
  }

  public static Performable validarTerminosYAceptar() {
    return instrumented(TerminosYCondicionesPaquetes.class);
  }

  public static Performable termiYCondPaquetesTodoIncluidoConRedes() {
    return instrumented(TerminosYCondicionesPaquetes.class);
  }
}
