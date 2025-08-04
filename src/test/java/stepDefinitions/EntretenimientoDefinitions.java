package stepDefinitions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import tasks.Entretenimiento.*;

import static net.serenitybdd.screenplay.actors.OnStage.theActorInTheSpotlight;

/**
 * Step Definitions para el módulo Entretenimiento
 * Mantiene la consistencia con el estilo existente del proyecto
 */
public class EntretenimientoDefinitions {

    // ===========================================
    // Generales
    // ===========================================

    @When("^SELECCIONA EL BOTON ENTRETENIMIENTO EN LA BARRA INFERIOR$")
    public void seleccionaBotonEntretenimiento() {
        theActorInTheSpotlight().attemptsTo(
                AccederEntretenimiento.acceder()
        );
    }


    @Then("^VALIDA REDIRECCIONAMIENTO ID ClARO$")
    public void validaRedireccionamientoIdClaro() {
        theActorInTheSpotlight().attemptsTo(
                ValidaRedireccionamientoIdClaro.validar()
        );
    }


    /*@And("^VALIDA REDIRECCION A ENTRETENIMIENTO$")
    public void validaRedireccionEntretenimiento() {
        theActorInTheSpotlight().attemptsTo(
                ValidarRedireccionEntretenimiento.validar()
        );
    }*/

    // ===========================================
    // SA059 - Claro Video
    // ===========================================

    @And("^SELECCIONA EL BOTON CLARO VIDEO$")
    public void seleccionaBotonClaroVideo() {
        theActorInTheSpotlight().attemptsTo(
                SeleccionarClaroVideo.seleccionar()
        );
    }

    @And("^VALIDA VERSION DE MINIPROGRAMA CLARO VIDEO$")
    public void validaVersionMiniProgramaClaroVideo() {
        theActorInTheSpotlight().attemptsTo(
                ValidarVersionMiniPrograma.validar()
        );
    }

    @Then("^VALIDA REDIRECCION APP STORE CLARO VIDEO$")
    public void ValidarRedireccionAppStoreClaroVideo() {
        theActorInTheSpotlight().attemptsTo(
                ValidarRedireccionLinkExterno.validar()
        );
    }



    // ===========================================
    // SA070 - Claro Club
    // ===========================================
    @And("^SELECCIONA EL BOTON CLARO CLUB$")
    public void seleccionaBotonClaroClub() {
        theActorInTheSpotlight().attemptsTo(
                SeleccionarClaroClub.seleccionar()
        );
    }

    @And("^VALIDA VERSION DE MINIPROGRAMA CLARO CLUB$")
    public void validaVersionMiniProgramaClaroClub() {
        theActorInTheSpotlight().attemptsTo(
                ValidarVersionMiniPrograma.validar()
        );
    }

    @Then("^VALIDA REDIRECCION CLARO CLUB$")
    public void ValidarRedireccionClaroClub() {
        theActorInTheSpotlight().attemptsTo(
                ValidarRedireccionClaroClub.validar()
        );
    }

    // ===========================================
    // SA059 - Claro Musica
    // ===========================================

    @And("^SELECCIONA EL BOTON CLARO MUSICA$")
    public void seleccionaBotonClaroMusica() {
        theActorInTheSpotlight().attemptsTo(
                SeleccionarClaroMusica.seleccionar()
        );
    }

    @And("^VALIDA VERSION DE MINIPROGRAMA CLARO MUSICA$")
    public void validaVersionMiniProgramaClaroMusica() {
        theActorInTheSpotlight().attemptsTo(
                ValidaVersionMiniProgramaClaroMusica.validar()
        );
    }

    @Then("^VALIDA REDIRECCION CLARO MUSICA APP$")
    public void ValidarRedireccionClaroMusicaApp() {
        theActorInTheSpotlight().attemptsTo(
                ValidarRedireccionClaroMusicaApp.validar()
        );
    }

    // ===========================================
    // SA063 - Netflix
    // ===========================================

    @And("^SELECCIONA BOTON NETFLIX$")
    public void seleccionaBotonNetflix() {
        theActorInTheSpotlight().attemptsTo(
                SeleccionarNetflix.seleccionar()
        );
    }

    @And("VALIDA REDIRECCIONAMIENTO NETFLIX")
    public void validaRedireccionamientoNetflix() {
        theActorInTheSpotlight().attemptsTo(
                ValidarRedireccionamientoNetflix.validar()
        );
    }

    @Then("^VALIDA TERMINOS Y CONDICIONES NETFLIX$")
    public void validaTerminosYCondicionesNetflix() {
        theActorInTheSpotlight().attemptsTo(
                ValidarTerminosCondicionesNetflix.validar()
        );
    }

    // ===========================================
    // SA066 - Disney+ Plan Estándar
    // ===========================================

    @And("^SELECCIONA BOTON DISNEY PLUS$")
    public void seleccionaBotonDisneyPlus() {
        theActorInTheSpotlight().attemptsTo(
                SeleccionarDisneyPlus.seleccionar()
        );
    }

    @And("^VALIDAR VERSION DE MINIPROGRAMA DISNEY$")
    public void validarVersionDeMiniprogramaDisney() {
        theActorInTheSpotlight().attemptsTo(
                ValidarVersionDeMiniprogramaDisney.validar()
        );
    }

    @And("^SELECCIONA PLAN ESTANDAR DISNEY$")
    public void seleccionaPlanEstandarDisney() {
        theActorInTheSpotlight().attemptsTo(
                SeleccionaPlanEstandarDisney.seleccionar()
        );
    }

