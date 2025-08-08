package tasks.PagosYConsultas.RecargasyPaquetes;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static userinterfaces.PagosYConsultasPage.*;
import static utils.Constants.*;

import interactions.Click.ClickTextoQueContengaX;
import interactions.comunes.Atras;
import interactions.validations.ValidarTexto;
import interactions.validations.ValidarTextoQueContengaX;
import interactions.wait.WaitForResponse;
import models.User;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import utils.AndroidObject;
import utils.EvidenciaUtils;
import utils.TestDataProvider;

public class MediosPagosRecargas implements Task {
  private final User user = TestDataProvider.getRealUser();

  private static final String pasoTarjeta =
      "a. Tarjeta crédito/débito: Redirección a formulario de tarjeta";
  private static final String pasoPse = "b. PSE - NEQUI: Redirección a portal PSE - NEQUI";
  private static final String pasoBancolombia =
      "c. Bancolombia: Redirección a portal de Bancolombia";
  private static final String pasoCodensa = "d. Codensa: Redirección a formulario Codensa";
  private static final String pasoOtrosMedios =
      "e. Otros medios de pago: Redirección a portal Otros medios de pago";
  private static final String pasoResumenCompra = "Resumen de la compra";
  private static final String pasoTYC = "Terminos y condiciones";
  private static final String pasoAceptarTyC = "Aceptar Terminos y condiciones";

  @Override
  public <T extends Actor> void performAs(T actor) {

    // Tarjeta crédito/débito
    actor.attemptsTo(ClickTextoQueContengaX.elTextoContiene(TARJETA_C_D));

    resumenCompra(actor);

    actor.attemptsTo(
        WaitForResponse.withText(AGREGAR_TARJETA),
        ValidarTexto.validarTexto(TARJETAS_NACIONALES),
        ValidarTexto.validarTexto(NUMERO_TARJETA));
    EvidenciaUtils.registrarCaptura(pasoTarjeta);
    cerrarPopupYRegresar(actor);

    // PSE - NEQUI
    actor.attemptsTo(Click.on(BTN_PSE_RECARGAS));

    resumenCompra(actor);

    actor.attemptsTo(
        WaitForResponse.withText(NUMERO_FACTURA),
        ValidarTextoQueContengaX.elTextoContiene(NUMERO_FACTURA),
        ValidarTextoQueContengaX.elTextoContiene(DESCRIPCION_COMPRA),
        ValidarTextoQueContengaX.elTextoContiene(PAGO_TOTAL));
    EvidenciaUtils.registrarCaptura(pasoPse);
    cerrarPopupYRegresar(actor);

    // Bancolombia
    actor.attemptsTo(ClickTextoQueContengaX.elTextoContiene(BOTON_BANCOLOMBIA));

    resumenCompra(actor);

    actor.attemptsTo(
        WaitForResponse.withText(BIENVENIDA),
        ValidarTextoQueContengaX.elTextoContiene(SUCURSAL_VIRTUAL_PERSONAS),
        ValidarTexto.validarTexto(BIENVENIDA),
        ValidarTexto.validarTexto(USUARIO));
    EvidenciaUtils.registrarCaptura(pasoBancolombia);
    cerrarPopupYRegresar(actor);

    // Codensa
    actor.attemptsTo(ClickTextoQueContengaX.elTextoContiene(CODENSA));

    resumenCompra(actor);

    actor.attemptsTo(
        WaitForResponse.withText(PORTAL_PAGOS_CLARO),
        ValidarTexto.validarTexto(TARJETAS_NACIONALES),
        ValidarTexto.validarTexto(NUMERO_TARJETA));
    EvidenciaUtils.registrarCaptura(pasoCodensa);
    cerrarPopupYRegresar(actor);

    // Otros medios de pago
    actor.attemptsTo(ClickTextoQueContengaX.elTextoContiene(OTROS_MEDIOS));

    resumenCompra(actor);

    actor.attemptsTo(
        WaitForResponse.withText(PORTAL_PAGOS_CLARO),
        ValidarTextoQueContengaX.elTextoContiene(VALOR_A_PAGAR),
        ValidarTexto.validarTexto(ESCOGE_MEDIO_PAGO),
        ValidarTextoQueContengaX.elTextoContiene(NUMERO_FACTURA_RECARGAS));
    EvidenciaUtils.registrarCaptura(pasoOtrosMedios);
  }

  private <T extends Actor> void cerrarPopupYRegresar(T actor) {
    actor.attemptsTo(Click.on(ICON_HOME), WaitForResponse.withText(POSTPAGO));

    AndroidObject.scrollCorto2(actor, LINEA + " " + user.getNumero() + " " + ELEGIR);

    actor.attemptsTo(
        ClickTextoQueContengaX.elTextoContiene(user.getNumero()),
        WaitForResponse.withText(RECARGAS),
        ClickTextoQueContengaX.elTextoContiene(RECARGAS),
        WaitForResponse.withText(RECARGA_TU_LINEA),
        ClickTextoQueContengaX.elTextoContiene(COMPRAR),
        WaitForResponse.withText(ELEGIR_OTRO_MEDIO_PAGO));
  }

  private <T extends Actor> void resumenCompra(T actor) {
    actor.attemptsTo(WaitForResponse.withText(RESUMEN_COMPRA));

    EvidenciaUtils.registrarCaptura(pasoResumenCompra);

    actor.attemptsTo(
        ClickTextoQueContengaX.elTextoContiene(TERMINOS_Y_CONDICIONES_RECARGAS),
        WaitForResponse.withText(TERMINOS_CONDICIONES_MI_CLARO),
        ValidarTextoQueContengaX.elTextoContiene(TERMINOS_CONDICIONES_MI_CLARO),
        ValidarTextoQueContengaX.elTextoContiene(COMPRA_DE_PAQUETES_Y_RECARGAS));

    EvidenciaUtils.registrarCaptura(pasoTYC);

    actor.attemptsTo(
        Atras.irAtras(),
        WaitForResponse.withText(RESUMEN_COMPRA),
        Click.on(CHECK_ACEPTAR_TERMINOS_CONDICIONES2));

    EvidenciaUtils.registrarCaptura(pasoAceptarTyC);

    actor.attemptsTo(Click.on(BTN_PAGAR));
  }

  public static Performable redireccionarMediosDePago() {
    return instrumented(MediosPagosRecargas.class);
  }
}
