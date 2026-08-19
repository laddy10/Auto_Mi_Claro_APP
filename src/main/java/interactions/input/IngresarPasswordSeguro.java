package interactions.input;

import static net.serenitybdd.screenplay.Tasks.instrumented;

import net.serenitybdd.core.pages.WebElementFacade;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Interaction;
import net.serenitybdd.screenplay.targets.Target;
import net.thucydides.core.annotations.Step;
import utils.AndroidObject;

/**
 * Ingresa datos sensibles (contraseña) sin exponer el valor en el reporte de Serenity.
 *
 * <p>Reemplaza a {@code Enter.theValue(password).into(campo)}, cuya interacción instrumentada
 * genera en el reporte la línea "actor enters '<valor>' into ...", filtrando la clave en texto
 * plano. Aquí se resuelve el campo y se escribe directo sobre el elemento nativo, por lo que el
 * único paso que Serenity registra es el @Step enmascarado de esta clase.
 */
public class IngresarPasswordSeguro extends AndroidObject implements Interaction {

    private final Target campo;
    private final String valor;

    public IngresarPasswordSeguro(Target campo, String valor) {
        this.campo = campo;
        this.valor = valor;
    }

    @Override
    @Step("El actor ingresa la contraseña de forma segura (valor oculto).")
    public <T extends Actor> void performAs(T actor) {
        WebElementFacade elemento = campo.resolveFor(actor);
        elemento.waitUntilEnabled();
        elemento.clear();
        // Escribe sobre el WebElementFacade sin pasar por la interacción Enter,
        // por lo que Serenity NO registra el valor en el árbol de pasos del reporte.
        elemento.sendKeys(valor);
    }

    public static IngresarPasswordSeguro en(Target campo, String valor) {
        return instrumented(IngresarPasswordSeguro.class, campo, valor);
    }
}