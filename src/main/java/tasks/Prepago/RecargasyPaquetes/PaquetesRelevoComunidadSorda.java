package tasks.Prepago.RecargasyPaquetes;

import interactions.Click.ClickElementByText;
import interactions.Click.ClickTextoQueContengaX;
import interactions.Scroll.Scroll;
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
import static userinterfaces.PagosyConsultasPrePage.LBL_VER_DETALLE_2;
import static utils.AndroidObject.scrollCorto2;
import static utils.Constants.*;

public class PaquetesRelevoComunidadSorda implements Task {

    private static final User user = TestDataProvider.getRealUser();
    private static final String paso1 = "Seleccionar Paquetes Relevo comunidad sorda";
    private static final String paso2 = "Validar primer paquete 800MB - Ver detalle";
    private static final String paso3 = "Validar segundo paquete 2,5GB - Ver detalle";
    private static final String paso4 = "Validar tercer paquete 6GB - Ver detalle";

    @Override
    public <T extends Actor> void performAs(T actor) {

        // PASO 1: Seleccionar tipo de paquete "Paquetes Relevo comunidad sorda"
        actor.attemptsTo(
                Scroll.scrollUnaVista());

        EvidenciaUtils.registrarCaptura(paso1);

        actor.attemptsTo(
                ClickTextoQueContengaX.elTextoContiene(PAQUETES_RELEVO_COMUNIDAD_SORDA),
                WaitForResponse.withText(ELIGE_TU_PAQUETE_IDEAL),
                ValidarTextoQueContengaX.elTextoContiene(user.getNumeroPrepago()),
                ValidarTexto.validarTexto(PAQUETES_RELEVO_COMUNIDAD_SORDA)
        );


        // PAQUETE 1: Validar primer paquete 800MB ($7.500)

        actor.attemptsTo(
                ValidarTexto.validarTexto(LABEL_PAQUETES),
                ValidarTexto.validarTexto("800 MB"),
                ValidarTexto.validarTexto(PRECIO),
                ValidarTexto.validarTexto("$ 7.500"),
                ValidarTexto.validarTexto("SMS ILIMITADOS"),
                ValidarTexto.validarTexto(APPS_INCLUIDAS),
                ClickElementByText.clickElementByText(VER_DETALLE_DEL_PAQUETE),
                ValidarTexto.validarTexto("Este paquete incluye 800MB de Navegacion, ilimitados de SMS + WhatsApp, Facebook , Twitter y la aplicación de Centro de Relevo durante una vigencia 7 dias.")
        );

        EvidenciaUtils.registrarCaptura(paso2);


        // PAQUETE 2: Segundo paquete 2,5GB ($15.500)
        scrollCorto2(actor, "2,5 GB");

        actor.attemptsTo(
                ValidarTexto.validarTexto(LABEL_PAQUETES),
                ValidarTexto.validarTexto("2,5 GB"),
                ValidarTexto.validarTexto(PRECIO),
                ValidarTexto.validarTexto("$ 15.500"),
                ValidarTexto.validarTexto("SMS ILIMITADOS"),
                ValidarTexto.validarTexto(APPS_INCLUIDAS),
                ClickElementByText.clickElementByText(VER_DETALLE_DEL_PAQUETE),
                ValidarTexto.validarTexto(
                        "Este paquete incluye 2,5GB de Navegacion, ilimitados de SMS + WhatsApp, Facebook , Twitter y la aplicación de Centro de Relevo durante una vigencia 15 dias.")
        );

        EvidenciaUtils.registrarCaptura(paso3);


        // PAQUETE 3: Tercer paquete 6GB ($30.500)

        actor.attemptsTo(
                Scroll.scrollUnaVista(),
                ValidarTexto.validarTexto(LABEL_PAQUETES),
                ValidarTexto.validarTexto("6 GB"),
                ValidarTexto.validarTexto(PRECIO),
                ValidarTexto.validarTexto("$ 30.500"),
                ValidarTexto.validarTexto("SMS ILIMITADOS"),
                ValidarTexto.validarTexto(APPS_INCLUIDAS),
                Click.on(LBL_VER_DETALLE_2),
                Scroll.scrollUnaVista(),
                ValidarTexto.validarTexto("Este paquete incluye 6GB de Navegacion, ilimitados de SMS + WhatsApp, Facebook , Twitter y la aplicación de Centro de Relevo durante una vigencia 30 dias.")
        );

        EvidenciaUtils.registrarCaptura(paso4);


    }

    public static Performable validar() {
        return instrumented(PaquetesRelevoComunidadSorda.class);
    }
}