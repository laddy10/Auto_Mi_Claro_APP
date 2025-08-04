package tasks.PagosYConsultas.DetalleDeTuPlan;

import interactions.validations.ValidarTexto;
import interactions.validations.ValidarTextoQueContengaX;
import interactions.wait.WaitForResponse;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import utils.EvidenciaUtils;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static utils.Constants.*;

public class GrupoFamiliaYAmigos implements Task {

    private static final String paso1 = "Validar grupo de Familia y Amigos";

    @Override
    public <T extends Actor> void performAs(T actor) {

        EvidenciaUtils.registrarCaptura(paso1);

        actor.attemptsTo(
                ValidarTextoQueContengaX.elTextoContiene(NUMERO_DE_CUENTA),
                ValidarTextoQueContengaX.elTextoContiene(CREA_TU_GRUPO_MAXIMO_5),
                ValidarTextoQueContengaX.elTextoContiene(AGREGA_HASTA_4_LINEAS),
                ValidarTextoQueContengaX.elTextoContiene(NUMEROS_AGREGADOS),
                ValidarTextoQueContengaX.elTextoContiene(NUMEROS_POR_AGREGAR),
                ValidarTextoQueContengaX.elTextoContiene(LIMITE_DE_NUMEROS)
        );
    }

    public static Performable validarGrupo() {
        return instrumented(GrupoFamiliaYAmigos.class);
    }
}