    @And("^SELECCIONA PLAN PREMIUM DISNEY$")
    public void seleccionaPlanPremiumDisney() {
        theActorInTheSpotlight().attemptsTo(
                SeleccionarPlanPremiumDisney.seleccionar()
        );
    }

    @Then("^VALIDA TERMINOS Y CONDICIONES DISNEY ESTANDAR$")
    public void validaTerminosYCondicionesDisneyEstandar() {
        theActorInTheSpotlight().attemptsTo(
                ValidarTerminosCondicionesDisneyEstandar.validar()
        );
    }

    @Then("^VALIDA TERMINOS Y CONDICIONES DISNEY PREMIUM$")
    public void validaTerminosYCondicionesDisneyPremium() {
        theActorInTheSpotlight().attemptsTo(
                ValidarTerminosCondicionesDisneyPremium.validar()
        );
    }

    // ===========================================
    // SA069 - Amazon Prime
    // ===========================================

    @And("^SELECCIONA BOTON AMAZON PRIME$")
    public void seleccionaBotonAmazonPrime() {
        theActorInTheSpotlight().attemptsTo(
                SeleccionarAmazonPrime.seleccionar()
        );
    }

    @And("^VALIDAR VERSION DE MINIPROGRAMA AMAZONPRIME$")
    public void validarVersionDeMiniProgramaAmazonPrime() {
        theActorInTheSpotlight().attemptsTo(
                ValidarVersionDeMiniProgramaAmazonPrime.validar()
        );
    }

    @And("^SELECCIONA PLAN AMAZON PRIME$")
    public void seleccionaPlanAmazonPrime() {
        theActorInTheSpotlight().attemptsTo(
                SeleccionarPlanAmazonPrime.seleccionar()
        );
    }

    @Then("^VALIDA TERMINOS Y CONDICIONES AMAZON PRIME$")
    public void validaTerminosYCondicionesAmazonPrime() {
        theActorInTheSpotlight().attemptsTo(
                ValidarTerminosCondicionesAmazonPrime.validar()
        );
    }
    // ===========================================
    // SA064 - Win Play
    // ===========================================

    @And("^SELECCIONA BOTON VER MAS PLATAFORMAS$")
    public void seleccionaBotonVerMasPlataformas() {
        theActorInTheSpotlight().attemptsTo(
                AccederVerMasPlataformas.acceder()
        );
    }

    @And("^SELECCIONA BOTON WIN PLAY$")
    public void seleccionaBotonWinPlay() {
        theActorInTheSpotlight().attemptsTo(
                SeleccionarWinPlay.seleccionar()
        );
    }

    @Then("^VALIDA REDIRECCION A PAGINA WIN PLAY$")
    public void validaRedireccionAPaginaWinPlay() {
        theActorInTheSpotlight().attemptsTo(
                ValidarRedireccionWinPlay.validar()
        );
    }

    // ===========================================
    // SA060 - RED + TV EN VIVO
    // ===========================================

    @And("^SELECCIONA EL BOTON RED PLUS TV EN VIVO$")
    public void seleccionaBotonRedPlusTVEnVivo() {
        theActorInTheSpotlight().attemptsTo(
                SeleccionarRedTVEnVivo.seleccionar()
        );
    }

    @Then("^VALIDA REDIRECCION A RED PLUS TV EN VIVO$")
    public void validaRedireccionRedPlusTVEnVivo() {
        theActorInTheSpotlight().attemptsTo(
                ValidarRedireccionRedTV.validar()
        );
    }

    // ===========================================
    // SA061 - RED + NOTICIAS
    // ===========================================

    @And("^DESPLAZARSE HASTA EL MODULO TUS PLATAFORMAS FAVORITAS$")
    public void desplazarseHastaModuloTusPlataformasFavoritas() {
        theActorInTheSpotlight().attemptsTo(
                AccederVerMasPlataformas.acceder()
        );
    }

    @And("^SELECCIONA BOTON RED PLUS NOTICIAS$")
    public void seleccionaBotonRedPlusNoticias() {
        theActorInTheSpotlight().attemptsTo(
                SeleccionarRedNoticias.seleccionar()
        );
    }
    /*
    @Then("^VALIDA REDIRECCION RED PLUS NOTICIAS$")
    public void validaRedireccionRedPlusNoticias() {
        theActorInTheSpotlight().attemptsTo(
                ValidarRedireccionRedNoticias.validar()
        );
    }*/







    // ===========================================
    // SA065 - HBO Max
    // ===========================================

    @And("^SELECCIONA BOTON HBOMAX$")
    public void seleccionaBotonHBOmax() {
        theActorInTheSpotlight().attemptsTo(
                SeleccionarHBOmax.seleccionar()
        );
    }

    @Then("^VALIDA REDIRECCION PAGINA A HBOMAX$")
    public void validaRedireccionPaginaAHBOmax() {
        theActorInTheSpotlight().attemptsTo(
                ValidarRedireccionHBOmax.validar()
        );
    }


    // ===========================================
    // SA068 - HotGo
    // ===========================================

    @And("^SELECCIONA BOTON HOTGO$")
    public void seleccionaBotonHotGo() {
        theActorInTheSpotlight().attemptsTo(
                SeleccionarHotGo.seleccionar()
        );
    }

    @Then("^VALIDA REDIRECCION A PAGINA HOTGO$")
    public void validaRedireccionAPaginaHotGo() {
        theActorInTheSpotlight().attemptsTo(
                ValidarRedireccionHotGo.validar()
        );
    }



}

// ===========================================