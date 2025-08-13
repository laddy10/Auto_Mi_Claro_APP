package userinterfaces;

import net.serenitybdd.core.annotations.findby.By;
import net.serenitybdd.screenplay.targets.Target;

public class EntretenimientoPage {

    // Navegación principal
    public static final Target TAB_ENTRETENIMIENTO = Target.the("Tab Entretenimiento en barra inferior")
            .located(By.xpath("//*[@text='Entretenimiento']"));

    public static final Target BTN_CONTINUAR = Target.the("Botón Continuar Netflix")
            .located(By.xpath("//*[@text='Continuar']"));

    public static final Target BTN_CLOSE = Target.the("Botón Close")
            .locatedBy("//android.widget.Button[@text='Close']");

    public static final Target BTN_CONTINUAR_2 = Target.the("Botón 'Continuar' (segundo)")
            .locatedBy("(//android.widget.Button[@text='Continuar'])[2]");

    public static final Target BTN_VOLVER = Target.the("Botón volver en mini programa")
            .located(By.xpath("//android.widget.ImageView[@resource-id='com.clarocolombia.miclaro:id/h5_iv_nav_back']"));

    public static final Target BTN_ACEPTAR_ET = Target.the("Botón 'Aceptar'")
            .locatedBy("//android.widget.Button[@text='Aceptar']");



    // Sección Hecho para ti
    public static final Target LBL_HECHO_PARA_TI =
            Target.the("Título Hecho para ti")
                    .located(By.xpath("//*[@text='Hecho para ti']"));

    public static final Target LBL_LO_MEJOR_CLARO =
            Target.the("Subtítulo Lo mejor de Claro, en tiempo real")
                    .located(By.xpath("//*[contains(@text,'Lo mejor de Claro, en tiempo real')]"));

    // Carrusel Hecho para ti - Elementos principales
    public static final Target BTN_CLARO_TV_PLUS =
            Target.the("Botón Claro tv +")
                    .located(By.xpath("//*[@text='Claro tv +']"));

    public static final Target BTN_RED_TV_EN_VIVO =
            Target.the("Botón Red + TV en vivo")
                    .located(By.xpath("//*[@text='Red + TV en vivo']"));

    public static final Target BTN_CLARO_VIDEO =
            Target.the("Botón Claro Video")
                    .located(By.xpath("//*[@text='Claro Video']"));

    public static final Target BTN_CLARO_MUSICA =
            Target.the("Botón Claro Música")
                    .located(By.xpath("//*[@text='Claro Música']"));

    // Sección Tus plataformas favoritas
    public static final Target LBL_TUS_PLATAFORMAS_FAVORITAS =
            Target.the("Título Tus plataformas favoritas")
                    .located(By.xpath("//*[@text='Tus plataformas favoritas']"));

    public static final Target LBL_EL_MEJOR_CONTENIDO =
            Target.the("Subtítulo El mejor contenido en un solo lugar")
                    .located(By.xpath("//*[@text='El mejor contenido en un solo lugar']"));

    // Plataformas principales
    public static final Target BTN_NETFLIX =
            Target.the("Botón Netflix")
                    .located(By.xpath("//*[@text='Netflix']"));

    public static final Target BTN_HBOMAX =
            Target.the("Botón HBOmax")
                    .located(By.xpath("//*[@text='HBOmax']"));

    public static final Target BTN_AMAZON_PRIME =
            Target.the("Botón Amazon Prime")
                    .located(By.xpath("//*[@text='Amazon Prime']"));

    public static final Target BTN_VER_MAS_PLATAFORMAS =
            Target.the("Botón Ver más en plataformas")
                    .located(By.xpath("//*[@text='Ver más']"));

    // Sección Series, películas
    public static final Target LBL_SERIES_PELICULAS =
            Target.the("Título Series, películas, en un solo lugar")
                    .located(By.xpath("//*[contains(@text,'Series, películas, en un solo lugar')]"));

    // Loading states
    public static final Target LBL_ESPERA_UN_MOMENTO =
            Target.the("Mensaje Espera un momento")
                    .located(By.xpath("//*[@text='Espera un momento']"));

    // Estados de redirección externa
    public static final Target LBL_SAVE_MY_PLACE =
            Target.the("Mensaje Save my place in the app")
                    .located(By.xpath("//*[contains(@text,'Save my place in the app')]"));

    public static final Target BTN_OPEN =
            Target.the("Botón OPEN para abrir aplicación externa")
                    .located(By.xpath("//*[@text='OPEN']"));

    // ===========================================
    // CLARO MÚSICA Y VIDEO - ELEMENTOS ESPECÍFICOS
    // ===========================================

    public static final Target VERSION_CLARO_MUSICA = Target.the("Versión Claro Música")
            .locatedBy("//android.widget.TextView[starts-with(@text, 'Ver')]");

    public static final Target LBL_CLARO_MUSICA_TITULO =
            Target.the("Título Claro Música")
                    .located(By.xpath("//*[@text='Claro Música']"));

    public static final Target LBL_ELIGE_NUMERO_CUENTA_MUSICA =
            Target.the("Texto Elige el número de cuenta o línea")
                    .located(By.xpath("//*[@text='Elige el número de cuenta o línea']"));

