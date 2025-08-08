package tasks.Prepago.RecargasyPaquetes;

import interactions.Click.ClickElementByText;
import interactions.Click.ClickTextoQueContengaX;
import interactions.Scroll.Scroll;
import interactions.validations.ValidarTexto;
import interactions.wait.WaitFor;
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
import static utils.Constants.ULTIMO;

public class TodoIncluidoConRedes implements Task {

    private static final User user = TestDataProvider.getRealUser();
    private static final String paso1 = "Seleccionar Paquetes todo incluido con redes";
    private static final String paso2 = "Validar primer paquete 400MB - Ver detalle";
    private static final String paso3 = "Validar segundo paquete 50MB - Ver detalle";
    private static final String paso4 = "Validar tercer paquete 400MB - Ver detalle";
    private static final String paso5 = "Dar clic en Último para segunda pagina";
    private static final String paso6 = "Validar cuarto paquete 1.4GB - Ver detalle";
    private static final String paso7 = "Validar quinto paquete 1.4GB - Ver detalle";
    private static final String paso8 = "Validar sexto paquete 2 GB - Ver detalle";
    private static final String paso9 = "Dar clic en Último para tercera pagina";
    private static final String paso10 = "Validar septimo paquete 3.5 GB - Ver detalle";
    private static final String paso11 = "Validar octavo paquete 80 GB - Ver detalle";
    private static final String paso12 = "Validar noveno paquete 7.5 GB - Ver detalle";
    private static final String paso13 = "Dar clic en Último para cuarta y ultima pagina";
    private static final String paso14 = "Validar decimo paquete 12 GB - Ver detalle";
    private static final String paso15 = "Validar undecimo paquete 18 GB - Ver detalle";


