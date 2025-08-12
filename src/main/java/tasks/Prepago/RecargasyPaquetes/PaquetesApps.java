package tasks.Prepago.RecargasyPaquetes;

import interactions.Click.ClickElementByText;
import interactions.Click.ClickTextoQueContengaX;
import interactions.Scroll.Scroll;
import interactions.validations.ValidarTexto;
import interactions.validations.ValidarTextoQueContengaX;
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

public class PaquetesApps implements Task {

    private static final User user = TestDataProvider.getRealUser();
    private static final String paso1 = "Seleccionar Paquetes apps";
    private static final String paso2 = "Página 1 - Validar primer paquete $3.000 - Ver detalle";
    private static final String paso3 = "Página 1 - Validar segundo paquete $4.500 - Ver detalle";
    private static final String paso4 = "Página 1 - Validar tercer paquete $6.000 - Ver detalle";
    private static final String paso5 = "Navegación a página 2";
    private static final String paso6 = "Página 2 - Validar cuarto paquete $1.500 WhatsApp - Ver detalle";
    private static final String paso7 = "Página 2 - Validar quinto paquete $3.000 Instagram - Ver detalle";
    private static final String paso8 = "Página 2 - Validar sexto paquete $3.500 YouTube - Ver detalle";
    private static final String paso9 = "Navegación a página 3";
    private static final String paso10 = "Página 3 - Validar séptimo paquete $3.500 Waze - Ver detalle";
    private static final String paso11 = "Página 3 - Validar octavo paquete $9.500 WhatsApp - Ver detalle";
    private static final String paso12 = "Página 3 - Validar noveno paquete $15.500 Instagram - Ver detalle";
    private static final String paso13 = "Navegación a página 4";
    private static final String paso14 = "Página 4 - Validar décimo paquete $18.500 WhatsApp - Ver detalle";