    public static final Target TAB_POSTPAGO_MUSICA =
            Target.the("Tab Postpago en Claro Música")
                    .located(By.xpath("//*[@text='Postpago']"));

    // Líneas específicas para Claro Música
    public static final Target LINEA_310_262_8443 =
            Target.the("Línea 310 262 8443")
                    .located(By.xpath("//*[@text='Línea 310 262 8443']"));

    public static final Target LINEA_310_263_5941 =
            Target.the("Línea 310 263 5941")
                    .located(By.xpath("//*[@text='Línea 310 263 5941']"));

    public static final Target LINEA_322_278_6448 =
            Target.the("Línea 322 278 6448")
                    .located(By.xpath("//*[@text='Línea 322 278 6448']"));

    // ===========================================
    // NETFLIX - ELEMENTOS ESPECÍFICOS
    // ===========================================

    public static final Target LBL_NETFLIX_TITULO =
            Target.the("Título Netflix")
                    .located(By.xpath("//*[@text='Netflix']"));

    public static final Target LBL_ELIGE_NUMERO_CUENTA_NETFLIX =
            Target.the("Texto Elige el número de cuenta o línea")
                    .located(By.xpath("//*[@text='Elige el número de cuenta o línea']"));
    public static final Target LBL_NETFLIX_LOGO =
            Target.the("Logo NETFLIX")
                    .located(By.xpath("//*[@text='NETFLIX']"));

    public static final Target LBL_VARIEDAD_SERIES =
            Target.the("Descripción variedad de series y películas")
                    .located(By.xpath("//*[contains(@text,'Variedad de series, películas')]"));

    public static final Target LBL_ACTIVA_NETFLIX =
            Target.the("Texto Activa Netflix con tu servicio móvil")
                    .located(By.xpath("//*[contains(@text,'Activa Netflix con tu servicio móvil')]"));

    public static final Target LBL_RECUERDA_PLAN_INCLUYE =
            Target.the("Texto recordatorio del plan")
                    .located(By.xpath("//*[contains(@text,'Recuerda que tu plan incluye Netflix')]"));

    public static final Target LBL_CODIGO_VENDEDOR =
            Target.the("Label Código del vendedor")
                    .located(By.xpath("//*[@text='Código del vendedor']"));

    public static final Target TXT_CODIGO_VENDEDOR =
            Target.the("Campo Código del vendedor")
                    .located(By.xpath("//*[@text='Escribe el código del vendedor']"));

    public static final Target TEXTO_TERMINOS = Target.the("Texto Términos y Condiciones")
            .located(By.xpath("//android.widget.TextView[@text='Aceptar Términos y Condiciones']"));







    // ===========================================
    // AMAZON PRIME - ELEMENTOS ESPECÍFICOS
    // ===========================================

    public static final Target LBL_AMAZON_PRIME_TITULO =
            Target.the("Título Amazon Prime")
                    .located(By.xpath("//*[@text='Amazon Prime']"));

    public static final Target LBL_DISFRUTA_TODO_CONTENIDO =
            Target.the("Descripción Amazon Prime")
                    .located(By.xpath("//*[contains(@text,'Disfruta de todo el contenido en línea')]"));

    public static final Target LBL_AMAZON_PRIME_LOGO =
            Target.the("Logo amazon prime")
                    .located(By.xpath("//*[@text='amazon prime']"));

    public static final Target LBL_UN_MES_CORTESIA =
            Target.the("Texto Te damos 1 mes de cortesía")
                    .located(By.xpath("//*[contains(@text,'Te damos 1 mes de cortesía')]"));

    public static final Target LBL_PRECIO_24900 =
            Target.the("Precio $ 24.900/mes")
                    .located(By.xpath("//*[@text='$ 24.900/mes']"));

    public static final Target LBL_IVA_INCLUIDO =
            Target.the("Texto IVA incluido")
                    .located(By.xpath("//*[@text='IVA incluido']"));

    public static final Target LBL_ENVIOS_RAPIDOS =
            Target.the("Título Envíos rápidos")
                    .located(By.xpath("//*[contains(@text,'Envíos rápidos y sin costo')]"));

    public static final Target LBL_ENTRETENIMIENTO_SIN_LIMITES =
            Target.the("Título Entretenimiento sin límites")
                    .located(By.xpath("//*[contains(@text,'Entretenimiento sin límites')]"));

    public static final Target LBL_PRIME_GAMING =
            Target.the("Título Prime Gaming")
                    .located(By.xpath("//*[@text='Prime Gaming']"));

    public static final Target BTN_ELEGIR_PLAN =
            Target.the("Botón Elegir plan")
                    .located(By.xpath("//*[@text='Elegir plan']"));

    // Pantalla de activación Amazon Prime
    public static final Target LBL_VALOR_SUSCRIPCION =
            Target.the("Texto valor de suscripción")
                    .located(By.xpath("//*[contains(@text,'El valor de la suscripción se cargará')]"));

