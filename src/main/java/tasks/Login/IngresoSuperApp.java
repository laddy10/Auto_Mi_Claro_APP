package tasks.Login;

import interactions.Click.ClickElementByText;
import interactions.comunes.Atras;
import interactions.validations.ValidarTextoQueContengaX;
import interactions.validations.ValidateInformationText;
import interactions.wait.WaitElement;
import interactions.wait.WaitFor;
import models.User;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Enter;
import net.serenitybdd.screenplay.questions.Presence;
import net.serenitybdd.screenplay.targets.Target;
import net.serenitybdd.screenplay.waits.WaitUntil;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import utils.AndroidObject;
import utils.EvidenciaUtils;
import utils.TestDataProvider;

import java.time.Duration;
import java.util.List;

import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static net.serenitybdd.screenplay.Tasks.instrumented;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isNotPresent;
import static org.hamcrest.core.IsEqual.equalTo;
import static userinterfaces.LoginPage.*;
import static utils.Constants.*;

public class IngresoSuperApp implements Task {

    private final User user = TestDataProvider.getRealUser();
    private static final String paso = "Login exitoso a la Super App";

    @Override
    public <T extends Actor> void performAs(T actor) {

        if (isVisible(actor, LBL_ENCABEZADO_USUARIO)) {
            String textoVisible = ValidateInformationText.validateInformationText(LBL_ENCABEZADO_USUARIO).answeredBy(actor);
            if (!"¡Hola!".equals(textoVisible)) {
                actor.should(seeThat(ValidateInformationText.validateInformationText(LBL_ENCABEZADO_USUARIO),
                        equalTo(user.getNombreUsuario())));
                EvidenciaUtils.registrarCaptura(paso);
                return;
            }
        }

        // PRIMERA VALIDACIÓN: Usuario ya logueado
        if (isVisible(actor, LBL_ENCABEZADO_USUARIO)) {
            actor.should(seeThat(ValidateInformationText.validateInformationText(LBL_ENCABEZADO_USUARIO),
                    equalTo(user.getNombreUsuario())));
            EvidenciaUtils.registrarCaptura(paso);
            return;
        }

        // SEGUNDA VALIDACIÓN: Sesión cerrada por seguridad
        if (isVisible(actor, LBL_SESION_CERRADA_POR_SEGURIDAD)) {
            clickAceptarSesion(actor);
            iniciarSesion(actor);
            validarLogin(actor);
            EvidenciaUtils.registrarCaptura(paso);
            return;
        }

        // TERCERA VALIDACIÓN: Mensaje de bienvenida
        if (isVisible(actor, LBL_NOS_ALEGRA_TENERTE_DE_VUELTA)) {
            iniciarSesion(actor);
            validarLogin(actor);
            EvidenciaUtils.registrarCaptura(paso);
            return;
        }

        // CUARTA VALIDACIÓN: Botón iniciar sesión visible
        if (isVisible(actor, LBL_INICIAR_SESION)) {
            actor.attemptsTo(Click.on(LBL_INICIAR_SESION));
            iniciarSesion(actor);
            validarLogin(actor);
            EvidenciaUtils.registrarCaptura(paso);
            return;
        }

        // QUINTA VALIDACIÓN: Otros métodos de ingreso con email
        if (isVisible(actor, BTN_OTROS_METODOS_INGRESO) && user.getEmail() != null && !user.getEmail().isEmpty()) {
            loginConEmail(actor);
            EvidenciaUtils.registrarCaptura(paso);
            return;
        }

        // SEXTA VALIDACIÓN: Campo usuario visible con cédula
        if (isVisible(actor, TXT_USERNAME) && user.getCedula() != null && !user.getCedula().isEmpty()) {
            loginConCedula(actor);
            EvidenciaUtils.registrarCaptura(paso);
            return;
        }

        // ÚLTIMO RECURSO: Login desde cero solo si no se cumple ninguna condición anterior
        loginDesdeCeroCompleto(actor);
        EvidenciaUtils.registrarCaptura(paso);
    }

