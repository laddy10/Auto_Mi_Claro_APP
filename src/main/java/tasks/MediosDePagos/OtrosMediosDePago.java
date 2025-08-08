package tasks.MediosDePagos;

import interactions.Click.ClickTextoQueContengaX;
import interactions.comunes.Atras;
import interactions.validations.ValidarTexto;
import interactions.validations.ValidarTextoQueContengaX;
import interactions.wait.WaitFor;
import models.User;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.targets.Target;
import utils.EvidenciaUtils;
import utils.TestDataProvider;


import static net.serenitybdd.screenplay.Tasks.instrumented;
import static userinterfaces.MediosPagoPage.*;
import static utils.Constants.*;

public class OtrosMediosDePago implements Task {

    private final User user = TestDataProvider.getRealUser();

    private static final String paso1 = "Validar direccionamiento y menu desplegable medios de pago";
    private static final String PASO_TARJETA_CREDITO = "Por medio de Tarjeta Crédito/Débito";
    private static final String PASO_TARJETA_CODENSA = "Por medio de Tarjeta Codensa";
    private static final String PASO_BANCOLOMBIA = "Por medio de Bancolombia";

    private static final int TIMEOUT_SHORT = 800;
    private static final int TIMEOUT_MEDIUM = 1500;
    private static final int TIMEOUT_LONG = 2500;

    @Override
    public <T extends Actor> void performAs(T actor) {
        validarElementosIniciales(actor);
        ejecutarValidacionesMediosPago(actor);
    }

    private <T extends Actor> void validarElementosIniciales(T actor) {
        actor.attemptsTo(
                WaitFor.aTime(TIMEOUT_LONG),
                ValidarTexto.validarTexto(COMPRA_DE_PAQUETES),
                ValidarTextoQueContengaX.elTextoContiene(VALOR_A_PAGAR),
                ValidarTextoQueContengaX.elTextoContiene(NUMERO_FACTURA_RECARGAS),
                ValidarTextoQueContengaX.elTextoContiene(DESCRIPCION_COMPRA),
                ValidarTextoQueContengaX.elTextoContiene(ESCOGE_MEDIO_PAGO),
                WaitFor.aTime(TIMEOUT_MEDIUM)
        );
    }

    private <T extends Actor> void ejecutarValidacionesMediosPago(T actor) {
        // Abrir dropdown antes de seleccionar PSE
        actor.attemptsTo(
                Click.on(DROPDOWN_MEDIO_PAGO),
                WaitFor.aTime(TIMEOUT_SHORT)
        );

        EvidenciaUtils.registrarCaptura(paso1);


        // Tarjeta Crédito/Débito
        validarMedioPago(actor, "Con tarjeta de Crédito / Débito",
                TarjetaCreditoDebito.validarRedireccion(), PASO_TARJETA_CREDITO, false);

        // Tarjeta Codensa
        validarMedioPago(actor, "Con tarjeta Codensa",
                TarjetaCreditoDebito.validarRedireccion(), PASO_TARJETA_CODENSA, false);

        // Bancolombia (requiere doble navegación hacia atrás)
        validarMedioPago(actor, "Por medio de Bancolombia",
                Bancolombia.validarRedireccion(), PASO_BANCOLOMBIA, true);
    }

    private <T extends Actor> void validarMedioPago(T actor, Target botonMedio,
                                                    Performable tareaValidacion, String paso, boolean esBancolombia) {
        actor.attemptsTo(
                Click.on(botonMedio),
                WaitFor.aTime(TIMEOUT_SHORT));

        EvidenciaUtils.registrarCaptura(paso);

        actor.attemptsTo(
                ClickTextoQueContengaX.elTextoContiene(CONTINUAR),
                WaitFor.aTime(TIMEOUT_LONG),
                tareaValidacion
        );

        regresarAlMenu(actor, esBancolombia);
    }

    private <T extends Actor> void validarMedioPago(T actor, String nombreMedio,
                                                    Performable tareaValidacion, String paso, boolean esBancolombia) {
        actor.attemptsTo(
                ClickTextoQueContengaX.elTextoContiene(nombreMedio),
                WaitFor.aTime(TIMEOUT_SHORT));

        EvidenciaUtils.registrarCaptura(paso);

        actor.attemptsTo(
                ClickTextoQueContengaX.elTextoContiene(CONTINUAR),
                WaitFor.aTime(TIMEOUT_LONG),
                tareaValidacion
        );

        regresarAlMenu(actor, esBancolombia);
    }

    private <T extends Actor> void regresarAlMenu(T actor, boolean esBancolombia) {
        if (esBancolombia) {
            actor.attemptsTo(
                    Atras.irAtras(),
                    WaitFor.aTime(1000),
                    Atras.irAtras(),
                    WaitFor.aTime(1000),
                    Click.on(DROPDOWN_MEDIO_PAGO),
                    WaitFor.aTime(TIMEOUT_SHORT)
            );
        } else {
            actor.attemptsTo(
                    Atras.irAtras(),
                    WaitFor.aTime(1000),
                    Click.on(DROPDOWN_MEDIO_PAGO),
                    WaitFor.aTime(TIMEOUT_SHORT)
            );
        }
    }

    public static Performable validarOtrosMedios() {
        return instrumented(OtrosMediosDePago.class);
    }
}