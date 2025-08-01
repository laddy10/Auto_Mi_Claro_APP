package tasks.PagosYConsultas.DetalleDeTuPlan;

import interactions.validations.ValidarTexto;
import interactions.validations.ValidarTextoQueContengaX;
import net.serenitybdd.core.pages.WebElementFacade;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import utils.EvidenciaUtils;

import java.util.List;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static userinterfaces.PagosYConsultasPage.LBL_FECHA_EXPIRACION;
import static userinterfaces.PagosYConsultasPage.POPUP_SIN_PQ_ADICIONALES;
import static utils.Constants.*;


public class DireccionamientoRoaming implements Task {

    private static final String paso1 = "Validar direccionamiento Administrar Roaming";
    private static final String paso2 = "Validar Administrar Roaming inactivo";

    @Override
    public <T extends Actor> void performAs(T actor) {

        EvidenciaUtils.registrarCaptura(paso1);

        // Validar elementos que siempre están presentes
        actor.attemptsTo(
                ValidarTexto.validarTexto(ACTIVA_ROAMING),
                ValidarTextoQueContengaX.elTextoContiene(SIGUE_CONECTADO_DURANTE_VIAJE),
                ValidarTexto.validarTexto(SERVICIO_ROAMING)
        );

        // Verificar estado del roaming mediante texto específico

        List<WebElementFacade> RoamingActivado = LBL_FECHA_EXPIRACION.resolveAllFor(actor);
        if (!RoamingActivado.isEmpty()) {

            actor.attemptsTo(
                    ValidarTextoQueContengaX.elTextoContiene(FECHA_DE_EXPIRACION),
                    ValidarTextoQueContengaX.elTextoContiene(MODIFICAR),
                    ValidarTextoQueContengaX.elTextoContiene(VER_PAQUETES)
            );

        } else {

            // Solo validar elementos básicos cuando está desactivado
            actor.attemptsTo(
                    ValidarTextoQueContengaX.elTextoContiene(SIGUE_CONECTADO_DURANTE_VIAJE)
            );

            EvidenciaUtils.registrarCaptura(paso2);

        }
    }

    public static Performable validarDireccionamiento() {
        return instrumented(DireccionamientoRoaming.class);
    }
}