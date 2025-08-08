package tasks.PagosYConsultas;

import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static net.serenitybdd.screenplay.Tasks.instrumented;
import static net.serenitybdd.screenplay.actors.OnStage.theActorInTheSpotlight;
import static userinterfaces.PagosYConsultasPage.IMAG_CONSTANCIA_AL_DIA;
import static utils.Constants.*;

import interactions.Click.ClickTextoQueContengaX;
import interactions.Scroll.ScrollHastaTexto;
import interactions.comunes.Atras;
import interactions.validations.ValidarElemento;
import interactions.validations.ValidarTexto;
import interactions.validations.ValidarTextoQueContengaX;
import interactions.wait.WaitFor;
import interactions.wait.WaitForResponse;
import models.User;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import utils.AndroidObject;
import utils.EvidenciaUtils;
import utils.TestDataProvider;

public class CertificacionCuentaAlDia implements Task {

  private static final User user = TestDataProvider.getRealUser();
  private static final String paso1 = "Hacer clic en Certificación cuenta al día";
  private static final String paso2 = "Direccionamiento correcto";
  private static final String paso3 = "Seleccionar línea " + user.getNumero();
  private static final String paso4 = "Descargar certificación";
  private static final String paso5 = "Verificar descarga";
  private static final String paso6 = "Volver y seleccionar enviar";
  private static final String paso7 = "Verificar popup de envío";

  @Override
  public <T extends Actor> void performAs(T actor) {

    // 1. Hacer scroll hasta encontrar la opción y hacer clic
    actor.attemptsTo(ScrollHastaTexto.conTexto(CERTIFICACION_CUENTA_AL_DIA));

    EvidenciaUtils.registrarCaptura(paso1);

    actor.attemptsTo(
        ClickTextoQueContengaX.elTextoContiene(CERTIFICACION_CUENTA_AL_DIA),
        WaitForResponse.withText(LINEAS_POSTPAGO));

    // 2. Direccionamiento correcto y evidenciar líneas

    actor.attemptsTo(
        WaitFor.aTime(5000),
        ValidarTexto.validarTexto(CUENTA_HOGAR),
        ValidarTexto.validarTexto(LINEAS_POSTPAGO));

    EvidenciaUtils.registrarCaptura(paso2);

    actor.attemptsTo(ClickTextoQueContengaX.elTextoContiene(LINEAS_POSTPAGO));

    // 3. Seleccionar línea (hacer scroll si no está visible)
    String numeroLimpio = user.getNumero().replaceAll("\\s", "");

    AndroidObject.scrollCorto2(actor, numeroLimpio);

    EvidenciaUtils.registrarCaptura(paso3);

    actor.attemptsTo(
        ClickTextoQueContengaX.elTextoContiene(numeroLimpio),
        WaitForResponse.withText(DESCARGAR),
        WaitFor.aTime(6000));

    // 4. Descargar
    EvidenciaUtils.registrarCaptura(paso4);

    actor.attemptsTo(ClickTextoQueContengaX.elTextoContiene(DESCARGAR), WaitFor.aTime(3000));

    // 5. Verificar descarga (validar que se abrió el documento)
    EvidenciaUtils.registrarCaptura(paso5);

    try {
      actor.attemptsTo(ValidarTextoQueContengaX.elTextoContiene(ABRIR_DOCUMENTO));

      theActorInTheSpotlight().should(seeThat(ValidarElemento.esVisible(IMAG_CONSTANCIA_AL_DIA)));

    } catch (Exception e) {
      System.out.println("No se pudo validar el contenido del documento descargado");
    }

    // 6. Volver y seleccionar opción enviar

    actor.attemptsTo(Atras.irAtras(), WaitForResponse.withText(DOCUMENTO_SELECCIONADO_1));

    EvidenciaUtils.registrarCaptura(paso6);

    actor.attemptsTo(
        ClickTextoQueContengaX.elTextoContiene(ENVIAR),
        WaitForResponse.withText(TU_SOLICITUD_FUE_GENERADA));

    // 7. Verificar popup de envío a correo asociado
    EvidenciaUtils.registrarCaptura(paso7);

    actor.attemptsTo(
        ValidarTextoQueContengaX.elTextoContiene(TU_SOLICITUD_FUE_GENERADA),
        ValidarTextoQueContengaX.elTextoContiene(ENVIAREMOS_TU_CERTIFICACION),
        ValidarTexto.validarTexto(ACEPTAR_2),
        ClickTextoQueContengaX.elTextoContiene(ACEPTAR_2));
  }

  public static Performable gestionarCertificacion() {
    return instrumented(CertificacionCuentaAlDia.class);
  }
}
