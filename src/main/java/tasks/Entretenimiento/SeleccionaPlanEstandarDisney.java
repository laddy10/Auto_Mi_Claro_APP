package tasks.Entretenimiento;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static userinterfaces.EntretenimientoPage.*;
import static utils.AdbUtils.ejecutarAdbTap;
import static utils.Constants.*;

import interactions.Scroll.ScrollHorizontalYValidar;
import interactions.validations.ValidarTextoQueContengaX;
import interactions.wait.WaitForTextContains;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import utils.EvidenciaUtils;

/**
 * Task para seleccionar el Plan Estándar de Disney+ Implementa scroll horizontal para encontrar la
 * opción específica
 *
 * <p>Aplicación de principios SOLID: - SRP: Responsabilidad única de seleccionar plan Disney - OCP:
 * Extensible para otros planes sin modificar código existente - DIP: Depende de abstracciones
 * (Interactions) no de implementaciones
 */
public class SeleccionaPlanEstandarDisney implements Task {

  private static final String PLAN_DISNEY_ESTANDAR = "Disney+ Premium";
  private static final String paso = "Seleccionar Plan Estándar Disney+";

  @Override
  public <T extends Actor> void performAs(T actor) {
    actor.attemptsTo(
        // Espera inicial para cargar la interfaz
        WaitForTextContains.withAnyTextContains("Todos los canales de ESPN"),
        // Scroll horizontal hasta encontrar el plan Disney+ Estándar
        ScrollHorizontalYValidar.scrollIzquierdaYValidar(ELEGIR_PLAN)

        // Validar que el plan está visible (opcional pero recomendado)
        //ValidarTextoQueContengaX.elTextoContiene(ELEGIR_PLAN)
    );

    // Captura de evidencia después del scroll
    EvidenciaUtils.registrarCaptura(paso + " - Plan encontrado");

    /*  actor.attemptsTo(
    // Click en el botón Elegir Plan
    Click.on(BTN_ELEGIR_PLAN_PREMIUM)); */

    ejecutarAdbTap(523, 1786); // Simula un toque en las coordenadas
  }

  /**
   * Factory method siguiendo el patrón del proyecto
   *
   * @return Performable task para seleccionar plan Disney+ Estándar
   */
  public static Performable seleccionar() {
    return instrumented(SeleccionaPlanEstandarDisney.class);
  }
}
