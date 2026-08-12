package stepDefinitions;

import static net.serenitybdd.screenplay.actors.OnStage.theActorCalled;
import static net.serenitybdd.screenplay.actors.OnStage.theActorInTheSpotlight;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isNotPresent;
import static userinterfaces.LoginPage.*;
import static utils.Constants.*;

import cucumber.api.Scenario;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import interactions.wait.WaitFor;
import models.User;
import net.serenitybdd.screenplay.actors.OnStage;
import net.serenitybdd.screenplay.actors.OnlineCast;
import net.serenitybdd.screenplay.waits.WaitUntil;
import tasks.Login.*;
import utils.*;

public class LoginDefinitions {

  private final User user = TestDataProvider.getRealUser();

  @Before
  public void initScenario(Scenario scenario) {
    OnStage.setTheStage(new OnlineCast());
    WordAppium.inicializarPlantillaReporte();
    EvidenciaUtils.reiniciarContador(); // Reinicia el conteo de pasos para este escenario
  }

  @Given("EL USUARIO ABRE LA SUPER APP")
  public void abrirSuperApp() {
    theActorCalled("actor")
            .attemptsTo(
                    WaitUntil.the(LOADING_SPLASH, isNotPresent()),
                    WaitUntil.the(LOADING_ESPERA_UN_MOMENTO, isNotPresent()).forNoMoreThan(40).seconds(),
                    WaitFor.aTime(2000));
  }

 /*@When("^REALIZA EL INGRESO$")
  public void ingresoSuperApp() {
    theActorInTheSpotlight().attemptsTo(IngresoSuperApp.ingresoSuperApp());
  }*/

  @Then("^VERIFICA VERSION DE LA SUPER APP$")
  public void verificaVersion() {
    theActorInTheSpotlight().attemptsTo(VersionSuperApp.validarVersion());
  }

  @When("^REALIZA EL INGRESO CON CEDULA$")
  public void ingresoConCedula() {
    theActorInTheSpotlight().attemptsTo(LoginConCedula.conCedula());
  }

  @When("^REALIZA EL INGRESO CON CORREO$")
  public void ingresoCorreo() {
    theActorInTheSpotlight().attemptsTo(LoginOrquestado.con(LoginOrquestado.Metodo.CORREO));
  }

  @When("^REALIZA EL INGRESO CON DOCUMENTO$")
  public void ingresoDocumento() {
    theActorInTheSpotlight().attemptsTo(LoginOrquestado.con(LoginOrquestado.Metodo.DOCUMENTO));
  }

  @When("^REALIZA EL INGRESO CON PIN$")
  public void ingresoPIN() {
    theActorInTheSpotlight().attemptsTo(LoginOrquestado.con(LoginOrquestado.Metodo.PIN));
  }

  /* @When("verifico los siguientes localizadores o textos {string} contra el page source {string}")
  public void verificoLosLocalizadoresContraElPageSource(String locatorsOrTexts, String xmlFileName) {
      // Leer maxLocatorsToSend desde serenity.properties si prefieres (por ejemplo 100)
      int maxLocatorsToSend = 100; // ajustable o configurable

      OnStage.theActorInTheSpotlight().attemptsTo(
              ValidateLocatorsWithOllama.using(xmlFileName, locatorsOrTexts, maxLocatorsToSend)
      );
  }*/

// STEP DEFINITIONS para src/test/java/stepDefinitions/LoginDefinitions.java  (Cucumber antiguo -> regex)
//
// Imports a asegurar:
//   import utils.CuentaManager;
//   import tasks.Login.GestionCuenta;
//   (theActorCalled ya se usa en abrirSuperApp)

  // (Opcional) Selecciona la cuenta activa sin tocar la UI. Alternativa al tag @cuenta_<id>.
  @When("^EL USUARIO CAMBIA A LA CUENTA \"(.*)\"$")
  public void elUsuarioCambiaALaCuenta(String id) {
    CuentaManager.activarCuenta(id);
  }

  // Step principal: decide segun la cuenta del escenario (tag @cuenta_<id> o el step de arriba):
//  - secundaria  -> reinicia la app y entra
//  - principal y ya logueada como principal -> continua (sin reinicio)
//  - principal y logueada como otra -> reinicia y entra con principal
//  - principal y sin sesion -> inicia sesion con principal (sin reinicio)
  @When("^EL USUARIO INICIA SESION SEGUN CUENTA$")
  public void elUsuarioIniciaSesionSegunCuenta() {
    theActorCalled("actor").attemptsTo(GestionCuenta.segunCuenta());
  }

  // ─────────────────────────────────────────────────────────────────────────────
// RECOMENDADO: para que TODOS los casos usen esta logica sin cambiar cada feature,
// reemplaza el CUERPO de tu step de ingreso actual por la llamada al orquestador.
// Ejemplo (ajusta al texto real de tu step):
//
  @When("^REALIZA EL INGRESO$")
  public void realizaElIngreso() {
    theActorCalled("actor").attemptsTo(GestionCuenta.segunCuenta());
  }

// Asi, los escenarios de principal NO se reinician (rapidos) y los marcados con
// @cuenta_secundaria si se reinician para entrar con la secundaria.
// ─────────────────────────────────────────────────────────────────────────────
}