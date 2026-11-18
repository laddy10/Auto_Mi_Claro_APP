package userinterfaces;

import net.serenitybdd.screenplay.targets.Target;
import org.openqa.selenium.By;

public class RoamingPage {
    public static final Target CHK_TERMINOS_ROAMING =
            Target.the("Check teminos y condiciones compra de paquete roaming")
                    .located(By.xpath("//*[@id=\"_r_41_-0.0.0.0.1.0.0.0.0.0.0.0.1.0.2\"]/span[2]"));

    public static final Target TXT_ACEPTO_TERMINOS = Target.the("texto Acepto términos y condiciones")
            .located(By.xpath("//android.widget.TextView[contains(@text,'Acepto') and contains(@text,'condiciones')]"));

    public static final Target TXT_ACEPTO_TERMINOS2 = Target.the("texto Acepto términos y condiciones")
            .located(By.xpath("//android.widget.TextView[@text=\"Acepto términos y condiciones\"]"));

    public static final Target BTN_ACTIVAR = Target.the("Botón activar")
            .located(By.xpath("//android.widget.Button[@text=\"Activar\"]"));

    public static final Target BTN_DATOS = Target.the("Botón datos")
            .located(By.xpath("(//android.view.View[@resource-id=\"__react-content\"]/android.view.View/android.widget.TextView[@text=\"\uE6B8\"])[2]"));

    public static final Target SW_ROAMING = Target.the("Switch activar y desactivar roaming")
            .located(By.xpath("(//android.widget.Switch/android.widget.TextView"));

    public static final Target BTN_NO_GRACIAS = Target.the("Botón no, gracias")
            .located(By.xpath("//android.widget.Button[@text=\"No, gracias\"]"));

    public static final Target BTN_CONTINUAR = Target.the("Botón Continua")
            .located(By.xpath("//android.widget.TextView[@text=\"Continuar\"]"));

    public static final Target BTN_DESACTIVAR = Target.the("Botón Desactivar")
            .located(By.xpath("//android.widget.Button[@text=\"Desactivar\"]"));
}
