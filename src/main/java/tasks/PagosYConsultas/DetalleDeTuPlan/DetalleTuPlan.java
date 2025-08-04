package tasks.PagosYConsultas.DetalleDeTuPlan;

import interactions.Click.ClickTextoQueContengaX;
import interactions.Scroll.Scroll;
import interactions.comunes.Atras;
import interactions.validations.ValidarTexto;
import interactions.validations.ValidarTextoQueContengaX;
import interactions.wait.WaitFor;
import interactions.wait.WaitForResponse;
import models.User;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import utils.AndroidObject;
import utils.EvidenciaUtils;
import utils.TestDataProvider;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static userinterfaces.PagosYConsultasPage.BTN_TRES_PUNTOS_MAS;
import static utils.Constants.*;

public class DetalleTuPlan implements Task {
    private static final User user = TestDataProvider.getRealUser();
    private static final String paso1 = "Seleccionar Detalle de tu plan";
    private static final String paso2 = "Seleccionar línea postpago " + user.getNumero();
    private static final String paso3 = "Validar versión de miniprograma";
    private static final String paso4 = "Validar información detalle del plan";
    private static final String paso5 = "Validar opciones disponibles";

    @Override
    public <T extends Actor> void performAs(T actor) {

        EvidenciaUtils.registrarCaptura(paso1);

        // Seleccionar "Detalle de tu plan"
        actor.attemptsTo(
                ClickTextoQueContengaX.elTextoContiene(DETALLE_DE_TU_PLAN),
                WaitForResponse.withText(POSTPAGO)
        );

        EvidenciaUtils.registrarCaptura(paso2);

        // Seleccionar línea postpago
        AndroidObject.scrollCorto2(actor, LINEA + " " + user.getNumero() + " " + VER_DETALLE);

        actor.attemptsTo(
                ClickTextoQueContengaX.elTextoContiene(user.getNumero()),
                WaitForResponse.withText(DETALLE_DE_TU_PLAN)
        );


        // Validar versión de miniprograma
        actor.attemptsTo(
                WaitFor.aTime(2000),
                Click.on(BTN_TRES_PUNTOS_MAS),
                ClickTextoQueContengaX.elTextoContiene(ACERCA_DE),
                WaitForResponse.withText(DETALLE_DE_TU_PLAN_POSTPAGO),
                ValidarTexto.validarTexto(DETALLE_DE_TU_PLAN_POSTPAGO),
                ValidarTexto.validarTexto(DECLARACION_SERVICIO),
                ValidarTextoQueContengaX.elTextoContiene(VER));

        EvidenciaUtils.registrarCaptura(paso3);

        // Validar información detalle del plan
        actor.attemptsTo(
                Atras.irAtras(),
                ValidarTexto.validarTexto(CONSULTA_LA_FACTURA_DE_TU_PLAN_POSTPAGO),
                ValidarTexto.validarTexto(VER_FACTURA),
                ValidarTexto.validarTexto(TU_PLAN),
                ValidarTextoQueContengaX.elTextoContiene(CARGO_FIJO_MENSUAL_IVA_INCLUIDO),

                // Validar datos del plan
                ValidarTexto.validarTexto(DATOS),
                ValidarTextoQueContengaX.elTextoContiene(TU_PLAN_INCLUYE),
                ValidarTextoQueContengaX.elTextoContiene(VER_DETALLE_DE_REDES),

                // Validar voz
                ValidarTexto.validarTexto(VOZ),
                ValidarTextoQueContengaX.elTextoContiene(SEGUNDOS_PARA_LLAMAR));

        EvidenciaUtils.registrarCaptura(paso4);


        actor.attemptsTo(
                // Validar SMS
                ValidarTexto.validarTexto(SMS),
                Scroll.scrollUnaVista(),

                // Validar Larga Distancia Internacional
                ValidarTexto.validarTexto(LARGA_DISTANCIA_INTERNACIONAL),

                // Validar texto botones
                ValidarTexto.validarTexto(DESCRIPCION_DEL_PLAN),
                ValidarTexto.validarTexto(FAMILIA_Y_AMIGOS),
                ValidarTexto.validarTexto(APLICACIONES_ELEGIBLES),
                ValidarTexto.validarTexto(MEJORAR_PLAN),
                ValidarTexto.validarTexto(PAQUETES_ADICIONALES),
                ValidarTexto.validarTexto(ADMINISTRAR_ROAMING)
        );

        EvidenciaUtils.registrarCaptura(paso5);

    }

    public static Performable validarDetalleTuPlan() {
        return instrumented(DetalleTuPlan.class);
    }
}