    @Override
    public <T extends Actor> void performAs(T actor) {

        // VALIDAR RESUMEN DE LA COMPRA
        EvidenciaUtils.registrarCaptura(paso1);

        actor.attemptsTo(
                ClickTextoQueContengaX.elTextoContiene(PAQUETES_TODO_INCLUIDO_CON_REDES),
                WaitFor.aTime(2000)
        );

        // PASO 1: Validar y explorar primer paquete 400MB

        actor.attemptsTo(
                ValidarTexto.validarTexto(LABEL_PAQUETES),
                ValidarTexto.validarTexto("400 MB"),
                ValidarTexto.validarTexto(PRECIO),
                ValidarTexto.validarTexto("$ 4.500"),
                ValidarTexto.validarTexto("Min ILIMITADOS"),
                ValidarTexto.validarTexto("SMS ILIMITADOS"),
                ValidarTexto.validarTexto("Apps incluidas"),
                ClickElementByText.clickElementByText(VER_DETALLE_DEL_PAQUETE),
                ValidarTexto.validarTexto("Todo incluido 3 Días con Minutos Ilimitados + 400MB")
        );

        EvidenciaUtils.registrarCaptura(paso2);


        // PASO 2: Validar y explorar segundo paquete 50 MB

        scrollCorto2(actor, "$ 2.500");

        actor.attemptsTo(
                ValidarTexto.validarTexto(LABEL_PAQUETES),
                ValidarTexto.validarTexto("50 MB"),
                ValidarTexto.validarTexto(PRECIO),
                ValidarTexto.validarTexto("$ 2.500"),
                ValidarTexto.validarTexto("50 min"),
                ValidarTexto.validarTexto("SMS ILIMITADOS"),
                ValidarTexto.validarTexto("Apps incluidas"),
                ClickElementByText.clickElementByText(VER_DETALLE_DEL_PAQUETE),
                ValidarTexto.validarTexto("Todo Incluido 50 minutos + SMS Ilimitados + W + 50 MB 1 Dia")
        );

        EvidenciaUtils.registrarCaptura(paso3);

        // PASO 3: Validar y explorar tercer paquete 400MB

        actor.attemptsTo(
                Scroll.scrollUnaVista(),
                ValidarTexto.validarTexto(LABEL_PAQUETES),
                ValidarTexto.validarTexto("400MB"),
                ValidarTexto.validarTexto(PRECIO),
                ValidarTexto.validarTexto("$ 5.500"),
                ValidarTexto.validarTexto("Min ILIMITADOS"),
                ValidarTexto.validarTexto("SMS ILIMITADOS"),
                ValidarTexto.validarTexto("Apps incluidas"),
                Click.on(LBL_VER_DETALLE_2),
                ValidarTexto.validarTexto(
                        "Todo Incluido 400MB + WhatsApp, Facebook y Twitter + Salud en Linea 4 beneficiarios por 3 Dias")
        );

        EvidenciaUtils.registrarCaptura(paso4);

        EvidenciaUtils.registrarCaptura(paso5);

        actor.attemptsTo(
                ClickTextoQueContengaX.elTextoContiene(ULTIMO),
                WaitForResponse.withText(ELIGE_TIPO_PAQUETE),

                // PASO 4: Validar y explorar cuarto paquete 1.4GB
                ValidarTexto.validarTexto(LABEL_PAQUETES),
                ValidarTexto.validarTexto("1.4 GB"),
                ValidarTexto.validarTexto(PRECIO),
                ValidarTexto.validarTexto("$ 8.500"),
                ValidarTexto.validarTexto("Min ILIMITADOS"),
                ValidarTexto.validarTexto("SMS ILIMITADOS"),
                ValidarTexto.validarTexto("Apps incluidas"),
                ClickElementByText.clickElementByText(VER_DETALLE_DEL_PAQUETE),
                ValidarTexto.validarTexto(
                        "Todo Incluido 1.4GB + WhatsApp, Facebook y Twitter + Salud en Linea 4 beneficiarios por 6 Dias")
        );

        EvidenciaUtils.registrarCaptura(paso6);


        // PASO 5: Validar y explorar quinto paquete 1.4GB
        scrollCorto2(actor, "$ 7.500");

        actor.attemptsTo(
                ValidarTexto.validarTexto(LABEL_PAQUETES),
                ValidarTexto.validarTexto("1.4 GB"),
                ValidarTexto.validarTexto(PRECIO),
                ValidarTexto.validarTexto("$ 7.500"),
                ValidarTexto.validarTexto("Min ILIMITADOS"),
                ValidarTexto.validarTexto("SMS ILIMITADOS"),
                ValidarTexto.validarTexto("Apps incluidas"),
                ClickElementByText.clickElementByText(VER_DETALLE_DEL_PAQUETE),
                ValidarTexto.validarTexto(
                        "Este paquete incluye los servicios ilimitados de Minutos y SMS todo destino + 1.4GB + WhatsApp, Twitter y Facebook, sin descontar de la capacidad incluida, Vigencia 6 dias")
        );

        EvidenciaUtils.registrarCaptura(paso7);

        // PASO :6 Validar y explorar sexto paquete 2GB
        actor.attemptsTo(
                Scroll.scrollUnaVista(),
                ValidarTexto.validarTexto(LABEL_PAQUETES),
                ValidarTexto.validarTexto("2 GB"),
                ValidarTexto.validarTexto(PRECIO),
                ValidarTexto.validarTexto("$ 8.500"),
                ValidarTexto.validarTexto("Min ILIMITADOS"),
                ValidarTexto.validarTexto("SMS ILIMITADOS"),
                ValidarTexto.validarTexto("Apps incluidas"),
                Click.on(LBL_VER_DETALLE_2),
                ValidarTexto.validarTexto(
                        "Este paquete incluye los servicios ilimitados de Minutos y SMS todo destino + 2GB + WhatsApp, Twitter y Facebook sin descontar de la capacidad incluida, Vigencia 7 dias.")
        );

        EvidenciaUtils.registrarCaptura(paso8);

        actor.attemptsTo(
                Scroll.scrollUnaVista());

        EvidenciaUtils.registrarCaptura(paso9);

        actor.attemptsTo(
                ClickTextoQueContengaX.elTextoContiene(ULTIMO),
                WaitForResponse.withText(ELIGE_TIPO_PAQUETE),

                // PASO 7: Validar y explorar septimo paquete 3.5GB
                ValidarTexto.validarTexto(LABEL_PAQUETES),
                ValidarTexto.validarTexto("3.5 GB"),
                ValidarTexto.validarTexto(PRECIO),
                ValidarTexto.validarTexto("$ 12.000"),
                ValidarTexto.validarTexto("Min ILIMITADOS"),
                ValidarTexto.validarTexto("SMS ILIMITADOS"),
                ValidarTexto.validarTexto("Apps incluidas"),
                ClickElementByText.clickElementByText(VER_DETALLE_DEL_PAQUETE),
                ValidarTexto.validarTexto(
                        "Este paquete incluye los servicios ilimitados de Minutos y SMS todo destino + 3.5GB + WhatsApp, Twitter y Facebook sin descontar de la capacidad")
        );

        EvidenciaUtils.registrarCaptura(paso10);

        // PASO 8: Validar y explorar octavo paquete 80GB
        scrollCorto2(actor, "$ 100.000");

        actor.attemptsTo(
                ValidarTexto.validarTexto(LABEL_PAQUETES),
                ValidarTexto.validarTexto("80 GB"),
                ValidarTexto.validarTexto(PRECIO),
                ValidarTexto.validarTexto("$ 100.000"),
                ValidarTexto.validarTexto("Min ILIMITADOS"),
                ValidarTexto.validarTexto("SMS ILIMITADOS"),
                ValidarTexto.validarTexto("Apps incluidas"),
                ClickElementByText.clickElementByText(VER_DETALLE_DEL_PAQUETE),
                ValidarTexto.validarTexto(
                        "Este paquete Todo Incluido incluye 80GB, ilimitados de Minutos + WhatsApp, Facebook y Twitter, Vigencia 30 dias.")
        );

        EvidenciaUtils.registrarCaptura(paso11);


        // PASO 9: Validar y explorar noveno paquete 7.5GB

        actor.attemptsTo(
                Scroll.scrollUnaVista(),
                ValidarTexto.validarTexto(LABEL_PAQUETES),
                ValidarTexto.validarTexto("7.5 GB"),
                ValidarTexto.validarTexto(PRECIO),
                ValidarTexto.validarTexto("$ 22.000"),
                ValidarTexto.validarTexto("Min ILIMITADOS"),
                ValidarTexto.validarTexto("SMS ILIMITADOS"),
                ValidarTexto.validarTexto("Apps incluidas"),
                Click.on(LBL_VER_DETALLE_2),
                ValidarTexto.validarTexto(
                        "Este paquete incluye los servicios ilimitados de Minutos y SMS todo destino + 7.5GB + WhatsApp, Twitter y Facebook sin descontar de la capacidad incluida, Vigencia 20 dias")
        );

        EvidenciaUtils.registrarCaptura(paso12);

        actor.attemptsTo(
                Scroll.scrollUnaVista()
        );

        EvidenciaUtils.registrarCaptura(paso13);

        actor.attemptsTo(
                ClickTextoQueContengaX.elTextoContiene(ULTIMO),
                WaitForResponse.withText(ELIGE_TIPO_PAQUETE),


                // PASO 10: Validar y explorar decimo paquete 12 GB
                ValidarTexto.validarTexto(LABEL_PAQUETES),
                ValidarTexto.validarTexto("12 GB"),
                ValidarTexto.validarTexto(PRECIO),
                ValidarTexto.validarTexto("$ 32.000"),
                ValidarTexto.validarTexto("Min ILIMITADOS"),
                ValidarTexto.validarTexto("SMS ILIMITADOS"),
                ValidarTexto.validarTexto("Apps incluidas"),
                ClickElementByText.clickElementByText(VER_DETALLE_DEL_PAQUETE),
                ValidarTexto.validarTexto(
                        "Este paquete incluye los servicios ilimitados de Minutos y SMS todo destino + 12GB + WhatsApp, Twitter y Facebook sin descontar de la capacidad")
        );

        EvidenciaUtils.registrarCaptura(paso14);


        // PASO 11: Validar y explorar undecimo paquete 12 GB
        actor.attemptsTo(
                Scroll.scrollUnaVista(),
                ValidarTexto.validarTexto(LABEL_PAQUETES),
                ValidarTexto.validarTexto("18 GB"),
                ValidarTexto.validarTexto(PRECIO),
                ValidarTexto.validarTexto("$ 42.000"),
                ValidarTexto.validarTexto("Min ILIMITADOS"),
                ValidarTexto.validarTexto("SMS ILIMITADOS"),
                ValidarTexto.validarTexto("Apps incluidas"),
                Click.on(LBL_VER_DETALLE_2),
                ValidarTexto.validarTexto(
                        "Este paquete incluye los servicios ilimitados de Minutos y SMS todo destino + 18GB + WhatsApp, Twitter y Facebook sin descontar de la capacidad incluida, Vigencia 30 días")
        );

        EvidenciaUtils.registrarCaptura(paso15);


    }

    public static Performable validar() {
        return instrumented(TodoIncluidoConRedes.class);
    }
}