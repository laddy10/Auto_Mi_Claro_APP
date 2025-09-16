package stepDefinitions;

import static net.serenitybdd.screenplay.actors.OnStage.theActorInTheSpotlight;
import static userinterfaces.AtencionClienteSoportePage.CHK_AUTORIZAR_MEDICION;
import static utils.Constants.*;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import interactions.Click.ClickTextoQueContengaX;
import interactions.Scroll.ScrollHastaTexto;
import interactions.validations.ValidarTexto;
import interactions.validations.ValidarTextoQueContengaX;
import models.User;
import net.serenitybdd.screenplay.actions.Click;
import tasks.AtencionClienteSoporte.*;
import tasks.AtencionClienteSoporte.ValidarMiniprogramaAtencionAlClienteYsoporte.ValidarVersionMiniprogramaConsultarPQR;
import tasks.AtencionClienteSoporte.ValidarMiniprogramaAtencionAlClienteYsoporte.ValidarVersionMiniprogramaEstadoServiciosTecnicos;
import tasks.AtencionClienteSoporte.ValidarMiniprogramaAtencionAlClienteYsoporte.ValidarVersionMiniprogramaNecesitasAyuda;
import utils.EvidenciaUtils;
import utils.TestDataProvider;

public class AtencionClienteSoporteDefinitions {

  private final User user = TestDataProvider.getRealUser();

  @And("^DESPLAZA HASTA LA OPCION ATENCION AL CLIENTE Y SOPORTE$")
  public void desplazaHastaAtencionClienteSoporte() {
    theActorInTheSpotlight().attemptsTo(ScrollHastaTexto.conTexto(ATENCION_AL_CLIENTE));
    EvidenciaUtils.registrarCaptura("Desplazarse hasta Atención al cliente y soporte");
  }

  @And("^INGRESA AL BOTON MEDICION DE RED$")
  public void ingresaAlBotonMedicionDeRed() {
    theActorInTheSpotlight().attemptsTo(MedicionDeRed.ingresarMedicionRed());
  }

  @And("^VERIFICA EL MENSAJE DE AUTORIZACION EN PANTALLA$")
  public void verificaMensajeAutorizacion() {
    theActorInTheSpotlight().attemptsTo(ValidarTexto.validarTexto(AUTORIZO_MEDICION_CALIDAD_RED));
    EvidenciaUtils.registrarCaptura("Verificar mensaje de autorización");
  }

  @And("^ACEPTA LA AUTORIZACION DE MEDICION$")
  public void aceptaAutorizacionMedicion() {
    theActorInTheSpotlight().attemptsTo(Click.on(CHK_AUTORIZAR_MEDICION));
  }

  @And("^REALIZA TEST DE VELOCIDAD$")
  public void realizaTestVelocidad() {
    theActorInTheSpotlight()
            .attemptsTo(ClickTextoQueContengaX.elTextoContiene(REALIZAR_TEST_VELOCIDAD));
  }

  @Then("^VERIFICA LA INFORMACION EN PANTALLA Y MANEJA CONDICIONALES$")
  public void verificaInformacionYManejaCondicionales() {
    theActorInTheSpotlight().attemptsTo(ManejarCondicionalMedicionRed.verificarYManejar());
  }

  @And("^INGRESA A LA OPCION SOPORTE HOGAR$")
  public void ingresaASoporteHogar() {
    theActorInTheSpotlight().attemptsTo(SoporteHogar.ingresar());
  }

  @Then("^SELECCIONA WHATSAPP Y VALIDA REDIRECCION$")
  public void seleccionaWhatsappYValidaRedireccion() {
    theActorInTheSpotlight().attemptsTo(SoporteHogar.seleccionarWhatsappYValidar());
  }

  @And("^SELECCIONA LA OPCION VER MAS$")
  public void seleccionaLaOpcionVerMas() {
    theActorInTheSpotlight().attemptsTo(ClickTextoQueContengaX.elTextoContiene(VER_MAS));
    EvidenciaUtils.registrarCaptura("Seleccionar opción Ver más");
  }

  @And("^INGRESA A LA OPCION CONSULTAR PQR$")
  public void ingresaAConsultarPQR() {
    theActorInTheSpotlight().attemptsTo(ConsultarPQR.ingresar());
  }

  @And("^DESPLAZA HASTA LINEA Y SELECCIONA VER DETALLE$")
  public void desplazaHastaLineaYSeleccionaVerDetalle() {
    theActorInTheSpotlight().attemptsTo(ConsultarPQR.seleccionarLineaYVerDetalle());
  }

  @Then("^VERIFICA REDIRECCION CORRECTA A PAGINA CLARO$")
  public void verificaRedireccionAPaginaClaro() {
    theActorInTheSpotlight()
            .attemptsTo(ValidarTextoQueContengaX.elTextoContiene(CLARO_COLOMBIA_PQR));
    EvidenciaUtils.registrarCaptura("Verificar redirección a página Claro");
  }

  @And("^INGRESA A LA OPCION PUNTOS DE ATENCION$")
  public void ingresaAPuntosDeAtencion() {
    theActorInTheSpotlight().attemptsTo(PuntosDeAtencion.ingresar());
  }

