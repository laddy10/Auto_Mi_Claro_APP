package tasks.MediosDePagos;

import interactions.Click.ClickTextoQueContengaX;
import interactions.Scroll.ScrollHastaTexto;
import interactions.validations.ValidarTexto;
import interactions.validations.ValidarTextoQueContengaX;
import interactions.wait.WaitFor;
import models.User;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.questions.Presence;
import utils.EvidenciaUtils;
import utils.TestDataProvider;

import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static net.serenitybdd.screenplay.Tasks.instrumented;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isPresent;
import static net.serenitybdd.screenplay.questions.WebElementQuestion.the;
import static userinterfaces.MediosPagoPage.DROPDOWN_BANCO;
import static userinterfaces.MediosPagoPage.DROPDOWN_TIPO_CLIENTE;
import static userinterfaces.PagosYConsultasPage.*;
import static utils.Constants.*;

public class PSE implements Task {

    private static final User user = TestDataProvider.getRealUser();
    private static final String paso1 = "Validar y completar formulario PSE";
    private static final String paso2 = "Seleccionar banco";
    private static final String paso3 = "Se selecciona de pago";
    private static final String paso4 = "Seleccionar tipo de persona";
    private static final String paso5 = "Validar correo electronico";
    private static final String paso6 = "Dar clic en boton Pagar";
    private static final String paso7 = "Validar direccionamiento correcto medio de pago";

    @Override
    public <T extends Actor> void performAs(T actor) {

        // VALIDAR DIRECCIONAMIENTO Y FORMULARIO PSE
        EvidenciaUtils.registrarCaptura(paso1);

        actor.attemptsTo(
                ValidarTexto.validarTexto(REALIZA_EL_PAGO_EN_LINEA),
                ValidarTexto.validarTexto(COMPRA_DE_PAQUETES),
                ValidarTexto.validarTexto(NUMERO_FACTURA),
                ValidarTexto.validarTexto(DESCRIPCION_DE_LA_COMPRA),
                ValidarTexto.validarTexto(PAGO_TOTAL),
                ValidarTexto.validarTexto(BANCO),
                ValidarTexto.validarTexto(TIPO_DE_CLIENTE),
                ValidarTextoQueContengaX.elTextoContiene(CORREO_ELECTRONICO)
        );

        // SELECCIONAR BANCO
        actor.attemptsTo(
                Click.on(DROPDOWN_BANCO),
                WaitFor.aTime(2000)
        );

        EvidenciaUtils.registrarCaptura(paso2);

        // Hacer scroll para validar diferentes bancos y seleccionar uno
        actor.attemptsTo(
                ValidarTexto.validarTexto(SELECCIONA_TU_BANCO),
                ValidarTexto.validarTexto(ALIANZA_FIDUCIARIA),
                ValidarTexto.validarTexto(BAN100),
                ValidarTexto.validarTexto(BANCAMIA_SA),
                ValidarTexto.validarTexto(BANCO_AGRARIO),
                ValidarTexto.validarTexto(BANCO_AV_VILLAS),
                ValidarTexto.validarTexto(BANCO_BBVA_COLOMBIA),
                ValidarTexto.validarTexto(BANCO_DE_BOGOTA),
                ScrollHastaTexto.conTexto(BANCO_POPULAR),
                ValidarTexto.validarTexto(BANCO_POPULAR),
                ValidarTexto.validarTexto(BANCO_SANTANDER_COLOMBIA)
        );

        EvidenciaUtils.registrarCaptura(paso3);

        // Seleccionar Banco Popular
        actor.attemptsTo(
                ClickTextoQueContengaX.elTextoContiene(BANCO_POPULAR),
                WaitFor.aTime(1000)
        );

        // VALIDAR TIPO DE CLIENTE
        actor.attemptsTo(
                Click.on(DROPDOWN_TIPO_CLIENTE),
                WaitFor.aTime(1000));

        EvidenciaUtils.registrarCaptura(paso4);

        actor.attemptsTo(
                ClickTextoQueContengaX.elTextoContiene(PERSONA_JURIDICA),
                WaitFor.aTime(1000),
                Click.on(DROPDOWN_TIPO_CLIENTE),
                ClickTextoQueContengaX.elTextoContiene(PERSONA_NATURAL),
                WaitFor.aTime(1000)
        );

        EvidenciaUtils.registrarCaptura(paso5);

        // VALIDAR CORREO ELECTRÓNICO
        actor.attemptsTo(
                ValidarTextoQueContengaX.elTextoContiene(user.getEmail()),
                WaitFor.aTime(1000)
        );

        // HACER CLIC EN PAGAR
        actor.attemptsTo(
                ScrollHastaTexto.conTexto(PAGAR));

        EvidenciaUtils.registrarCaptura(paso6);

        actor.attemptsTo(
                ClickTextoQueContengaX.elTextoContiene(PAGAR),
                WaitFor.aTime(15000)
        );

        // VALIDAR REDIRECCIÓN AL PORTAL DE PAGOS

        // Validar presencia de iconos
        actor.should(
                seeThat(the(ICON_HOME), isPresent())
        );

        actor.should(
                seeThat(the(BTN_TRES_PUNTOS_MAS), isPresent())
        );

        EvidenciaUtils.registrarCaptura(paso7);

    }

    public static Performable validarRedireccion() {
        return instrumented(PSE.class);
    }
}