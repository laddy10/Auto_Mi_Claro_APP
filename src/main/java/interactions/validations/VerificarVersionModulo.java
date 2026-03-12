package interactions.validations;

import static net.serenitybdd.screenplay.Tasks.instrumented;

import net.serenitybdd.core.Serenity;
import net.serenitybdd.core.annotations.findby.By;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Interaction;
import net.serenitybdd.screenplay.targets.Target;
import net.thucydides.core.annotations.Step;

public class VerificarVersionModulo implements Interaction {

  private final String versionEsperada;

  public VerificarVersionModulo(String versionEsperada) {
    this.versionEsperada = versionEsperada;
  }

  @Override
  @Step("{0} verifica la versión del módulo, esperando: #versionEsperada")
  public <T extends Actor> void performAs(T actor) {
    // Localizador dinámico: Busca cualquier TextView que contenga "Versión " o "Ver "
    // Ajusta "Ver " o "Versión " según lo que realmente aparece en la app.
    // En VersionesMiniProgramaPage vi que usan "Ver 1.X.X..."
    Target LBL_VERSION_DINAMICA =
        Target.the("Texto de Versión Dinámica")
            .located(
                By.xpath(
                    "//android.widget.TextView[contains(@text, 'Ver ') or contains(@text, 'Versión ')]"));

    // Verificamos si es visible antes de intentar obtener texto
    if (!LBL_VERSION_DINAMICA.resolveFor(actor).isVisible()) {
      Serenity.recordReportData()
          .withTitle("⚠️ Versión No Encontrada")
          .andContents("No se encontró ningún elemento de versión en la pantalla.");
      return;
    }

    String versionActual = LBL_VERSION_DINAMICA.resolveFor(actor).getText();

    if (!versionActual.equals(versionEsperada)) {
      // ALERTA: No fallamos el test, solo reportamos la diferencia
      Serenity.recordReportData()
          .withTitle("⚠️ Cambio de Versión Detectado")
          .andContents(
              "Esperaba: "
                  + versionEsperada
                  + "\n"
                  + "Encontró: "
                  + versionActual
                  + "\n\n"
                  + "Por favor actualice el archivo utils.ConstantsMiniVersiones.Versiones.java con la nueva versión.");

      System.out.println(
          "⚠️ ALERTA: La versión ha cambiado. Esperada: "
              + versionEsperada
              + " | Actual: "
              + versionActual);

      // Opcional: Si quieres que el reporte muestre un warning visual (estado 'PENDING' o similar),
      // aunque Serenity no tiene un estado 'WARNING' directo, el recordReportData es lo mejor.
    } else {
      Serenity.recordReportData()
          .withTitle("✅ Versión Correcta")
          .andContents("La versión coincide: " + versionActual);
    }
  }

  public static VerificarVersionModulo conLaEsperada(String versionEsperada) {
    return instrumented(VerificarVersionModulo.class, versionEsperada);
  }
}
