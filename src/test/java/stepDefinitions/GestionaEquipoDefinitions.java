package stepDefinitions;

import static net.serenitybdd.screenplay.actors.OnStage.theActorInTheSpotlight;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import tasks.GestionaEquipo.*;

/**
 * Step Definitions para el módulo Gestiona tu Equipo Mantiene la consistencia con el estilo
 * existente del proyecto
 */
public class GestionaEquipoDefinitions {

  @And("^INGRESA AL MODULO GESTIONA TU EQUIPO$")
  public void ingresaModuloGestionaEquipo() {
    theActorInTheSpotlight().attemptsTo(AccederGestionaEquipo.acceder());
  }

  // ===========================================
  // SA020 - Registrar Equipo
  // ===========================================

  @And("^SELECCIONA LA OPCION REGISTRAR EQUIPO$")
  public void seleccionaRegistrarEquipo() {
    theActorInTheSpotlight().attemptsTo(RegistrarEquipo.registrar());
  }

  @And("^SELECCIONA EL BOTON REGISTRAR$")
  public void seleccionaBotonRegistrar() {
    theActorInTheSpotlight().attemptsTo(ConfirmarRegistroEquipo.confirmar());
  }

  @Then("^CONFIRMA INGRESO AL REGISTRO DE EQUIPO$")
  public void confirmaIngresoRegistroEquipo() {
    theActorInTheSpotlight().attemptsTo(RegistrarEquipo.registrar());
  }

  // ===========================================
  // SA021 - Reportar por Robo o Pérdida
  // ===========================================

  @And("^SELECCIONA LA OPCION REPORTAR POR ROBO O PERDIDA$")
  public void seleccionaReportarRoboPerdida() {
    theActorInTheSpotlight().attemptsTo(ReportarRoboPerdida.reportar());
  }

  @Then("^CONFIRMA INGRESO A LA OPCION Y VALIDA TEXTOS DISPONIBLES$")
  public void confirmaIngresoYValidaTextos() {
    theActorInTheSpotlight().attemptsTo(ConfirmarIngresoReporte.confirmar());
  }

  // ===========================================
  // SA022 - Solucionar IMEI Duplicado
  // ===========================================

  @And("^SELECCIONA LA OPCION SOLUCIONAR EQUIPO CON IMEI DUPLICADO$")
  public void seleccionaSolucionarIMEIDuplicado() {
    theActorInTheSpotlight().attemptsTo(SolucionarIMEIDuplicado.solucionar());
  }

  @Then("^VERIFICA QUE SE MUESTREN LAS DOS OPCIONES EN PANTALLA$")
  public void verificaOpcionesIMEIDuplicado() {
    theActorInTheSpotlight().attemptsTo(VerificarOpcionesIMEI.verificar());
  }

  // ===========================================
  // SA023 - Reconectar por Robo o Pérdida
  // ===========================================

  @And("^SELECCIONA LA OPCION RECONECTAR POR ROBO O PERDIDA$")
  public void seleccionaReconectarRoboPerdida() {
    theActorInTheSpotlight().attemptsTo(ReconectarRoboPerdida.reconectar());
  }

  @Then("^VALIDA TEXTO DISPONIBLE Y CIERRA MODAL$")
  public void validaTextoYCierraModal() {
    theActorInTheSpotlight().attemptsTo(ValidarTextoYCerrar.validarYCerrar());
  }

  // ===========================================
  // SA024 - Consultar Equipo
  // ===========================================

  @And("^SELECCIONA LA OPCION CONSULTAR EQUIPO$")
  public void seleccionaConsultarEquipo() {
    theActorInTheSpotlight().attemptsTo(ConsultarEquipo.consultar());
  }

  @Then("^VERIFICA INFORMACION DISPONIBLE DEL EQUIPO$")
  public void verificaInformacionEquipo() {
    theActorInTheSpotlight().attemptsTo(VerificarInformacionEquipo.verificar());
  }

  // ===========================================
  // SA025 - Actualizar Datos de Equipo
  // ===========================================

  @And("^SELECCIONA LA OPCION ACTUALIZAR DATOS DE TU EQUIPO$")
  public void seleccionaActualizarDatos() {
    theActorInTheSpotlight().attemptsTo(ActualizarDatosEquipo.actualizar());
  }

  @Then("^VERIFICA LA INFORMACION DISPONIBLE DE EQUIPOS$")
  public void verificaInformacionDisponible() {
    theActorInTheSpotlight().attemptsTo(VerificarInformacionDisponible.verificar());
  }
}
