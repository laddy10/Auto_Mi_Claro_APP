package tasks.PagosYConsultas.DetalleDeTuPlan;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static userinterfaces.PagosYConsultasPage.*;
import static utils.AndroidObject.digitarDesdeTeclado;
import static utils.Constants.*;

import interactions.Click.ClickElementByText;
import interactions.Click.ClickTextoQueContengaX;
import interactions.validations.ValidarTextoQueContengaX;
import interactions.wait.WaitFor;
import interactions.wait.WaitForResponse;
import models.User;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import utils.EvidenciaUtils;
import utils.TestDataProvider;

public class DesactivarFamiliaYAmigos implements Task {

  private final User user = TestDataProvider.getRealUser();

  private static final String paso1 = "Validar pantalla Administra tu grupo";
  private static final String paso2 = "Agregar número";
  private static final String paso3 = "Confirmar la linea a agregar";
  private static final String paso4 = "Validar Popup exitoso";
  private static final String paso5 = "Validar registro de numero agregado y eliminarlo";
  private static final String paso6 = "Eliminar numero de familia y amigos";
  private static final String paso7 = "Popup eliminación exitosa";
  private static final String paso8 = "Desactivar servicio";
  private static final String paso9 = "Verificar popup desactivación";

  @Override
  public <T extends Actor> void performAs(T actor) {

    // 1. Validar pantalla inicial
    EvidenciaUtils.registrarCaptura(paso1);

    actor.attemptsTo(Click.on(ICON_MAS_FAMILIA_Y_AMIGOS), WaitFor.aTime(2000));

    actor.attemptsTo(Click.on(TXT_NUMERO_FAMILIA));
    digitarDesdeTeclado(user.getNumeroFamiliayAmigos());

    //  Enter.theValue(user.getNumeroFamiliayAmigos()).into(TXT_NUMERO_FAMILIA));

    EvidenciaUtils.registrarCaptura(paso2);

    actor.attemptsTo(
        ClickTextoQueContengaX.elTextoContiene(AGREGAR),
        WaitForResponse.withText(CONFIRMAR_AGREGAR));

    EvidenciaUtils.registrarCaptura(paso3);

    actor.attemptsTo(
        ClickTextoQueContengaX.elTextoContiene(CONFIRMAR_AGREGAR),
        WaitForResponse.withText(LINEA_AGREGADA_EXITO));

    EvidenciaUtils.registrarCaptura(paso4);

    actor.attemptsTo(ClickElementByText.clickElementByText(ACEPTAR_2));

    EvidenciaUtils.registrarCaptura(paso5);

    actor.attemptsTo(
        ValidarTextoQueContengaX.elTextoContiene(user.getNumeroFamiliayAmigos()),
        Click.on(ICON_ELIMINAR),
        ValidarTextoQueContengaX.elTextoContiene(user.getNumeroFamiliayAmigos()));

    EvidenciaUtils.registrarCaptura(paso6);

    actor.attemptsTo(
        ClickTextoQueContengaX.elTextoContiene(SI_ELIMINAR),
        WaitForResponse.withText(CERRAR),
        ValidarTextoQueContengaX.elTextoContiene(SMS_ELIMINADO_EXITOSO));

    EvidenciaUtils.registrarCaptura(paso7);

    actor.attemptsTo(ClickTextoQueContengaX.elTextoContiene(CERRAR));

    EvidenciaUtils.registrarCaptura(paso8);

    actor.attemptsTo(
        ClickElementByText.clickElementByText(DESACTIVAR),
        ValidarTextoQueContengaX.elTextoContiene(AL_DESACTIVARLOS_SE_ELIMINARAN));

    EvidenciaUtils.registrarCaptura(paso9);
  }

  public static Performable gestionarFamiliaYAmigos() {
    return instrumented(DesactivarFamiliaYAmigos.class);
  }
}
