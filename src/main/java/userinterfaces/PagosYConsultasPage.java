package userinterfaces;

import net.serenitybdd.screenplay.targets.Target;
import org.openqa.selenium.By;

public class PagosYConsultasPage {

    public static final Target BTN_TRES_PUNTOS_MAS =
            Target.the("Boton de los 3 punticos para validar la mini versión")
                    .located(By.xpath("//android.widget.TextView[@content-desc=\"Más\"]"));
    public static final Target BTN_PAGAR_FACTURA =
            Target.the("Boton Pagar factura")
                    .located(By.xpath("//*[@text='Pagar factura' and @class='android.widget.Button']"));
    public static final Target BTN_PSE=
            Target.the("Boton PSE")
                    .located(By.xpath("//android.view.View[@resource-id=\"__react-content\"]/android.view.View/android.widget.TextView[6]"));
    public static final Target BTN_PSE_RECARGAS=
            Target.the("Boton PSE")
                    .located(By.xpath("//android.view.View[@resource-id=\"__react-content\"]/android.view.View/android.widget.TextView[4]"));
    public static final Target CBX_TIPO_PAQUETE =
            Target.the("Combo seleccion tipo de paquetes")
                    .located(By.xpath(
                                    "//*[@text='Paquetes de datos']"));
    public static final Target CBX_TIPO_PAQUETE_RECARGAS =
            Target.the("Combo seleccion tipo de paquetes")
                    .located(
                            By.xpath(
                                    "//android.widget.Button[@text=\"Paquetes todo incluido con redes\"]"));
    public static final Target ICON_HOME =
            Target.the("Icono Home")
                    .located(By.id("h5_tv_nav_back_to_home"));

    public static final Target ICON_DEBITO_PSE =
            Target.the("Icono Debito bancario PSE")
                    .located(By.xpath("(((//*[@text='Portal Pagos Claro' and @class='android.webkit.WebView']/*[@class='android.view.View'])[2]/*/*[@class='android.view.View' and ./parent::*[@class='android.view.View']])[2]/*[@class='android.view.View'])[1]"));


    // Elementos para pago parcial
    public static final Target TXT_MONTO_PAGO_PARCIAL =
            Target.the("Campo monto pago parcial")
                    .located(By.xpath("//android.widget.Button[@text=\"$0\"]"));

    public static final Target ICON_DESCARGAR_FACTURA =
            Target.the("Boton descargar factura")
                    .located(By.xpath("//android.view.View[@resource-id=\"__react-content\"]/android.view.View/android.widget.Button[1]"));

    public static final Target TXT_CONTRASENA_FACTURA =
            Target.the("Campo contraseña factura")
                    .located(By.id("com.android.chrome:id/password"));

    public static final Target URL_FACTURA_DESCARGADA =
            Target.the("URL factura descargada")
                    .located(By.id("com.android.chrome:id/url_bar"));

    public static final Target LBL_CARGO_FACTURA_CLARO =
            Target.the("Texto Con cargo a la factura Claro")
                    .located(By.xpath("//*[@text='Con cargo a la factura Claro']"));
    public static final Target BTN_FAMILIA_Y_AMIGOS =
            Target.the("Boton Familia y amigos")
                    .located(By.xpath("(//android.widget.TextView[@text=\"Familia y amigos\"])[2]"));
    public static final Target BTN_ELEGIDOS_TODO_DESTINO =
            Target.the("Boton Elegidos todo destino")
                    .located(By.xpath("//android.widget.TextView[@text=\"Elegidos todo destino\"]"));
    public static final Target LBL_APP_GRATIS =
            Target.the("Texto Aplicaciones gratis ya instaladas")
                    .located(By.xpath("//android.widget.TextView[@text=\"Aplicaciones gratis ya instaladas\"]"));

