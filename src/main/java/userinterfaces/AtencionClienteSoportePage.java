// src/main/java/userinterfaces/AtencionClienteSoportePage.java
package userinterfaces;

import io.appium.java_client.AppiumBy;
import net.serenitybdd.screenplay.targets.Target;

public class AtencionClienteSoportePage {

    public static final Target LBL_CONECTATE_PLAN_DATOS =
            Target.the("Mensaje Conéctate a tu plan de datos")
                    .located(AppiumBy.xpath("//android.widget.TextView[@text='Conéctate a tu plan de datos']"));

    public static final Target ICON_WHATSAPP =
            Target.the("Ícono WhatsApp")
                    .located(AppiumBy.xpath("//android.widget.ImageView[@content-desc=\"WhatsApp\"]"));

    public static final Target BTN_SI_PERMITIR =
            Target.the("Botón Sí, permitir")
                    .located(AppiumBy.xpath("//android.widget.TextView[@text='Sí, permitir']"));

    public static final Target BTN_EN_OTRO_MOMENTO =
            Target.the("Botón En otro momento")
                    .located(AppiumBy.xpath("//android.widget.TextView[@text='En otro momento']"));

    public static final Target MAPA_UBICACION =
            Target.the("Mapa de ubicación")
                    .located(AppiumBy.xpath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.RelativeLayout/android.widget.RelativeLayout[2]/android.widget.RelativeLayout/android.widget.RelativeLayout/android.view.ViewGroup/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.webkit.WebView/android.webkit.WebView/android.view.View/android.view.View[2]"));

    public static final Target COMBO_CRITERIO_BUSQUEDA =
            Target.the("Combo criterio de búsqueda")
                    .located(AppiumBy.xpath("//android.widget.TextView[@text='Elige el criterio de búsqueda']"));

    public static final Target TXT_INGRESA_NUMERO =
            Target.the("Campo ingresar número")
                    .located(AppiumBy.xpath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.RelativeLayout/android.widget.RelativeLayout[2]/android.widget.RelativeLayout/android.widget.RelativeLayout/android.view.ViewGroup/android.widget.FrameLayout/android.webkit.WebView/android.webkit.WebView/android.view.View/android.view.View[2]/android.view.View/android.widget.EditText"));
    public static final Target CHK_AUTORIZAR_MEDICION =
            Target.the("Check autorizar medición de velocidad")
                    .located(AppiumBy.xpath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.RelativeLayout/android.widget.RelativeLayout[2]/android.widget.RelativeLayout/android.widget.RelativeLayout/android.view.ViewGroup/android.widget.FrameLayout/android.webkit.WebView/android.webkit.WebView/android.view.View/android.view.View/android.view.View/android.view.View[2]/android.widget.CheckBox/android.widget.CheckBox"));
    public static final Target BTN_CONSULTAR =
            Target.the("Botón consultar ordenes de servicio")
                    .located(AppiumBy.xpath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.RelativeLayout/android.widget.RelativeLayout[2]/android.widget.RelativeLayout/android.widget.RelativeLayout/android.view.ViewGroup/android.widget.FrameLayout/android.webkit.WebView/android.webkit.WebView/android.view.View/android.view.View[3]/android.widget.Button"));

}