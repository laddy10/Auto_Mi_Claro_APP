package tasks.Entretenimiento;

import interactions.Click.ClickTextoQueContengaX;
import interactions.validations.ValidarTextoQueContengaX;
import interactions.wait.WaitFor;
import interactions.wait.WaitForResponse;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.targets.Target;
import net.serenitybdd.screenplay.waits.Wait;
import utils.EvidenciaUtils;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static userinterfaces.EntretenimientoPage.*;
import static utils.Constants.*;

public class ValidarRedireccionClaroClub implements Task {
    private static final String paso1 = "Validar texto 'Categorías' en pantalla inicial";
    private static final String paso2 = "Ingresar al menú categorías";
    private static final String paso3 = "Validar categoría Más Descargados";
    private static final String paso4 = "Validar categoría Comidas";
    private static final String paso5 = "Validar categoría Productos Claro";
    private static final String paso6 = "Validar categoría Viajes";
    private static final String paso7 = "Validar categoría Entretenimiento";
    private static final String paso8 = "Validar categoría Mascotas";
    private static final String paso9 = "Validar categoría Variedades";
    private static final String paso10 = "Validar categoría Educación";
    private static final String pasoFinal = "Fin validación categorías";

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                WaitFor.aTime(7000),
                Click.on(BTN_CLOSE),
                ValidarTextoQueContengaX.elTextoContiene("Categorías")
        );
        EvidenciaUtils.registrarCaptura(paso1);

        actor.attemptsTo(
                Click.on(MENU_CATEGORIAS)
        );
        EvidenciaUtils.registrarCaptura(paso2);

        validarCategoria(actor, MAS_DESCARGADOS, "Más Descargados", paso3);
        validarCategoria(actor, COMIDAS, "Comidas", paso4);
        validarCategoria(actor, PRODUCTOS_CLARO, "Productos Claro", paso5);
        validarCategoria(actor, VIAJES, "Viajes", paso6);
        validarCategoria(actor, ENTRETENIMIENTO_CLAROCLUB, "Entretenimiento", paso7);
        validarCategoria(actor, MASCOTAS, "Mascotas", paso8);
        validarCategoria(actor, VARIEDADES, "Variedades", paso9);
        validarCategoria(actor, EDUCACION, "Educación", paso10);

        EvidenciaUtils.registrarCaptura(pasoFinal);
    }

    private <T extends Actor> void validarCategoria(T actor, Target categoriaTarget, String textoValidacion, String pasoCaptura) {
        actor.attemptsTo(
                Click.on(categoriaTarget),
                ValidarTextoQueContengaX.elTextoContiene(textoValidacion)
        );
        EvidenciaUtils.registrarCaptura(pasoCaptura);
        WaitFor.aTime(2000);
        actor.attemptsTo(
                Click.on(MENU_CATEGORIAS) // Volver al menú para la siguiente categoría
        );
        EvidenciaUtils.registrarCaptura(paso2);
    }

    public static Performable validar() {
        return instrumented(ValidarRedireccionClaroClub.class);
    }
}
