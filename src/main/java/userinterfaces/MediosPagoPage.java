package userinterfaces;

import io.appium.java_client.MobileBy;
import net.serenitybdd.screenplay.targets.Target;
import org.openqa.selenium.By;

public class MediosPagoPage {

  // Campos del formulario
  public static final Target TXT_NUMERO_TARJETA =
      Target.the("Campo número de tarjeta")
          .located(By.xpath("//android.widget.EditText[@resource-id=\"numeroTarjeta\"]"));

  public static final Target TXT_NOMBRE_TARJETA =
      Target.the("Campo nombre tarjeta")
          .located(By.xpath("//android.widget.EditText[@resource-id=\"NOMBRE_TARJETA\"]"));

  public static final Target DROPDOWN_TIPO_DOCUMENTO =
      Target.the("Dropdown tipo documento")
          .located(By.xpath("//android.view.View[@resource-id=\"TIPO_DOCUMENTO\"]"));

  public static final Target TXT_NUMERO_DOCUMENTO =
      Target.the("Campo número documento")
          .located(By.xpath("//android.widget.EditText[@resource-id=\"NUMERO_DOCUMENTO\"]"));

  public static final Target TXT_FECHA_EXPIRACION =
      Target.the("Campo fecha expiración")
          .located(By.xpath("//android.widget.EditText[@resource-id=\"mesAnoFechaVencimiento\"]"));

  public static final Target TXT_CVV =
      Target.the("Campo CVV")
          .located(By.xpath("//android.widget.EditText[@resource-id=\"inputCVV\"]"));

  public static final Target TXT_EMAIL =
      Target.the("Campo email")
          .located(By.xpath("//android.widget.EditText[@resource-id=\"EMAIL\"]"));

  public static final Target TXT_TELEFONO =
      Target.the("Campo teléfono")
          .located(By.xpath("//android.widget.EditText[@resource-id=\"TELEFONO\"]"));

  // Botones de cuotas
  public static final Target BTN_MAS_CUOTAS =
      Target.the("Botón más cuotas").located(By.xpath("//android.widget.Button[@text='+']"));

  public static final Target BTN_MENOS_CUOTAS =
      Target.the("Botón menos cuotas").located(By.xpath("//android.widget.Button[@text='-']"));

  // Aliases compatibles con formularios de tarjeta de crédito
  public static final Target TXT_NOMBRE_APELLIDO = TXT_NOMBRE_TARJETA;
  public static final Target SELECT_TIPO_DOCUMENTO = DROPDOWN_TIPO_DOCUMENTO;
  public static final Target TXT_CVC = TXT_CVV;
  public static final Target TXT_CORREO_ELECTRONICO = TXT_EMAIL;
  public static final Target BTN_AUMENTAR_CUOTAS = BTN_MAS_CUOTAS;
  public static final Target BTN_DISMINUIR_CUOTAS = BTN_MENOS_CUOTAS;
  public static final Target CHK_GUARDAR_TARJETA =
      Target.the("Checkbox guardar tarjeta")
          .located(By.xpath("//*[contains(@text, 'Guardar')]"));

  public static final Target RDO_ASOCIAR_CLPAY =
      Target.the("Radio button asociar Claro Pay")
          .located(By.xpath("//android.widget.RadioButton[@resource-id=\"asociar_clpay\"]"));

  public static final Target DROPDOWN_BANCO =
      Target.the("Dropdown banco").located(By.xpath("//android.view.View[@resource-id='banco']"));

  public static final Target DROPDOWN_TIPO_CLIENTE =
      Target.the("Dropdown tipo cliente")
          .located(By.xpath("//android.view.View[@resource-id='tipoCliente']"));

  public static final Target BTN_PSE_RECARGAS =
      Target.the("Boton PSE de recargas y paquetes")
          .located(
              By.xpath(
                  "/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.RelativeLayout/android.widget.RelativeLayout[2]/android.widget.RelativeLayout/android.widget.RelativeLayout/android.view.ViewGroup/android.widget.FrameLayout/android.webkit.WebView/android.webkit.WebView/android.view.View/android.view.View/android.widget.TextView[5]"));
  public static final Target LBL_PAQUETE_INSTALADO =
      Target.the("Texto paquete activo").located(By.xpath("//*[contains(@text, 'fue instalado')]"));

