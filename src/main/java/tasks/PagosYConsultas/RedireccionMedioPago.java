package tasks.PagosYConsultas;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static userinterfaces.PagosYConsultasPage.*;
import static utils.Constants.*;

import interactions.Click.ClickTextoQueContengaX;
import interactions.validations.ValidarTexto;
import interactions.validations.ValidarTextoQueContengaX;
import interactions.wait.WaitForResponse;
import models.User;
import net.serenitybdd.core.pages.WebElementFacade;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import utils.AndroidObject;
import utils.EvidenciaUtils;
import utils.TestDataProvider;

import java.util.List;

public class RedireccionMedioPago implements Task {
    private final User user = TestDataProvider.getRealUser();

    private static final String pasoTarjeta =
            "a. Tarjeta crédito/débito: Redirección a formulario de tarjeta";
    private static final String pasoPse = "b. PSE - NEQUI: Redirección a portal PSE - NEQUI";
    private static final String pasoBancolombia =
            "c. Bancolombia: Redirección a portal de Bancolombia";
    private static final String pasoCodensa = "d. Codensa: Redirección a formulario Codensa";
    private static final String pasoNequi =
            "e. Otros medios de pago: Redirección a portal Otros medios de pago";

    @Override
    public <T extends Actor> void performAs(T actor) {

        List<WebElementFacade> lblelegirotromediopago = LBL_ELEGIR_OTRO_MEDIO_PAGO.resolveAllFor(actor);
        if (!lblelegirotromediopago.isEmpty()) {

            // Tarjeta crédito/débito
            actor.attemptsTo(
                    ClickTextoQueContengaX.elTextoContiene(TARJETA_C_D),
                    WaitForResponse.withText(AGREGAR_TARJETA),
                    ValidarTexto.validarTexto(TARJETAS_NACIONALES),
                    ValidarTexto.validarTexto(NUMERO_TARJETA));
            EvidenciaUtils.registrarCaptura(pasoTarjeta);
            cerrarPopupYRegresar(actor);

            // PSE - NEQUI
            actor.attemptsTo(
                    Click.on(BTN_PSE),
                    WaitForResponse.withText(PORTAL_PAGOS_CLARO),
                    ValidarTexto.validarTexto(NUMERO_FACTURA),
                    ValidarTexto.validarTexto(DESCRIPCION_COMPRA),
                    ValidarTexto.validarTexto(PAGO_TOTAL));
            EvidenciaUtils.registrarCaptura(pasoPse);
            cerrarPopupYRegresar(actor);

            // Bancolombia
            actor.attemptsTo(
                    ClickTextoQueContengaX.elTextoContiene(BOTON_BANCOLOMBIA),
                    WaitForResponse.withText(SUCURSAL_VIRTUAL_PERSONAS),
                    ValidarTexto.validarTexto(BIENVENIDA),
                    ValidarTexto.validarTexto(USUARIO));
            EvidenciaUtils.registrarCaptura(pasoBancolombia);
            cerrarPopupYRegresar(actor);

            // Codensa
            actor.attemptsTo(
                    ClickTextoQueContengaX.elTextoContiene(CODENSA),
                    WaitForResponse.withText(PORTAL_PAGOS_CLARO),
                    ValidarTexto.validarTexto(TARJETAS_NACIONALES),
                    ValidarTexto.validarTexto(NUMERO_TARJETA));
            EvidenciaUtils.registrarCaptura(pasoCodensa);
            cerrarPopupYRegresar(actor);

            // Otros medios de pago
            String numeroLimpio = user.getNumero().replaceAll("\\s", "");
            actor.attemptsTo(
                    ClickTextoQueContengaX.elTextoContiene(OTROS_MEDIOS),
                    WaitForResponse.withText(PORTAL_PAGOS_CLARO),
                    ValidarTexto.validarTexto(PAGO_FACTURA_MOVIL_POST),
                    ValidarTexto.validarTexto(ESCOGE_MEDIO_PAGO),
                    ValidarTextoQueContengaX.elTextoContiene(numeroLimpio));
            EvidenciaUtils.registrarCaptura(pasoNequi);
        }
    }

        private <T extends Actor > void cerrarPopupYRegresar (T actor){
            actor.attemptsTo(
                    Click.on(ICON_HOME),
                    WaitForResponse.withText(MSM_EXPERIENCIA),
                    ClickTextoQueContengaX.elTextoContiene(OMITIR),
                    WaitForResponse.withText(POSTPAGO),
                    ClickTextoQueContengaX.elTextoContiene(POSTPAGO));

            AndroidObject.scrollCorto2(actor, LINEA + " " + user.getNumero() + " " + VER_DETALLE);

            actor.attemptsTo(
                    ClickTextoQueContengaX.elTextoContiene(user.getNumero()),
                    WaitForResponse.withText(PAGAR_FACTURA),
                    Click.on(BTN_PAGAR_FACTURA),
                    WaitForResponse.withText(ELEGIR_OTRO_MEDIO_PAGO));
        }

    public static Performable redireccionarMediosDePago() {
        return instrumented(RedireccionMedioPago.class);
    }
}
