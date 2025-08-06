package userinterfaces;

import io.appium.java_client.AppiumBy;
import net.serenitybdd.screenplay.targets.Target;

public class GestionaEquipoPage {

    // Elementos principales del módulo
    public static final Target LBL_GESTIONA_TU_EQUIPO =
            Target.the("Etiqueta Gestiona tu equipo")
                    .located(AppiumBy.xpath("//*[@text='Gestiona tu equipo']"));

    public static final Target LBL_ELIGE_NUMERO_CUENTA =
            Target.the("Etiqueta Elige el número de cuenta o línea")
                    .located(AppiumBy.xpath("//*[@text='Elige el número de cuenta o línea']"));

    public static final Target TAB_POSTPAGO =
            Target.the("Tab Postpago")
                    .located(AppiumBy.xpath("//*[@text='Postpago']"));

    public static final Target BTN_VER_DETALLE =
            Target.the("Botón Ver detalle")
                    .located(AppiumBy.xpath("//*[@text='Ver detalle']"));

    // Loading
    public static final Target LBL_ESPERA_UN_MOMENTO =
            Target.the("Mensaje Espera un momento")
                    .located(AppiumBy.xpath("//*[@text='Espera un momento']"));

    // Opciones del menú principal
    public static final Target BTN_REGISTRAR_EQUIPO =
            Target.the("Botón Registrar equipo")
                    .located(AppiumBy.xpath("//*[@text='Registrar equipo']"));

    public static final Target BTN_REPORTAR_ROBO_PERDIDA =
            Target.the("Botón Reportar por robo o pérdida")
                    .located(AppiumBy.xpath("//*[@text='Reportar por robo o pérdida']"));

    public static final Target BTN_SOLUCIONAR_IMEI_DUPLICADO =
            Target.the("Botón Solucionar equipo con IMEI Duplicado")
                    .located(AppiumBy.xpath("//*[@text='Solucionar equipo con IMEI Duplicado']"));

    public static final Target BTN_RECONECTAR_ROBO_PERDIDA =
            Target.the("Botón Reconectar por robo o pérdida")
                    .located(AppiumBy.xpath("//*[@text='Reconectar por robo o pérdida']"));

    public static final Target BTN_CONSULTAR_EQUIPO =
            Target.the("Botón Consultar equipo")
                    .located(AppiumBy.xpath("//*[@text='Consultar equipo']"));

    public static final Target BTN_ACTUALIZAR_DATOS_EQUIPO =
            Target.the("Botón Actualizar datos de tu equipo")
                    .located(AppiumBy.xpath("//*[@text='Actualizar datos de tu equipo']"));

    // Registrar equipo - Modal
    public static final Target MODAL_REGISTRA_EQUIPO =
            Target.the("Modal Registra el equipo")
                    .located(AppiumBy.xpath("//*[@text='Registra el equipo']"));

    public static final Target LBL_RECUERDA_SIM_CARD =
            Target.the("Texto recordatorio SIM Card")
                    .located(AppiumBy.xpath("//*[contains(@text,'Recuerda tener la SIM Card puesta')]"));

    public static final Target BTN_REGISTRAR_MODAL =
            Target.the("Botón Registrar en modal")
                    .located(AppiumBy.xpath("//*[@text='Registrar']"));

    public static final Target BTN_CANCELAR_MODAL =
            Target.the("Botón Cancelar en modal")
                    .located(AppiumBy.xpath("//*[@text='Cancelar']"));

    // Reportar por robo o pérdida
    public static final Target LBL_REPORTAR_TITULO =
            Target.the("Título Reportar por robo o pérdida")
                    .located(AppiumBy.xpath("//*[@text='Reportar por robo o pérdida']"));

    public static final Target LBL_CONFIRMA_NUMERO_DATOS =
            Target.the("Texto Confirma el número de línea y datos")
                    .located(AppiumBy.xpath("//*[contains(@text,'Confirma el número de línea y datos')]"));

    public static final Target LBL_NUMERO_VALOR =
            Target.the("Valor del número de línea")
                    .located(AppiumBy.xpath("//*[@text='3108598508']"));

    public static final Target LBL_IMEI_VALOR =
            Target.the("Valor del IMEI")
                    .located(AppiumBy.xpath("//*[@text='860479068145924']"));

