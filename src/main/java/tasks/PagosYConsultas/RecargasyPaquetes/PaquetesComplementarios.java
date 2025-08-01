package tasks.PagosYConsultas.RecargasyPaquetes;

import interactions.Click.ClickTextoQueContengaX;
import interactions.Scroll.Scroll;
import interactions.validations.ValidarTexto;
import interactions.validations.ValidarTextoQueContengaX;
import interactions.wait.WaitFor;
import interactions.wait.WaitForResponse;
import models.User;
import net.serenitybdd.core.pages.WebElementFacade;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import utils.AndroidObject;
import utils.EvidenciaUtils;
import utils.TestDataProvider;

import java.util.List;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static userinterfaces.PagosYConsultasPage.*;
import static utils.Constants.*;

public class PaquetesComplementarios implements Task {

    private final User user = TestDataProvider.getRealUser();
    private static final String paso1 = "Ingresar a Paquetes complementarios";
    private static final String paso2 = "Validar direccionamiento paquetes complementarios";
    private static final String paso3 = "Validar detalle del paquete activo";
    private static final String paso4 = "Validar primer paquete";
    private static final String paso5 = "Validar segundo paquete";
    private static final String paso6 = "Validar tercer paquete";

    @Override
    public <T extends Actor> void performAs(T actor) {

        EvidenciaUtils.registrarCaptura(paso1);

        actor.attemptsTo(
                ClickTextoQueContengaX.elTextoContiene(PAQUETES_COMPLEMENTARIOS),
                WaitForResponse.withText(DESACTIVAR)
        );

        EvidenciaUtils.registrarCaptura(paso2);

        actor.attemptsTo(
                ValidarTexto.validarTexto(PAQUETES_COMPLEMENTARIOS),
                ValidarTextoQueContengaX.elTextoContiene(user.getNumero())
        );

        // Validar paquete activo si existe
        List<WebElementFacade> paqueteActivo = LBL_PAQUETE_ACTIVO.resolveAllFor(actor);
        if (!paqueteActivo.isEmpty()) {
            actor.attemptsTo(
                    ValidarTexto.validarTexto(PAQUETE_ACTIVO),
                    ValidarTexto.validarTexto(DESACTIVAR),
                    ClickTextoQueContengaX.elTextoContiene(VER_DETALLE_DEL_PAQUETE),
                    ValidarTextoQueContengaX.elTextoContiene(COMPLEMENTARIO_RECURRENTE)
            );

            EvidenciaUtils.registrarCaptura(paso3);

        }

        // VALIDAR PRIMER PAQUETE 5GB

        AndroidObject.scrollCorto2(actor, CAMBIAR_PAQUETE);

        actor.attemptsTo(
                //   ScrollHastaTexto.conTexto(CAMBIAR_PAQUETE), // EL SCROLL FUE MUY LARGO
                ValidarTexto.validarTexto(PRECIO)
        );

        // Hacer clic en primer "Ver detalle del paquete"
        actor.attemptsTo(
                Click.on(BTN_VER_DETALLE_PRIMER_PAQUETE),
                WaitFor.aTime(2000),
                ValidarTextoQueContengaX.elTextoContiene(COMPLEMENTARIO_RECURRENTE)
        );

        EvidenciaUtils.registrarCaptura(paso4);


        // VALIDAR SEGUNDO PAQUETE 10GB

        actor.attemptsTo(
                Scroll.scrollUnaVista(),
                Click.on(BTN_VER_DETALLE_SEGUNDO_PAQUETE),
                ValidarTextoQueContengaX.elTextoContiene(COMPLEMENTARIO_RECURRENTE)
        );

        EvidenciaUtils.registrarCaptura(paso5);


        // Hacer clic en segundo "Ver detalle del paquete"
        actor.attemptsTo(
                Scroll.scrollUnaVista(),
                Click.on(BTN_VER_DETALLE_SEGUNDO_PAQUETE),
                WaitFor.aTime(2000),
                ValidarTextoQueContengaX.elTextoContiene(COMPLEMENTARIO_RECURRENTE)

        );

        // VALIDAR TERCER PAQUETE 20GB
        EvidenciaUtils.registrarCaptura(paso6);

    }

    public static Performable ingresar() {
        return instrumented(PaquetesComplementarios.class);
    }

    public static Performable validarDireccionamiento() {
        return instrumented(PaquetesComplementarios.class);
    }
}