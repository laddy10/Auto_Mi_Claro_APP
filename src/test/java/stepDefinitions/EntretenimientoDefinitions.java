package stepDefinitions;

import static net.serenitybdd.screenplay.actors.OnStage.theActorInTheSpotlight;
import static utils.Constants.*;


import cucumber.api.java.ast.Y;
import cucumber.api.java.es.Cuando;
import cucumber.api.java.es.Entonces;
import interactions.Click.ClickTextoQueContengaX;
import interactions.Scroll.Scroll;
import interactions.Scroll.ScrollHastaTexto;
import tasks.Entretenimiento.*;
import tasks.Entretenimiento.RutasDeAcceso.*;
import tasks.Entretenimiento.ValidarMiniVersionesEntretenimientoPospago.*;
import tasks.Entretenimiento.ValidarMiniVersionesEntretenimientoPrepago.*;
import tasks.Entretenimiento.ValidarRedirecciones.*;
import tasks.Entretenimiento.ValidarTC.*;
import utils.EvidenciaUtils;

/**
 * Step Definitions para el módulo Entretenimiento Mantiene la consistencia con el estilo existente
 * del proyecto
 */
public class EntretenimientoDefinitions {

  // ===========================================
  // Generales
  // ===========================================

  @Cuando("^SELECCIONA EL BOTON ENTRETENIMIENTO EN LA BARRA INFERIOR$")
  public void seleccionaBotonEntretenimiento() {
    theActorInTheSpotlight().attemptsTo(AccederEntretenimiento.acceder());
  }
  @Y("^INGRESA A DISNEY EN LA OPCION MUNDIAL$")
  public void desplazarseopcionmundial() {
    theActorInTheSpotlight()
            .attemptsTo(
                    ScrollHastaTexto.conTexto(CADA_PARTIDO),
                    //Scroll.scrollUnaVista(),
                    ClickTextoQueContengaX.elTextoContiene(DISNEY));

    EvidenciaUtils.registrarCaptura("Menú mundial");
  }



  @Cuando("^SELECCIONA EL BOTON VER MAS EN SECCION TUS SERVCICOS FAVORITOS$")
  public void seleccionaVerMasEnTusServiciosFavoritos() {
    theActorInTheSpotlight().attemptsTo(AccederVerMasTusServiciosFavoritos.acceder());
  }

  @Entonces("^VALIDA REDIRECCIONAMIENTO ID ClARO$")
  public void validaRedireccionamientoIdClaro() {
    theActorInTheSpotlight().attemptsTo(ValidaRedireccionamientoIdClaro.validar());
  }

  @Y("^VALIDA VERSION DE MINIPROGRAMA PRE CLARO VIDEO$")
  public void validaVersionMiniProgramaClaroVideoPre() {
    theActorInTheSpotlight().attemptsTo(ValidarVersionMiniProgramaClaroVideoPre.validar());
  }

  @Y("^VALIDA VERSION DE MINIPROGRAMA PRE CLARO MUSICA$")
  public void validaVersionMiniProgramaClaroMusicaPre() {
    theActorInTheSpotlight().attemptsTo(ValidarVersionMiniProgramaClaroMusicaPre.validar());
  }

  @Y("^VALIDA VERSION DE MINIPROGRAMA PRE CLARO CLUB$")
  public void validaVersionMiniProgramaClaroClubPre() {
    theActorInTheSpotlight().attemptsTo(ValidarVersionMiniProgramaClaroClubPre.validar());
  }

  /*@Y("^VALIDA REDIRECCION A ENTRETENIMIENTO$")
  public void validaRedireccionEntretenimiento() {
      theActorInTheSpotlight().attemptsTo(
              ValidarRedireccionEntretenimiento.validar()
      );
  }*/

  // ===========================================
  // SA059 - Claro Video
  // ===========================================

  @Y("^SELECCIONA EL BOTON CLARO VIDEO$")
  public void seleccionaBotonClaroVideo() {
    theActorInTheSpotlight().attemptsTo(SeleccionarClaroVideo.seleccionar());
  }

  @Y("^VALIDA VERSION DE MINIPROGRAMA CLARO VIDEO$")
  public void validaVersionMiniProgramaClaroVideo() {
    theActorInTheSpotlight().attemptsTo(ValidarVersionMiniProgramaClaroVideo.validar());
  }

  @Entonces("^VALIDA REDIRECCION APP STORE CLARO VIDEO$")
  public void ValidarRedireccionAppStoreClaroVideo() {
    theActorInTheSpotlight().attemptsTo(ValidarRedireccionPlayStoreClaroVideo.validar());
  }