  public static final Target TXT_USUARIO_BANCOLOMBIA =
      Target.the("Caja de texto usuario Bancolombia")
          .located(By.xpath("//android.view.View[@text=\"Usuario\"]"));

  // Elementos Daviplata

  public static final Target BTN_TIPO_DOCUMENTO_DAVIPLATA =
      Target.the("Botón tipo documento Daviplata")
          .located(By.xpath("//android.widget.Button[@text='Cédula de ciudadanía']"));

  public static final Target BTN_TIPO_DOCUMENTO_DAVIPLATA_2 =
      Target.the("Botón tipo documento Daviplata")
          .located(By.xpath("//android.widget.Button[@text=\"Cedula de extranjería\"]"));

  public static final Target DROPDOWN_MEDIO_PAGO =
      Target.the("Flecha desplegar medios de pago")
          .located(By.xpath("//android.widget.Button[@resource-id=\"dropdownMenuButton\"]"));

  public static final Target BTN_MEDIO_PSE =
      Target.the("Boton Por medio de PSE")
          .located(
              By.xpath(
                  "/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.RelativeLayout/android.widget.RelativeLayout[2]/android.widget.RelativeLayout/android.widget.RelativeLayout/android.view.ViewGroup/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.webkit.WebView/android.webkit.WebView/android.view.View[2]/android.view.View/android.view.View[2]/android.view.View/android.view.View[1]/android.view.View/android.view.View[1]"));
  public static final Target TXT_INGRESAR_NUMERO_DOCUMENTO_DAVIPLATA =
      Target.the("Caja de texto ingresar documento")
          .located(By.xpath("//android.widget.TextView[@text=\"Escribe el número de documento\"]"));

  // Elementos medios de pago paa paga tu factua

  public static final Target DOPDOWN_ENTIDAD_BANCARIA =
      Target.the("Desplegar entidad bancaria")
          .located(
              By.xpath(
                  "//*[@class='android.widget.EditText' and (./preceding-sibling::* | ./following-sibling::*)[@class='android.widget.Image']]"));

  public static final Target TXT_ESCRIBE_CORREO =
      Target.the("Textbox escribe correo electrónico")
          .located(By.className("android.widget.EditText"));

  public static final Target TXT_NUMERO_TARJETA2 =
      Target.the("Campo número de tarjeta")
          .located(
              By.xpath(
                  "/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.view.ViewGroup/android.webkit.WebView/android.webkit.WebView/android.view.View/android.view.View/android.view.View[1]/android.widget.EditText"));
  public static final Target TXT_NOMBRE_TARJETA2 =
      Target.the("Campo nombre tarjeta")
          .located(
              By.xpath(
                  "/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.view.ViewGroup/android.webkit.WebView/android.webkit.WebView/android.view.View/android.view.View/android.view.View[2]/android.widget.EditText"));

  public static final Target DROPDOWN_TIPO_DOCUMENTO2 =
      Target.the("Dropdown tipo documento")
          .located(
              By.xpath(
                  "/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.view.ViewGroup/android.webkit.WebView/android.webkit.WebView/android.view.View/android.view.View/android.view.View[3]/android.view.View[2]"));
  public static final Target TXT_FECHA_VENCIMIENTO =
      Target.the("Campo fecha vencimiento")
          .located(
              By.xpath(
                  "/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.view.ViewGroup/android.webkit.WebView/android.webkit.WebView/android.view.View/android.view.View/android.view.View[5]/android.widget.EditText"));
  public static final Target TXT_CVV2 =
      Target.the("Campo CVV")
          .located(
              By.xpath(
                  "/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.view.ViewGroup/android.webkit.WebView/android.webkit.WebView/android.view.View/android.view.View/android.view.View[6]/android.widget.EditText"));
  public static final Target TXT_EMAIL2 =
      Target.the("Campo Correo electrónico")
          .located(
              By.xpath(
                  "/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.view.ViewGroup/android.webkit.WebView/android.webkit.WebView/android.view.View/android.view.View/android.view.View[7]/android.widget.EditText"));
  public static final Target TXT_TELEFONO2 =
      Target.the("Campo teléfono")
          .located(
              By.xpath(
                  "/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.view.ViewGroup/android.webkit.WebView/android.webkit.WebView/android.view.View/android.view.View/android.view.View[8]/android.widget.EditText"));
  public static final Target TXT_NUMERO_DOCUMENTO2 =
      Target.the("Campo número documento")
          .located(
              By.xpath(
                  "/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.RelativeLayout/android.widget.RelativeLayout[2]/android.widget.RelativeLayout/android.widget.RelativeLayout/android.view.ViewGroup/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.webkit.WebView/android.webkit.WebView/android.view.View/android.view.View/android.view.View/android.view.View/android.view.View/android.view.View[4]/android.widget.EditText"));
  public static final Target TXT_ESCRIBE_CORREO_PSE =
      Target.the("Textbox correo electrónico PSE")
          .located(MobileBy.AndroidUIAutomator("new UiSelector().resourceId(\"pseEmail\")"));

