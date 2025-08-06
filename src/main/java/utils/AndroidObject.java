package utils;

import exceptions.Excepciones;
import interactions.Click.ClickElementByText;
import interactions.wait.WaitFor;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import net.serenitybdd.core.Serenity;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.questions.Presence;
import net.serenitybdd.screenplay.targets.Target;
import net.thucydides.core.webdriver.WebDriverFacade;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;


public class AndroidObject extends Excepciones {

    public void HideKeyboard(Actor actor) {
        androidDriver(actor).hideKeyboard();
    }



    //SCROLL
    public void SwipeToElement(Actor actor, String label) {
        androidDriver(actor).findElement(
                        AppiumBy.androidUIAutomator(
                                "new UiScrollable(new UiSelector().scrollable(true)).scrollIntoView(" + "new UiSelector().text(\""
                                        + label + "\"));"))
                .click();
    }


    public void UnScrollArribaInicio(Actor actor) {
        try {
            androidDriver(actor).findElement(
                    AppiumBy.androidUIAutomator(
                            "new UiScrollable(new UiSelector().resourceIdMatches(\"android:id/list\").scrollable(true)).scrollBackward()"));
        } catch (Exception e) {
            e.printStackTrace(); // Agrega esto para ver si hay algún error
        }
    }

    public void UnScrollAbajo(Actor actor) {
        try {
            androidDriver(actor).findElement(
                    AppiumBy.androidUIAutomator(
                            "new UiScrollable(new UiSelector().scrollable(true)).scrollForward()"));
        } catch (Exception e) {
        }
    }

    public static void scrollToText(Actor actor, String texto) {
        try {
            androidDriver(actor).findElement(
                    AppiumBy.androidUIAutomator(
                            "new UiScrollable(new UiSelector().scrollable(true))" +
                                    ".scrollIntoView(new UiSelector().textContains(\"" + texto + "\"))"
                    )
            );
        } catch (Exception e) {
            throw new RuntimeException("No se encontró el texto al hacer scroll: " + texto, e);
        }
    }



    public static void swipeVertical(Actor actor, double inicioRatio, double finRatio, double duracionSegs) {
        Dimension dimension = androidDriver(actor).manage().window().getSize();
        int ancho = dimension.width / 2; // El ancho siempre será el centro de la pantalla
        int inicioY = (int) (dimension.height * inicioRatio);
        int finY = (int) (dimension.height * finRatio);
        new TouchAction<>(androidDriver(actor))
                .press(PointOption.point(ancho, inicioY))
                .waitAction(WaitOptions.waitOptions(Duration.ofSeconds((long) duracionSegs)))
                .moveTo(PointOption.point(ancho, finY))
                .release()
                .perform();
    }

    protected static boolean estaCercaDelCentro(AndroidDriver driver, WebElement elemento) {
        int screenHeight = driver.manage().window().getSize().getHeight();
        int elementoY = elemento.getLocation().getY();

        int margenSuperior = (int) (screenHeight * 0.3);
        int margenInferior = (int) (screenHeight * 0.7);

        return elementoY > margenSuperior && elementoY < margenInferior;
    }

    public static void centrarElementoSuavemente(Actor actor, WebElement elemento) {
        int screenHeight = androidDriver(actor).manage().window().getSize().getHeight();
        int elementoY = elemento.getLocation().getY();

        int movimiento = (screenHeight / 2) - elementoY;

        System.out.println("📌 Ajustando texto suavemente: " + movimiento + " píxeles");

        if (Math.abs(movimiento) > 20) { // Evitamos movimientos muy pequeños
            swipeVertical(actor, 0.5, 0.5 + (movimiento / (double) screenHeight), 0.3);
        }
    }


    public static void scrollCorto2(Actor actor, String textoOpcional) {
        int intentosMaximos = 7;

        try {
            AndroidDriver driver
                    = androidDriver(actor);

            // 1️⃣ Primera verificación sin hacer scroll
            if (textoOpcional != null && !textoOpcional.isEmpty()) {
                List<WebElement> elementos =
                        driver.findElements(AppiumBy.androidUIAutomator(
                                "new UiSelector().textContains(\"" + textoOpcional + "\")"));

                for (WebElement elemento : elementos) {
                    if (elemento.isDisplayed()) {
                        System.out.println("✅ Texto visible sin scroll: " + textoOpcional);
                        return;
                    }
                }
            }

            // 2️⃣ Scroll progresivo hasta encontrar el texto
            for (int intento = 1; intento <= intentosMaximos; intento++) {
                List<WebElement> elementos =
                        driver.findElements(AppiumBy.androidUIAutomator(
                                "new UiSelector().textContains(\"" + textoOpcional + "\")"));

                for (WebElement elemento : elementos) {
                    if (elemento.isDisplayed()) {
                        if (estaCercaDelCentro(driver, elemento)) {
                            System.out.println("✅ Texto ya centrado: " + textoOpcional);
                            return;
                        }

                        centrarElementoSuavemente(actor, elemento);
                        System.out.println("✅ Texto encontrado y ajustado: " + textoOpcional);
                        return;
                    }
                }

                // 3️⃣ Scroll corto si no se encuentra aún
                System.out.println("🔄 Scroll intento #" + intento);
                swipeVertical(actor, 0.7, 0.5, 0.3);
                Thread.sleep(700);
            }

            System.out.println("❌ Texto no encontrado tras " + intentosMaximos + " intentos: " + textoOpcional);

        } catch (Exception e) {
            System.out.println("⛔ Error en scrollCorto2: " + e.getMessage());
            e.printStackTrace();
        }
    }





