package userinterfaces;

import io.appium.java_client.AppiumBy;
import net.serenitybdd.screenplay.targets.Target;

public class VersionesMiniProgramaPage {

    public static final Target MINI_VERSION_CLARO_VIDEO_TARGET = Target.the("etiqueta de versión")
            .located(AppiumBy.xpath("//android.widget.TextView[@text=\"Ver 1.4.6 (2025-03-03)\"]"));

    public static final Target MINI_VERSION_CLARO_CLUB_TARGET = Target.the("etiqueta de versión 1.4.9")
            .located(AppiumBy.xpath("//android.widget.TextView[@text=\"Ver 1.4.9 (2025-06-04)\"]"));

    public static final Target MINI_VERSION_CLARO_MUSICA_TARGET = Target.the("etiqueta de versión 1.4.4")
            .located(AppiumBy.xpath("//android.widget.TextView[@text=\"Ver 1.4.4 (2025-03-03)\"]"));

    public static final Target MINI_VERSION_NETFLIX_TARGET = Target.the("etiqueta de versión 1.1.86")
            .located(AppiumBy.xpath("//android.widget.TextView[@text=\"Ver 1.1.86 (2025-07-10)\"]"));

    public static final Target MINI_VERSION_AMAZON_PRIME_TARGET = Target.the("etiqueta de versión 1.4.25")
            .located(AppiumBy.xpath("//android.widget.TextView[@text=\"Ver 1.4.25 (2025-07-25)\"]"));

    public static final Target MINI_VERSION_DISNEY_TARGET = Target.the("etiqueta de versión 1.0.48")
            .located(AppiumBy.xpath("//android.widget.TextView[@text=\"Ver 1.0.48 (2025-06-16)\"]"));

    public static final Target MINI_VERSION_PAGA_TU_FACTURA_TARGET = Target.the("etiqueta de versión 1.0.563")
            .located(AppiumBy.xpath("//android.widget.TextView[@text=\"Ver 1.0.563 (2025-08-06)\"]"));

    public static final Target MINI_VERSION_RECARGAS_Y_PAQUETES_TARGET = Target.the("etiqueta de versión 1.0.394")
            .located(AppiumBy.xpath("//android.widget.TextView[@text=\"Ver 1.0.394 (2025-08-05)\"]"));

    public static final Target MINI_VERSION_NECESITAS_AYUDA_TARGET = Target.the("etiqueta de versión 1.0.41")
            .located(AppiumBy.xpath("//android.widget.TextView[@text=\"Ver 1.0.41 (2025-06-25)\"]"));

    public static final Target MINI_VERSION_ESTADO_SERVICIOS_TECNICOS_TARGET = Target.the("etiqueta de versión 1.4.6 del 17 de junio")
            .located(AppiumBy.xpath("//android.widget.TextView[@text=\"Ver 1.4.6 (2025-06-17)\"]"));

    public static final Target MINI_VERSION_CONSULTAR_PQR_TARGET = Target.the("etiqueta de versión 1.4.3 del 14 de julio")
            .located(AppiumBy.xpath("//android.widget.TextView[@text=\"Ver 1.4.3 (2025-07-14)\"]"));

    public static final Target MINI_VERSION_DETALLE_DE_TU_PLAN_TARGET = Target.the("etiqueta de versión 1.0.267")
            .located(AppiumBy.xpath("//android.widget.TextView[@text=\"Ver 1.0.267 (2025-08-06)\"]"));

    public static final Target MINI_VERSION_ADQUIRIR_PRODUCTOS_TARGET = Target.the("etiqueta de versión 1.0.305")
            .located(AppiumBy.xpath("//android.widget.TextView[@text=\"Ver 1.0.305 (2025-07-09)\"]"));

    public static final Target MINI_VERSION_CONSULTAR_CONSUMOS_TARGET = Target.the("etiqueta de versión 1.0.365")
            .located(AppiumBy.xpath("//android.widget.TextView[@text=\"Ver 1.0.365 (2025-07-30)\"]"));

    public static final Target MINI_VERSION_eSIM_CLARO_TARGET = Target.the("etiqueta de versión 1.0.60")
            .located(AppiumBy.xpath("//android.widget.TextView[@text=\"Ver 1.0.60 (2025-07-28)\"]"));

    public static final Target MINI_VERSION_FAMILIA_Y_AMIGOS_TARGET = Target.the("etiqueta de versión 1.0.261")
            .located(AppiumBy.xpath("//android.widget.TextView[@text=\"Ver 1.0.261 (2025-07-11)\"]"));
}
