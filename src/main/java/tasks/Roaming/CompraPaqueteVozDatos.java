package tasks.Roaming;

import interactions.Click.ClickTextoQueContengaX;
import interactions.Click.TocarCentroElementoWebView;
import interactions.Click.TocarRadioIzquierdaDeTexto;
import interactions.Scroll.Scroll;
import interactions.input.DepurarTecladoTexto;
import interactions.input.IngresarTextoConTeclado;
import interactions.validations.ValidarTexto;
import interactions.validations.ValidarTextoQueContengaX;
import interactions.wait.WaitFor;
import interactions.wait.WaitForResponse;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import utils.EvidenciaUtils;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static userinterfaces.PagosYConsultasPage.TXT_ESCRIBIR_PAIS;
import static userinterfaces.RoamingPage.BTN_ACTIVAR;
import static userinterfaces.RoamingPage.TXT_ACEPTO_TERMINOS;
import static utils.Constants.*;
import static utils.Constants.PAQUETE_ACTIVADO;

public class CompraPaqueteVozDatos implements Task {

        private static final String paso1 = "Ingresar al boton ver paquetes";
        private static final String paso2 = "Validar opciones de vigencias";
        private static final String paso3 = "Ingresar al boton ver paquetes";
        private static final String paso4 = "Validar direccionamiento Ver paquetes - País destino";
        private static final String paso5 = "Ingresar país y continuar";
        private static final String paso6 = "Validar dirección a tipo de paquetes";
        private static final String paso7 = "Seleccionar paquetes Voz y datos";
        private static final String paso8 = "Comprar paquete pasaporte américa";
        private static final String paso9 = "Seleccionar paquetes datos";
        private static final String paso10 = "Información Paquetes datos disponibles";

        @Override

public <T extends Actor > void performAs(T actor) {

    /*EvidenciaUtils.registrarCaptura(paso1);

    // Validar opciones de modificar roaming
    actor.attemptsTo(
            ClickTextoQueContengaX.elTextoContiene(VER_PAQUETES));

    EvidenciaUtils.registrarCaptura(paso2);

    actor.attemptsTo(
            ValidarTextoQueContengaX.elTextoContiene(ELIGE_VIGENCIA_NECESITES),
            ValidarTexto.validarTexto(ACTIVAR_POR_TIEMPO_INDEFINIDO),
            ValidarTexto.validarTexto(ACTIVAR_CON_FECHA_LIMITE),
            Atras.irAtras(),
            WaitFor.aTime(5000));*/

            EvidenciaUtils.registrarCaptura(paso3);

            // Volver y validar flujo Ver paquetes
            actor.attemptsTo(ClickTextoQueContengaX.elTextoContiene(VER_PAQUETES), WaitFor.aTime(5000));

            EvidenciaUtils.registrarCaptura(paso4);

            actor.attemptsTo(
                    ValidarTextoQueContengaX.elTextoContiene(ACTIVAR_PAQUETES),
                    ValidarTextoQueContengaX.elTextoContiene(INGRESA_MAXIMO_5_PAISES),
                    ValidarTextoQueContengaX.elTextoContiene(AGREGAR_PAIS),
                    ValidarTextoQueContengaX.elTextoContiene(CONTINUAR));

            // Ingresar país ESTADOS UNIDOS
            actor.attemptsTo(
                    Click.on(TXT_ESCRIBIR_PAIS),
                    WaitFor.aTime(1000),
                    DepurarTecladoTexto.explorar(),
                    IngresarTextoConTeclado.elTexto("es"),
                    WaitFor.aTime(1000),
                    ClickTextoQueContengaX.elTextoContiene("ESTADOS UNIDOS"),
                    WaitFor.aTime(1000));

            EvidenciaUtils.registrarCaptura(paso5);

            actor.attemptsTo(
                    ClickTextoQueContengaX.elTextoContiene(CONTINUAR), WaitForResponse.withText(VOZ_Y_DATOS));

            EvidenciaUtils.registrarCaptura(paso6);

            actor.attemptsTo(
                    ValidarTexto.validarTexto(ACTIVAR_PAQUETES),
                    ValidarTextoQueContengaX.elTextoContiene(ELIGE_PAQUETE_PREFIERAS),
                    ValidarTextoQueContengaX.elTextoContiene(VOZ_Y_DATOS),
                    ValidarTextoQueContengaX.elTextoContiene(DATOS));

            EvidenciaUtils.registrarCaptura(paso7);

            // Seleccionar Voz y Datos
            actor.attemptsTo(
                    ClickTextoQueContengaX.elTextoContiene(VOZ_Y_DATOS),
                    WaitForResponse.withText(PAQUETE_ROAMING));

            actor.attemptsTo(
                    ValidarTextoQueContengaX.elTextoContiene(PAISES_DESTINO_USA),
                    ValidarTextoQueContengaX.elTextoContiene(TIPO_PAQUETE1),
                    ValidarTextoQueContengaX.elTextoContiene(PAQUETE_ROAMING),
                    ValidarTextoQueContengaX.elTextoContiene(VOZ_Y_DATOS));

            EvidenciaUtils.registrarCaptura(paso8);

            actor.attemptsTo(
                    Scroll.scrollUnaVista(),
                    Scroll.scrollUnaVista(),
                    Scroll.scrollMediaVista(),
                    ClickTextoQueContengaX.elTextoContiene(ELEGIR),
                    WaitFor.aTime(2000),
                    TocarRadioIzquierdaDeTexto.de(TXT_ACEPTO_TERMINOS),
                    ClickTextoQueContengaX.elTextoContiene(ACTIVAR_PAQUETE),
                    WaitFor.aTime(2000),
                    TocarCentroElementoWebView.de(BTN_ACTIVAR),
                    WaitForResponse.withAnyText(PAQUETE_ACTIVADO)
            );
        }

    public static Performable CompraPaqueteVozDatos() {
        return instrumented(CompraPaqueteVozDatos.class);
    }
}