  // Nuevos localizadores para el flujo de PSE y Banco Popular
  public static final Target BTN_OPCION_PSE_MODAL =
      Target.the("Opción PSE en modal de medios de pago")
          .located(By.xpath("//*[contains(@text, 'PSE') and not(contains(@text, 'Débito'))]"));

  public static final Target LST_BANCO_POPULAR =
      Target.the("Banco Popular en lista de bancos")
          .located(By.xpath("//*[contains(@text, 'BANCO POPULAR') or contains(@text, 'Banco Popular')]"));

  public static final Target BTN_PAGAR_PORTAL_PSE =
      Target.the("Botón Pagar en portal PSE")
          .located(By.xpath("//android.widget.Button[@text='Pagar']"));

  public static final Target TXT_NUMERO_DOCUMENTO_BANCO_POPULAR =
      Target.the("Campo número de documento Banco Popular")
          .located(By.xpath("(//android.widget.EditText)[1]"));

  public static final Target TXT_CONTRASENA_BANCO_POPULAR =
      Target.the("Campo contraseña Banco Popular")
          .located(By.xpath("(//android.widget.EditText)[2]"));

  public static final Target BTN_INGRESAR_BANCO_POPULAR =
      Target.the("Botón Ingresar Banco Popular")
          .located(By.xpath("//android.widget.Button[contains(@text, 'Ingresar')]"));

  public static final Target BTN_SELECCIONAR_PSE =
      Target.the("Boton seleccionar PSE en medios de pago")
          .located(
              By.xpath(
                  "//android.view.View[@resource-id=\"__react-content\"]/android.view.View/android.widget.TextView[4]"));

  public static final Target TXT_NUMERO_DOCUMENTO_BANCO_POPULAR_PORTAL =
      Target.the("Campo número de documento Banco Popular Portal")
          .located(
              By.xpath(
                  "//android.view.ViewGroup[@resource-id=\"com.clarocolombia.miclaro:id/griver_layout_refresh\"]/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.webkit.WebView/android.webkit.WebView/android.view.View/android.view.View[1]/android.view.View[2]/android.view.View[1]/android.widget.EditText"));

  public static final Target TXT_CONTRASENA_BANCO_POPULAR_PORTAL =
      Target.the("Campo contraseña Banco Popular Portal")
          .located(
              By.xpath(
                  "//android.view.ViewGroup[@resource-id=\"com.clarocolombia.miclaro:id/griver_layout_refresh\"]/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.webkit.WebView/android.webkit.WebView/android.view.View/android.view.View[1]/android.view.View[2]/android.view.View[1]/android.view.View[6]/android.widget.EditText"));

  public static final Target BTN_INGRESAR_BANCO_POPULAR_LOGIN =
      Target.the("Botón Ingresar Banco Popular Login")
          .located(By.xpath("//android.widget.Button[@resource-id=\"btn_login\"]"));

  public static final Target BTN_AHORA_NO_GOOGLE =
      Target.the("Botón Ahora no en diálogo de Google Password Manager")
          .located(By.xpath("//*[contains(@text, 'Ahora no')]"));

  public static final Target LBL_ERROR_NO_REGISTRADO_BANCO_POPULAR =
      Target.the("Mensaje de error documento no registrado Banco Popular")
          .located(
              By.xpath(
                  "//*[contains(@text, 'no se encuentran registrados') or contains(@text, 'Valida tu información')]"));

