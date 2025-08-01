package tasks.PagosYConsultas.RecargasyPaquetes;

import interactions.Click.ClickTextoQueContengaX;
import interactions.validations.ValidarTexto;
import interactions.validations.ValidarTextoQueContengaX;
import interactions.wait.WaitForResponse;
import models.User;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import utils.EvidenciaUtils;
import utils.TestDataProvider;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static utils.Constants.*;

public class Recargas implements Task {
    private final User user = TestDataProvider.getRealUser();
    private static final String paso1 = "Seleccionar Recargas";
    private static final String paso2 = "Verificar línea y opciones de recarga";
    private static final String paso3 = "Seleccionar boton Comprar";

    @Override
    public <T extends Actor> void performAs(T actor) {

        EvidenciaUtils.registrarCaptura(paso1);

        // Seleccionar "Recargas"
        actor.attemptsTo(
                ClickTextoQueContengaX.elTextoContiene(RECARGAS),
                WaitForResponse.withText(RECARGA_TU_LINEA),

        // Verificar línea y opciones disponibles
                ValidarTexto.validarTexto(RECARGA_TU_LINEA),
                ValidarTextoQueContengaX.elTextoContiene(user.getNumero()),
                ValidarTextoQueContengaX.elTextoContiene(FILTRAR_POR),
                ValidarTextoQueContengaX.elTextoContiene(ORDENAR_POR),
                ValidarTextoQueContengaX.elTextoContiene(RECARGAS_VOZ_Y_DATOS)
        );

        EvidenciaUtils.registrarCaptura(paso2);

        EvidenciaUtils.registrarCaptura(paso3);

        // Comprar recarga seleccionada
        actor.attemptsTo(
                ClickTextoQueContengaX.elTextoContiene(COMPRAR),
                WaitForResponse.withText(ELEGIR_OTRO_MEDIO_PAGO)
        );
    }

    public static Performable seleccionarRecargas() {
        return instrumented(Recargas.class);
    }
}