    @Override
    public <T extends Actor> void performAs(T actor) {

        // PASO 1: Seleccionar tipo de paquete "Paquetes apps"
        EvidenciaUtils.registrarCaptura(paso1);

        actor.attemptsTo(
                ClickTextoQueContengaX.elTextoContiene(PAQUETES_APPS),
                WaitForResponse.withText(ELIGE_TU_PAQUETE_IDEAL),
                ValidarTextoQueContengaX.elTextoContiene(user.getNumeroPrepago()),
                ValidarTexto.validarTexto(PAQUETES_APPS)
        );

        // PAQUETE 1: Validar primer paquete $3.000 (Salud en línea)

        actor.attemptsTo(
                ValidarTexto.validarTexto(LABEL_PAQUETES),
                ValidarTexto.validarTexto(PRECIO),
                ValidarTexto.validarTexto("$ 3.000"),
                ClickElementByText.clickElementByText(VER_DETALLE_DEL_PAQUETE),
                ValidarTexto.validarTexto(
                        "Salud en línea te conecta con grupo Mok tu médico virtual, disfruta de consultas médicas y más beneficios, agenda tu cita telefónica al 5800838 vigencia 30 días.")
        );

        EvidenciaUtils.registrarCaptura(paso2);


        // PAQUETE 2: Segundo paquete $4.500 (Salud en línea)
        actor.attemptsTo(
                Scroll.scrollUnaVista(),
                ValidarTexto.validarTexto(LABEL_PAQUETES),
                ValidarTexto.validarTexto(PRECIO),
                ValidarTexto.validarTexto("$ 4.500"),
                ClickElementByText.clickElementByText(VER_DETALLE_DEL_PAQUETE),
                ValidarTexto.validarTexto(
                        "Salud en línea te conecta con grupo Mok tu médico virtual, disfruta de consultas médicas para ti y un beneficiario, agenda tu cita telefónica al 5800838 vigencia 30 días.")
        );

        EvidenciaUtils.registrarCaptura(paso3);


        // PAQUETE 3: Tercer paquete $6.000 (Salud en línea)

        actor.attemptsTo(
                Scroll.scrollUnaVista(),
                ValidarTexto.validarTexto(LABEL_PAQUETES),
                ValidarTexto.validarTexto(PRECIO),
                ValidarTexto.validarTexto("$ 6.000"),
                Click.on(LBL_VER_DETALLE_2),
                ValidarTexto.validarTexto(
                        "Salud en línea te conecta con grupo Mok tu médico virtual, disfruta de consultas médicas para ti y 3 beneficiarios, agenda tu cita telefónica al 5800838 vigencia 30 días.")
        );

        EvidenciaUtils.registrarCaptura(paso4);


        // NAVEGACIÓN A PÁGINA 2
        actor.attemptsTo(
                Scroll.scrollUnaVista()
        );

        EvidenciaUtils.registrarCaptura(paso5);

        actor.attemptsTo(
                ClickTextoQueContengaX.elTextoContiene(ULTIMO),
                WaitForResponse.withText(ELIGE_TIPO_PAQUETE)
        );


        // PAQUETE 4: Cuarto paquete $1.500 (WhatsApp)

        actor.attemptsTo(
                ValidarTexto.validarTexto(LABEL_PAQUETES),
                ValidarTexto.validarTexto(PRECIO),
                ValidarTexto.validarTexto("$ 1.500"),
                ValidarTexto.validarTexto(APPS_INCLUIDAS),
                ClickElementByText.clickElementByText(VER_DETALLE_DEL_PAQUETE),
                ValidarTexto.validarTexto("Paquete de WAZE por 1 Día.")
        );

        EvidenciaUtils.registrarCaptura(paso6);


        // PAQUETE 5: Quinto paquete $3.000 (Instagram)


        actor.attemptsTo(
                Scroll.scrollUnaVista(),
                ValidarTexto.validarTexto(LABEL_PAQUETES),
                ValidarTexto.validarTexto(PRECIO),
                ValidarTexto.validarTexto("$ 3.000"),
                ValidarTexto.validarTexto(APPS_INCLUIDAS),
                ClickElementByText.clickElementByText(VER_DETALLE_DEL_PAQUETE),
                ValidarTexto.validarTexto("Paquete de Instagram 1 Día")
        );

        EvidenciaUtils.registrarCaptura(paso7);


        // PAQUETE 6: Sexto paquete $3.500 (YouTube)

        actor.attemptsTo(
                ValidarTexto.validarTexto(LABEL_PAQUETES),
                ValidarTexto.validarTexto(PRECIO),
                ValidarTexto.validarTexto("$ 3.500"),
                ValidarTexto.validarTexto(APPS_INCLUIDAS),
                Click.on(LBL_VER_DETALLE_2),
                ValidarTexto.validarTexto("Paquete de YouTube por 1 hora")
        );

        EvidenciaUtils.registrarCaptura(paso8);


        // NAVEGACIÓN A PÁGINA 3
        actor.attemptsTo(
                Scroll.scrollUnaVista()
        );

        EvidenciaUtils.registrarCaptura(paso9);

        actor.attemptsTo(
                ClickTextoQueContengaX.elTextoContiene(ULTIMO),
                WaitForResponse.withText(ELIGE_TIPO_PAQUETE)
        );


        // PAQUETE 7: Séptimo paquete $3.500 (Waze)

        actor.attemptsTo(
                ValidarTexto.validarTexto(LABEL_PAQUETES),
                ValidarTexto.validarTexto(PRECIO),
                ValidarTexto.validarTexto("$ 3.500"),
                ValidarTexto.validarTexto(APPS_INCLUIDAS),
                ClickElementByText.clickElementByText(VER_DETALLE_DEL_PAQUETE),
                ValidarTexto.validarTexto("Paquete de Waze 7 dias")
        );

        EvidenciaUtils.registrarCaptura(paso10);


        //PAQUETE 8: Octavo paquete $9.500 (WhatsApp)

        actor.attemptsTo(
                Scroll.scrollUnaVista(),
                ValidarTexto.validarTexto(LABEL_PAQUETES),
                ValidarTexto.validarTexto(PRECIO),
                ValidarTexto.validarTexto("$ 9.500"),
                ValidarTexto.validarTexto(APPS_INCLUIDAS),
                ClickElementByText.clickElementByText(VER_DETALLE_DEL_PAQUETE),
                ValidarTexto.validarTexto("Servicio Chat WhatsApp por 15 días.")
        );

        EvidenciaUtils.registrarCaptura(paso11);


        // PAQUETE 9: Noveno paquete $15.500 (Instagram)

        actor.attemptsTo(
                ValidarTexto.validarTexto(LABEL_PAQUETES),
                ValidarTexto.validarTexto(PRECIO),
                ValidarTexto.validarTexto("$ 15.500"),
                ValidarTexto.validarTexto(APPS_INCLUIDAS),
                Click.on(LBL_VER_DETALLE_2),
                ValidarTexto.validarTexto("Paquete de Instagram 7 dias.")
        );

        EvidenciaUtils.registrarCaptura(paso12);


        // PÁGINA 4 - PAQUETE 10: Décimo paquete $18.500 (WhatsApp)
        actor.attemptsTo(
                Scroll.scrollUnaVista()
        );

        EvidenciaUtils.registrarCaptura(paso13);

        actor.attemptsTo(
                ClickTextoQueContengaX.elTextoContiene(ULTIMO),
                WaitForResponse.withText(ELIGE_TIPO_PAQUETE)
        );


        actor.attemptsTo(
                ValidarTexto.validarTexto(LABEL_PAQUETES),
                ValidarTexto.validarTexto(PRECIO),
                ValidarTexto.validarTexto("$ 18.500"),
                ValidarTexto.validarTexto(APPS_INCLUIDAS),
                ClickElementByText.clickElementByText(VER_DETALLE_DEL_PAQUETE),
                ValidarTexto.validarTexto("Servicio Chat WhatsApp por 30 días.")
        );

        EvidenciaUtils.registrarCaptura(paso14);


        // Validación final del recorrido completo
        actor.attemptsTo(
                ValidarTexto.validarTexto(ELIGE_TU_PAQUETE_IDEAL),
                ValidarTextoQueContengaX.elTextoContiene(PAQUETES_APPS),
                ValidarTexto.validarTexto("3"),
                ValidarTexto.validarTexto("4")
        );
    }

    public static Performable validar() {
        return instrumented(PaquetesApps.class);
    }
}