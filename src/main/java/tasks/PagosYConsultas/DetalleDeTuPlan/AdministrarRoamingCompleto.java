package tasks.PagosYConsultas.DetalleDeTuPlan;

import interactions.Click.ClickElementByText;
import interactions.Click.ClickTextoQueContengaX;
import interactions.Scroll.Scroll;
import interactions.Scroll.ScrollHastaTexto;
import interactions.comunes.Atras;
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
import static utils.Constants.*;

public class AdministrarRoamingCompleto implements Task {

    private static final String paso1 = "Ingresar al boton Modificar";
    private static final String paso2 = "Validar opciones de vigencias";
    private static final String paso3 = "Ingresar al boton ver paquetes";
    private static final String paso4 = "Validar direccionamiento Ver paquetes - País destino";
    private static final String paso5 = "Ingresar país y continuar";
    private static final String paso6 = "Validar dirección a tipo de paquetes";
    private static final String paso7 = "Seleccionar paquetes Voz y datos";
    private static final String paso8 = "Información Paquetes disponibles";
    private static final String paso9 = "Seleccionar paquetes datos";
    private static final String paso10 = "Información Paquetes datos disponibles";

    @Override
    public <T extends Actor> void performAs(T actor) {

        EvidenciaUtils.registrarCaptura(paso1);

        // Validar opciones de modificar roaming
        actor.attemptsTo(
                ClickTextoQueContengaX.elTextoContiene(MODIFICAR),
                WaitForResponse.withText(ACTIVAR_POR_TIEMPO_INDEFINIDO)
        );

        EvidenciaUtils.registrarCaptura(paso2);

        actor.attemptsTo(
                ValidarTextoQueContengaX.elTextoContiene(ELIGE_VIGENCIA_NECESITES),
                ValidarTexto.validarTexto(ACTIVAR_POR_TIEMPO_INDEFINIDO),
                ValidarTexto.validarTexto(ACTIVAR_CON_FECHA_LIMITE),
                Atras.irAtras(),
                WaitFor.aTime(5000));


        EvidenciaUtils.registrarCaptura(paso3);

        // Volver y validar flujo Ver paquetes
        actor.attemptsTo(
                ClickTextoQueContengaX.elTextoContiene(VER_PAQUETES),
                WaitFor.aTime(5000)
        );

        EvidenciaUtils.registrarCaptura(paso4);

        actor.attemptsTo(
                ValidarTextoQueContengaX.elTextoContiene(ACTIVAR_PAQUETES),
                ValidarTextoQueContengaX.elTextoContiene(INGRESA_MAXIMO_5_PAISES),
                ValidarTextoQueContengaX.elTextoContiene(AGREGAR_PAIS),
                ValidarTextoQueContengaX.elTextoContiene(CONTINUAR)
        );

        // Ingresar país BELGICA
        actor.attemptsTo(
                Click.on(TXT_ESCRIBIR_PAIS),
                WaitFor.aTime(1000),
                DepurarTecladoTexto.explorar(),
                IngresarTextoConTeclado.elTexto("b"),
                WaitFor.aTime(1000),
                ClickTextoQueContengaX.elTextoContiene("BELGICA"),
                WaitFor.aTime(1000)
        );

        EvidenciaUtils.registrarCaptura(paso5);

        actor.attemptsTo(
                ClickTextoQueContengaX.elTextoContiene(CONTINUAR),
                WaitForResponse.withText(VOZ_Y_DATOS)
        );

        EvidenciaUtils.registrarCaptura(paso6);

        actor.attemptsTo(
                ValidarTexto.validarTexto(ACTIVAR_PAQUETES),
                ValidarTextoQueContengaX.elTextoContiene(ELIGE_PAQUETE_PREFIERAS),
                ValidarTextoQueContengaX.elTextoContiene(VOZ_Y_DATOS),
                ValidarTextoQueContengaX.elTextoContiene(DATOS)
        );


        EvidenciaUtils.registrarCaptura(paso7);

        // Seleccionar Datos
        actor.attemptsTo(
                ClickTextoQueContengaX.elTextoContiene(VOZ_Y_DATOS),
                WaitForResponse.withText(PAQUETE_ROAMING)
        );


        actor.attemptsTo(
                ValidarTextoQueContengaX.elTextoContiene(PAISES_DESTINO_BELGICA),
                ValidarTextoQueContengaX.elTextoContiene(TIPO_PAQUETE_DATOS),
                ValidarTextoQueContengaX.elTextoContiene(PAQUETE_ROAMING),
                ValidarTextoQueContengaX.elTextoContiene(VOZ_Y_DATOS)
        );

        EvidenciaUtils.registrarCaptura(paso8);

        actor.attemptsTo(
                Atras.irAtras(),
                WaitForResponse.withText(VER_PAQUETES),
                ClickTextoQueContengaX.elTextoContiene(VER_PAQUETES),
                WaitForResponse.withText(ACTIVAR_PAQUETES),
                Click.on(TXT_ESCRIBIR_PAIS),
                WaitFor.aTime(1000),
                DepurarTecladoTexto.explorar(),
                IngresarTextoConTeclado.elTexto("b"),
                WaitFor.aTime(1000),
                ClickTextoQueContengaX.elTextoContiene("BELGICA"),
                WaitFor.aTime(1000),
                ClickTextoQueContengaX.elTextoContiene(CONTINUAR),
                WaitForResponse.withText(DATOS)
        );

        EvidenciaUtils.registrarCaptura(paso9);

        actor.attemptsTo(
               ClickElementByText.clickElementByText(DATOS),
                WaitForResponse.withText(PAQUETE_ROAMING),
                ValidarTextoQueContengaX.elTextoContiene(PAISES_DESTINO_BELGICA),
                ValidarTextoQueContengaX.elTextoContiene(TIPO_PAQUETE_DATOS),
                ValidarTextoQueContengaX.elTextoContiene(PAQUETE_ROAMING),
                ValidarTextoQueContengaX.elTextoContiene(DATOS),
                Scroll.scrollUnaVista()
        );

        EvidenciaUtils.registrarCaptura(paso10);


    }

    public static Performable validarFlujoCompleto() {
        return instrumented(AdministrarRoamingCompleto.class);
    }
}