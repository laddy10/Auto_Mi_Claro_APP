package userinterfaces;

import io.appium.java_client.AppiumBy;
import net.serenitybdd.screenplay.targets.Target;

public class PagosYConsultasPage {

    public static final Target BTN_TRES_PUNTOS_MAS =
            Target.the("Boton de los 3 punticos para validar la mini versión")
                    .located(AppiumBy.xpath("//android.widget.TextView[@content-desc=\"Más\"]"));
    public static final Target BTN_PAGAR_FACTURA =
            Target.the("Boton Pagar factura")
                    .located(AppiumBy.xpath("//*[@text='Pagar factura' and @class='android.widget.Button']"));
    public static final Target BTN_PSE=
            Target.the("Boton PSE")
                    .located(AppiumBy.xpath("//android.view.View[@resource-id=\"__react-content\"]/android.view.View/android.widget.TextView[6]"));
    public static final Target CBX_TIPO_PAQUETE =
            Target.the("Combo seleccion tipo de paquetes")
                    .located(AppiumBy.xpath(
                                    "//*[@text='Paquetes de datos']"));
    public static final Target ICON_HOME =
            Target.the("Icono Home")
                    .located(AppiumBy.id("h5_tv_nav_back_to_home"));

    public static final Target ICON_DEBITO_PSE =
            Target.the("Icono Debito bancario PSE")
                    .located(AppiumBy.xpath("(((//*[@text='Portal Pagos Claro' and @class='android.webkit.WebView']/*[@class='android.view.View'])[2]/*/*[@class='android.view.View' and ./parent::*[@class='android.view.View']])[2]/*[@class='android.view.View'])[1]"));


    // Elementos para pago parcial
    public static final Target TXT_MONTO_PAGO_PARCIAL =
            Target.the("Campo monto pago parcial")
                    .located(AppiumBy.xpath("//android.widget.Button[@text=\"$0\"]"));

    public static final Target ICON_DESCARGAR_FACTURA =
            Target.the("Boton descargar factura")
                    .located(AppiumBy.xpath("//android.view.View[@resource-id=\"__react-content\"]/android.view.View/android.widget.Button[1]"));

    public static final Target TXT_CONTRASENA_FACTURA =
            Target.the("Campo contraseña factura")
                    .located(AppiumBy.id("com.android.chrome:id/password"));

    public static final Target URL_FACTURA_DESCARGADA =
            Target.the("URL factura descargada")
                    .located(AppiumBy.id("com.android.chrome:id/url_bar"));

    public static final Target LBL_CARGO_FACTURA_CLARO =
            Target.the("Texto Con cargo a la factura Claro")
                    .located(AppiumBy.xpath("//*[@text='Con cargo a la factura Claro']"));

}
