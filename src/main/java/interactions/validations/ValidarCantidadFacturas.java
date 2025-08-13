package interactions.validations;

import static net.serenitybdd.screenplay.Tasks.instrumented;

import java.util.List;

import io.appium.java_client.MobileBy;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Interaction;
import org.openqa.selenium.WebElement;
import utils.AndroidObject;

public class ValidarCantidadFacturas implements Interaction {
    private final int cantidadEsperada;

    public ValidarCantidadFacturas(int cantidad) {
        this.cantidadEsperada = cantidad;
    }

    public static ValidarCantidadFacturas disponibles(int cantidad) {
        return instrumented(ValidarCantidadFacturas.class, cantidad);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        // Buscar elementos más específicos (que contengan mes y año)
        List<WebElement> facturas =
                AndroidObject.androidDriver(actor)
                        .findElements(
                                new MobileBy.ByAndroidUIAutomator(
                                        "new UiSelector().textMatches(\"Factura \\\\w+ - \\\\d{4}\")"));

        // Si no funciona el regex, usar un filtro manual
        if (facturas.isEmpty()) {
            List<WebElement> todasLasFacturas =
                    AndroidObject.androidDriver(actor)
                            .findElements(
                                    new MobileBy.ByAndroidUIAutomator("new UiSelector().textContains(\"Factura\")"));

            System.out.println("Total elementos con 'Factura': " + todasLasFacturas.size());

            // Filtrar solo las que tienen formato correcto
            for (WebElement elemento : todasLasFacturas) {
                String texto = elemento.getText();
                System.out.println("Elemento encontrado: '" + texto + "'");

                // Verificar que tenga formato "Factura [Mes] - [Año]"
                if (texto.matches("Factura \\w+ - \\d{4}")) {
                    facturas.add(elemento);
                    System.out.println("✅ Factura válida: " + texto);
                } else {
                    System.out.println("❌ Elemento descartado: " + texto);
                }
            }
        }

        int facturasEncontradas = facturas.size();
        System.out.println("Facturas válidas encontradas: " + facturasEncontradas);

        if (facturasEncontradas != cantidadEsperada) {
            throw new AssertionError(
                    "Se esperaban "
                            + cantidadEsperada
                            + " facturas, pero se encontraron "
                            + facturasEncontradas);
        }
    }
}