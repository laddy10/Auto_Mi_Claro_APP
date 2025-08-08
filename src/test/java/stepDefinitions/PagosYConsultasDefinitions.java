package stepDefinitions;

import static net.serenitybdd.screenplay.actors.OnStage.theActorInTheSpotlight;
import static utils.Constants.*;

import hooks.ReportHooks;
import interactions.Click.ClickTextoQueContengaX;
import interactions.Scroll.ScrollHastaTexto;
import interactions.comunes.Atras;
import interactions.validations.ValidarTexto;
import interactions.validations.ValidarTextoQueContengaX;
import interactions.wait.WaitFor;
import interactions.wait.WaitForResponse;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import models.User;
import tasks.PagosYConsultas.*;
import tasks.PagosYConsultas.AdquirirProductos.MiniprogramaAdquirirProductos;
import tasks.PagosYConsultas.AdquirirProductos.ValidarPaginaClaro;
import tasks.PagosYConsultas.DetalleDeTuPlan.*;
import tasks.PagosYConsultas.Portabilidad.*;
import tasks.PagosYConsultas.RecargasyPaquetes.*;
import tasks.PagosYConsultas.eSIM.MiniprogramaEsim;
import tasks.PagosYConsultas.eSIM.SeleccionarLineaPostEsim;
import tasks.PagosYConsultas.eSIM.ValidarOpcionesEsim;
import utils.EvidenciaUtils;
import utils.TestDataProvider;

public class PagosYConsultasDefinitions {

  private final User user = TestDataProvider.getRealUser();

  @And("^INGRESA AL MENU PAGOS Y CONSULTAS$")
  public void menuPagosYConsultas() {
    final String paso = "Menu Pagos y consultas";
    theActorInTheSpotlight()
        .attemptsTo(
            ScrollHastaTexto.conTexto(PAGOS_Y_CONSULTAS),
            ClickTextoQueContengaX.elTextoContiene(VER_MAS));
    EvidenciaUtils.registrarCaptura(paso);
  }

  @And("^INGRESA AL PORTAL DE PAGA TU FACTURA$")
  public void portalPagaTuFactura() {
    theActorInTheSpotlight().attemptsTo(PagaTuFactura.pagaTuFactura());
  }

  @And("^VALIDAR REDIRECCION BOTON PAGAR FACTURA$")
  public void botonPagarFactura() {
    theActorInTheSpotlight().attemptsTo(RedireccionPagarFactura.redireccionPagarFactura());
  }

  @And("INGRESA AL PORTAL DE RECARGAS Y PAQUETES")
  public void ingresaPortalRecargas() {
    theActorInTheSpotlight()
        .attemptsTo(
            AccesoRecargasYPaquetes.accederRecargasYPaquetes(),
            ValidarInfoRecargas.validarInfoRecargas());
  }

  @Then("VALIDA PAQUETES LDI")
  public void validaPaquetesLDI() {
    theActorInTheSpotlight().attemptsTo(PaquetesLDI.seleccionarPaquetes());
  }

  @Then("VALIDA PAQUETES DE DATOS")
  public void validaPaquetesDatos() {
    theActorInTheSpotlight().attemptsTo(PaquetesDatos.seleccionarPaquetes());
  }

  @Then("VALIDAR REDIRECCION DE LOS MEDIOS DE PAGO")
  public void redireccionMediosPago() {
    theActorInTheSpotlight().attemptsTo(RedireccionMedioPago.redireccionarMediosDePago());
  }

  @And("^REALIZA PAGO PARCIAL$")
  public void realizaPagoParcial() {
    theActorInTheSpotlight().attemptsTo(RealizarPagoParcial.conLosDatos());
  }

  @Then("VALIDA LA REDIRECCION A MEDIOS DE PAGO DISPONIBLES")
  public void redireccionMediosPagoDisponibles() {
    final String paso5 = "Validar redirección a los medios de pago";

    theActorInTheSpotlight()
        .attemptsTo(
            ValidarTexto.validarTexto(ELEGIR_OTRO_MEDIO_PAGO),
            ValidarTextoQueContengaX.elTextoContiene(TARJETA_C_D),
            ValidarTextoQueContengaX.elTextoContiene(BOTON_BANCOLOMBIA),
            ValidarTextoQueContengaX.elTextoContiene(CODENSA),
            ValidarTextoQueContengaX.elTextoContiene(OTROS_MEDIOS));

    EvidenciaUtils.registrarCaptura(paso5);
  }

