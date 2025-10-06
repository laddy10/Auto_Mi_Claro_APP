package tasks.PagosYConsultas.AdquirirProductos;

import interactions.Click.ClickTextoQueContengaX;
import interactions.Scroll.ScrollHastaTexto;
import interactions.validations.ValidarTexto;
import interactions.validations.ValidarTextoQueContengaX;
import interactions.wait.WaitFor;
import net.serenitybdd.core.pages.WebElementFacade;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import utils.EvidenciaUtils;

import java.util.List;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static userinterfaces.PagosyConsultasPrePage.LBL_MENSAJE_COOKIES;
import static utils.Constants.*;

public class ValidarPaginaClaro implements Task {

    private static final String paso1 = "Validar redireccionamiento a página de Claro";
    private static final String paso2 = "Validar página de Claro";

    @Override
    public <T extends Actor> void performAs(T actor) {


        if (LBL_MENSAJE_COOKIES.resolveFor(actor).isVisible()) {
            actor.attemptsTo(
                    ClickTextoQueContengaX.elTextoContiene(ENTENDIDO),
                    WaitFor.aTime(2000)
            );
        }else
        {
            EvidenciaUtils.registrarCaptura(paso1);
            return;
        }


        actor.attemptsTo(
                ValidarTexto.validarTexto(COMPRA_POR_CATEGORIA),
                ScrollHastaTexto.conTexto(NUESTROS_SERVICIOS)
        );

        EvidenciaUtils.registrarCaptura(paso2);

        actor.attemptsTo(
                ValidarTextoQueContengaX.elTextoContiene(PASATE_A_POSTPAGO),
                ValidarTextoQueContengaX.elTextoContiene(RENUEVA_TU_HOGAR),
                ValidarTextoQueContengaX.elTextoContiene(SERVICIOS_HOGAR)
        );
    }

    public static Performable validarRedireccionamiento() {
        return instrumented(ValidarPaginaClaro.class);
    }
}