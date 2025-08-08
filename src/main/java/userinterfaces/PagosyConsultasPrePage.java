package userinterfaces;

import net.serenitybdd.screenplay.targets.Target;
import org.openqa.selenium.By;

public class PagosyConsultasPrePage {

    public static final Target DROPDOWN_DURACION = Target.the("Dropdown duración")
            .located(By.xpath("//android.widget.Button[@text=\"0 días\"]"));

    public static final Target DROPDOWN_DATOS = Target.the("Dropdown datos")
            .located(By.xpath("//android.widget.Button[@text=\"0 GB\"]"));

    public static final Target LBL_VER_DETALLE_1 =
            Target.the("Texto Ver detalle del paquete 1")
                    .located(
                            By.xpath("(//android.widget.Button[@text=\"Ver detalle del paquete\"])[1]"));

    public static final Target LBL_VER_DETALLE_2 =
            Target.the("Texto Ver detalle del paquete 2")
                    .located(
                            By.xpath("(//android.widget.Button[@text=\"Ver detalle del paquete\"])[2]"));
    public static final Target LBL_VER_DETALLE_3 =
            Target.the("Texto Ver detalle del paquete 3")
                    .located(
                            By.xpath("(//android.widget.Button[@text=\"Ver detalle del paquete\"])[3]"));

    public static final Target LBL_MENSAJE_COOKIES =
            Target.the("Texto Información importante")
                    .located(
                            By.xpath("//android.widget.TextView[@text=\"Información importante\"]"));

}
