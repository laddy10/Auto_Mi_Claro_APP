package stepDefinitions;

import static net.serenitybdd.screenplay.actors.OnStage.theActorInTheSpotlight;


import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import tasks.ConsultarConsumos.*;

/**
 * Step Definitions para el módulo Consultar Consumos Mantiene la consistencia con el estilo
 * existente del proyecto
 */
public class ConsultarConsumosDefinitions {

  @And("^INGRESA AL MODULO CONSULTAR CONSUMOS$")
  public void ingresaModuloConsultarConsumos() {
    theActorInTheSpotlight().attemptsTo(AccederConsultarConsumos.acceder());
  }

  @And("^DESPLAZARSE HASTA EL NUMERO Y SELECCIONAR VER DETALLE$")
  public void desplazarseHastaNumeroYSeleccionarVerDetalle() {
    theActorInTheSpotlight().attemptsTo(SeleccionarLineaYVerDetalle.seleccionar());
  }

  @And("^ESPERA A QUE INGRESE A LA OPCION$")
  public void esperaAQueIngreseALaOpcion() {
    theActorInTheSpotlight().attemptsTo(VerificarTextosDisponibles.verificar());
  }

  @And("^VERIFICA LOS TEXTOS DISPONIBLES$")
  public void verificaLosTextosDisponibles() {
    theActorInTheSpotlight().attemptsTo(VerificarTextosDisponibles.verificar());
  }

  // ===========================================
  // Consumo de Datos
  // ===========================================

  @And("^SELECCIONA CONSUMO DE DATOS$")
  public void seleccionaConsumoDeatos() {
    theActorInTheSpotlight().attemptsTo(ValidarConsumoDeDatos.validar());
  }

  @And("^VALIDA LA INFORMACION DISPONIBLE DE DATOS$")
  public void validaLaInformacionDisponibleDeDatos() {
    theActorInTheSpotlight().attemptsTo(ValidarInformacionDisponibleDatos.validar());
  }

  // ===========================================
  // Apps sin límite de consumo
  // ===========================================

  @And("^REGRESA ATRAS Y SELECCIONA APPS SIN LIMITE DE CONSUMO$")
  public void regresaAtrasYSeleccionaAppsSinLimiteDeConsumo() {
    theActorInTheSpotlight().attemptsTo(ValidarAppsSinLimite.validar());
  }

  @And("^VALIDA LA INFORMACION DISPONIBLE DE APPS$")
  public void validaLaInformacionDisponibleDeApps() {
    theActorInTheSpotlight().attemptsTo(ValidarAppsSinLimite.validar());
  }

  // ===========================================
  // Consumo de Voz
  // ===========================================

  @And("^REGRESA ATRAS Y SELECCIONA CONSUMO DE VOZ$")
  public void regresaAtrasYSeleccionaConsumoDeVoz() {
    theActorInTheSpotlight().attemptsTo(ValidarConsumoVoz.validar());
  }

  @And("^VERIFICA MENSAJE AUN NO REGISTRAS CONSUMOS VOZ$")
  public void verificaMensajeAunNoRegistrasConsumosVoz() {
    theActorInTheSpotlight().attemptsTo(ValidarConsumoVoz.validar());
  }

  // ===========================================
  // Consumo de SMS
  // ===========================================

  @And("^REGRESA ATRAS Y SELECCIONA CONSUMO DE SMS$")
  public void regresaAtrasYSeleccionaConsumoDeSMS() {
    theActorInTheSpotlight().attemptsTo(ValidarConsumoSMS.validar());
  }

  @And("^VERIFICA MENSAJE AUN NO REGISTRAS CONSUMOS SMS$")
  public void verificaMensajeAunNoRegistrasConsumosSMS() {
    theActorInTheSpotlight().attemptsTo(ValidarConsumoSMS.validar());
  }

  // ===========================================
  // Consumo Paquetes y Recargas
  // ===========================================

  @And("^REGRESA ATRAS Y SELECCIONA CONSUMO PAQUETES Y RECARGAS$")
  public void regresaAtrasYSeleccionaConsumoPaquetesYRecargas() {
    theActorInTheSpotlight().attemptsTo(ValidarConsumoPaquetesRecargas.validar());
  }

  @And("^VERIFICA LA INFORMACION DISPONIBLE PAQUETES RECARGAS$")
  public void verificaLaInformacionDisponiblePaquetesRecargas() {
    theActorInTheSpotlight().attemptsTo(VerificarInformacionPaquetesRecargas.verificar());
  }

  // ===========================================
  // Opción Paquetes
  // ===========================================

  @And("^VERIFICA OPCION PAQUETES$")
  public void verificaOpcionPaquetes() {
    theActorInTheSpotlight().attemptsTo(VerificarOpcionPaquetes.verificar());
  }

  @And("^VALIDA MENSAJE AUN NO HAS ADQUIRIDO PAQUETES$")
  public void validaMensajeAunNoHasAdquiridoPaquetes() {
    theActorInTheSpotlight().attemptsTo(ValidarMensajeNoHasAdquiridoPaquetes.validar());
  }

  // ===========================================
  // Opción Recargas
  // ===========================================

  @And("^VERIFICA OPCION RECARGAS$")
  public void verificaOpcionRecargas() {
    theActorInTheSpotlight().attemptsTo(VerificarOpcionRecargas.verificar());
  }

  @And("^VALIDA MENSAJE AUN NO HAS HECHO RECARGAS$")
  public void validaMensajeAunNoHasHechoRecargas() {
    theActorInTheSpotlight().attemptsTo(ValidarMensajeNoHasHechoRecargas.validar());
  }

  // ===========================================
  // Opción Consumo del mes
  // ===========================================

  @And("^VERIFICA OPCION CONSUMO DEL MES$")
  public void verificaOpcionConsumoDelMes() {
    theActorInTheSpotlight().attemptsTo(VerificarOpcionConsumoDelMes.verificar());
  }

  @Then("^VALIDA MENSAJE EN ESTE MES NO REGISTRAS CONSUMOS$")
  public void validaMensajeEnEsteMesNoRegistrasConsumos() {
    theActorInTheSpotlight().attemptsTo(ValidarMensajeNoRegistrasConsumos.validar());
  }
}