  @Then("^VALIDA PAGOS AUTOMATICOS$")
  public void validaPagosAutomaticos() {
    theActorInTheSpotlight().attemptsTo(PagosAutomaticos.validarPagosAutomaticos());
  }

  @Then("^DESCARGA FACTURA$")
  public void descargaFactura() {
    theActorInTheSpotlight().attemptsTo(DescargaFactura.descargarFactura());
  }

  @Then("^ENVIA FACTURA POR CORREO$")
  public void enviaFacturaPorCorreo() {
    theActorInTheSpotlight().attemptsTo(EnviarFacturaPorCorreo.enviarFacturaPorCorreo());
  }

  @Then("^VALIDA DETALLE ULTIMA FACTURACION$")
  public void validaDetalleUltimaFacturacion() {
    theActorInTheSpotlight().attemptsTo(DetalleUltimaFacturacion.validarDetalleUltimaFacturacion());
  }

  @Then("^VALIDA HISTORIAL DE PAGOS$")
  public void validaHistorialDePagos() {
    theActorInTheSpotlight().attemptsTo(HistorialDePagos.validarHistorialDePagos());
  }

  @Then("^SELECCIONA RECARGAS$")
  public void seleccionaRecargas() {
    theActorInTheSpotlight().attemptsTo(Recargas.seleccionarRecargas());
  }

  @Then("^GESTIONA COMPRAS RECURRENTES$")
  public void gestionaComprasRecurrentes() {
    theActorInTheSpotlight()
        .attemptsTo(GestionarComprasRecurrentes.validarGestionarComprasRecurrentes());
  }

  @Then("^VALIDA DETALLE DE TU PLAN$")
  public void validaDetalleTuPlan() {
    theActorInTheSpotlight().attemptsTo(DetalleTuPlan.validarDetalleTuPlan());

    ReportHooks.setLinea(user.getNumero());
  }

  @Then("^VALIDA DETALLE DE TU PLAN PARA LINEA QUE NO PERMITE COMPRAR APLICACIONES$")
  public void validaDetalleTuPlanLineaNoCompraApp() {
    theActorInTheSpotlight().attemptsTo(DetalleTuPlanCompraApp.validarDetalleTuPlan());
  }

  @And("VALIDA DESCRIPCION DEL PLAN")
  public void validaDescripcionDelPlan() {
    theActorInTheSpotlight().attemptsTo(ValidarDescripcionDelPlan.validarInformacionDescripcion());
  }

  @And("INGRESA VER FACTURA")
  public void ingresaVerFactura() {
    theActorInTheSpotlight().attemptsTo(IngresarVerFactura.ingresarVerFactura());
  }

  @Then("VALIDA DIRECCIONAMIENTO PAGAR FACTURA")
  public void validaDireccionamientoPagarFactura() {
    theActorInTheSpotlight()
        .attemptsTo(ValidarDireccionamientoPagarFactura.validarDireccionamientoPagarFactura());
  }

  @And("INGRESA FAMILIA Y AMIGOS")
  public void ingresaFamiliaYAmigos() {
    theActorInTheSpotlight().attemptsTo(FamiliaYAmigos.ingresarFamiliaYAmigos());
  }

  @And("SELECCIONA LINEA POSTPAGO")
  public void seleccionaLineaPostpago() {
    theActorInTheSpotlight().attemptsTo(SeleccionarLineaPostpago.seleccionarLinea());

    ReportHooks.setLinea(user.getNumero());
  }

  @And("VALIDA DIRECCIONAMIENTO FAMILIA Y AMIGOS")
  public void validaDireccionamientoFamiliaYAmigos() {
    theActorInTheSpotlight().attemptsTo(ValidarFamiliaYAmigos.validarDireccionamiento());
  }

  @And("INGRESA OPCION FAMILIA Y AMIGOS")
  public void ingresaOpcionFamiliaYAmigos() {
    theActorInTheSpotlight().attemptsTo(IngresarFamiliaYAmigos.ingresarOpcion());
  }

  @And("VALIDA EL GRUPO DE FAMILIA Y AMIGOS")
  public void validaElGrupoDeFamiliaYAmigos() {
    theActorInTheSpotlight().attemptsTo(GrupoFamiliaYAmigos.validarGrupo());
  }

  @And("REGRESA ATRAS")
  public void regresaAtras() {
    theActorInTheSpotlight().attemptsTo(Atras.irAtras());
  }

  @And("INGRESA OPCION ELEGIDOS TODO DESTINO")
  public void ingresaOpcionElegidosTodoDestino() {
    theActorInTheSpotlight().attemptsTo(ElegidosTodoDestino.ingresarOpcion());
  }

