package tasks.Prepago.RecargasyPaquetes;

import interactions.Click.ClickElementByText;
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

public class ValidarPaqueteSeleccionado implements Task {

    private static final User user = TestDataProvider.getRealUser();
    private static final String paso1 = "Validar paquete seleccionado";
    private static final String paso2 = "Ver detalle del paquete y dar clic en boton Ir a pagar";

    @Override
    public <T extends Actor> void performAs(T actor) {

        EvidenciaUtils.registrarCaptura(paso1);

        actor.attemptsTo(
                ValidarTexto.validarTexto(PAQUETE_ARMADO_PARA_LINEA),
                ValidarTextoQueContengaX.elTextoContiene(user.getNumeroPrepago()),
                ValidarTextoQueContengaX.elTextoContiene(TODOS_PAQUETES_INCLUYEN),
                ValidarTexto.validarTexto(LABEL_PAQUETES),
                ValidarTexto.validarTexto(user.getCantidadDatosArmar()),
                ValidarTexto.validarTexto(PRECIO),
                ValidarTexto.validarTexto(MIN_ILIMITADOS),
                ValidarTextoQueContengaX.elTextoContiene("Vigencia " + user.getDuracionPaqueteArmar()),
                ValidarTexto.validarTexto(APPS_INCLUIDAS),
                ValidarTexto.validarTexto(VER_DETALLE_DEL_PAQUETE),
                ValidarTexto.validarTexto(IR_A_PAGAR),
                ValidarTexto.validarTexto(EDITAR),
                ClickElementByText.clickElementByText(VER_DETALLE_DEL_PAQUETE)
        );

        EvidenciaUtils.registrarCaptura(paso2);

        actor.attemptsTo(
                ClickElementByText.clickElementByText(IR_A_PAGAR),
                WaitForResponse.withText(ELEGIR_OTRO_MEDIO_PAGO)
        );

    }

    public static Performable validarPaquete() {
        return instrumented(ValidarPaqueteSeleccionado.class);
    }
}