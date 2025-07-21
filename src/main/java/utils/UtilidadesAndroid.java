package utils;

import io.appium.java_client.android.AndroidDriver;
import net.thucydides.core.webdriver.SerenityWebdriverManager;
import net.thucydides.core.webdriver.WebDriverFacade;
import org.openqa.selenium.WebDriver;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UtilidadesAndroid {

    public static void abrirLinkEnNavegador(String url) {
        WebDriver webdriver = SerenityWebdriverManager.inThisTestThread().getCurrentDriver();

        if (webdriver instanceof WebDriverFacade) {
            WebDriverFacade facade = (WebDriverFacade) webdriver;
            WebDriver proxiedDriver = facade.getProxiedDriver();

            if (proxiedDriver instanceof AndroidDriver) {
                AndroidDriver driver = (AndroidDriver) proxiedDriver;

                Map<String, Object> intentArgs = new HashMap<>();
                intentArgs.put("command", "am");
                intentArgs.put("args", List.of(
                        "start",
                        "-a", "android.intent.action.VIEW",
                        "-d", url
                ));

                driver.executeScript("mobile: shell", intentArgs);
            } else {
                throw new RuntimeException("El ProxiedDriver no es una instancia de AndroidDriver");
            }
        } else {
            throw new RuntimeException("El WebDriver no es una instancia de WebDriverFacade");
        }
    }
}