    private <T extends Actor> void loginConEmail(T actor) {
        actor.attemptsTo(
                ClickElementByText.clickElementByText(OTROS_METODOS_DE_INGRESO),
                ClickElementByText.clickElementByText(CORREO_ELECTRONICO),
                WaitElement.isEnable(TXT_USERNAME),
                Enter.theValue(user.getEmail()).into(TXT_USERNAME),
                ClickElementByText.clickElementByText(CONTINUAR),
                Enter.theValue(user.getPassword()).into(TXT_PASSWORD),
                ClickElementByText.clickElementByText(CONTINUAR),
                WaitUntil.the(LOADING_ESPERA_UN_MOMENTO, isNotPresent()).forNoMoreThan(30).seconds()
        );
        validarLogin(actor);
    }

    private <T extends Actor> void loginConCedula(T actor) {
        actor.attemptsTo(
                Enter.theValue(user.getCedula()).into(TXT_USERNAME),
                ClickElementByText.clickElementByText(CONTINUAR),
                Enter.theValue(user.getPassword()).into(TXT_PASSWORD),
                ClickElementByText.clickElementByText(CONTINUAR),
                WaitUntil.the(LOADING_ESPERA_UN_MOMENTO, isNotPresent()).forNoMoreThan(30).seconds()
        );
        validarLogin(actor);
    }

    private <T extends Actor> void loginDesdeCeroCompleto(T actor) {
        aceptarPermisosIniciales(actor);
        loginDesdeCero(actor);
        validarLogin(actor);
    }

    private <T extends Actor> void iniciarSesion(T actor) {
        actor.attemptsTo(
                ClickElementByText.clickElementByText(CONTINUAR),
                Enter.theValue(user.getPassword()).into(TXT_PASSWORD),
                ClickElementByText.clickElementByText(CONTINUAR),
                WaitUntil.the(LOADING_ESPERA_UN_MOMENTO, isNotPresent()).forNoMoreThan(30).seconds()
        );
    }

    private <T extends Actor> void clickAceptarSesion(T actor) {
        if (isVisible(actor, BTN_ACEPTAR)) {
            actor.attemptsTo(ClickElementByText.clickElementByText(ACEPTAR));
        } else {
            actor.attemptsTo(ClickElementByText.clickElementByText(ACEPTAR_2));
        }
    }

    // Resto de métodos sin cambios...
    private <T extends Actor> void aceptarPermisosIniciales(T actor) {
        clickSiExiste(actor, BTN_PERMISO_UBICACION, MIENTRAS_APP_ESTA_EN_USO);
        clickSiExiste(actor, BTN_ACEPTAR_PERMISO, ACEPTAR_2);
        clickSiExiste(actor, SMS_PERMISO_LLAMADAS, NO_PERMITIR);
        clickSiExiste(actor, SMS_PERMISO_NOTIFICACIONES, NO_PERMITIR);
        clickSiExiste(actor, BTN_OMITIR, OMITIR);
        clickSiExisteCheckboxYContinuar(actor, LBL_BIENVENIDA, CHECK_TC, CONTINUAR);
        clickSiExiste(actor, TXT_AUTORIZACION_VELOCIDAD, ACEPTAR_2);
    }

    private <T extends Actor> void loginDesdeCero(T actor) {
        actor.attemptsTo(ClickElementByText.clickElementByText(INICIAR_SESION));
        ValidarTextoQueContengaX.elTextoContiene(VERSION);

        if (user.getEmail() != null && !user.getEmail().isEmpty()) {
            actor.attemptsTo(
                    ClickElementByText.clickElementByText(OTROS_METODOS_DE_INGRESO),
                    ClickElementByText.clickElementByText(CORREO_ELECTRONICO),
                    WaitElement.isEnable(TXT_USERNAME),
                    Enter.theValue(user.getEmail()).into(TXT_USERNAME)
            );
        } else {
            actor.attemptsTo(
                    WaitElement.isEnable(TXT_USERNAME),
                    Enter.theValue(user.getCedula()).into(TXT_USERNAME)
            );
        }

        actor.attemptsTo(
                ClickElementByText.clickElementByText(CONTINUAR),
                Enter.theValue(user.getPassword()).into(TXT_PASSWORD),
                ClickElementByText.clickElementByText(CONTINUAR),
                WaitUntil.the(LOADING_ESPERA_UN_MOMENTO, isNotPresent()).forNoMoreThan(30).seconds()
        );
    }

