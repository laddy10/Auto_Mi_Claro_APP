package userinterfaces;

import io.appium.java_client.AppiumBy;
import net.serenitybdd.screenplay.targets.Target;

public class ConsultarConsumosPage {

    // Elementos principales del módulo
    public static final Target BTN_CONSULTAR_CONSUMOS =
            Target.the("Botón Consultar consumos")
                    .located(AppiumBy.xpath("//*[@text='Consultar consumos']"));

    public static final Target LBL_DETALLE_CONSUMOS =
            Target.the("Título Detalle de consumos")
                    .located(AppiumBy.xpath("//*[@text='Detalle de consumos']"));

    public static final Target LBL_NUMERO_LINEA =
            Target.the("Número de línea 310 263 2840")
                    .located(AppiumBy.xpath("//*[@text='310 263 2840']"));

    // Sección Tu plan
    public static final Target LBL_TU_PLAN =
            Target.the("Sección Tu plan")
                    .located(AppiumBy.xpath("//*[@text='Tu plan']"));

    public static final Target LBL_PLAN_CONECTADOS =
            Target.the("Plan Conectados 25 V 7.3 SALUD")
                    .located(AppiumBy.xpath("//*[@text='Conectados 25 V 7.3 SALUD']"));

    public static final Target LBL_GB_PLAN_160 =
            Target.the("160,00 GB plan")
                    .located(AppiumBy.xpath("//*[@text='160,00 GB plan']"));

    public static final Target LBL_FECHA_INICIO =
            Target.the("Fecha inicio")
                    .located(AppiumBy.xpath("//*[contains(@text,'Fecha inicio:')]"));

