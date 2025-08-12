package tasks.Entretenimiento;

import interactions.Click.ClickTextoQueContengaX;
import interactions.Scroll.ScrollHorizontalHastaTexto;
import interactions.Scroll.ScrollHorizontalYValidar;
import interactions.validations.ValidarTextoQueContengaX;
import interactions.wait.WaitFor;
import interactions.wait.WaitForResponse;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import utils.EvidenciaUtils;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static utils.Constants.*;

/**
 * Task para seleccionar el Plan Estándar de Disney+
 * Implementa scroll horizontal para encontrar la opción específica
 *
 * Aplicación de principios SOLID:
 * - SRP: Responsabilidad única de seleccionar plan Disney
 * - OCP: Extensible para otros planes sin modificar código existente
 * - DIP: Depende de abstracciones (Interactions) no de implementaciones
 */
public class SeleccionaPlanEstandarDisney implements Task {

    private static final String PLAN_DISNEY_ESTANDAR = "Disney+ Premium";
    private static final String paso = "Seleccionar Plan Estándar Disney+";

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                // Espera inicial para cargar la interfaz
                WaitForResponse.withAnyText("Disney+ Premium"),

                // Scroll horizontal hasta encontrar el plan Disney+ Estándar
                ScrollHorizontalYValidar.scrollIzquierdaYValidar(ELEGIR_PLAN),

                // Validar que el plan está visible (opcional pero recomendado)
                ValidarTextoQueContengaX.elTextoContiene(ELEGIR_PLAN)
        );

        // Captura de evidencia después del scroll
        EvidenciaUtils.registrarCaptura(paso + " - Plan encontrado");

        actor.attemptsTo(
                // Click en el botón Elegir Plan
                ClickTextoQueContengaX.elTextoContiene(ELEGIR_PLAN)
        );
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