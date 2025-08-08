package tasks.PagosYConsultas.DetalleDeTuPlan;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static userinterfaces.PagosYConsultasPage.LBL_SIN_APP_DISPONIBLES;
import static utils.Constants.*;

import interactions.Click.ClickTextoQueContengaX;
import interactions.validations.ValidarTexto;
import interactions.validations.ValidarTextoQueContengaX;
import interactions.wait.WaitFor;
import java.util.List;
import models.User;
import net.serenitybdd.core.pages.WebElementFacade;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import utils.EvidenciaUtils;
import utils.TestDataProvider;

public class AdministrarAplicacionesIncluidas implements Task {

  private static final User user = TestDataProvider.getRealUser();
  private static final String paso1 = "Ingresar Administra las aplicaciones incluidas";
  private static final String paso2 = "Validar contenido aplicaciones incluidas";

  @Override
  public <T extends Actor> void performAs(T actor) {

    // ADMINISTRA LAS APLICACIONES INCLUIDAS
    EvidenciaUtils.registrarCaptura(paso1);

    actor.attemptsTo(
        ClickTextoQueContengaX.elTextoContiene(ADMINISTRA_LAS_APLICACIONES_INCLUIDAS),
        WaitFor.aTime(2000));

    EvidenciaUtils.registrarCaptura(paso2);

    // Validar elementos comunes que siempre aparecen
    actor.attemptsTo(
        ValidarTexto.validarTexto(APLICACIONES_ELEGIBLES),
        ValidarTextoQueContengaX.elTextoContiene(user.getNumero()));

    // Verificar si aparece el popup de "No tienes aplicaciones disponibles"
    List<WebElementFacade> lblsinapp = LBL_SIN_APP_DISPONIBLES.resolveAllFor(actor);
    if (!lblsinapp.isEmpty()) {
      actor.attemptsTo(
          ValidarTexto.validarTexto(NO_TIENES_APLICACIONES_DISPONIBLES),
          ValidarTexto.validarTexto(ACEPTAR_2),
          ClickTextoQueContengaX.elTextoContiene(ACEPTAR_2));

    } else {
      actor.attemptsTo(
          ValidarTexto.validarTexto(APLICACIONES_GRATIS_YA_INSTALADAS),
          ValidarTexto.validarTexto(APLICACIONES_QUE_PUEDES_INSCRIBIR));
    }
  }

  public static Performable administrarYValidar() {
    return instrumented(AdministrarAplicacionesIncluidas.class);
  }
}