    public static final Target LBL_LINEA_POSTPAGO =
            Target.the("Label Línea postpago")
                    .located(By.xpath("//*[@text='Línea postpago']"));

    public static final Target LBL_NUMERO_LINEA_PRIME =
            Target.the("Número de línea Amazon Prime")
                    .located(By.xpath("//*[@text='310 262 8443']"));

    public static final Target TXT_CODIGO_VENDEDOR_OPCIONAL =
            Target.the("Campo Código del vendedor opcional")
                    .located(By.xpath("//*[@text='Escribe el código del vendedor']"));

    public static final Target CHK_ACEPTAR_TERMINOS_PRIME =
            Target.the("Checkbox Términos Amazon Prime")
                    .located(By.xpath("//*[contains(@text,'Aceptar Términos y Condiciones')]"));

    public static final Target BTN_ACEPTAR_AMAZON = Target.the("Botón Aceptar")
            .located(By.xpath("//android.widget.Button[@text='Aceptar']"));




    public static final Target LBL_VALOR_FINAL =
            Target.the("Valor final")
                    .located(By.xpath("//*[@text='Valor']"));

    // ===========================================
    // PLATAFORMAS ADICIONALES (Ver más)
    // ===========================================

    public static final Target BTN_WIN_PLAY =
            Target.the("Botón Win Play")
                    .located(By.xpath("//*[@text='Win Play']"));

    public static final Target BTN_DISNEY_PLUS =
            Target.the("Botón Disney+")
                    .located(By.xpath("//*[@text='Disney+']"));

    public static final Target BTN_HOTGO =
            Target.the("Botón HotGo")
                    .located(By.xpath("//*[@text='HotGo']"));

    public static final Target BTN_RED_NOTICIAS =
            Target.the("Botón RED + NOTICIAS")
                    .located(By.xpath("//*[@text='RED + NOTICIAS']"));

    // Disney+ específicos
    public static final Target BTN_PLAN_ESTANDAR =
            Target.the("Botón Plan Estándar Disney+")
                    .located(By.xpath("//*[@text='Plan Estándar']"));

    public static final Target BTN_PLAN_PREMIUM =
            Target.the("Botón Plan Premium Disney+")
                    .located(By.xpath("//*[@text='Plan Premium']"));

    public static final Target CHECK_TERMINOS = Target.the("Check de aceptar términos y condiciones")
            .located(By.xpath("//android.view.View[@resource-id='__react-content']/android.view.View[3]/android.view.View/android.widget.TextView"));

    public static final Target CHECK_TERMINOS_AMAZON_PRIME = Target.the("Check de aceptar términos y condiciones")
            .located(By.xpath("//android.view.View[@resource-id=\"__react-content\"]/android.view.View/android.view.View[2]/android.widget.TextView"));

    public static final Target DISNEY_ESTANDAR_EN_CONTENT = Target.the("Texto Disney+ Estándar dentro del content_id")
            .located(By.xpath("//android.widget.RelativeLayout[@resource-id='com.clarocolombia.miclaro:id/content_id']//*[contains(@text, 'Disney+ Estándar')]"));

    public static final Target BTN_ALERT_CONFIRM = Target.the("Botón confirmar alerta")
            .located(By.xpath("//android.widget.Button[@resource-id='com.claro.claromusica.latam:id/btn_alert_confirm']"));

    public static final Target LBL_MENSAJE_ALERT = Target.the("mensaje de alerta")
            .located(By.xpath("//android.widget.TextView[@resource-id='com.claro.claromusica.latam:id/tv_message']"));

    public static final Target MENU_CATEGORIAS = Target.the("Menú de categorías")
            .located(By.xpath("//android.view.View[@resource-id='categoriesmenu']"));

    public static final Target MAS_DESCARGADOS = Target.the("Botón 'Más Descargados'")
            .located(By.xpath("//android.view.View[contains(@content-desc, 'Más Descargados')]"));

    public static final Target COMIDAS = Target.the("Botón 'Comidas'")
            .located(By.xpath("//android.view.View[contains(@content-desc, 'Comidas')]"));

    public static final Target PRODUCTOS_CLARO = Target.the("Botón 'Productos Claro'")
            .located(By.xpath("//android.view.View[contains(@content-desc, 'Productos Claro')]"));

    public static final Target VIAJES = Target.the("Botón 'Viajes'")
            .located(By.xpath("//android.view.View[contains(@content-desc, 'Viajes')]"));

    public static final Target ENTRETENIMIENTO_CLAROCLUB = Target.the("Botón 'Entretenimiento'")
            .located(By.xpath("//android.view.View[contains(@content-desc, 'Entretenimiento')]"));

    public static final Target MASCOTAS = Target.the("Botón 'Mascotas'")
            .located(By.xpath("//android.view.View[contains(@content-desc, 'Mascotas')]"));

    public static final Target VARIEDADES = Target.the("Botón 'Variedades'")
            .located(By.xpath("//android.view.View[contains(@content-desc, 'Variedades')]"));

    public static final Target EDUCACION = Target.the("Botón 'Educación'")
            .located(By.xpath("//android.view.View[contains(@content-desc, 'Educación')]"));
}