  @And("^MANEJA PERMISOS DE UBICACION$")
  public void manejaPermisosUbicacion() {
    theActorInTheSpotlight().attemptsTo(PuntosDeAtencion.manejarPermisosUbicacion());
  }

  @Then("^VERIFICA QUE ESTE PRESENTE EL MAPA$")
  public void verificaQueEstePresenteElMapa() {
    theActorInTheSpotlight().attemptsTo(PuntosDeAtencion.verificarMapa());
  }

  @And("^SELECCIONA LA OPCION NECESITAS AYUDA$")
  public void seleccionaLaOpcionNecesitasAyuda() {
    theActorInTheSpotlight().attemptsTo(NecesitasAyuda.ingresar());
  }

  @And("^DESPLAZA HASTA LINEA Y SELECCIONA VER DETALLE NECESITAS AYUDA$")
  public void desplazaHastaLineaYSeleccionaVerDetalleNecesitasAyuda() {
    theActorInTheSpotlight().attemptsTo(NecesitasAyuda.seleccionarLineaYVerDetalle());
  }

  @Then("^VERIFICA REDIRECCION A CLAROBOT$")
  public void verificaRedireccionAClarobot() {
    theActorInTheSpotlight().attemptsTo(NecesitasAyuda.verificarRedireccionClarobot());
  }

  @And("^SELECCIONA LA OPCION ESTADO SERVICIOS TECNICOS$")
  public void seleccionaLaOpcionEstadoServiciosTecnicos() {
    theActorInTheSpotlight().attemptsTo(EstadoServiciosTecnicos.ingresar());
  }

  @And("^DESPLAZA HASTA LINEA Y SELECCIONA VER DETALLE SERVICIOS TECNICOS$")
  public void desplazaHastaLineaYSeleccionaVerDetalleServiciosTecnicos() {
    theActorInTheSpotlight().attemptsTo(EstadoServiciosTecnicos.seleccionarLineaYVerDetalle());
  }

  @And("^VERIFICA REDIRECCION ORDENES DE SERVICIO$")
  public void verificaRedireccionOrdenesDeServicio() {
    theActorInTheSpotlight().attemptsTo(ValidarTexto.validarTexto(ORDENES_DE_SERVICIO));
    EvidenciaUtils.registrarCaptura("Verificar redirección Órdenes de servicio");
  }

  @And("^CONSULTA POR NUMERO DE DOCUMENTO$")
  public void consultaPorNumeroDeDocumento() {
    theActorInTheSpotlight().attemptsTo(EstadoServiciosTecnicos.consultarPorNumeroDocumento());
  }

  @And("^CONSULTA POR NUMERO DE CELULAR$")
  public void consultaPorNumeroDeCelular() {
    theActorInTheSpotlight().attemptsTo(EstadoServiciosTecnicos.consultarPorNumeroCelular());
  }

  @Then("^CONSULTA POR IMEI$")
  public void consultaPorImei() {
    theActorInTheSpotlight().attemptsTo(EstadoServiciosTecnicos.consultarPorImei());
  }

  @And("^VALIDA VERSION DE MINIPROGRAMA NECESITAS AYUDA$")
  public void validaVersionMiniprogramaNecesitasAyuda() {
    theActorInTheSpotlight().attemptsTo(
            ValidarVersionMiniprogramaNecesitasAyuda.validar()
    );
  }

  @And("^VALIDA VERSION DE MINIPROGRAMA ESTADO SERVICIOS TECNICOS$")
  public void ValidarVersionMiniprogramaEstadoServiciosTecnicos() {
    theActorInTheSpotlight().attemptsTo(
            ValidarVersionMiniprogramaEstadoServiciosTecnicos.validar()
    );
  }

  @And("^VALIDA VERSION DE MINIPROGRAMA CONSULTAR PQR$")
  public void ValidarVersionMiniprogramaConsultarPQR() {
    theActorInTheSpotlight().attemptsTo(
            ValidarVersionMiniprogramaConsultarPQR.validar()
    );
  }

  @And("^DESPLAZA HASTA LINEA Y SELECCIONA VER DETALLE NECESITAS AYUDA PRE$")
  public void desplazaHastaLineaYSeleccionaVerDetalleNecesitasAyudaPre() {
    theActorInTheSpotlight().attemptsTo(NecesitasAyudaPRE.seleccionarLineaYVerDetalle());
  }

  @And("^DESPLAZA HASTA LINEA Y SELECCIONA VER DETALLE SERVICIOS TECNICOS PRE$")
  public void desplazaHastaLineaYSeleccionaVerDetalleServiciosTecnicosPre() {
    theActorInTheSpotlight().attemptsTo(EstadoServiciosTecnicosPRE.seleccionarLineaYVerDetalle());
  }

  @And("^DESPLAZA HASTA LINEA Y SELECCIONA VER DETALLE CONSULTA PQR PRE$")
  public void desplazaHastaLineaYSeleccionaVerDetalleConsultaPQRPre() {
    theActorInTheSpotlight().attemptsTo(ConsultarPQRPre.seleccionarLineaYVerDetalle());
  }
}