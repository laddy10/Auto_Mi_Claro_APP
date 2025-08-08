package tasks.Prepago.RecargasyPaquetes;

import interactions.Click.ClickElementByText;
import interactions.Click.ClickTextoQueContengaX;
import interactions.Scroll.Scroll;
import interactions.validations.ValidarTexto;
import interactions.wait.WaitForResponse;
import models.User;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import utils.EvidenciaUtils;
import utils.TestDataProvider;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static userinterfaces.PagosyConsultasPrePage.LBL_VER_DETALLE_2;
import static userinterfaces.PagosyConsultasPrePage.LBL_VER_DETALLE_3;
import static utils.Constants.*;

public class PaquetesDeVoz implements Task {

    private static final User user = TestDataProvider.getRealUser();
    private static final String paso1 = "Seleccionar Paquetes de voz";
    private static final String paso2 = "Validar primer paquete 300 Min - Ver detalle";
    private static final String paso3 = "Validar segundo paquete 1000 Min - Ver detalle";
    private static final String paso4 = "Validar tercer paquete 300 Min - Ver detalle";

    @Override
    public <T extends Actor> void performAs(T actor) {

        // PASO 1: Seleccionar tipo de paquete "Paquetes de voz"
        actor.attemptsTo(
                Scroll.scrollUnaVista());

        EvidenciaUtils.registrarCaptura(paso1);

        actor.attemptsTo(
                ClickTextoQueContengaX.elTextoContiene(PAQUETES_DE_VOZ),
                WaitForResponse.withText(ELIGE_TU_PAQUETE_IDEAL)
        );

        // PASO 2: Validar y explorar primer paquete 300 Min ($2.000)

        actor.attemptsTo(
                ValidarTexto.validarTexto(LABEL_PAQUETES),
                ValidarTexto.validarTexto(PRECIO),
                ValidarTexto.validarTexto("$ 2.000"),
                ValidarTexto.validarTexto("300 Min"),
                ClickElementByText.clickElementByText(VER_DETALLE_DEL_PAQUETE),
                ValidarTexto.validarTexto("Paquete 300 Minutos Vig 1 dia")
        );

        EvidenciaUtils.registrarCaptura(paso2);


        // PASO 3: Validar y explorar segundo paquete 1000 Min ($16.500)

        actor.attemptsTo(
                Scroll.scrollUnaVista(),
                ValidarTexto.validarTexto(LABEL_PAQUETES),
                ValidarTexto.validarTexto(PRECIO),
                ValidarTexto.validarTexto("$ 16.500"),
                ValidarTexto.validarTexto("1000 Min"),
                Click.on(LBL_VER_DETALLE_2),
                ValidarTexto.validarTexto("Paquete 1000 Minutos Vig 20 dias")
        );

        EvidenciaUtils.registrarCaptura(paso3);


        // PASO 4: Validar y explorar tercer paquete 300 Min ($2.500)

        actor.attemptsTo(
                Scroll.scrollUnaVista(),
                ValidarTexto.validarTexto(LABEL_PAQUETES),
                ValidarTexto.validarTexto(PRECIO),
                ValidarTexto.validarTexto("$ 2.500"),
                ValidarTexto.validarTexto("300 Min"),
                Click.on(LBL_VER_DETALLE_3),
                ValidarTexto.validarTexto("Paquete 300 Minutos Vig 2 dias")
        );

        EvidenciaUtils.registrarCaptura(paso4);


    }

    public static Performable validar() {
        return instrumented(PaquetesDeVoz.class);
    }
}