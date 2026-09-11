package stepDefinitions;

import static net.serenitybdd.screenplay.actors.OnStage.theActorInTheSpotlight;
import static utils.Constants.*;

import cucumber.api.java.ast.Y;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.es.Entonces;
import interactions.Click.ClickTextoQueContengaX;
import interactions.Scroll.ScrollHastaTexto;
import models.User;
import tasks.ExplorayCompra.ClaroPay;
import utils.EvidenciaUtils;
import utils.TestDataProvider;

public class ExplorayCompraDefinitions {

  private final User user = TestDataProvider.getRealUser();

  @Y("^INGRESA AL MENU EXPLORA Y COMPRA$")
  public void ingresaAlMenuExploraYCompra() {
    theActorInTheSpotlight()
        .attemptsTo(
            ScrollHastaTexto.conTexto(CLARO_PAY), ClickTextoQueContengaX.elTextoContiene(VER_MAS));

    EvidenciaUtils.registrarCaptura("Menu Explora y compra");
  }

  @Entonces("^DIRECCIONAMIENTO A CLARO PAY$")
  public void direccionamientoAClaroPay() {
    theActorInTheSpotlight().attemptsTo(ClaroPay.validarDireccionamiento());
  }
}