  public static final Target BTN_SELECCIONAR_BANCOLOMBIA =
      Target.the("Botón seleccionar Bancolombia en medios de pago")
          .located(By.xpath("//android.widget.TextView[@text=\"Bancolombia\"]"));

  public static final Target BTN_CONTINUAR_BANCOLOMBIA =
      Target.the("Botón Continuar Bancolombia")
          .located(By.xpath("//android.widget.Button[@text=\"Continuar\" or contains(@text, 'Continuar')]"));

  public static final Target TXT_CAMPO_USUARIO_BANCOLOMBIA =
      Target.the("Campo de texto usuario Bancolombia")
          .located(By.xpath("//android.widget.EditText"));

  public static final Target BTN_SELECCIONAR_DAVIPLATA =
      Target.the("Botón seleccionar Daviplata en medios de pago")
          .located(By.xpath("//android.widget.TextView[@text=\"Daviplata\"]"));

  public static final Target TXT_NUMERO_DOCUMENTO_DAVIPLATA =
      Target.the("Campo número de documento Daviplata")
          .located(By.xpath("//android.widget.EditText"));

  public static final Target BTN_CONTINUAR_DAVIPLATA =
      Target.the("Botón Continuar Daviplata")
          .located(By.xpath("//android.widget.Button[@text=\"Continuar\" or contains(@text, 'Continuar')]"));

  public static final Target LBL_CODIGO_DAVIPLATA =
      Target.the("Texto ingresa código de 6 dígitos Daviplata")
          .located(
              By.xpath(
                  "//*[contains(@text, '6 dígitos') or contains(@text, 'DaviPlata envió') or contains(@text, 'Anular transacción')]"));

  public static final Target BTN_ANULAR_TRANSACCION_DAVIPLATA =
      Target.the("Botón Anular transacción Daviplata")
          .located(By.xpath("//*[contains(@text, 'Anular transacción')]"));

  public static final Target BTN_SELECCIONAR_OTROS_MEDIOS =
      Target.the("Botón seleccionar Otros medios de pago")
          .located(By.xpath("//*[contains(@text, 'Otros medios')]"));

  public static final Target BTN_OPCION_PSE_DROPDOWN =
      Target.the("Opción PSE en dropdown medios de pago online")
          .located(By.xpath("//*[contains(@text, 'PSE')]"));

  public static final Target BTN_OPCION_TARJETA_DROPDOWN =
      Target.the("Opción Tarjeta Crédito/Débito en dropdown medios de pago online")
          .located(By.xpath("//*[contains(@text, 'Crédito') or contains(@text, 'Credito')]"));

  public static final Target BTN_OPCION_BANCOLOMBIA_DROPDOWN =
      Target.the("Opción Bancolombia en dropdown medios de pago online")
          .located(By.xpath("//*[contains(@text, 'Bancolombia')]"));

  public static final Target BTN_CONTINUAR_PORTAL_PAGOS =
      Target.the("Botón Continuar en Portal Pagos Claro")
          .located(By.xpath("//android.widget.Button[contains(@text, 'Continuar')]"));

  public static final Target TXT_NUMERO_DOCUMENTO_BANCO_BOGOTA =
      Target.the("Campo número de documento Banco de Bogotá")
          .located(By.xpath("//android.widget.EditText[@resource-id=\"sp-at-input\"]"));

  public static final Target BTN_CONTINUAR_BANCO_BOGOTA =
      Target.the("Botón Continuar Banco de Bogotá")
          .located(By.xpath("//android.widget.Button[@text=\"Continuar\" or contains(@text, 'Continuar')]"));

  public static final Target LBL_MODAL_VERIFICA_DATOS_BANCO_BOGOTA =
      Target.the("Modal Verifica los datos que ingresaste")
          .located(
              By.xpath(
                  "//*[contains(@text, 'Verifica los datos que ingresaste') or contains(@content-desc, 'Verifica los datos que ingresaste')]"));

  public static final Target BTN_REINTENTAR_BANCO_BOGOTA =
      Target.the("Botón Reintentar Banco de Bogotá")
          .located(
              By.xpath(
                  "//*[contains(@text, 'Reintentar') or contains(@content-desc, 'Reintentar')]"));
}
