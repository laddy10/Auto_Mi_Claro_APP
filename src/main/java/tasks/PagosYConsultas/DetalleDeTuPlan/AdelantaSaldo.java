package tasks.PagosYConsultas.DetalleDeTuPlan;

import interactions.Click.ClickTextoQueContengaX;
import interactions.comunes.Atras;
import interactions.validations.ValidarTexto;
import interactions.validations.ValidarTextoQueContengaX;
import interactions.wait.WaitForResponse;
import models.User;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import utils.EvidenciaUtils;
import utils.TestDataProvider;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static userinterfaces.PagosYConsultasPage.BTN_TRES_PUNTOS_MAS;
import static utils.Constants.*;

public class AdelantaSaldo implements Task {

    private static final User user = TestDataProvider.getRealUser();
    private static final String paso1 = "Validar versión de miniprograma";
    private static final String paso2 = "Validar direccionamiento correcto Adelanta tu saldo";
    private static final String paso3 = "Hacer clic en botón Adquirir";


    @Override
    public <T extends Actor> void performAs(T actor) {

        actor.attemptsTo(
                Click.on(BTN_TRES_PUNTOS_MAS),
                ClickTextoQueContengaX.elTextoContiene(ACERCA_DE),
                WaitForResponse.withText(DECLARACION_SERVICIO),
                ValidarTexto.validarTexto(ADELANTA_TU_SALDO),
                ValidarTexto.validarTexto(DECLARACION_SERVICIO),
                ValidarTextoQueContengaX.elTextoContiene(VER)
        );

        EvidenciaUtils.registrarCaptura(paso1);

        actor.attemptsTo(
                Atras.irAtras()
        );

        EvidenciaUtils.registrarCaptura(paso2);

        actor.attemptsTo(
                ValidarTextoQueContengaX.elTextoContiene(user.getNumero()),
                ValidarTexto.validarTexto(ADELANTA_SALDO_LABEL),
                ValidarTexto.validarTexto(PRECIO_LABEL),
                ValidarTexto.validarTexto(ADQUIRIR)
        );

        EvidenciaUtils.registrarCaptura(paso3);

        actor.attemptsTo(
                ClickTextoQueContengaX.elTextoContiene(ADQUIRIR),
                WaitForResponse.withText(RESUMEN_DEL_ADELANTO)
        );
    }

    public static Performable validarDireccionamiento() {
        return instrumented(AdelantaSaldo.class);
    }
}