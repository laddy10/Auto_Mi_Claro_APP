package tasks.MediosDePagos;

import interactions.Click.ClickElementByText;
import interactions.comunes.Atras;
import interactions.validations.ValidarTexto;
import interactions.validations.ValidarTextoQueContengaX;
import interactions.wait.WaitFor;
import interactions.wait.WaitForResponse;
import net.serenitybdd.core.pages.WebElementFacade;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.questions.Presence;
import utils.AndroidObject;
import utils.EvidenciaUtils;
import utils.UtilidadesAndroid;

import java.util.List;

import static userinterfaces.MediosPagoPage.LBL_PAQUETE_INSTALADO;
import static userinterfaces.PagosYConsultasPage.TXT_CLARO;
import static utils.Constants.*;

public class ValidarNotificacion extends AndroidObject implements Task {

    private static final String paso1 = "Validar mensaje Operación exitosa";
    private static final String paso2 = "Validar mensaje de instalación";
    private static final String paso3 = "Validar direccionamiento de URL Claro";


    public static ValidarNotificacion deCompra() {
        return new ValidarNotificacion();
    }

    @Override
    public <T extends Actor> void performAs(T actor) {

        EvidenciaUtils.registrarCaptura(paso1);

        actor.attemptsTo(
                ClickElementByText.clickElementByText(CERRAR),
                WaitFor.aTime(15000));

        LeerMensaje(actor);

        if (!Presence.of(TXT_CLARO).viewedBy(actor).resolveAll().isEmpty()) {
            actor.attemptsTo(
                    WaitForResponse.withText(CLARO_2),
                    ClickElementByText.clickElementByText(CLARO_2));

            //   CapturaDePantallaMovil.tomarCapturaPantalla("captura_pantalla");

            List<WebElementFacade> lblpaqueteinstalado = LBL_PAQUETE_INSTALADO.resolveAllFor(actor);

            if (!lblpaqueteinstalado.isEmpty()) {
                actor.attemptsTo(
                        ValidarTextoQueContengaX.elTextoContiene(INSTALACION),
                        ValidarTextoQueContengaX.elTextoContiene("www.claro.com.co"));

                EvidenciaUtils.registrarCaptura(paso2);

                UtilidadesAndroid.abrirLinkEnNavegador("https://www.claro.com.co");

                actor.attemptsTo(
                        WaitForResponse.withText(PERSONAS),
                        ValidarTexto.validarTexto(PERSONAS)
                );

                EvidenciaUtils.registrarCaptura(paso3);

                actor.attemptsTo(
                        Atras.irAtras());

            } else {
                actor.attemptsTo(
                        ValidarTextoQueContengaX.elTextoContiene(COMPRASTE_PAQUETE));

                EvidenciaUtils.registrarCaptura(paso2);
            }

        } else {
            actor.attemptsTo(WaitFor.aTime(1000));
        }

        actor.attemptsTo(
                Atras.irAtras(),
                Atras.irAtras());

    }
}