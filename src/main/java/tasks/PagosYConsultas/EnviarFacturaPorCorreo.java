package tasks.PagosYConsultas;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static utils.Constants.*;

import interactions.Click.ClickTextoQueContengaX;
import interactions.Scroll.ScrollHastaTexto;
import interactions.validations.ValidarTexto;
import interactions.validations.ValidarTextoQueContengaX;
import interactions.wait.WaitForResponse;
import models.User;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import utils.EvidenciaUtils;
import utils.TestDataProvider;

public class EnviarFacturaPorCorreo implements Task {
  private final User user = TestDataProvider.getRealUser();
  private static final String paso1 = "Seleccionar Factura por correo electrónico";
  private static final String paso2 = "Verificar número de celular";
  private static final String paso3 = "Validar opciones en Más información";

  @Override
  public <T extends Actor> void performAs(T actor) {

    EvidenciaUtils.registrarCaptura(paso1);
    // Seleccionar "Factura por correo electrónico"
    actor.attemptsTo(
        ScrollHastaTexto.conTexto(ULTIMOS_PAGOS),
        ClickTextoQueContengaX.elTextoContiene(FACTURA_POR_CORREO_ELECTRONICO),
        WaitForResponse.withText(POSTPAGO));

    // Verificar número de celular en pantalla
    actor.attemptsTo(
        ValidarTextoQueContengaX.elTextoContiene(user.getNumero()),
        ValidarTextoQueContengaX.elTextoContiene(RECIBE_TU_FACTURA_A_TRAVES_CORREO));
    EvidenciaUtils.registrarCaptura(paso2);

    // Verificar campos de información (número y email)
    actor.attemptsTo(
        ValidarTexto.validarTexto(NUMERO_CELULAR),
        ValidarTexto.validarTexto(CORREO_ELECTRONICO),
        ValidarTextoQueContengaX.elTextoContiene(user.getNumero().replace(" ", "")),
        ValidarTexto.validarTexto(MAS_INFORMACION),

        // Validar opciones disponibles en "Más información"
        ValidarTextoQueContengaX.elTextoContiene(SUSCRIPCIONES_MODIFICACIONES_DESACTIVACIONES),
        ValidarTextoQueContengaX.elTextoContiene(NOTIFICACIONES_POR_EMAIL_SMS),
        ValidarTextoQueContengaX.elTextoContiene(NOTIFICACIONES_PUBLICACION_FACTURA));

    EvidenciaUtils.registrarCaptura(paso3);
  }

  public static Performable enviarFacturaPorCorreo() {
    return instrumented(EnviarFacturaPorCorreo.class);
  }
}
