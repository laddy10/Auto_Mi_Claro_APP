package tasks.MediosDePagos;

import interactions.validations.ValidarTexto;
import interactions.validations.ValidarTextoQueContengaX;
import interactions.wait.WaitFor;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;

import net.serenitybdd.screenplay.actions.Click;
import utils.EvidenciaUtils;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static userinterfaces.MediosPagoPage.*;
import static utils.Constants.*;

public class Bancolombia implements Task {

    private static final String paso1 = "Validar redirección a portal Bancolombia";


    @Override
    public <T extends Actor> void performAs(T actor) {

        actor.attemptsTo(
                WaitFor.aTime(5000)
        );

        // VALIDAR REDIRECCIÓN A PORTAL BANCOLOMBIA
        actor.attemptsTo(
                ValidarTexto.validarTexto(AUTENTICACION_BANCOLOMBIA),
                ValidarTexto.validarTexto(SALIR),
                ValidarTexto.validarTexto(TE_DAMOS_LA_BIENVENIDA),
                ValidarTexto.validarTexto(USUARIO),
                ValidarTextoQueContengaX.elTextoContiene(OLVIDASTE_TU_USUARIO),
                Click.on(TXT_USUARIO_BANCOLOMBIA)
        );

        EvidenciaUtils.registrarCaptura(paso1);

    }

    public static Performable validarRedireccion() {
        return instrumented(Bancolombia.class);
    }
}