    public static final Target LBL_MARCA_VALOR =
            Target.the("Valor de la marca")
                    .located(AppiumBy.xpath("//*[@text='POCO']"));

    public static final Target LBL_MODELO_VALOR =
            Target.the("Valor del modelo")
                    .located(AppiumBy.xpath("//*[@text='2201116PG']"));

    public static final Target BTN_REPORTAR =
            Target.the("Botón Reportar")
                    .located(AppiumBy.xpath("//*[@text='Reportar']"));

    // Solucionar equipo con IMEI Duplicado
    public static final Target LBL_SOLUCIONAR_IMEI_TITULO =
            Target.the("Título Solucionar equipo con IMEI Duplicado")
                    .located(AppiumBy.xpath("//*[@text='Solucionar equipo con IMEI Duplicado']"));

    public static final Target BTN_REGISTRAR_LINEA_IMEI =
            Target.the("Botón Registrar la línea de tu IMEI")
                    .located(AppiumBy.xpath("//*[@text='Registrar la línea de tu IMEI']"));

    public static final Target BTN_GESTIONAR_LINEAS_IMEI =
            Target.the("Botón Gestionar las líneas de tu IMEI")
                    .located(AppiumBy.xpath("//*[@text='Gestionar las líneas de tu IMEI']"));

    // Reconectar por robo o pérdida - Modal de información
    public static final Target MODAL_RECONECTAR_INFO =
            Target.the("Modal información reconectar")
                    .located(AppiumBy.xpath("//*[contains(@text,'La línea') and contains(@text,'no están reportados')]"));

    public static final Target BTN_CERRAR_MODAL =
            Target.the("Botón Cerrar modal")
                    .located(AppiumBy.xpath("//*[@text='Cerrar']"));

    // Consultar equipo
    public static final Target LBL_CONSULTAR_TITULO =
            Target.the("Título Consultar equipo")
                    .located(AppiumBy.xpath("//*[@text='Consultar equipo']"));

    public static final Target LBL_TU_EQUIPO_ACTUAL =
            Target.the("Sección Tu equipo actual")
                    .located(AppiumBy.xpath("//*[@text='Tu equipo actual']"));

    public static final Target LBL_IMEI_CONSULTA =
            Target.the("IMEI en consulta")
                    .located(AppiumBy.xpath("//*[@text='860479068145924']"));

    public static final Target LBL_MARCA_CONSULTA =
            Target.the("Marca en consulta")
                    .located(AppiumBy.xpath("//*[@text='POCO']"));

    public static final Target LBL_MODELO_CONSULTA =
            Target.the("Modelo en consulta")
                    .located(AppiumBy.xpath("//*[@text='2201116PG']"));

    public static final Target LBL_ESTADO_EQUIPO =
            Target.the("Estado del equipo")
                    .located(AppiumBy.xpath("//*[contains(@text,'Este celular ya fue registrado exitosamente')]"));

    // Actualizar datos de tu equipo
    public static final Target LBL_ACTUALIZAR_TITULO =
            Target.the("Título Actualizar datos de tu equipo")
                    .located(AppiumBy.xpath("//*[@text='Actualizar datos de tu equipo']"));

    public static final Target CARD_EQUIPO_POCO =
            Target.the("Card equipo POCO")
                    .located(AppiumBy.xpath("//*[@text='POCO']"));

    public static final Target CARD_EQUIPO_ALCATEL =
            Target.the("Card equipo Alcatel")
                    .located(AppiumBy.xpath("//*[@text='Alcatel']"));

    public static final Target CARD_EQUIPO_SAMSUNG =
            Target.the("Card equipo SAMSUNG")
                    .located(AppiumBy.xpath("//*[@text='SAMSUNG']"));

    // Elementos de navegación hacia la derecha en las cards
    public static final Target ARROW_POCO =
            Target.the("Flecha tarjeta POCO")
                    .located(AppiumBy.xpath("//*[@text='POCO']/following-sibling::*"));

    public static final Target ARROW_ALCATEL =
            Target.the("Flecha tarjeta Alcatel")
                    .located(AppiumBy.xpath("//*[@text='Alcatel']/following-sibling::*"));

    public static final Target ARROW_SAMSUNG =
            Target.the("Flecha tarjeta SAMSUNG")
                    .located(AppiumBy.xpath("//*[@text='SAMSUNG']/following-sibling::*"));

}