    public static final Target LBL_FECHA_CARGA =
            Target.the("Fecha carga")
                    .located(AppiumBy.xpath("//*[contains(@text,'Fecha carga:')]"));
    public static final Target OCULTAR_OPCIONES =
            Target.the("Ocultar opciones en consumos")
                    .located(AppiumBy.xpath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.RelativeLayout/android.widget.RelativeLayout[2]/android.widget.RelativeLayout/android.widget.RelativeLayout/android.view.ViewGroup/android.widget.FrameLayout/android.webkit.WebView/android.webkit.WebView/android.view.View/android.view.View/android.view.View[2]/android.widget.TextView[1]"));

    // Opciones del menú principal
    public static final Target BTN_CONSUMO_DATOS =
            Target.the("Botón Consumo de datos")
                    .located(AppiumBy.xpath("//*[@text='Consumo de datos']"));

    public static final Target BTN_APPS_SIN_LIMITE =
            Target.the("Botón Apps sin límite de consumo")
                    .located(AppiumBy.xpath("//*[@text='Apps sin límite de consumo']"));

    public static final Target BTN_CONSUMO_VOZ =
            Target.the("Botón Consumo de voz")
                    .located(AppiumBy.xpath("//*[@text='Consumo de voz']"));

    public static final Target BTN_CONSUMO_SMS =
            Target.the("Botón Consumo de SMS")
                    .located(AppiumBy.xpath("//*[@text='Consumo de SMS']"));

    public static final Target BTN_CONSUMO_PAQUETES_RECARGAS =
            Target.the("Botón Consumo paquetes y recargas")
                    .located(AppiumBy.xpath("//*[@text='Consumo paquetes y recargas']"));

    public static final Target BTN_COMPRAR_PAQUETES_RECARGAS =
            Target.the("Botón Comprar paquetes y recargas")
                    .located(AppiumBy.xpath("//*[@text='Comprar paquetes y recargas']"));

    // ===========================================
    // CONSUMO DE DATOS - ELEMENTOS ESPECÍFICOS
    // ===========================================

    public static final Target LBL_CONSUMO_DATOS_TITULO =
            Target.the("Título Consumo de datos")
                    .located(AppiumBy.xpath("//*[@text='Consumo de datos']"));

    public static final Target LBL_TU_PLAN_DATOS =
            Target.the("Tu plan en datos")
                    .located(AppiumBy.xpath("//*[@text='Tu plan']"));

    public static final Target LBL_CONSUMIDO_DEL_PLAN =
            Target.the("Consumido del plan")
                    .located(AppiumBy.xpath("//*[@text='Consumido del plan']"));

    public static final Target LBL_CONSUMIDO_1_40_GB =
            Target.the("1,40 GB consumido")
                    .located(AppiumBy.xpath("//*[@text='1,40 GB']"));

    public static final Target LBL_PAQUETES_COMPLEMENTARIOS =
            Target.the("Paquetes complementarios")
                    .located(AppiumBy.xpath("//*[@text='Paquetes complementarios']"));

    public static final Target LBL_PAQUETES_0_00_GB =
            Target.the("0,00 GB paquetes")
                    .located(AppiumBy.xpath("//*[@text='0,00 GB']"));

    public static final Target LBL_HAS_CONSUMIDO =
            Target.the("Has consumido 1,40 GB")
                    .located(AppiumBy.xpath("//*[contains(@text,'Has consumido 1,40 GB')]"));

    // Roaming Internacional
    public static final Target LBL_ROAMING_INTERNACIONAL =
            Target.the("Roaming Internacional")
                    .located(AppiumBy.xpath("//*[@text='Roaming Internacional']"));

    public static final Target LBL_PASAPORTE_AMERICA =
            Target.the("Pasaporte América")
                    .located(AppiumBy.xpath("//*[@text='Pasaporte América']"));

    public static final Target LBL_PAQUETES_ROAMING =
            Target.the("Paquetes Roaming Internacional")
                    .located(AppiumBy.xpath("//*[@text='Paquetes Roaming Internacional']"));

    public static final Target LBL_41_PAISES =
            Target.the("41 Países de América y Europa")
                    .located(AppiumBy.xpath("//*[contains(@text,'41 Países de América y Europa')]"));

    public static final Target LBL_RESTO_MUNDO =
            Target.the("Resto del Mundo")
                    .located(AppiumBy.xpath("//*[contains(@text,'Resto del Mundo')]"));

    public static final Target LBL_CONSUMOS_ADICIONALES =
            Target.the("Consumos adicionales Roaming Internacional")
                    .located(AppiumBy.xpath("//*[contains(@text,'Consumos adicionales Roaming')]"));

    public static final Target LBL_CONSUMOS_DEMANDA =
            Target.the("Consumos por demanda Internacional")
                    .located(AppiumBy.xpath("//*[contains(@text,'Consumos por demanda Internacional')]"));

    public static final Target LBL_DATOS_CONSUMIDOS =
            Target.the("Datos Consumidos")
                    .located(AppiumBy.xpath("//*[contains(@text,'Datos Consumidos')]"));

    // ===========================================
    // APPS SIN LÍMITE - ELEMENTOS ESPECÍFICOS
    // ===========================================

    public static final Target LBL_APPS_SIN_LIMITE_TITULO =
            Target.the("Título Apps sin límite de consumo")
                    .located(AppiumBy.xpath("//*[@text='Apps sin límite de consumo']"));

    public static final Target LBL_FACEBOOK =
            Target.the("Facebook")
                    .located(AppiumBy.xpath("//*[@text='Facebook']"));

    public static final Target LBL_FACEBOOK_CONSUMIDO =
            Target.the("Has consumido Facebook 0,00 GB")
                    .located(AppiumBy.xpath("//*[contains(@text,'Has consumido') and contains(@text,'0,00 GB')]"));

    // ===========================================
    // MENSAJES DE NO CONSUMO
    // ===========================================

    public static final Target LBL_AUN_NO_REGISTRAS_CONSUMOS =
            Target.the("Mensaje Aún no registras consumos en tu cuenta")
                    .located(AppiumBy.xpath("//*[contains(@text,'Aún no registras consumos en tu cuenta')]"));

    public static final Target LBL_AUN_NO_HAS_ADQUIRIDO_PAQUETES =
            Target.the("Mensaje Aún no has adquirido paquetes")
                    .located(AppiumBy.xpath("//*[contains(@text,'Aún no has adquirido paquetes')]"));

    public static final Target LBL_AUN_NO_HAS_HECHO_RECARGAS =
            Target.the("Mensaje Aún no has hecho recargas")
                    .located(AppiumBy.xpath("//*[contains(@text,'Aún no has hecho recargas')]"));

    public static final Target LBL_EN_ESTE_MES_NO_REGISTRAS =
            Target.the("Mensaje En este mes no registras consumos")
                    .located(AppiumBy.xpath("//*[contains(@text,'En este mes no registras consumos')]"));

    // ===========================================
    // CONSUMO PAQUETES Y RECARGAS
    // ===========================================

    public static final Target LBL_CONSUMO_PAQUETES_TITULO =
            Target.the("Título Consumo paquetes y recargas")
                    .located(AppiumBy.xpath("//*[@text='Consumo paquetes y recargas']"));

    public static final Target LBL_SALDO_DISPONIBLE =
            Target.the("Saldo disponible")
                    .located(AppiumBy.xpath("//*[@text='Saldo disponible']"));

    public static final Target LBL_SALDO_0_PESOS =
            Target.the("$ 0")
                    .located(AppiumBy.xpath("//*[@text='$ 0']"));

    public static final Target LBL_FECHA_VENCIMIENTO =
            Target.the("Fecha de vencimiento")
                    .located(AppiumBy.xpath("//*[@text='Fecha de vencimiento']"));

    public static final Target LBL_FECHA_2025_03_03 =
            Target.the("Fecha 2025/03/03")
                    .located(AppiumBy.xpath("//*[@text='2025/03/03']"));

    public static final Target LBL_CONSULTA_PAQUETES_ACTIVOS =
            Target.the("Consulta los paquetes y recargas activos")
                    .located(AppiumBy.xpath("//*[contains(@text,'Consulta los paquetes y recargas activos')]"));

    // Tabs de paquetes y recargas
    public static final Target TAB_PAQUETES =
            Target.the("Tab Paquetes")
                    .located(AppiumBy.xpath("//*[@text='Paquetes']"));

    public static final Target TAB_RECARGAS =
            Target.the("Tab Recargas")
                    .located(AppiumBy.xpath("//*[@text='Recargas']"));

    public static final Target TAB_CONSUMO_DEL_MES =
            Target.the("Tab Consumo del mes")
                    .located(AppiumBy.xpath("//*[@text='Consumo del mes']"));

    // Información adicional
    public static final Target LBL_HAZ_RECARGA_RENOVAR =
            Target.the("Haz una recarga para renovar la vigencia")
                    .located(AppiumBy.xpath("//*[contains(@text,'Haz una recarga para renovar la vigencia')]"));

    public static final Target LBL_SALDO_MES_ANTERIOR =
            Target.the("Saldo del mes anterior")
                    .located(AppiumBy.xpath("//*[@text='Saldo del mes anterior']"));

    public static final Target LBL_JUNIO_2025 =
            Target.the("Junio 2025")
                    .located(AppiumBy.xpath("//*[@text='Junio 2025']"));

    public static final Target LBL_OTROS =
            Target.the("Otros")
                    .located(AppiumBy.xpath("//*[@text='Otros']"));

    public static final Target BTN_VER_DETALLE_SALDO =
            Target.the("Ver detalle del saldo")
                    .located(AppiumBy.xpath("//*[@text='Ver detalle']"));

    // Loading
    public static final Target LBL_ESPERA_UN_MOMENTO =
            Target.the("Mensaje Espera un momento")
                    .located(AppiumBy.xpath("//*[@text='Espera un momento']"));

}