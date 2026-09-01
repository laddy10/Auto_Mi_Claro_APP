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

  //Elementos medios de pago paa paga tu factua

  public static final Target DOPDOWN_ENTIDAD_BANCARIA =
          Target.the("Desplegar entidad bancaria")
                  .located(By.xpath("//*[@class='android.widget.EditText' and (./preceding-sibling::* | ./following-sibling::*)[@class='android.widget.Image']]"));

  public static final Target TXT_ESCRIBE_CORREO =
        Target.the("Textbox escribe correo electrónico")
                .located(By.className("android.widget.EditText"));

  public static final Target TXT_NUMERO_TARJETA2 =
          Target.the("Campo número de tarjeta")
                  .located(By.xpath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.view.ViewGroup/android.webkit.WebView/android.webkit.WebView/android.view.View/android.view.View/android.view.View[1]/android.widget.EditText"));
  public static final Target TXT_NOMBRE_TARJETA2 =
          Target.the("Campo nombre tarjeta")
                  .located(By.xpath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.view.ViewGroup/android.webkit.WebView/android.webkit.WebView/android.view.View/android.view.View/android.view.View[2]/android.widget.EditText"));

  public static final Target DROPDOWN_TIPO_DOCUMENTO2 =
          Target.the("Dropdown tipo documento")
                  .located(By.xpath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.view.ViewGroup/android.webkit.WebView/android.webkit.WebView/android.view.View/android.view.View/android.view.View[3]/android.view.View[2]"));
  public static final Target TXT_FECHA_VENCIMIENTO =
          Target.the("Campo fecha vencimiento")
                  .located(By.xpath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.view.ViewGroup/android.webkit.WebView/android.webkit.WebView/android.view.View/android.view.View/android.view.View[5]/android.widget.EditText"));
  public static final Target TXT_CVV2 =
          Target.the("Campo CVV")
                  .located(By.xpath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.view.ViewGroup/android.webkit.WebView/android.webkit.WebView/android.view.View/android.view.View/android.view.View[6]/android.widget.EditText"));
  public static final Target TXT_EMAIL2 =
          Target.the("Campo Correo electrónico")
                  .located(By.xpath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.view.ViewGroup/android.webkit.WebView/android.webkit.WebView/android.view.View/android.view.View/android.view.View[7]/android.widget.EditText"));
  public static final Target TXT_TELEFONO2 =
          Target.the("Campo teléfono")
                  .located(By.xpath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.view.ViewGroup/android.webkit.WebView/android.webkit.WebView/android.view.View/android.view.View/android.view.View[8]/android.widget.EditText"));
  public static final Target TXT_NUMERO_DOCUMENTO2 =
          Target.the("Campo número documento")
                  .located(By.xpath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.RelativeLayout/android.widget.RelativeLayout[2]/android.widget.RelativeLayout/android.widget.RelativeLayout/android.view.ViewGroup/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.webkit.WebView/android.webkit.WebView/android.view.View/android.view.View/android.view.View/android.view.View/android.view.View/android.view.View[4]/android.widget.EditText"));
  public static final Target TXT_ESCRIBE_CORREO_PSE =
          Target.the("Textbox correo electrónico PSE")
                  .located(MobileBy.AndroidUIAutomator(
                          "new UiSelector().resourceId(\"pseEmail\")"));
}