  @And("VALIDA DIRECCIONAMIENTO ELEGIDOS TODO DESTINO")
  public void validaDireccionamientoElegidosTodoDestino() {
    theActorInTheSpotlight().attemptsTo(ValidarElegidosTodoDestino.validarDireccionamiento());
  }

  @Then("VE TERMINOS Y CONDICIONES")
  public void veTerminosYCondiciones() {
    theActorInTheSpotlight().attemptsTo(VerTerminosYCondiciones.verTerminos());
  }

  @And("INGRESA Y VALIDA APLICACIONES ELEGIBLES")
  public void ingresaYValidaAplicacionesElegibles() {
    theActorInTheSpotlight().attemptsTo(AplicacionesElegibles.ingresarYValidar());
  }

  @And("INGRESA Y VALIDA COMPRAR APLICACIONES")
  public void ingresaYValidaComprarAplicaciones() {
    theActorInTheSpotlight().attemptsTo(ComprarAplicaciones.ingresarYValidar());
  }

  @Then("ADMINISTRA APLICACIONES INCLUIDAS Y VALIDA POPUP")
  public void administraAplicacionesIncluidasYValidaPopup() {
    theActorInTheSpotlight().attemptsTo(AdministrarAplicacionesIncluidas.administrarYValidar());
  }

  @Then("^INGRESA Y VALIDA COMPRAR APLICACIONES NO PERMITIDO$")
  public void ingresaYValidaComprarAplicacionesNoPermitido() {
    theActorInTheSpotlight().attemptsTo(ComprarAplicacionesNoPermitido.ingresarYValidar());
  }

  @And("INGRESA MEJORAR PLAN")
  public void ingresaMejorarPlan() {
    EvidenciaUtils.registrarCaptura("Ingresar opción Mejorar plan");

    theActorInTheSpotlight()
        .attemptsTo(
            ClickTextoQueContengaX.elTextoContiene(MEJORAR_PLAN),
            WaitForResponse.withText(VER_PLANES_ESPECIALES));
  }

  @Then("VALIDA DIRECCIONAMIENTO MEJORAR PLAN")
  public void validaDireccionamientoMejorarPlan() {
    theActorInTheSpotlight().attemptsTo(MejorarPlan.validarDireccionamiento());
  }

  @And("INGRESA PAQUETES ADICIONALES")
  public void ingresaPaquetesAdicionales() {
    EvidenciaUtils.registrarCaptura("Ingresar opción Paquetes adicionales");

    theActorInTheSpotlight()
        .attemptsTo(
            ClickTextoQueContengaX.elTextoContiene(PAQUETES_ADICIONALES), WaitFor.aTime(3000));
  }

  @Then("VALIDA DIRECCIONAMIENTO PAQUETES ADICIONALES")
  public void validaDireccionamientoPaquetesAdicionales() {
    theActorInTheSpotlight().attemptsTo(PaquetesAdicionales.validarDireccionamiento());
  }

  @And("INGRESA ADMINISTRAR ROAMING")
  public void ingresaAdministrarRoaming() {
    theActorInTheSpotlight().attemptsTo(AdministrarRoaming.ingresarRoaming());
  }

  @And("INGRESA ADMINISTRAR ROAMING SERVICIO ACTIVO")
  public void administrarRoamingServicioActivo() {
    theActorInTheSpotlight().attemptsTo(AdministrarRoamingActivo.ingresarRoaming());
  }

  @Then("VALIDA DIRECCIONAMIENTO ROAMING")
  public void validaDireccionamientoRoaming() {
    theActorInTheSpotlight().attemptsTo(DireccionamientoRoaming.validarDireccionamiento());
  }

  @And("HACE CLIC EN ADELANTA TU SALDO")
  public void haceClicEnAdelantaTuSaldo() {
    EvidenciaUtils.registrarCaptura("Hacer clic en Adelanta tu saldo");

    theActorInTheSpotlight()
        .attemptsTo(
            ClickTextoQueContengaX.elTextoContiene(ADELANTA_TU_SALDO),
            WaitForResponse.withText(POSTPAGO));
  }

  @And("VALIDA DIRECCIONAMIENTO CORRECTO")
  public void validaDireccionamientoCorrectol() {
    theActorInTheSpotlight().attemptsTo(AdelantaSaldo.validarDireccionamiento());
  }