  // ===========================================
  // SA070 - Claro Club
  // ===========================================
  @Y("^SELECCIONA EL BOTON CUPONES EN EXPLORA Y COMPRA$")
  public void seleccionaBotonClaroClubEnExploraYCompra() {
    theActorInTheSpotlight().attemptsTo(SeleccionarClaroClubEnExploraYCompra.seleccionar());
  }

  @Y("^SELECCIONA EL BOTON CLARO CLUB$")
  public void seleccionaBotonClaroClub() {
    theActorInTheSpotlight().attemptsTo(SeleccionarClaroClub.seleccionar());
  }

  @Y("^VALIDA VERSION DE MINIPROGRAMA CLARO CLUB$")
  public void validaVersionMiniProgramaClaroClub() {
    theActorInTheSpotlight().attemptsTo(ValidarVersionMiniProgramaClaroClub.validar());
  }

  @Entonces("^VALIDA REDIRECCION CLARO CLUB$")
  public void ValidarRedireccionClaroClub() {
    theActorInTheSpotlight().attemptsTo(ValidarRedireccionClaroClub.validar());
  }

  // ===========================================
  // SA059 - Claro Musica
  // ===========================================

  @Y("^SELECCIONA EL BOTON CLARO MUSICA$")
  public void seleccionaBotonClaroMusica() {
    theActorInTheSpotlight().attemptsTo(SeleccionarClaroMusica.seleccionar());
  }

  @Y("^VALIDA VERSION DE MINIPROGRAMA CLARO MUSICA$")
  public void validaVersionMiniProgramaClaroMusica() {
    theActorInTheSpotlight().attemptsTo(ValidarVersionMiniProgramaClaroMusica.validar());
  }

  @Entonces("^VALIDA REDIRECCION CLARO MUSICA APP$")
  public void ValidarRedireccionClaroMusicaApp() {
    theActorInTheSpotlight().attemptsTo(ValidarRedireccionClaroMusicaApp.validar());
  }

  // ===========================================
  // SA063 - Netflix
  // ===========================================

  @Y("^SELECCIONA BOTON NETFLIX$")
  public void seleccionaBotonNetflix() {
    theActorInTheSpotlight().attemptsTo(SeleccionarNetflix.seleccionar());
  }

  @Y("VALIDA VERSION DE MINIPROGRAMA NETFLIX")
  public void validaRedireccionamientoNetflix() {
    theActorInTheSpotlight().attemptsTo(ValidarVersionMiniProgramaNetflix.validar());
  }

  @Entonces("^VALIDA TERMINOS Y CONDICIONES NETFLIX$")
  public void validaTerminosYCondicionesNetflix() {
    theActorInTheSpotlight().attemptsTo(ValidarTerminosCondicionesNetflix.validar());
  }

  // ===========================================
  // SA066 - Disney+ Plan Estándar
  // ===========================================

  @Y("^SELECCIONA BOTON DISNEY PLUS$")
  public void seleccionaBotonDisneyPlus() {
    theActorInTheSpotlight().attemptsTo(SeleccionarDisneyPlus.seleccionar());
  }

  @Y("^VALIDAR VERSION DE MINIPROGRAMA DISNEY$")
  public void validarVersionDeMiniprogramaDisney() {
    theActorInTheSpotlight().attemptsTo(ValidarVersionMiniprogramaDisney.validar());
  }

  @Y("^SELECCIONA PLAN ESTYAR DISNEY$")
  public void seleccionaPlanEstYarDisney() {
    theActorInTheSpotlight().attemptsTo(SeleccionaPlanEstandarDisney.seleccionar());
  }

  @Y("^SELECCIONA PLAN PREMIUM DISNEY$")
  public void seleccionaPlanPremiumDisney() {
    theActorInTheSpotlight().attemptsTo(SeleccionarPlanPremiumDisney.seleccionar());
  }

  @Entonces("^VALIDA TERMINOS Y CONDICIONES DISNEY ESTYAR$")
  public void validaTerminosYCondicionesDisneyEstYar() {
    theActorInTheSpotlight().attemptsTo(ValidarTerminosCondicionesDisneyEstandar.validar());
  }

  @Entonces("^VALIDA TERMINOS Y CONDICIONES DISNEY PREMIUM$")
  public void validaTerminosYCondicionesDisneyPremium() {
    theActorInTheSpotlight().attemptsTo(ValidarTerminosCondicionesDisneyPremium.validar());
  }

