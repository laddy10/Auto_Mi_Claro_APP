package stepDefinitions;

import static net.serenitybdd.screenplay.actors.OnStage.theActorInTheSpotlight;


import cucumber.api.java.ast.Y;
import cucumber.api.java.es.Entonces;
import tasks.ConsultarConsumos.*;

/**
 * Step Definitions para el módulo Consultar Consumos Mantiene la consistencia con el estilo
 * existente del proyecto
 */
public class ConsultarConsumosDefinitions {

  @Y("^INGRESA AL MODULO CONSULTAR CONSUMOS$")
  public void ingresaModuloConsultarConsumos() {
    theActorInTheSpotlight().attemptsTo(AccederConsultarConsumos.acceder());
  }

  @Y("^DESPLAZARSE HASTA EL NUMERO Y SELECCIONAR VER DETALLE$")
  public void desplazarseHastaNumeroYSeleccionarVerDetalle() {
    theActorInTheSpotlight().attemptsTo(SeleccionarLineaYVerDetalle.seleccionar());
  }

  @Y("^ESPERA A QUE INGRESE A LA OPCION$")
  public void esperaAQueIngreseALaOpcion() {
    theActorInTheSpotlight().attemptsTo(VerificarTextosDisponibles.verificar());
  }

  @Y("^VERIFICA LOS TEXTOS DISPONIBLES$")
  public void verificaLosTextosDisponibles() {
    theActorInTheSpotlight().attemptsTo(VerificarTextosDisponibles.verificar());
  }

  // ===========================================
  // Consumo de Datos
  // ===========================================

  @Y("^SELECCIONA CONSUMO DE DATOS$")
  public void seleccionaConsumoDeatos() {
    theActorInTheSpotlight().attemptsTo(ValidarConsumoDeDatos.validar());
  }

  @Y("^VALIDA LA INFORMACION DISPONIBLE DE DATOS$")
  public void validaLaInformacionDisponibleDeDatos() {
    theActorInTheSpotlight().attemptsTo(ValidarInformacionDisponibleDatos.validar());
  }

  // ===========================================
  // Apps sin límite de consumo
  // ===========================================

  @Y("^REGRESA ATRAS Y SELECCIONA APPS SIN LIMITE DE CONSUMO$")
  public void regresaAtrasYSeleccionaAppsSinLimiteDeConsumo() {
    theActorInTheSpotlight().attemptsTo(ValidarAppsSinLimite.validar());
  }

  @Y("^VALIDA LA INFORMACION DISPONIBLE DE APPS$")
  public void validaLaInformacionDisponibleDeApps() {
    theActorInTheSpotlight().attemptsTo(ValidarAppsSinLimite.validar());
  }

  // ===========================================
  // Consumo de Voz
  // ===========================================

  @Y("^REGRESA ATRAS Y SELECCIONA CONSUMO DE VOZ$")
  public void regresaAtrasYSeleccionaConsumoDeVoz() {
    theActorInTheSpotlight().attemptsTo(ValidarConsumoVoz.validar());
  }

  @Y("^VERIFICA MENSAJE AUN NO REGISTRAS CONSUMOS VOZ$")
  public void verificaMensajeAunNoRegistrasConsumosVoz() {
    theActorInTheSpotlight().attemptsTo(ValidarConsumoVoz.validar());
  }

  // ===========================================
  // Consumo de SMS
  // ===========================================

  @Y("^REGRESA ATRAS Y SELECCIONA CONSUMO DE SMS$")
  public void regresaAtrasYSeleccionaConsumoDeSMS() {
    theActorInTheSpotlight().attemptsTo(ValidarConsumoSMS.validar());
  }

  @Y("^VERIFICA MENSAJE AUN NO REGISTRAS CONSUMOS SMS$")
  public void verificaMensajeAunNoRegistrasConsumosSMS() {
    theActorInTheSpotlight().attemptsTo(ValidarConsumoSMS.validar());
  }

  // ===========================================
  // Consumo Paquetes y Recargas
  // ===========================================

  @Y("^REGRESA ATRAS Y SELECCIONA CONSUMO PAQUETES Y RECARGAS$")
  public void regresaAtrasYSeleccionaConsumoPaquetesYRecargas() {
    theActorInTheSpotlight().attemptsTo(ValidarConsumoPaquetesRecargas.validar());
  }

  @Y("^VERIFICA LA INFORMACION DISPONIBLE PAQUETES RECARGAS$")
  public void verificaLaInformacionDisponiblePaquetesRecargas() {
    theActorInTheSpotlight().attemptsTo(VerificarInformacionPaquetesRecargas.verificar());
  }

  // ===========================================
  // Opción Paquetes
  // ===========================================

  @Y("^VERIFICA OPCION PAQUETES$")
  public void verificaOpcionPaquetes() {
    theActorInTheSpotlight().attemptsTo(VerificarOpcionPaquetes.verificar());
  }

  @Y("^VALIDA MENSAJE AUN NO HAS ADQUIRIDO PAQUETES$")
  public void validaMensajeAunNoHasAdquiridoPaquetes() {
    theActorInTheSpotlight().attemptsTo(ValidarMensajeNoHasAdquiridoPaquetes.validar());
  }

  // ===========================================
  // Opción Recargas
  // ===========================================

  @Y("^VERIFICA OPCION RECARGAS$")
  public void verificaOpcionRecargas() {
    theActorInTheSpotlight().attemptsTo(VerificarOpcionRecargas.verificar());
  }

  @Y("^VALIDA MENSAJE AUN NO HAS HECHO RECARGAS$")
  public void validaMensajeAunNoHasHechoRecargas() {
    theActorInTheSpotlight().attemptsTo(ValidarMensajeNoHasHechoRecargas.validar());
  }

  // ===========================================
  // Opción Consumo del mes
  // ===========================================

  @Y("^VERIFICA OPCION CONSUMO DEL MES$")
  public void verificaOpcionConsumoDelMes() {
    theActorInTheSpotlight().attemptsTo(VerificarOpcionConsumoDelMes.verificar());
  }

  @Entonces("^VALIDA MENSAJE EN ESTE MES NO REGISTRAS CONSUMOS$")
  public void validaMensajeEnEsteMesNoRegistrasConsumos() {
    theActorInTheSpotlight().attemptsTo(ValidarMensajeNoRegistrasConsumos.validar());
  }
}