    //VALIDACIONES
    public boolean validarTexto(Actor actor, String text) {
        try {
            return androidDriver(actor)
                    .findElement(AppiumBy.androidUIAutomator("new UiSelector().text(\"" + text + "\")"))
                    .isDisplayed();
        } catch (NoSuchElementException e) {
            System.out.println("Texto no encontrado: " + text);
            return false;
        }
    }

    protected <T extends Actor> boolean isVisible(T actor, Target element) {
        return Presence.of(element).answeredBy(actor);
    }



    public void ElTextoContiene(Actor actor, String text) {
        androidDriver(actor).findElement(
                        AppiumBy.androidUIAutomator("new UiSelector().textContains(\"" + text + "\")"))
                .isDisplayed();
    }

    public void ValidarTexto(Actor actor, String text) {
        androidDriver(actor)
                .findElement((
                        AppiumBy.androidUIAutomator("new UiSelector().text(\"" + text + "\")")))
                .isDisplayed();
    }


    //CLICK
    public void ClickByText(Actor actor, String text) {
        actor.attemptsTo(WaitFor.aTime(1000));
        androidDriver(actor).findElement(
                        AppiumBy.androidUIAutomator("new UiSelector().textMatches(\"(?i)^" + text + "$\")"))
                .click();
    }


    public void ClickElTextoContiene(Actor actor, String text) {
        try {
            androidDriver(actor).findElement(
                            AppiumBy.androidUIAutomator("new UiSelector().textContains(\"" + text + "\")"))
                    .click();
        } catch (Exception e) {
            ExClickElTextoContiene(actor, text);
        }
    }

    public void Atras(Actor actor) {
        androidDriver(actor).navigate().back();
    }


    public static void digitarDesdeTeclado(String numeros) {
        AndroidDriver driver = (AndroidDriver) Serenity.getDriver();

        for (char numero : numeros.toCharArray()) {
            switch (numero) {
                case '0':
                    driver.pressKey(new KeyEvent(AndroidKey.DIGIT_0));
                    break;
                case '1':
                    driver.pressKey(new KeyEvent(AndroidKey.DIGIT_1));
                    break;
                case '2':
                    driver.pressKey(new KeyEvent(AndroidKey.DIGIT_2));
                    break;
                case '3':
                    driver.pressKey(new KeyEvent(AndroidKey.DIGIT_3));
                    break;
                case '4':
                    driver.pressKey(new KeyEvent(AndroidKey.DIGIT_4));
                    break;
                case '5':
                    driver.pressKey(new KeyEvent(AndroidKey.DIGIT_5));
                    break;
                case '6':
                    driver.pressKey(new KeyEvent(AndroidKey.DIGIT_6));
                    break;
                case '7':
                    driver.pressKey(new KeyEvent(AndroidKey.DIGIT_7));
                    break;
                case '8':
                    driver.pressKey(new KeyEvent(AndroidKey.DIGIT_8));
                    break;
                case '9':
                    driver.pressKey(new KeyEvent(AndroidKey.DIGIT_9));
                    break;
                default:
                    throw new IllegalArgumentException("Caracter no válido: " + numero);
            }
        }
    }


    String texto = "";

    public ArrayList<Character> LeerMensaje(Actor actor) {
        // Abrir notificaciones
        androidDriver(actor).openNotifications();
        actor.attemptsTo(WaitFor.aTime(3000));  // Esperar más tiempo por si el mensaje demora

        // Posibles textos que pueden venir en el SMS
        String[] posiblesTextos = {
                "Codigo", "Código", "verificacion"
        };

        // Buscar el texto en las notificaciones
        for (String clave : posiblesTextos) {
            try {
                texto = androidDriver(actor).findElement(
                        AppiumBy.androidUIAutomator("new UiSelector().textContains(\"" + clave + "\")"))
                        .getText();
                if (texto != null && !texto.isEmpty()) {
                    System.out.println("📩 Mensaje encontrado: " + texto);
                    break;
                }
            } catch (Exception e) {
                // Continuar con el siguiente término si no encuentra este
            }
        }

        // Extraer solo los dígitos del mensaje
        ArrayList<Character> lista = new ArrayList<>();
        if (texto != null && !texto.isEmpty()) {
            for (int i = 0; i < texto.length(); i++) {
                if (Character.isDigit(texto.charAt(i))) {
                    lista.add(texto.charAt(i));
                }
            }
            System.out.println("🔐 Código detectado: " + lista.toString());
        } else {
            System.out.println("⚠️ No se encontró ningún mensaje con código.");
        }

        return lista;
    }






    public AndroidDriver getAndroidDriver(Actor actor) {
        return androidDriver(actor);
    }


    @SuppressWarnings("unchecked")
    public static AndroidDriver androidDriver(Actor actor) {
        return (AndroidDriver) ((WebDriverFacade) getDriver(actor)).getProxiedDriver();
    }


    private static WebDriverFacade getDriver(Actor actor) {
        return ((WebDriverFacade) BrowseTheWeb.as(actor).getDriver());
    }

    public TouchAction withAction(Actor actor) {
        return new TouchAction(androidDriver(actor));
    }

    public void SwitchtoFrame(Actor actor, int id) {
        androidDriver(actor).switchTo().frame(id);
    }
}