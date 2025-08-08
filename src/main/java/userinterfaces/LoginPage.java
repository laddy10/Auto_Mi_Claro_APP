package userinterfaces;

import static utils.Constants.MIENTRAS_APP_ESTA_EN_USO;

import io.appium.java_client.AppiumBy;
import net.serenitybdd.screenplay.targets.Target;

public class LoginPage {

  public class AppConstants {
    public static final String WHATSAPP_PACKAGE = "com.clarocolombia.miclaro";
    public static final String WHATSAPP_ACTIVITY = "com.claro.superapp.SplashActivity";
  }

  public static final Target LOADING_SPLASH =
      Target.the("Logo de espera al iniciar la App Mi Claro")
          .located(AppiumBy.id("imgLoadingSplash"));
  public static final Target LBL_ENCABEZADO_USUARIO =
      Target.the("Label Encabezado de usuario").located(AppiumBy.id("home_user_name_tv"));
  public static final Target LBL_SESION_CERRADA_POR_SEGURIDAD =
      Target.the("Texto Tu sesión se ha cerrado por seguridad")
          .located(AppiumBy.xpath("//*[@text='Tu sesión se ha cerrado por seguridad']"));
  public static final Target LBL_NOS_ALEGRA_TENERTE_DE_VUELTA =
      Target.the("Texto ¡Nos alegra tenerte de vuelta!")
          .located(AppiumBy.xpath("//*[@text='¡Nos alegra tenerte de vuelta!']"));
  public static final Target LBL_INICIAR_SESION =
      Target.the("Texto Iniciar sesión").located(AppiumBy.xpath("//*[@text='Iniciar sesión']"));
  public static final Target LOADING_ESPERA_UN_MOMENTO =
      Target.the("Logo de espera al iniciar la App Mi Claro")
          .located(AppiumBy.id("animation_splash_lottie"));
  public static final Target BTN_ACEPTAR =
      Target.the("Boton Aceptar").located(AppiumBy.xpath("//*[@text='Aceptar']"));
  public static final Target BTN_PERMISO_UBICACION =
      Target.the("Botón mientras la app está en uso")
          .located(
              AppiumBy.xpath(
                  "//*[translate(@text, 'ABCDEFGHIJKLMNOPQRSTUVWXYZÁÉÍÓÚ', 'abcdefghijklmnopqrstuvwxyzáéíóú') = '"
                      + MIENTRAS_APP_ESTA_EN_USO
                      + "']"));

  public static final Target BTN_ACEPTAR_PERMISO =
      Target.the("Boton Aceptar").located(AppiumBy.xpath("//*[@text='Aceptar']"));
  public static final Target SMS_PERMISO_LLAMADAS =
      Target.the("Mensaje ¿Permitir que Mi Claro haga y administre las llamadas telefónicas?")
          .located(
              AppiumBy.xpath(
                  "//*[@text='¿Permitir que Mi Claro haga y administre las llamadas telefónicas?']"));
  public static final Target SMS_PERMISO_NOTIFICACIONES =
      Target.the("Mensaje ¿Permitir que Mi Claro te envíe notificaciones? ")
          .located(AppiumBy.xpath("//*[@text='¿Permitir que Mi Claro te envíe notificaciones?']"));
  public static final Target SMS_PERMISO_NOTIFICACIONES2 =
      Target.the("Mensaje ¿Permitir que Mi Claro te envíe notificaciones? ")
          .located(
              AppiumBy.xpath(
                  "//*[@text='¿Quieres permitir que Mi Claro te envíe notificaciones?']"));
  public static final Target BTN_OMITIR =
      Target.the("Boton de Omitir").located(AppiumBy.xpath("//*[@text='Omitir']"));
  public static final Target LBL_BIENVENIDA =
      Target.the("Texto ¡Te damos la bienvenida!")
          .located(AppiumBy.xpath("//*[@text='¡Te damos la bienvenida!']"));
  public static final Target TXT_PASSWORD =
      Target.the("Ingresar Password").located(AppiumBy.id("input"));
  public static final Target CHECK_TC =
      Target.the("Check Acepto los términos y condiciones, el tratamiento de mis datos")
          .located(AppiumBy.id("iv_protocol"));
  public static final Target TXT_USERNAME =
      Target.the("Ingresar Usuario").located(AppiumBy.id("input"));
  public static final Target LBL_TERMINOS_Y_CONDICIONES =
      Target.the("Texto términos y condiciones")
          .located(AppiumBy.xpath("//*[@text='¡Te damos la bienvenida!']"));
  public static final Target CHECK_TERMINOS_Y_CONDICIONES =
      Target.the("Texto Acepto los términos y condiciones, el tratamiento de mis datos")
          .located(AppiumBy.id("iv_protocol"));
  public static final Target LBL_SESION_ABIERTA =
      Target.the("Texto Tienes una sesión abierta en otro dispositivo")
          .located(AppiumBy.xpath("//*[@text='Tienes una sesión abierta en otro dispositivo']"));
  public static final Target LBL_INGRESO_BIOMETRICO =
      Target.the("Boton Activar ingreso biometrico")
          .located(AppiumBy.xpath("//*[@text='Activa el ingreso biométrico desde tu celular']"));
  public static final Target TXT_AUTORIZACION_VELOCIDAD =
      Target.the("Texto de Autorización de medición de velocidad")
          .located(AppiumBy.xpath("//*[@text='Autorización de medición de velocidad']"));

  public static final Target TXT_AUTORIZACION_VELOCIDAD_2 =
      Target.the("Texto Autorización para Medición de Velocidad")
          .located(AppiumBy.xpath("//*[@text='Autorización para Medición de Velocidad']"));

  public static final Target LBL_NUMERO_DOCUMENTO =
      Target.the("Texto Ingresa con tu número de documento")
          .located(AppiumBy.xpath("//*[@text='Ingresa con tu número de documento']"));

  public static final Target BTN_OTROS_METODOS_INGRESO =
      Target.the("Boton Otros métodos de ingreso")
          .located(AppiumBy.xpath("//*[@text='Otros métodos de ingreso']"));

  public static final Target LBL_IDENTIFICADOR_USUARIO =
      Target.the("Texto que identifica la cuenta actual (correo o documento)")
          .located(AppiumBy.id("relogin_account"));

  public static final Target BTN_CONTINUAR =
      Target.the("Botón continuar con cuenta activa").located(AppiumBy.id("login_entry_next"));

  public static final Target BTN_INGRESAR_OTRA_CUENTA =
      Target.the("Botón para ingresar con otra cuenta")
          .located(AppiumBy.id("relogin_use_change_account"));

  public static final Target TXT_NO_PERMITIR =
      Target.the("Texto No permitir")
          .located(AppiumBy.xpath("//*[contains(text(),'No permitir')]"));
}