    private <T extends Actor> void validarLogin(T actor) {
        // VALIDACIÓN TEMPRANA: Si ya está logueado, salir inmediatamente
        if (isUserAlreadyLoggedIn(actor)) {
            return;
        }

        // Solo ejecutar validaciones si NO está logueado aún
        if (isVisibleFast(actor, LBL_TERMINOS_Y_CONDICIONES)) {
            actor.attemptsTo(
                    Click.on(CHECK_TERMINOS_Y_CONDICIONES),
                    ClickElementByText.clickElementByText(CONTINUAR)
            );
        }

        if (isVisibleFast(actor, LBL_SESION_ABIERTA)) {
            actor.attemptsTo(ClickElementByText.clickElementByText(CONTINUAR), WaitFor.aTime(6000));
        }

        if (isVisibleFast(actor, LBL_INGRESO_BIOMETRICO)) {
            actor.attemptsTo(ClickElementByText.clickElementByText("En otro momento"));
        }

        // Verificar de nuevo antes de continuar
        if (isUserAlreadyLoggedIn(actor)) {
            return;
        }

        clickSiExisteFast(actor, SMS_PERMISO_NOTIFICACIONES, NO_PERMITIR);

        if (isVisibleFast(actor, TXT_AUTORIZACION_VELOCIDAD)) {
            actor.attemptsTo(WaitFor.aTime(1000), ClickElementByText.clickElementByText(ACEPTAR));
        }

        if (isVisibleFast(actor, TXT_AUTORIZACION_VELOCIDAD_2)) {
            actor.attemptsTo(
                    WaitFor.aTime(1000),
                    ClickElementByText.clickElementByText(ACEPTAR),
                    Atras.irAtras());
        }

        // Validación final del login
        actor.should(seeThat(ValidateInformationText.validateInformationText(LBL_ENCABEZADO_USUARIO),
                equalTo(user.getNombreUsuario())));
    }

    // Método para verificar si el usuario ya está logueado (MUY RÁPIDO)
    private <T extends Actor> boolean isUserAlreadyLoggedIn(T actor) {
        try {
            // Usar xpath simple y directo para el nombre de usuario
            List<WebElement> userElements = AndroidObject.androidDriver(actor)
                    .findElements(By.xpath("//android.widget.TextView[contains(@text, '" + user.getNombreUsuario() + "')]"));

            return !userElements.isEmpty() && userElements.get(0).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    // Método ultra rápido para verificar visibilidad (sin waits)
    private <T extends Actor> boolean isVisibleFast(T actor, Target element) {
        try {
            return element.resolveFor(actor).withTimeoutOf(Duration.ofMillis(500)).isPresent();
        } catch (Exception e) {
            return false;
        }
    }

    // Método optimizado para clic condicional ultra rápido
    private <T extends Actor> void clickSiExisteFast(T actor, Target elemento, String texto) {
        if (isVisibleFast(actor, elemento)) {
            actor.attemptsTo(ClickElementByText.clickElementByText(texto));
        }
        // Sin waits innecesarios
    }

    private <T extends Actor> boolean isVisible(T actor, Target element) {
        return Presence.of(element).answeredBy(actor);
    }

    private <T extends Actor> void clickSiExiste(T actor, Target elemento, String texto) {
        if (isVisible(actor, elemento)) {
            actor.attemptsTo(ClickElementByText.clickElementByText(texto));
        } else {
            actor.attemptsTo(WaitFor.aTime(1000));
        }
    }

    private <T extends Actor> void clickSiExisteCheckboxYContinuar(T actor, Target condicion, Target checkbox, String botonTexto) {
        if (isVisible(actor, condicion)) {
            actor.attemptsTo(
                    Click.on(checkbox),
                    ClickElementByText.clickElementByText(botonTexto)
            );
        }
    }

    public static Performable ingresoSuperApp() {
        return instrumented(IngresoSuperApp.class);
    }
}