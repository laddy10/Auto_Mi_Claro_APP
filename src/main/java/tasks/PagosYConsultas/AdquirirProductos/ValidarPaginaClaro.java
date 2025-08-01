package tasks.PagosYConsultas.AdquirirProductos;

import interactions.Scroll.ScrollHastaTexto;
import interactions.validations.ValidarTexto;
import interactions.validations.ValidarTextoQueContengaX;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import utils.EvidenciaUtils;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static utils.Constants.*;

public class ValidarPaginaClaro implements Task {

    private static final String paso1 = "Validar redireccionamiento a página de Claro";
    private static final String paso2 = "Validar página de Claro";

    @Override
    public <T extends Actor> void performAs(T actor) {

        EvidenciaUtils.registrarCaptura(paso1);

        actor.attemptsTo(
                ValidarTextoQueContengaX.elTextoContiene(CLARO_COLOMBIA),
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