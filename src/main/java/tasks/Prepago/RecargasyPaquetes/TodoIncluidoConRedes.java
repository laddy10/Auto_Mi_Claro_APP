package tasks.Prepago.RecargasyPaquetes;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static userinterfaces.PagosyConsultasPrePage.LBL_VER_DETALLE_2;
import static userinterfaces.PagosyConsultasPrePage.LBL_VER_DETALLE_3;
import static utils.AndroidObject.scrollCorto2;
import static utils.Constants.*;
import static utils.Constants.ULTIMO;
import static utils.ConstantsPaquetes.*;
import static utils.ConstantsPaquetes.PAQUETES_TODO_INCLUIDO_CON_REDES;

import interactions.Click.ClickElementByText;
import interactions.Click.ClickTextoQueContengaX;
import interactions.Scroll.Scroll;
import interactions.Scroll.ScrollHastaTexto;
import interactions.validations.ValidarTexto;
import interactions.wait.WaitFor;
import interactions.wait.WaitForResponse;
import models.User;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.ClickOnBy;
import org.openqa.selenium.remote.server.handler.ClickElement;
import utils.EvidenciaUtils;
import utils.TestDataProvider;

public class TodoIncluidoConRedes implements Task {

    private static final User user = TestDataProvider.getRealUser();
    private static final String paso1 = "Seleccionar Paquetes todo incluido con redes";
    private static final String paso2 = "Validar primer paquete 2 GB - Ver detalle";
    private static final String paso3 = "Validar segundo paquete 2 GB - Ver detalle";
    private static final String paso4 = "Validar tercer paquete 3.5 GB - Ver detalle";
    private static final String paso5 = "Validar tercer paquete 3.5 GB - Ver detalle";
    private static final String paso6 = "Validar cuarto paquete 7.5 GB - Ver detalle";
    private static final String paso7 = "Validar quinto paquete 7.5 GB - Ver detalle";
    private static final String paso8 = "Validar sexto paquete 18 GB - Ver detalle";
    private static final String paso9 = "Validar sexto paquete 12 GB - Ver detalle";
    private static final String paso10 = "Validar octavo paquete 18 GB - Ver detalle";
    private static final String paso11 = "Validar septimo paquete 12 GB - Ver detalle";
    private static final String paso12 = "Validar noveno paquete 400 MB - Ver detalle";
    private static final String paso13 = "Validar sexto paquete 1.4 GB - Ver detalle";
    private static final String paso14 = "Validar decimo paquete 1.4 GB - Ver detalle";
    private static final String paso15 = "Validar undecimo paquete 2 GB - Ver detalle";
    private static final String paso16 = "Validar undecimo paquete 80 GB - Ver detalle";

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

        // PASO 2: Validar y explorar segundo paquete 2 GB

        actor.attemptsTo(
                Scroll.scrollMediaVista(),
                ValidarTexto.validarTexto(LABEL_PAQUETES),
                ValidarTexto.validarTexto("2 GB"),
                ValidarTexto.validarTexto(PRECIO),
                ValidarTexto.validarTexto(PRECIO_$11000),
                ValidarTexto.validarTexto("Min ILIMITADOS"),
                ValidarTexto.validarTexto("SMS ILIMITADOS"),
                ValidarTexto.validarTexto("Apps incluidas"),
                ClickElementByText.clickElementByText(VER_DETALLE_DEL_PAQUETE),
                ValidarTexto.validarTexto(TODO_INCLUIDO_7DIAS_2GB));


        EvidenciaUtils.registrarCaptura(paso3);

        // PASO 3: Validar y explorar segundo paquete 3.5 GB

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

        EvidenciaUtils.registrarCaptura(paso4);


        actor.attemptsTo(
                ClickTextoQueContengaX.elTextoContiene(ULTIMO),
                WaitForResponse.withText(ELIGE_TIPO_PAQUETE),

                // PASO 4: Validar y explorar cuarto paquete 3.5 GB

                ValidarTexto.validarTexto(LABEL_PAQUETES),
                ValidarTexto.validarTexto("3.5 GB"),
                ValidarTexto.validarTexto(PRECIO),
                ValidarTexto.validarTexto(PRECIO_$15000),
                ValidarTexto.validarTexto("Min ILIMITADOS"),
                ValidarTexto.validarTexto("SMS ILIMITADOS"),
                ValidarTexto.validarTexto("Apps incluidas"),
                ClickElementByText.clickElementByText(VER_DETALLE_DEL_PAQUETE));
        scrollCorto2(actor, "Comprar");
        actor.attemptsTo(ValidarTexto.validarTexto(TODO_INCLUIDO_3_5GB));