  // ===========================================
  // SA069 - Amazon Prime
  // ===========================================

  @Y("^SELECCIONA BOTON AMAZON PRIME$")
  public void seleccionaBotonAmazonPrime() {
    theActorInTheSpotlight().attemptsTo(SeleccionarAmazonPrime.seleccionar());
  }

  @Y("^VALIDAR VERSION DE MINIPROGRAMA AMAZONPRIME$")
  public void validarVersionDeMiniProgramaAmazonPrime() {
    theActorInTheSpotlight().attemptsTo(ValidarVersionMiniProgramaAmazonPrime.validar());
  }

  @Y("^SELECCIONA PLAN AMAZON PRIME$")
  public void seleccionaPlanAmazonPrime() {
    theActorInTheSpotlight().attemptsTo(SeleccionarPlanAmazonPrime.seleccionar());
  }

  @Entonces("^VALIDA TERMINOS Y CONDICIONES AMAZON PRIME$")
  public void validaTerminosYCondicionesAmazonPrime() {
    theActorInTheSpotlight().attemptsTo(ValidarTerminosCondicionesAmazonPrime.validar());
  }
  // ===========================================
  // SA064 - Win Play
  // ===========================================

  @Y("^SELECCIONA BOTON VER MAS PLATAFORMAS$")
  public void seleccionaBotonVerMasPlataformas() {
    theActorInTheSpotlight().attemptsTo(AccederVerMasPlataformas.acceder());
  }

  @Y("^SELECCIONA BOTON WIN PLAY$")
  public void seleccionaBotonWinPlay() {
    theActorInTheSpotlight().attemptsTo(SeleccionarWinPlay.seleccionar());
  }

  @Entonces("^VALIDA REDIRECCION A PAGINA WIN PLAY$")
  public void validaRedireccionAPaginaWinPlay() {
    theActorInTheSpotlight().attemptsTo(ValidarRedireccionWinPlay.validar());
  }

  // ===========================================
  // SA060 - RED + TV EN VIVO
  // ===========================================

  @Y("^SELECCIONA EL BOTON RED PLUS TV EN VIVO$")
  public void seleccionaBotonRedPlusTVEnVivo() {
    theActorInTheSpotlight().attemptsTo(SeleccionarRedTVEnVivo.seleccionar());
  }

  @Entonces("^VALIDA REDIRECCION A RED PLUS TV EN VIVO$")
  public void validaRedireccionRedPlusTVEnVivo() {
    theActorInTheSpotlight().attemptsTo(ValidarRedireccionRedTV.validar());
  }

  // ===========================================
  // SA061 - RED + NOTICIAS
  // ===========================================

  @Y("^DESPLAZARSE HASTA EL MODULO TUS PLATAFORMAS FAVORITAS$")
  public void desplazarseHastaModuloTusPlataformasFavoritas() {
    theActorInTheSpotlight().attemptsTo(AccederVerMasPlataformas.acceder());
  }

  @Y("^SELECCIONA BOTON RED PLUS NOTICIAS$")
  public void seleccionaBotonRedPlusNoticias() {
    theActorInTheSpotlight().attemptsTo(SeleccionarRedNoticias.seleccionar());
  }
  /*
  @Entonces("^VALIDA REDIRECCION RED PLUS NOTICIAS$")
  public void validaRedireccionRedPlusNoticias() {
      theActorInTheSpotlight().attemptsTo(
              ValidarRedireccionRedNoticias.validar()
      );
  }*/

  // ===========================================
  // SA065 - HBO Max
  // ===========================================

  @Y("^SELECCIONA BOTON HBOMAX$")
  public void seleccionaBotonHBOmax() {
    theActorInTheSpotlight().attemptsTo(SeleccionarHBOmax.seleccionar());
  }

  @Entonces("^VALIDA REDIRECCION PAGINA A HBOMAX$")
  public void validaRedireccionPaginaAHBOmax() {
    theActorInTheSpotlight().attemptsTo(ValidarRedireccionHBOmax.validar());
  }

  // ===========================================
  // SA068 - HotGo
  // ===========================================

  @Y("^SELECCIONA BOTON HOTGO$")
  public void seleccionaBotonHotGo() {
    theActorInTheSpotlight().attemptsTo(SeleccionarHotGo.seleccionar());
  }

  @Entonces("^VALIDA REDIRECCION A PAGINA HOTGO$")
  public void validaRedireccionAPaginaHotGo() {
    theActorInTheSpotlight().attemptsTo(ValidarRedireccionHotGo.validar());
  }
}

// ===========================================
