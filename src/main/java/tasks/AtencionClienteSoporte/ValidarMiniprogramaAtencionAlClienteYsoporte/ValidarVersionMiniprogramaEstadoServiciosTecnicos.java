package tasks.AtencionClienteSoporte.ValidarMiniprogramaAtencionAlClienteYsoporte;

import interactions.Click.ClickTextoQueContengaX;
import interactions.validations.ValidarTextoQueContengaX;
import interactions.wait.WaitFor;
import interactions.wait.WaitForResponse;
import models.User;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.waits.WaitUntil;
import utils.EvidenciaUtils;
import utils.TestDataProvider;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isNotPresent;
import static userinterfaces.EntretenimientoPage.BTN_VOLVER;
import static userinterfaces.EntretenimientoPage.LBL_ESPERA_UN_MOMENTO;
import static userinterfaces.PagosYConsultasPage.BTN_TRES_PUNTOS_MAS;
import static utils.Constants.ESTADO_SERVICIOS_TECNICOS;
import static utils.ConstantsMiniVersiones.Versiones.MINI_VERSION_ESTADO_SERVICIOS_TECNICOS_CONSTANT;

public class ValidarVersionMiniprogramaEstadoServiciosTecnicos implements Task {

    private static final User user = TestDataProvider.getRealUser();
    private static final String paso1 = "Esperar desaparición del texto 'Espera un momento'";
    private static final String paso2 = "Ingresar al menú de tres puntos y seleccionar 'Acerca de'";
    private static final String paso3 = "Validar versión de mini programa Estado de servicios tècnicos";


    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                WaitUntil.the(LBL_ESPERA_UN_MOMENTO, isNotPresent()).forNoMoreThan(30).seconds(),
                WaitFor.aTime(2000)
        );
        EvidenciaUtils.registrarCaptura(paso1);
        //**********************************************************************************
        actor.attemptsTo(
                Click.on(BTN_TRES_PUNTOS_MAS)
        );
        EvidenciaUtils.registrarCaptura(paso2);
        actor.attemptsTo(
                ClickTextoQueContengaX.elTextoContiene("Acerca de")
        );
        //**********************************************************************************
        actor.attemptsTo(
                WaitForResponse.withText("Ver"),
                ValidarTextoQueContengaX.elTextoContiene(ESTADO_SERVICIOS_TECNICOS),
                ValidarTextoQueContengaX.elTextoContiene(MINI_VERSION_ESTADO_SERVICIOS_TECNICOS_CONSTANT)
        );
        EvidenciaUtils.registrarCaptura(paso3);
        //**********************************************************************************
        actor.attemptsTo(
                Click.on(BTN_VOLVER)
        );
    }

    public static Performable validar() {
        return instrumented(ValidarVersionMiniprogramaEstadoServiciosTecnicos.class);
    }
}

