package tasks.MediosDePagos;

import interactions.Click.ClickTextoQueContengaX;
import interactions.Scroll.ScrollHastaTexto;
import interactions.validations.ValidarTexto;
import interactions.wait.WaitElement;
import interactions.wait.WaitFor;
import interactions.wait.WaitForTextContains;
import models.User;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Enter;
import net.serenitybdd.screenplay.waits.Wait;
import net.serenitybdd.screenplay.waits.WaitUntil;
import utils.EvidenciaUtils;
import utils.TestDataProvider;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isNotPresent;
import static userinterfaces.LoginPage.LOADING_ESPERA_UN_MOMENTO;
import static userinterfaces.MediosPagoPage.DOPDOWN_ENTIDAD_BANCARIA;
import static userinterfaces.MediosPagoPage.TXT_ESCRIBE_CORREO;
import static utils.Constants.*;

public class PSEPagaTuFactua implements Task {

    private static final User user = TestDataProvider.getRealUser();
    private static final String paso1 = "Validar y completar formulario inicial";

    @Override
    public <T extends Actor> void performAs(T actor) {

        EvidenciaUtils.registrarCaptura(paso1);

        actor.attemptsTo(
                ValidarTexto.validarTexto(PAGO_PSE),
                Click.on(DOPDOWN_ENTIDAD_BANCARIA),
                WaitFor.aTime(2000),
                ScrollHastaTexto.conTexto(BANCO_AV_VILLAS),
                ClickTextoQueContengaX.elTextoContiene(BANCO_AV_VILLAS),
                Enter.theValue(user.getEmail()).into(TXT_ESCRIBE_CORREO),
                ClickTextoQueContengaX.elTextoContiene(IR_A_PSE),
                WaitFor.aTime(6000),
                ValidarTexto.validarTexto(INGRESO_BANCO)
        );

    }

    public static Performable validarRedireccionPSEPagaTuFactura() {
        return instrumented(PSEPagaTuFactua.class);
    }
}