    public static final Target LBL_SIN_APP_DISPONIBLES =
            Target.the("Texto No tienes aplicaciones disponibles para administrar")
                    .located(By.xpath("//android.widget.TextView[@text=\"No tienes aplicaciones disponibles para administrar\"]"));
    public static final Target POPUP_SIN_PQ_ADICIONALES =
            Target.the("Popup Actualmente no cuenta con paquetes adicionales")
                    .located(By.xpath("//android.widget.TextView[@text=\"Actualmente no cuenta con paquetes adicionales\"]"));
    public static final Target LBL_FECHA_EXPIRACION =
            Target.the("Texto fecha expiración")
                    .located(By.xpath("//android.widget.TextView[@text=\"Fecha de expiración:\"]"));
    public static final Target POPUP_EQUIPO_REGISTRADO =
            Target.the("Texto fecha expiración")
                    .located(By.xpath("//android.widget.TextView[@text=\"El equipo ya se encuentra registrado.\"]"));
    public static final Target ICON_MAS_FAMILIA_Y_AMIGOS =
            Target.the("Icono de + familia y amigos")
                    .located(By.xpath("//android.widget.Image"));
    public static final Target TXT_NUMERO_FAMILIA =
            Target.the("Caja de texto agregar numero familia y amigos")
                    .located(By.xpath("//android.widget.TextView[@text=\"Agrega una nueva línea a Familia y Amigos\"]"));
    public static final Target ICON_ELIMINAR=
            Target.the("Icono ce eliminar")
                    .located(By.xpath("//android.view.View[@resource-id=\"__react-content\"]/android.view.View/android.widget.Image[2]"));
    public static final Target IMAG_CONSTANCIA_AL_DIA=
            Target.the("Constancia de cuenta al dia")
                    .located(By.id("com.clarocolombia.miclaro:id/page"));
    public static final Target LBL_INFORMACION_IMPORTANTE =
            Target.the("Texto Información Importante")
                    .located(By.xpath("//android.widget.TextView[@text=\"Información Importante\"]"));
    public static final Target CHECK_ACEPTAR_TERMINOS_CONDICIONES2 =
            Target.the("Texto Acepto términos y condiciones")
                    .located(
                            By.xpath(
                                    "//android.view.View[@resource-id=\"__react-content\"]/android.view.View[2]/android.widget.CheckBox/android.widget.CheckBox\n"));
    public static final Target BTN_PAGAR =
            Target.the("Boton Pagar")
                    .located(
                            By.xpath("//*[@text='Pagar' and @class='android.widget.Button']"));

    public static final Target CHECK_TIKTOK =
            Target.the("Check Tiktok")
                    .located(
                            By.xpath("//android.view.View[@resource-id=\"__react-content\"]/android.view.View/android.view.View[2]/android.widget.ToggleButton/android.widget.CheckBox"));
    public static final Target TXT_ESCRIBIR_PAIS =
            Target.the("Caja de texto Escribe el país de destino")
                    .located(
                            By.xpath("//android.widget.TextView[@text=\"Escribe el país de destino\"]"));

    public static final Target LBL_PAQUETE_ACTIVO = Target.the("Label paquete activo")
            .located(By.xpath("//android.widget.TextView[@text='PAQUETE ACTIVO']"));

    public static final Target BTN_VER_DETALLE_PAQUETE_ACTIVO = Target.the("Botón ver detalle paquete activo")
            .located(By.xpath("//android.widget.TextView[@text='PAQUETE ACTIVO']/following::android.widget.TextView[@text='Ver detalle del paquete'][1]"));

    public static final Target BTN_VER_DETALLE_PAQUETES = Target.the("Botones ver detalle de paquetes")
            .located(By.xpath("//android.widget.TextView[@text='Ver detalle del paquete']"));
    // Target para el botón de regresar a inicio
    public static final Target BTN_BACK_TO_HOME =
            Target.the("Botón regresar a inicio")
            .locatedBy("//android.widget.TextView[@resource-id='com.clarocolombia.miclaro:id/h5_tv_nav_back_to_home']");

    public static final Target BTN_VER_DETALLE_PRIMER_PAQUETE = Target.the("Primer botón ver detalle")
            .located(By.xpath("(//android.widget.Button[@text='Ver detalle del paquete'])[1]"));

    public static final Target BTN_VER_DETALLE_SEGUNDO_PAQUETE = Target.the("Segundo botón ver detalle")
            .located(By.xpath("(//android.widget.Button[@text='Ver detalle del paquete'])[2]"));

    public static final Target BTN_VER_DETALLE_TERCER_PAQUETE = Target.the("Tercer botón ver detalle")
            .located(By.xpath("(//android.widget.Button[@text='Ver detalle del paquete'])[3]"));

    public static final Target TXT_CLARO =
            Target.the("Mensaje CLARO")
                    .located(By.xpath("//*[@text='CLARO']"));
    public static final Target LBL_FECHA_PAGO_OPORTUNO =
            Target.the("Texto Fecha de pago oportuno:")
                    .located(By.xpath("//*[@text='Fecha de pago oportuno:']"));

    public static final Target LBL_ELEGIR_OTRO_MEDIO_PAGO =
            Target.the("Texto Elegir otro medio de pago")
                    .located(By.xpath("//*[@text='Elegir otro medio de pago']"));
}
