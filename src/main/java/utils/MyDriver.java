package utils;

import io.appium.java_client.android.AndroidDriver;
import net.thucydides.core.webdriver.DriverSource;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.IOException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import static userinterfaces.LoginPage.AppConstants.WHATSAPP_ACTIVITY;
import static userinterfaces.LoginPage.AppConstants.WHATSAPP_PACKAGE;


public class MyDriver implements DriverSource {

    private static AndroidDriver driver;


    public static AndroidDriver getDriver() {
        return driver;
    }


    @Override
    public AndroidDriver newDriver() {
        try {
            ChromeOptions options = new ChromeOptions();
            options.addArguments("test-type");
            DesiredCapabilities capabilities = new DesiredCapabilities();
            capabilities.setCapability("appActivity", WHATSAPP_ACTIVITY);
            capabilities.setCapability("appPackage", WHATSAPP_PACKAGE);
            capabilities.setCapability(
                    "chromedriverExecutable", "src/test/resources/webdriver/windows/chromedriver.exe");


            capabilities.setCapability("noReset", true);  // No borra datos, mantiene la sesión
            capabilities.setCapability("fullReset", false);  // Evita reiniciar la app


            capabilities.setCapability("automationName", "UiAutomator2");
            capabilities.setCapability("autoGrantPermissions", "true");
            capabilities.setCapability("reset", "false");
            capabilities.setCapability("noReset", "false");
            capabilities.setCapability("autoDismissAlerts", "true");

            driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
            driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
            return driver;
        } catch (IOException e) {
            throw new Error(e);
        }
    }

    @Override
    public boolean takesScreenshots() {
        return true;
    }
}