package tasks.PagosYConsultas;

import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static net.serenitybdd.screenplay.Tasks.instrumented;
import static org.hamcrest.core.IsEqual.equalTo;
import static userinterfaces.PagosYConsultasPage.BTN_TRES_PUNTOS_MAS;
import static userinterfaces.VersionesMiniProgramaPage.MINI_VERSION_RECARGAS_Y_PAQUETES_TARGET;
import static utils.Constants.*;
import static utils.ConstantsMiniVersiones.Versiones.MINI_VERSION_RECARGAS_Y_PAQUETES_CONSTANT;

import interactions.Click.ClickTextoQueContengaX;
import interactions.comunes.Atras;
import interactions.validations.ValidarTexto;
import interactions.validations.ValidarTextoQueContengaX;
import interactions.validations.ValidateInformationText;
import interactions.wait.WaitForResponse;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import utils.EvidenciaUtils;

public class ValidarInfoRecargas implements Task {
    private static final String paso = "Validar Mini Versión de Recargas";

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                Click.on(BTN_TRES_PUNTOS_MAS),
                ClickTextoQueContengaX.elTextoContiene(ACERCA_DE),
                WaitForResponse.withText(RECARGAS_Y_PAQUETES),
                ValidarTexto.validarTexto(RECARGAS_Y_PAQUETES),
                ValidarTexto.validarTexto(DECLARACION_SERVICIO),
                ValidarTextoQueContengaX.elTextoContiene(VER));

        EvidenciaUtils.registrarCaptura(paso);

        actor.should(seeThat(ValidateInformationText.validateInformationText(MINI_VERSION_RECARGAS_Y_PAQUETES_TARGET),
                equalTo(MINI_VERSION_RECARGAS_Y_PAQUETES_CONSTANT)));

        actor.attemptsTo(Atras.irAtras());
    }

    public static Performable validarInfoRecargas() {
        return instrumented(ValidarInfoRecargas.class);
    }
}
