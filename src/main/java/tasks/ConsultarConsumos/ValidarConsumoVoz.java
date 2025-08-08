package tasks.ConsultarConsumos;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static userinterfaces.ConsultarConsumosPage.LBL_AUN_NO_REGISTRAS_CONSUMOS;
import static userinterfaces.ConsultarConsumosPage.OCULTAR_OPCIONES;
import static utils.Constants.*;

import interactions.Click.ClickTextoQueContengaX;
import interactions.validations.ValidarTexto;
import interactions.validations.ValidarTextoQueContengaX;
import interactions.wait.WaitFor;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.questions.Presence;
import net.serenitybdd.screenplay.targets.Target;
import utils.EvidenciaUtils;

public class ValidarConsumoVoz implements Task {

  private static final String paso = "Validar Consumo de Voz";

  @Override
  public <T extends Actor> void performAs(T actor) {
    // Navegación inicial común
    actor.attemptsTo(
        Click.on(OCULTAR_OPCIONES),
        WaitFor.aTime(2000),
        ClickTextoQueContengaX.elTextoContiene(CONSUMO_DE_VOZ),
        WaitFor.aTime(3000));

    // Verificar qué escenario tenemos
    if (isVisible(actor, LBL_AUN_NO_REGISTRAS_CONSUMOS)) {
      // Escenario 1: No hay consumos registrados
      validarSinConsumos(actor);
    } else {
      // Escenario 2: Hay información de consumo disponible
      validarConInformacionDisponible(actor);
    }

    EvidenciaUtils.registrarCaptura(paso);
  }

  /** Valida el escenario donde no hay consumos registrados */
  private <T extends Actor> void validarSinConsumos(T actor) {
    System.out.println("✅ Escenario detectado: Sin consumos de voz registrados");

    actor.attemptsTo(ValidarTextoQueContengaX.elTextoContiene(AUN_NO_REGISTRAS_CONSUMOS_CUENTA));

    EvidenciaUtils.registrarCaptura(paso);
  }

  /** Valida el escenario donde hay información de consumo disponible */
  private <T extends Actor> void validarConInformacionDisponible(T actor) {
    System.out.println("✅ Escenario detectado: Información de consumo de voz disponible");

    actor.attemptsTo(
        // Validar título principal
        ValidarTexto.validarTexto(CONSUMO_DE_VOZ),

        // Validar sección Móviles Claro
        ValidarTexto.validarTexto(MOVILES_CLARO),

        // Validar sección A otros operadores
        ValidarTexto.validarTexto(A_OTROS_OPERADORES),

        // Validar sección a fijos
        ValidarTexto.validarTexto(A_FIJOS),

        // Validar sección Roaming Internacional
        ValidarTexto.validarTexto(ROAMING_INTERNACIONAL_TEXTO),
        ValidarTextoQueContengaX.elTextoContiene("Entrante por paquete:"),
        ValidarTextoQueContengaX.elTextoContiene("Larga distancia Internacional por paquete:"),
        ValidarTextoQueContengaX.elTextoContiene("Local por paquete:"),
        ValidarTextoQueContengaX.elTextoContiene("Entrante por demanda:"),
        ValidarTextoQueContengaX.elTextoContiene("Local por demanda:"));

    EvidenciaUtils.registrarCaptura(paso);
  }

  /** Método de utilidad para verificar si un elemento está visible */
  private <T extends Actor> boolean isVisible(T actor, Target element) {
    try {
      return Presence.of(element).answeredBy(actor);
    } catch (Exception e) {
      return false;
    }
  }

  public static Performable validar() {
    return instrumented(ValidarConsumoVoz.class);
  }
}
