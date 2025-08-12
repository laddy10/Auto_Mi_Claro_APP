package userinterfaces;

import net.serenitybdd.screenplay.targets.Target;
import org.openqa.selenium.By;

public class MediosPagoPage {

    // Campos del formulario
    public static final Target TXT_NUMERO_TARJETA = Target.the("Campo número de tarjeta")
            .located(By.xpath("//android.widget.EditText[@resource-id=\"numeroTarjeta\"]"));
    public static final Target TXT_NOMBRE_TARJETA = Target.the("Campo nombre tarjeta")
            .located(By.xpath("//android.widget.EditText[@resource-id=\"NOMBRE_TARJETA\"]"));

    public static final Target DROPDOWN_TIPO_DOCUMENTO = Target.the("Dropdown tipo documento")
            .located(By.xpath("//android.view.View[@resource-id=\"TIPO_DOCUMENTO\"]"));

    public static final Target TXT_NUMERO_DOCUMENTO = Target.the("Campo número documento")
            .located(By.xpath("//android.widget.EditText[@resource-id=\"NUMERO_DOCUMENTO\"]"));

    public static final Target TXT_FECHA_EXPIRACION = Target.the("Campo fecha expiración")
            .located(By.xpath("//android.widget.EditText[@resource-id=\"mesAnoFechaVencimiento\"]"));

    public static final Target TXT_CVV = Target.the("Campo CVV")
            .located(By.xpath("//android.widget.EditText[@resource-id=\"inputCVV\"]"));

    public static final Target TXT_EMAIL = Target.the("Campo email")
            .located(By.xpath("//android.widget.EditText[@resource-id=\"EMAIL\"]"));

    public static final Target TXT_TELEFONO = Target.the("Campo teléfono")
            .located(By.xpath("//android.widget.EditText[@resource-id=\"TELEFONO\"]"));

    // Botones de cuotas
    public static final Target BTN_MAS_CUOTAS = Target.the("Botón más cuotas")
            .located(By.xpath("//android.widget.Button[@text='+']"));

    public static final Target BTN_MENOS_CUOTAS = Target.the("Botón menos cuotas")
            .located(By.xpath("//android.widget.Button[@text='-']"));


    public static final Target DROPDOWN_BANCO = Target.the("Dropdown banco")
            .located(By.xpath("//android.view.View[@resource-id='banco']"));

    public static final Target DROPDOWN_TIPO_CLIENTE = Target.the("Dropdown tipo cliente")
            .located(By.xpath("//android.view.View[@resource-id='tipoCliente']"));

    public static final Target BTN_PSE_RECARGAS =
            Target.the("Boton PSE de recargas y paquetes")
                    .located(By.xpath("//android.view.View[@resource-id=\"__react-content\"]/android.view.View/android.widget.TextView[4]"));
    public static final Target LBL_PAQUETE_INSTALADO =
            Target.the("Texto paquete activo")
                    .located(By.xpath("//*[contains(@text, 'fue instalado')]"));

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
            Target.the("Bton Por medio de PSE")
                    .located(By.xpath("//android.view.View[@text=\"Medios de pago online\"]/android.view.View[1]"));

}