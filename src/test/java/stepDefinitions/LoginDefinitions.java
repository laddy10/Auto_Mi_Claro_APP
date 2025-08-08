package stepDefinitions;

import interactions.wait.WaitFor;
import interactions.wait.WaitForResponse;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import models.User;
import net.serenitybdd.screenplay.actors.OnStage;
import net.serenitybdd.screenplay.actors.OnlineCast;
import net.serenitybdd.screenplay.waits.WaitUntil;
import tasks.Login.IngresoSuperApp;
import tasks.Login.LoginConCedula;
import tasks.Login.VersionSuperApp;
import utils.EvidenciaUtils;
import utils.TestDataProvider;
import utils.WordAppium;

import static net.serenitybdd.screenplay.actors.OnStage.theActorCalled;
import static net.serenitybdd.screenplay.actors.OnStage.theActorInTheSpotlight;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isNotPresent;
import static userinterfaces.LoginPage.*;
import static utils.Constants.*;

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
                        WaitFor.aTime(2000)
                );
    }

    @When("^REALIZA EL INGRESO$")
    public void ingresoSuperApp() {
        theActorInTheSpotlight().attemptsTo(
                IngresoSuperApp.ingresoSuperApp()
        );
    }

    @Then("^VERIFICA VERSION DE LA SUPER APP$")
    public void verificaVersion() {
        theActorInTheSpotlight().attemptsTo(
                VersionSuperApp.validarVersion()
        );
    }

    @When("^REALIZA EL INGRESO CON CEDULA$")
    public void ingresoConCedula() {
        theActorInTheSpotlight().attemptsTo(
                LoginConCedula.conCedula()
        );
    }

}