  @Then("VERIFICA POPUP ADELANTO")
  public void verificaPopupAdelanto() {
    theActorInTheSpotlight().attemptsTo(PopupAdelanto.verificarPopup());
  }

  @Then("VALIDA DIRECCIONAMIENTO AGENDAR TURNOS")
  public void validaDireccionamientoAgendarTurnos() {
    theActorInTheSpotlight().attemptsTo(AgendarTurnos.validarDireccionamiento());
  }

  @Then("INGRESA A FAMILIA Y AMIGOS Y GESTIONA NUMEROS")
  public void ingresaAFamiliaYAmigosYGestionaNumeros() {
    theActorInTheSpotlight().attemptsTo(DesactivarFamiliaYAmigos.gestionarFamiliaYAmigos());
  }

  @Then("VALIDA DIRECCIONAMIENTO A ROAMING")
  public void validaDireccionamientoARoaming() {
    theActorInTheSpotlight().attemptsTo(ValidarDireccionamientoARoaming.validarDireccionamiento());
  }

  @And("INGRESA A PORTABILIDAD")
  public void ingresaAPortabilidad() {
    EvidenciaUtils.registrarCaptura("Hacer clic en Portabilidad");

    theActorInTheSpotlight()
        .attemptsTo(
            ClickTextoQueContengaX.elTextoContiene(PORTABILIDAD),
            WaitForResponse.withText(ESTADO_DE_PORTABILIDAD));
  }

  @And("VALIDA VERSION DE MINIPROGRAMA PORTABILIDAD")
  public void validaVersionDeMiniprogramaPortabilidad() {
    theActorInTheSpotlight().attemptsTo(MiniprogramaPortabilidad.validarVersion());
  }

  @Then("VALIDA ESTADO DE PORTABILIDAD Y REDIRECCION")
  public void validaEstadoDePortabilidadYRedireccion() {
    theActorInTheSpotlight().attemptsTo(EstadoPortabilidad.validarEstadoYRedireccion());
  }

  @Then("VALIDA ENVIO DE SIM CARD Y REDIRECCION")
  public void validaEnvioSimCardYRedireccion() {
    theActorInTheSpotlight().attemptsTo(EnvioSimCard.validarEnvioSimCard());
  }

  @Then("VALIDA PORTABILIDAD PREPAGO Y REDIRECCION")
  public void validaPortabilidadPrepagoYRedireccion() {
    theActorInTheSpotlight().attemptsTo(PortabilidadPrepago.validarPortabilidadPrepago());
  }

  @Then("VALIDA PORTABILIDAD POSTPAGO Y REDIRECCION")
  public void validaPortabilidadPostpagoYRedireccion() {
    theActorInTheSpotlight().attemptsTo(PortabilidadPostpago.validarPortabilidadPostpago());
  }

  @And("INGRESA A ADQUIRIR PRODUCTOS")
  public void ingresaAAdquirirProductos() {
    theActorInTheSpotlight().attemptsTo(ScrollHastaTexto.conTexto(ADQUIRIR_PRODUCTOS));

    EvidenciaUtils.registrarCaptura("Hacer clic en Adquirir Productos");

    theActorInTheSpotlight()
        .attemptsTo(
            ClickTextoQueContengaX.elTextoContiene(ADQUIRIR_PRODUCTOS),
            WaitForResponse.withText(POSTPAGO));
  }

  @And("VALIDA VERSION DE MINIPROGRAMA ADQUIRIR")
  public void validaVersionDeMiniprogramaAdquirir() {
    theActorInTheSpotlight().attemptsTo(MiniprogramaAdquirirProductos.validarVersion());
  }

  @Then("VALIDA REDIRECCIONAMIENTO A PAGINA CLARO")
  public void validaRedireccionamientoAPaginaClaro() {
    theActorInTheSpotlight().attemptsTo(ValidarPaginaClaro.validarRedireccionamiento());
  }

  @And("INGRESA A ESIM CLARO")
  public void ingresaAEsimClaro() {
    theActorInTheSpotlight().attemptsTo(ScrollHastaTexto.conTexto(ESIM_CLARO));

    EvidenciaUtils.registrarCaptura("Hacer clic en eSIM Claro");

    theActorInTheSpotlight()
        .attemptsTo(
            ClickTextoQueContengaX.elTextoContiene(ESIM_CLARO), WaitForResponse.withText(POSTPAGO));
  }

  @Then("SELECCIONA LINEA POSTPAGO eSIM")
  public void seleccionaLineaPostpagoeSIM() {
    theActorInTheSpotlight().attemptsTo(SeleccionarLineaPostEsim.seleccionarLinea());

    ReportHooks.setLinea(user.getNumero());
  }

