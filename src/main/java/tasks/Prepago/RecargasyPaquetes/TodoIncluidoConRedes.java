package tasks.Prepago.RecargasyPaquetes;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static utils.AndroidObject.scrollCorto2;
import static utils.Constants.*;
import static utils.Constants.ULTIMO;
import static utils.ConstantsPaquetes.*;
import static utils.ConstantsPaquetes.PAQUETES_TODO_INCLUIDO_CON_REDES;

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
import utils.EvidenciaUtils;
import utils.TestDataProvider;

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
    private static final String paso10 = "Validar octavo paquete 80 GB - Ver detalle";
    private static final String paso11 = "Validar septimo paquete 3.5 GB - Ver detalle";
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
                WaitFor.aTime(2000));

        // PASO 1: Validar y explorar primer paquete 2GB

        actor.attemptsTo(
                ValidarTexto.validarTexto(LABEL_PAQUETES),
                ValidarTexto.validarTexto("2 GB"),
                ValidarTexto.validarTexto(PRECIO),
                ValidarTexto.validarTexto(PRECIO_$11000),
                ValidarTexto.validarTexto("Min ILIMITADOS"),
                ValidarTexto.validarTexto("SMS ILIMITADOS"),
                ValidarTexto.validarTexto("Apps incluidas"),
                ClickElementByText.clickElementByText(VER_DETALLE_DEL_PAQUETE));
        scrollCorto2(actor, "Comprar");
        actor.attemptsTo(ValidarTexto.validarTexto(TODO_INCLUIDO_7DIAS_400MB));

        EvidenciaUtils.registrarCaptura(paso2);

        // PASO 2: Validar y explorar segundo paquete 3.5 GB

        scrollCorto2(actor, PRECIO_$15000);

        actor.attemptsTo(
                ValidarTexto.validarTexto(LABEL_PAQUETES),
                ValidarTexto.validarTexto("3.5 GB"),
                ValidarTexto.validarTexto(PRECIO),
                ValidarTexto.validarTexto(PRECIO_$15000),
                ValidarTexto.validarTexto("Min ILIMITADOS"),
                ValidarTexto.validarTexto("SMS ILIMITADOS"),
                ValidarTexto.validarTexto("Apps incluidas"),
                ClickElementByText.clickElementByText(VER_DETALLE_DEL_PAQUETE));
        scrollCorto2(actor, "Comprar");
        actor.attemptsTo(ValidarTexto.validarTexto(TODO_INCLUIDO_10DIAS_1_3_5GB));

        EvidenciaUtils.registrarCaptura(paso3);

        // PASO 3: Validar y explorar tercer paquete 7.5 GB

        actor.attemptsTo(
                Scroll.scrollUnaVista(),
                ValidarTexto.validarTexto(LABEL_PAQUETES),
                ValidarTexto.validarTexto("7.5 GB"),
                ValidarTexto.validarTexto(PRECIO),
                ValidarTexto.validarTexto(PRECIO_$25000),
                ValidarTexto.validarTexto("Min ILIMITADOS"),
                ValidarTexto.validarTexto("SMS ILIMITADOS"),
                ValidarTexto.validarTexto("Apps incluidas"),
                Scroll.scrollUnaVista(),
                ClickElementByText.clickElementByText(VER_DETALLE_DEL_PAQUETE),
                ValidarTexto.validarTexto(TODO_INCLUIDO_20DIAS_7_5GB));

        EvidenciaUtils.registrarCaptura(paso4);

        EvidenciaUtils.registrarCaptura(paso5);

        actor.attemptsTo(
                ClickTextoQueContengaX.elTextoContiene(ULTIMO),
                WaitForResponse.withText(ELIGE_TIPO_PAQUETE),

                // PASO 4: Validar y explorar cuarto paquete 12GB

                ValidarTexto.validarTexto(LABEL_PAQUETES),
                ValidarTexto.validarTexto("12 GB"),
                ValidarTexto.validarTexto(PRECIO),
                ValidarTexto.validarTexto(PRECIO_$35000),
                ValidarTexto.validarTexto("Min ILIMITADOS"),
                ValidarTexto.validarTexto("SMS ILIMITADOS"),
                ValidarTexto.validarTexto("Apps incluidas"),
                Scroll.scrollUnaVista(),
                ClickElementByText.clickElementByText(VER_DETALLE_DEL_PAQUETE),
                ValidarTexto.validarTexto(TODO_INCLUIDO_12GB));


        EvidenciaUtils.registrarCaptura(paso6);

        // PASO 5: Validar y explorar quinto paquete 18GB

        scrollCorto2(actor, PRECIO_$45000);

        actor.attemptsTo(
                Scroll.scrollUnaVista(),
                ValidarTexto.validarTexto(LABEL_PAQUETES),
                ValidarTexto.validarTexto("18 GB"),
                ValidarTexto.validarTexto(PRECIO),
                ValidarTexto.validarTexto(PRECIO_$45000),
                ValidarTexto.validarTexto("Min ILIMITADOS"),
                ValidarTexto.validarTexto("SMS ILIMITADOS"),
                ValidarTexto.validarTexto("Apps incluidas"),
                ClickElementByText.clickElementByText(VER_DETALLE_DEL_PAQUETE),
                ValidarTexto.validarTexto(TODO_INCLUIDO_30DIAS_18GB));


        EvidenciaUtils.registrarCaptura(paso7);

        // PASO :6 Validar y explorar sexto paquete 400GB

        actor.attemptsTo(
                Scroll.scrollUnaVista(),
                ValidarTexto.validarTexto(LABEL_PAQUETES),
                ValidarTexto.validarTexto("400MB"),
                ValidarTexto.validarTexto(PRECIO),
                ValidarTexto.validarTexto(PRECIO_$6000),
                ValidarTexto.validarTexto("Min ILIMITADOS"),
                ValidarTexto.validarTexto("SMS ILIMITADOS"),
                ValidarTexto.validarTexto("Apps incluidas"),
                Scroll.scrollUnaVista(),
                ClickElementByText.clickElementByText(VER_DETALLE_DEL_PAQUETE),
                ValidarTexto.validarTexto(TODO_INCLUIDO_3DIAS_400MB));


        EvidenciaUtils.registrarCaptura(paso8);

        actor.attemptsTo(Scroll.scrollUnaVista());

        EvidenciaUtils.registrarCaptura(paso9);


        actor.attemptsTo(
                ClickTextoQueContengaX.elTextoContiene(ULTIMO),
                WaitForResponse.withText(ELIGE_TIPO_PAQUETE),

                // PASO 8: Validar y explorar octavo paquete 1.4 GB


                ValidarTexto.validarTexto(LABEL_PAQUETES),
                ValidarTexto.validarTexto("1.4 GB"),
                ValidarTexto.validarTexto(PRECIO),
                ValidarTexto.validarTexto(PRECIO_$9000),
                ValidarTexto.validarTexto("Min ILIMITADOS"),
                ValidarTexto.validarTexto("SMS ILIMITADOS"),
                ValidarTexto.validarTexto("Apps incluidas"),
                ClickElementByText.clickElementByText(VER_DETALLE_DEL_PAQUETE),
                ValidarTexto.validarTexto(TODO_INCLUIDO_6DIAS_1_4GB_4BENEFICIARIOS));


        EvidenciaUtils.registrarCaptura(paso10);
        actor.attemptsTo(

                // PASO 7: Validar y explorar septimo paquete 400MB

                Scroll.scrollUnaVista(),
                ValidarTexto.validarTexto(LABEL_PAQUETES),
                ValidarTexto.validarTexto("400 MB"),
                ValidarTexto.validarTexto(PRECIO),
                ValidarTexto.validarTexto(PRECIO_$5000),
                ValidarTexto.validarTexto("Min ILIMITADOS"),
                ValidarTexto.validarTexto("SMS ILIMITADOS"),
                ValidarTexto.validarTexto("Apps incluidas"),
                Scroll.scrollUnaVista(),
                ClickElementByText.clickElementByText(VER_DETALLE_DEL_PAQUETE),
                ValidarTexto.validarTexto(TODO_INCLUIDO_3DIAS_400MB1));

        EvidenciaUtils.registrarCaptura(paso11);

        // PASO 9: Validar y explorar noveno paquete 1.4 GB


        actor.attemptsTo(
                Scroll.scrollUnaVista(),
                ValidarTexto.validarTexto(LABEL_PAQUETES),
                ValidarTexto.validarTexto("1.4 GB"),
                ValidarTexto.validarTexto(PRECIO),
                ValidarTexto.validarTexto(PRECIO_$8000),
                ValidarTexto.validarTexto("Min ILIMITADOS"),
                ValidarTexto.validarTexto("SMS ILIMITADOS"),
                ValidarTexto.validarTexto("Apps incluidas"),
                ClickElementByText.clickElementByText(VER_DETALLE_DEL_PAQUETE),
                ValidarTexto.validarTexto(TODO_INCLUIDO_6DIAS_1_4GB_4BENEFICIARIOS2));


        EvidenciaUtils.registrarCaptura(paso12);

        actor.attemptsTo(Scroll.scrollUnaVista());

        EvidenciaUtils.registrarCaptura(paso13);

        actor.attemptsTo(
                ClickTextoQueContengaX.elTextoContiene(ULTIMO),
                WaitForResponse.withText(ELIGE_TIPO_PAQUETE),

                // PASO 10: Validar y explorar decimo paquete 2 GB

                ValidarTexto.validarTexto(LABEL_PAQUETES),
                ValidarTexto.validarTexto("2 GB"),
                ValidarTexto.validarTexto(PRECIO),
                ValidarTexto.validarTexto(PRECIO_$9000),
                ValidarTexto.validarTexto("Min ILIMITADOS"),
                ValidarTexto.validarTexto("SMS ILIMITADOS"),
                ValidarTexto.validarTexto("Apps incluidas"),
                ClickElementByText.clickElementByText(VER_DETALLE_DEL_PAQUETE),
                ValidarTexto.validarTexto(TODO_INCLUIDO_7DIAS_2GB));

        EvidenciaUtils.registrarCaptura(paso14);

        // PASO 11: Validar y explorar undecimo paquete 3.5 GB

        actor.attemptsTo(
                Scroll.scrollUnaVista(),
                ValidarTexto.validarTexto(LABEL_PAQUETES),
                ValidarTexto.validarTexto("3.5 GB"),
                ValidarTexto.validarTexto(PRECIO),
                ValidarTexto.validarTexto(PRECIO_$13000),
                ValidarTexto.validarTexto("Min ILIMITADOS"),
                ValidarTexto.validarTexto("SMS ILIMITADOS"),
                ValidarTexto.validarTexto("Apps incluidas"),
                ClickElementByText.clickElementByText(VER_DETALLE_DEL_PAQUETE),
                ValidarTexto.validarTexto(TODO_INCLUIDO_3_5GB));

        EvidenciaUtils.registrarCaptura(paso15);

        actor.attemptsTo(Scroll.scrollUnaVista());

        // PASO 12: Validar y explorar undecimo paquete 7.5 GB

        actor.attemptsTo(
                Scroll.scrollUnaVista(),
                ValidarTexto.validarTexto(LABEL_PAQUETES),
                ValidarTexto.validarTexto("7.5 GB"),
                ValidarTexto.validarTexto(PRECIO),
                ValidarTexto.validarTexto(PRECIO_$23000),
                ValidarTexto.validarTexto("Min ILIMITADOS"),
                ValidarTexto.validarTexto("SMS ILIMITADOS"),
                ValidarTexto.validarTexto("Apps incluidas"),
                Scroll.scrollUnaVista(),
                ClickElementByText.clickElementByText(VER_DETALLE_DEL_PAQUETE),
                ValidarTexto.validarTexto(TODO_INCLUIDO_20DIAS_7_5GB2));
    }

    public static Performable validar() {
        return instrumented(TodoIncluidoConRedes.class);
    }
}