        EvidenciaUtils.registrarCaptura(paso5);

        // PASO 5: Validar y explorar quinto paquete 7.5 GB

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

        EvidenciaUtils.registrarCaptura(paso6);

        // PASO 6: Validar y explorar sexto paquete 7.5 GB

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
                ValidarTexto.validarTexto(TODO_INCLUIDO_20DIAS_7_5GB2));

        EvidenciaUtils.registrarCaptura(paso7);

        actor.attemptsTo(
                Scroll.scrollUnaVista(),
                ClickTextoQueContengaX.elTextoContiene(ULTIMO),
                WaitForResponse.withText(ELIGE_TIPO_PAQUETE),


                // PASO 7: Validar y explorar séptimo paquete 18GB

                ValidarTexto.validarTexto(LABEL_PAQUETES),
                ValidarTexto.validarTexto("18 GB"),
                ValidarTexto.validarTexto(PRECIO),
                ValidarTexto.validarTexto(PRECIO_$45000),
                ValidarTexto.validarTexto("Min ILIMITADOS"),
                ValidarTexto.validarTexto("SMS ILIMITADOS"),
                ValidarTexto.validarTexto("Apps incluidas"),
                ClickElementByText.clickElementByText(VER_DETALLE_DEL_PAQUETE));
        scrollCorto2(actor, "Comprar");
        actor.attemptsTo(ValidarTexto.validarTexto(TODO_INCLUIDO_30DIAS_18GB));

        EvidenciaUtils.registrarCaptura(paso8);

        // PASO 8: Validar y explorar octavo paquete 12 GB

        actor.attemptsTo(
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

        EvidenciaUtils.registrarCaptura(paso9);

        actor.attemptsTo(

                // PASO 9: Validar y explorar noveno paquete 18 GB

                Scroll.scrollUnaVista(),
                ValidarTexto.validarTexto(LABEL_PAQUETES),
                ValidarTexto.validarTexto("18 GB"),
                ValidarTexto.validarTexto(PRECIO),
                ValidarTexto.validarTexto(PRECIO_$45000),
                ValidarTexto.validarTexto("Min ILIMITADOS"),
                ValidarTexto.validarTexto("SMS ILIMITADOS"),
                ValidarTexto.validarTexto("Apps incluidas"),
                ClickElementByText.clickElementByText(VER_DETALLE_DEL_PAQUETE),
                Scroll.scrollUnaVista(),
                ValidarTexto.validarTexto(TODO_INCLUIDO_30DIAS_18GB2));

        EvidenciaUtils.registrarCaptura(paso10);


        actor.attemptsTo(
                Scroll.scrollUnaVista(),
                ClickTextoQueContengaX.elTextoContiene(ULTIMO),
                WaitForResponse.withText(ELIGE_TIPO_PAQUETE),

                // PASO 10: Validar y explorar décimo paquete 12 GB

                ValidarTexto.validarTexto(LABEL_PAQUETES),
                ValidarTexto.validarTexto("12 GB"),
                ValidarTexto.validarTexto(PRECIO),
                ValidarTexto.validarTexto(PRECIO_$49000),
                ValidarTexto.validarTexto("Min ILIMITADOS"),
                ValidarTexto.validarTexto("SMS ILIMITADOS"),
                ValidarTexto.validarTexto("Apps incluidas"),
                ClickElementByText.clickElementByText(VER_DETALLE_DEL_PAQUETE),
                Scroll.scrollUnaVista(),
                ValidarTexto.validarTexto(TODO_INCLUIDO_30DIAS_12GB));

        EvidenciaUtils.registrarCaptura(paso11);


        // PASO 10: Validar y explorar onceavo paquete 400 MB
        actor.attemptsTo(
                Scroll.scrollUnaVista(),
                ValidarTexto.validarTexto(LABEL_PAQUETES),
                ValidarTexto.validarTexto("400MB"),
                ValidarTexto.validarTexto(PRECIO),
                ValidarTexto.validarTexto(PRECIO_$7000),
                ValidarTexto.validarTexto("Min ILIMITADOS"),
                ValidarTexto.validarTexto("SMS ILIMITADOS"),
                ValidarTexto.validarTexto("Apps incluidas"),
                Scroll.scrollUnaVista(),
                ClickElementByText.clickElementByText(VER_DETALLE_DEL_PAQUETE),
                ValidarTexto.validarTexto(TODO_INCLUIDO_3DIAS_400MB));

        EvidenciaUtils.registrarCaptura(paso12);

        // PASO 12: Validar y explorar doceavo paquete 1.4 GB


        actor.attemptsTo(
                Scroll.scrollUnaVista(),
                ValidarTexto.validarTexto(LABEL_PAQUETES),
                ValidarTexto.validarTexto("1.4 GB"),
                ValidarTexto.validarTexto(PRECIO),
                ValidarTexto.validarTexto(PRECIO_$10000),
                ValidarTexto.validarTexto("Min ILIMITADOS"),
                ValidarTexto.validarTexto("SMS ILIMITADOS"),
                ValidarTexto.validarTexto("Apps incluidas"),
                Scroll.scrollUnaVista(),
                Click.on(LBL_VER_DETALLE_2),
                ValidarTexto.validarTexto(TODO_INCLUIDO_6DIAS_1_4GB_4BENEFICIARIOS));

        EvidenciaUtils.registrarCaptura(paso13);

        actor.attemptsTo(
                Scroll.scrollUnaVista(),
                ClickTextoQueContengaX.elTextoContiene(ULTIMO),
                WaitForResponse.withText(ELIGE_TIPO_PAQUETE),

                // PASO 13: Validar y explorar treceavo paquete 1.4 GB

                ValidarTexto.validarTexto(LABEL_PAQUETES),
                ValidarTexto.validarTexto("1.4 GB"),
                ValidarTexto.validarTexto(PRECIO),
                ValidarTexto.validarTexto(PRECIO_$9000),
                ValidarTexto.validarTexto("SMS ILIMITADOS"),
                ValidarTexto.validarTexto("Apps incluidas"),
                Scroll.scrollUnaVista(),
                ClickElementByText.clickElementByText(VER_DETALLE_DEL_PAQUETE),
                ValidarTexto.validarTexto(TODO_INCLUIDO_6DIAS_1_4GB_4BENEFICIARIOS2));

        EvidenciaUtils.registrarCaptura(paso14);

        // PASO 14: Validar y explorar catorvceavo paquete 1.4 GB

        actor.attemptsTo(
                Scroll.scrollUnaVista(),
                ValidarTexto.validarTexto(LABEL_PAQUETES),
                ValidarTexto.validarTexto("2 GB"),
                ValidarTexto.validarTexto(PRECIO),
                ValidarTexto.validarTexto(PRECIO_$10000),
                ValidarTexto.validarTexto("Min ILIMITADOS"),
                ValidarTexto.validarTexto("SMS ILIMITADOS"),
                ValidarTexto.validarTexto("Apps incluidas"),
                ClickElementByText.clickElementByText(VER_DETALLE_DEL_PAQUETE),
                ValidarTexto.validarTexto(TODO_INCLUIDO_7DIAS_2GB2));

        EvidenciaUtils.registrarCaptura(paso15);


        // PASO 15: Validar y explorar quinceavo paquete 80 GB

        actor.attemptsTo(
                Scroll.scrollUnaVista(),
                ValidarTexto.validarTexto(LABEL_PAQUETES),
                ValidarTexto.validarTexto("80 GB"),
                ValidarTexto.validarTexto(PRECIO),
                ValidarTexto.validarTexto(PRECIO_$100000),
                ValidarTexto.validarTexto("Min ILIMITADOS"),
                ValidarTexto.validarTexto("SMS ILIMITADOS"),
                ValidarTexto.validarTexto("Apps incluidas"),
                ClickElementByText.clickElementByText(VER_DETALLE_DEL_PAQUETE),
                ValidarTexto.validarTexto(TODO_INCLUIDO_30DIAS_80GB));

        EvidenciaUtils.registrarCaptura(paso16);

    }

    public static Performable validar() {
        return instrumented(TodoIncluidoConRedes.class);
    }
}