  @And("PRESIONA BOTON SIGUIENTE")
  public void presionaBotonSiguiente() {
    EvidenciaUtils.registrarCaptura("Boton Siguiente");

    theActorInTheSpotlight().attemptsTo(WaitFor.aTime(5000));

    EvidenciaUtils.registrarCaptura("Hacer clic en boton Continuar");

    theActorInTheSpotlight().attemptsTo(ClickTextoQueContengaX.elTextoContiene(SIGUIENTE));
  }

  @And("VALIDA VERSION DE MINIPROGRAMA ESIM")
  public void validaVersionDeMiniprogramaEsim() {
    theActorInTheSpotlight().attemptsTo(MiniprogramaEsim.validarVersion());
  }

  @Then("VALIDA DIRECCIONAMIENTO OPCIONES ESIM")
  public void validaDireccionamientoOpcionesEsim() {
    theActorInTheSpotlight().attemptsTo(ValidarOpcionesEsim.validarOpciones());
  }

  @Then("GESTIONA CERTIFICACION CUENTA AL DIA")
  public void gestionaCertificacionCuentaAlDia() {
    theActorInTheSpotlight().attemptsTo(CertificacionCuentaAlDia.gestionarCertificacion());
    ReportHooks.setLinea(user.getNumero());
  }

  @Then("VALIDA DIRECCIONAMIENTO VEHICULO CONECTADO")
  public void validaDireccionamientoVehiculoConectado() {
    theActorInTheSpotlight().attemptsTo(VehiculoConectado.validarDireccionamiento());
  }

  @Then("VALIDA DIRECCIONAMIENTO CORRECTO PAGOS")
  public void validaDireccionamientoCorrectoPagos() {
    theActorInTheSpotlight().attemptsTo(Pagos.validarDireccionamiento());
  }

  @Then("VALIDA DIRECCIONAMIENTO CORRECTO LEGALIZACION")
  public void validaDireccionamientoCorrectoLegalizacion() {
    theActorInTheSpotlight().attemptsTo(LegalizacionLineas.validarDireccionamiento());
  }

  @Then("^VALIDA DIRECCIONAMIENTO CORRECTO BENEFICIOS$")
  public void validaDireccionamientoCorrectoBeneficios() {
    theActorInTheSpotlight().attemptsTo(Beneficios.validarDireccionamiento());
  }

  @And("^SELECCIONAR LINEA POSTPAGO QUE PERMITA ADMINISTRAR APPS$")
  public void seleccionarLineaPostpagoQuePermitaAdministrarApps() {
    theActorInTheSpotlight().attemptsTo(DetalleTuPlanLineaEspecifica.seleccionarLinea());
  }

  @Then("^ADMINISTRAR Y VALIDAR APLICACIONES INCLUIDAS$")
  public void administrarYValidarAplicacionesIncluidas() {
    theActorInTheSpotlight()
        .attemptsTo(AdministrarAplicacionesIncluidasPermitido.administrarYValidar());
  }

  @Then("^ADMINISTRAR Y VALIDAR APLICACIONES NO PERMITIDO$")
  public void administrarYValidarAplicacionesNoPermitido() {
    theActorInTheSpotlight().attemptsTo(AdministrarAplicacionesNoPermitido.administrarYValidar());
  }

  @And("^VALIDA DETALLE DE TU PLAN LINEA ESPECIFICA$")
  public void validaDetalleDeTuPlanLineaEspecifica() {
    theActorInTheSpotlight()
        .attemptsTo(DetalleTuPlanLineaEspecificaPaquetes.validarDetalleTuPlan());
  }

  @Then("^VALIDA POPUP NO TIENE PAQUETES$")
  public void validaPopupNoTienePaquetes() {
    theActorInTheSpotlight().attemptsTo(PaquetesAdicionalesNoTienePaquetes.validarPopup());
  }

  @Then("VALIDA VIGENCIA Y PLANES ROAMING")
  public void validaRoamingVigenciaPlanes() {
    theActorInTheSpotlight().attemptsTo(AdministrarRoamingCompleto.validarFlujoCompleto());
  }

  @Then("^VALIDA DIRECCIONAMIENTO PAQUETES COMPLEMENTARIOS$")
  public void validaDireccionamientoPaquetesComplementarios() {
    theActorInTheSpotlight().attemptsTo(PaquetesComplementarios.validarDireccionamiento());

    ReportHooks.setLinea(user.getNumero());
  }
}
