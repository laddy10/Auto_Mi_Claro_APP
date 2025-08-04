package tasks.PagosYConsultas.DetalleDeTuPlan;

import interactions.Click.ClickTextoQueContengaX;
import interactions.Scroll.Scroll;
import interactions.validations.ValidarTextoQueContengaX;
import interactions.wait.WaitFor;
import models.User;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import utils.EvidenciaUtils;
import utils.TestDataProvider;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static utils.Constants.*;

public class MejorarPlan implements Task {

    private static final User user = TestDataProvider.getRealUser();
    private static final String paso1 = "Validar direccionamiento Mejorar plan";
    private static final String paso2 = "Direccionamiento correcto";

    @Override
    public <T extends Actor> void performAs(T actor) {

        actor.attemptsTo(
                // Validar elementos principales
                ValidarTextoQueContengaX.elTextoContiene(MEJORA_TU_PLAN),
                ValidarTextoQueContengaX.elTextoContiene(user.getNumero()),
                ValidarTextoQueContengaX.elTextoContiene(PLAN_LABEL),
                ValidarTextoQueContengaX.elTextoContiene(PRECIO_LABEL),
                ValidarTextoQueContengaX.elTextoContiene(COMPRAR),
                ValidarTextoQueContengaX.elTextoContiene(VER_PLANES_ESPECIALES)
        );

        EvidenciaUtils.registrarCaptura(paso1);

        actor.attemptsTo(
                ClickTextoQueContengaX.elTextoContiene(VER_MAS_PLANES),
                WaitFor.aTime(3000),
                Scroll.scrollUnaVista(),
                Scroll.scrollUnaVista()
        );

        EvidenciaUtils.registrarCaptura(paso2);

    }

    public static Performable validarDireccionamiento() {
        return instrumented(MejorarPlan.class);
    }
}