package tasks.Prepago.RecargasyPaquetes;

import interactions.Click.ClickTextoQueContengaX;
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
import static userinterfaces.PagosyConsultasPrePage.DROPDOWN_DATOS;
import static userinterfaces.PagosyConsultasPrePage.DROPDOWN_DURACION;
import static utils.Constants.*;

public class ArmarPaquete implements Task {

    private static final User user = TestDataProvider.getRealUser();
    private static final String paso1 = "Clic Arma tu paquete";
    private static final String paso2 = "Valida direccionamiento para armar el paquete";
    private static final String paso3 = "Seleccionar duración del paquete " + user.getDuracionPaqueteArmar();
    private static final String paso4 = "Seleccionar cantidad datos del paquete " + user.getCantidadDatosArmar();
    private static final String paso5 = "Validar paquete seleccionado";

    @Override
    public <T extends Actor> void performAs(T actor) {

        EvidenciaUtils.registrarCaptura(paso1);

        // ARMAR PAQUETE
        actor.attemptsTo(
                ClickTextoQueContengaX.elTextoContiene(ARMA_TU_PAQUETE),
                WaitForResponse.withText(SELECCIONA_LA_DURACION));


        EvidenciaUtils.registrarCaptura(paso2);

        actor.attemptsTo(
                ValidarTexto.validarTexto(ARMA_TU_PAQUETE),
                ValidarTextoQueContengaX.elTextoContiene(user.getNumeroPrepago()),
                ValidarTexto.validarTexto(SELECCIONA_LA_DURACION),
                ValidarTexto.validarTexto(SELECCIONA_LA_CANTIDAD_DATOS)
        );


        // Seleccionar duración
        actor.attemptsTo(
                Click.on(DROPDOWN_DURACION),
                WaitForResponse.withText(ELIGE_DURACION_PAQUETE)
        );

        EvidenciaUtils.registrarCaptura(paso3);


        actor.attemptsTo(
                ValidarTextoQueContengaX.elTextoContiene(ELIGE_DURACION_PAQUETE),
                ClickTextoQueContengaX.elTextoContiene(user.getDuracionPaqueteArmar()),
                WaitFor.aTime(1000)
        );


        // Hacer clic en "Selecciona la cantidad de datos"
        actor.attemptsTo(
                Click.on(DROPDOWN_DATOS),
                WaitForResponse.withText(ELIGE_GIGAS_NECESITAS)
        );

        EvidenciaUtils.registrarCaptura(paso4);

        actor.attemptsTo(
                ValidarTextoQueContengaX.elTextoContiene(ELIGE_GIGAS_NECESITAS),
                ValidarTexto.validarTexto(user.getCantidadDatosArmar()),
                ClickTextoQueContengaX.elTextoContiene(user.getCantidadDatosArmar()),
                WaitFor.aTime(1000)
        );

        // Validar paquete configurado
        actor.attemptsTo(
                ValidarTexto.validarTexto(user.getDuracionPaqueteArmar()),
                ValidarTexto.validarTexto(user.getCantidadDatosArmar()),
                ValidarTexto.validarTexto(APLICACIONES_INCLUIDAS),
                ValidarTexto.validarTexto(MIN_ILIMITADOS),
                ValidarTexto.validarTexto(VER_RESUMEN)
        );
    }

    public static Performable armar() {
        return instrumented(ArmarPaquete.class);
    }
}