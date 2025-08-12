package stepDefinitions;

import static net.serenitybdd.screenplay.actors.OnStage.theActorInTheSpotlight;
import static utils.Constants.*;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import interactions.Click.ClickTextoQueContengaX;
import interactions.Scroll.ScrollHastaTexto;
import models.User;
import tasks.ExplorayCompra.ClaroPay;
import utils.EvidenciaUtils;
import utils.TestDataProvider;

public class ExplorayCompraDefinitions {

  private final User user = TestDataProvider.getRealUser();

  @And("^INGRESA AL MENU EXPLORA Y COMPRA$")
  public void ingresaAlMenuExploraYCompra() {
    theActorInTheSpotlight()
        .attemptsTo(
            ScrollHastaTexto.conTexto(CLARO_PAY), ClickTextoQueContengaX.elTextoContiene(VER_MAS));

    EvidenciaUtils.registrarCaptura("Menu Explora y compra");
  }

  @Then("^DIRECCIONAMIENTO A CLARO PAY$")
  public void direccionamientoAClaroPay() {
    theActorInTheSpotlight().attemptsTo(ClaroPay.validarDireccionamiento());
  }
}
