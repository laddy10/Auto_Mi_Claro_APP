package stepDefinitions;

import static net.serenitybdd.screenplay.actors.OnStage.theActorInTheSpotlight;
import static userinterfaces.PagosYConsultasPage.*;
import static utils.Constants.*;


import cucumber.api.java.ast.Y;
import cucumber.api.java.es.Entonces;
import hooks.ReportHooks;
import interactions.Click.ClickTextoQueContengaX;
import interactions.Scroll.Scroll;
import interactions.Scroll.ScrollHastaTexto;
import interactions.comunes.Atras;
import interactions.validations.ValidarTexto;
import interactions.validations.ValidarTextoQueContengaX;
import interactions.wait.WaitFor;
import interactions.wait.WaitForResponse;
import java.util.List;
import models.User;
import net.serenitybdd.core.pages.WebElementFacade;
import net.serenitybdd.screenplay.actions.Click;
import tasks.MediosDePagos.Bancolombia;
import tasks.MediosDePagos.PSEPagaTuFactura;
import tasks.MediosDePagos.TarjetasPagaTuFactura;
import tasks.PagosYConsultas.*;
import tasks.PagosYConsultas.AdquirirProductos.MiniprogramaAdquirirProductos;
import tasks.PagosYConsultas.AdquirirProductos.ValidarPaginaClaro;
import tasks.PagosYConsultas.DetalleDeTuPlan.*;
import tasks.PagosYConsultas.Portabilidad.*;
import tasks.PagosYConsultas.RecargasyPaquetes.*;
import tasks.PagosYConsultas.eSIM.MiniprogramaEsim;
import tasks.PagosYConsultas.eSIM.SeleccionarLineaPostEsim;
import tasks.PagosYConsultas.eSIM.ValidarOpcionesEsim;
import tasks.Prepago.RecargasyPaquetes.SeleccionLineaPrepago;
import utils.EvidenciaUtils;
import utils.TestDataProvider;

public class PagosYConsultasDefinitions {

  private final User user = TestDataProvider.getRealUser();

  @Y("^INGRESA AL MENU PAGOS Y CONSULTAS$")
  public void menuPagosYConsultas() {
    final String paso = "Menu Pagos y consultas";

    theActorInTheSpotlight()
            .attemptsTo(
                    ScrollHastaTexto.conTexto(HAZLO_TODO_EN_LINEA),
                    ScrollHastaTexto.conTexto(DISFRUTA_DE_TIEMPO_CON_MICLARO),
                    Scroll.scrollMediaVista(),
                    ClickTextoQueContengaX.elTextoContiene(VER_MAS));

    EvidenciaUtils.registrarCaptura(paso);
  }

  @Y("^INGRESA A OPCION DE PAGA TU FACTURA$")
  public void portalPagaTuFactura() {
    theActorInTheSpotlight().attemptsTo(PagaTuFactura.pagaTuFactura());
  }

  @Y("^VALIDAR REDIRECCION BOTON PAGAR FACTURA$")
  public void botonPagarFactura() {
    theActorInTheSpotlight().attemptsTo(RedireccionPagarFactura.redireccionPagarFactura());
  }

  @Y("INGRESA AL PORTAL DE RECARGAS Y PAQUETES")
  public void ingresaPortalRecargas() {
    theActorInTheSpotlight()
        .attemptsTo(
            AccesoRecargasYPaquetes.accederRecargasYPaquetes(),
            ValidarInfoRecargas.validarInfoRecargas());

    ReportHooks.setLinea(user.getNumero());
  }

  @Y("INGRESA A RECARGAS Y PAQUETES EN PREPAGO")
  public void ingresaPortalRecargasPrepago() {
    theActorInTheSpotlight()
        .attemptsTo(SeleccionLineaPrepago.seleccionar(), ValidarInfoRecargas.validarInfoRecargas());
  }

  @Entonces("VALIDA PAQUETES LDI")
  public void validaPaquetesLDI() {
    theActorInTheSpotlight().attemptsTo(PaquetesLDI.seleccionarPaquetes());
  }

  @Entonces("VALIDA PAQUETES DE DATOS")
  public void validaPaquetesDatos() {
    theActorInTheSpotlight().attemptsTo(PaquetesDatos.seleccionarPaquetes());
  }

  @Entonces("VALIDAR REDIRECCION DE LOS MEDIOS DE PAGO")
  public void redireccionMediosPago() {
    theActorInTheSpotlight().attemptsTo(RedireccionMedioPago.redireccionarMediosDePago());
  }

  @Y("^REALIZA PAGO PARCIAL$")
  public void realizaPagoParcial() {
    theActorInTheSpotlight().attemptsTo(RealizarPagoParcial.conLosDatos());
  }

  @Entonces("^VALIDA LA REDIRECCION A MEDIOS DE PAGO DISPONIBLES$")
  public void redireccionMediosPagoDisponibles() {
    List<WebElementFacade> lblelegirotromediopago =
        LBL_ELEGIR_OTRO_MEDIO_PAGO.resolveAllFor(theActorInTheSpotlight());

    if (!lblelegirotromediopago.isEmpty()) {

      final String paso5 = "Validar redirección a los medios de pago";

      theActorInTheSpotlight()
          .attemptsTo(
              ValidarTexto.validarTexto(ELEGIR_OTRO_MEDIO_PAGO),
              ValidarTextoQueContengaX.elTextoContiene(TARJETA_C_D),
              ValidarTextoQueContengaX.elTextoContiene(BOTON_BANCOLOMBIA),
              //ValidarTextoQueContengaX.elTextoContiene(PSE1),
              ValidarTextoQueContengaX.elTextoContiene(OTROS_MEDIOS));

      EvidenciaUtils.registrarCaptura(paso5);
    }
  }

  @Entonces("^VALIDA PAGOS AUTOMATICOS$")
  public void validaPagosAutomaticos() {
    theActorInTheSpotlight().attemptsTo(PagosAutomaticos.validarPagosAutomaticos());
  }

  @Entonces("^DESCARGA FACTURA$")
  public void descargaFactura() {
    theActorInTheSpotlight().attemptsTo(DescargaFactura.descargarFactura());
  }

  @Entonces("^ENVIA FACTURA POR CORREO$")
  public void enviaFacturaPorCorreo() {
    theActorInTheSpotlight().attemptsTo(EnviarFacturaPorCorreo.enviarFacturaPorCorreo());
  }

  @Entonces("^VALIDA DETALLE ULTIMA FACTURACION$")
  public void validaDetalleUltimaFacturacion() {
    theActorInTheSpotlight().attemptsTo(DetalleUltimaFacturacion.validarDetalleUltimaFacturacion());
  }

  @Entonces("^VALIDA HISTORIAL DE PAGOS$")
  public void validaHistorialDePagos() {
    theActorInTheSpotlight().attemptsTo(HistorialDePagos.validarHistorialDePagos());
  }

  @Entonces("^SELECCIONA RECARGAS$")
  public void seleccionaRecargas() {
    theActorInTheSpotlight().attemptsTo(Recargas.seleccionarRecargas());
  }

  @Entonces("^GESTIONA COMPRAS RECURRENTES$")
  public void gestionaComprasRecurrentes() {
    theActorInTheSpotlight()
        .attemptsTo(GestionarComprasRecurrentes.validarGestionarComprasRecurrentes());
  }

  @Entonces("^VALIDA DETALLE DE TU PLAN$")
  public void validaDetalleTuPlan() {
    theActorInTheSpotlight().attemptsTo(DetalleTuPlan.validarDetalleTuPlan());

    ReportHooks.setLinea(user.getNumeroFamiliayAmigos());
  }

  @Entonces("^VALIDA DETALLE DE TU PLAN PARA LINEA QUE NO PERMITE COMPRAR APLICACIONES$")
  public void validaDetalleTuPlanLineaNoCompraApp() {
    theActorInTheSpotlight().attemptsTo(DetalleTuPlanCompraApp.validarDetalleTuPlan());
  }

  @Y("VALIDA DESCRIPCION DEL PLAN")
  public void validaDescripcionDelPlan() {
    theActorInTheSpotlight().attemptsTo(ValidarDescripcionDelPlan.validarInformacionDescripcion());
  }

  @Y("INGRESA VER FACTURA")
  public void ingresaVerFactura() {
    theActorInTheSpotlight().attemptsTo(IngresarVerFactura.ingresarVerFactura());
  }

  @Entonces("VALIDA DIRECCIONAMIENTO PAGAR FACTURA")
  public void validaDireccionamientoPagarFactura() {
    theActorInTheSpotlight()
        .attemptsTo(ValidarDireccionamientoPagarFactura.validarDireccionamientoPagarFactura());
  }

  @Y("INGRESA FAMILIA Y AMIGOS")
  public void ingresaFamiliaYAmigos() {
    theActorInTheSpotlight().attemptsTo(FamiliaYAmigos.ingresarFamiliaYAmigos());
  }

  @Y("SELECCIONA LINEA POSTPAGO")
  public void seleccionaLineaPostpago() {
    theActorInTheSpotlight().attemptsTo(SeleccionarLineaPostpago.seleccionarLinea());

    ReportHooks.setLinea(user.getNumero());
  }

  @Y("VALIDA DIRECCIONAMIENTO FAMILIA Y AMIGOS")
  public void validaDireccionamientoFamiliaYAmigos() {
    theActorInTheSpotlight().attemptsTo(ValidarFamiliaYAmigos.validarDireccionamiento());
  }

  @Y("INGRESA OPCION FAMILIA Y AMIGOS")
  public void ingresaOpcionFamiliaYAmigos() {
    theActorInTheSpotlight().attemptsTo(IngresarFamiliaYAmigos.ingresarOpcion());
  }

  @Y("VALIDA EL GRUPO DE FAMILIA Y AMIGOS")
  public void validaElGrupoDeFamiliaYAmigos() {
    theActorInTheSpotlight().attemptsTo(GrupoFamiliaYAmigos.validarGrupo());
  }

  @Y("REGRESA ATRAS")
  public void regresaAtras() {
    theActorInTheSpotlight().attemptsTo(Atras.irAtras());
  }

  @Y("INGRESA OPCION ELEGIDOS TODO DESTINO")
  public void ingresaOpcionElegidosTodoDestino() {
    theActorInTheSpotlight().attemptsTo(ElegidosTodoDestino.ingresarOpcion());
  }

  @Y("VALIDA DIRECCIONAMIENTO ELEGIDOS TODO DESTINO")
  public void validaDireccionamientoElegidosTodoDestino() {
    theActorInTheSpotlight().attemptsTo(ValidarElegidosTodoDestino.validarDireccionamiento());
  }

  @Entonces("VE TERMINOS Y CONDICIONES")
  public void veTerminosYCondiciones() {
    theActorInTheSpotlight().attemptsTo(VerTerminosYCondiciones.verTerminos());
  }

  @Y("INGRESA Y VALIDA APLICACIONES ELEGIBLES")
  public void ingresaYValidaAplicacionesElegibles() {
    theActorInTheSpotlight().attemptsTo(AplicacionesElegibles.ingresarYValidar());
  }

  @Y("INGRESA APLICACIONES ELEGIBLES PERMITIDO")
  public void ingresaAplicacionesElegiblesPermitido() {
    theActorInTheSpotlight().attemptsTo(AplicacionesElegiblesPermitido.ingresarYValidarPermitido());
  }

  @Y("INGRESA Y VALIDA COMPRAR APLICACIONES")
  public void ingresaYValidaComprarAplicaciones() {
    theActorInTheSpotlight().attemptsTo(ComprarAplicaciones.ingresarYValidar());
  }

  @Entonces("ADMINISTRA APLICACIONES INCLUIDAS Y VALIDA POPUP")
  public void administraAplicacionesIncluidasYValidaPopup() {
    theActorInTheSpotlight().attemptsTo(AdministrarAplicacionesIncluidas.administrarYValidar());
  }

  @Entonces("^VALIDAR COMPRAR APLICACIONES NO PERMITIDO$")
  public void validarComprarAplicacionesNoPermitido() {
    theActorInTheSpotlight().attemptsTo(ComprarAplicacionesNoPermitido.ingresarYValidar());
  }

  @Y("INGRESA MEJORAR PLAN")
  public void ingresaMejorarPlan() {
    EvidenciaUtils.registrarCaptura("Ingresar opción Gestionar mi plan - Mejorar plan");

    theActorInTheSpotlight()
        .attemptsTo(
            ClickTextoQueContengaX.elTextoContiene(GESTIONAR_MI_PLAN),
            ClickTextoQueContengaX.elTextoContiene(MEJORAR_PLAN),
            WaitForResponse.withText(VER_PLANES_ESPECIALES));
  }

  @Entonces("VALIDA DIRECCIONAMIENTO MEJORAR PLAN")
  public void validaDireccionamientoMejorarPlan() {
    theActorInTheSpotlight().attemptsTo(MejorarPlan.validarDireccionamiento());
  }

  @Y("INGRESA PAQUETES ADICIONALES")
  public void ingresaPaquetesAdicionales() {
    EvidenciaUtils.registrarCaptura("Ingresar opción Paquetes adicionales");

    theActorInTheSpotlight()
        .attemptsTo(
            ClickTextoQueContengaX.elTextoContiene(PAQUETES_ADICIONALES), WaitFor.aTime(3000));
  }

  @Entonces("VALIDA DIRECCIONAMIENTO PAQUETES ADICIONALES")
  public void validaDireccionamientoPaquetesAdicionales() {
    theActorInTheSpotlight().attemptsTo(PaquetesAdicionales.validarDireccionamiento());
  }

  @Y("INGRESA ADMINISTRAR ROAMING")
  public void ingresaAdministrarRoaming() {
    theActorInTheSpotlight().attemptsTo(AdministrarRoaming.ingresarRoaming());
  }

  @Y("ADMINISTRAR ROAMING SERVICIO ACTIVO")
  public void administrarRoaServActivo() {
    theActorInTheSpotlight().attemptsTo(AdministrarRoamingActivo.ingresarRoaming());
  }

  @Entonces("VALIDA DIRECCIONAMIENTO ROAMING")
  public void validaDireccionamientoRoaming() {
    theActorInTheSpotlight().attemptsTo(DireccionamientoRoaming.validarDireccionamiento());
  }

  @Y("HACE CLIC EN ADELANTA TU SALDO")
  public void haceClicEnAdelantaTuSaldo() {
    EvidenciaUtils.registrarCaptura("Hacer clic en Adelanta tu saldo");

    theActorInTheSpotlight()
        .attemptsTo(
            ClickTextoQueContengaX.elTextoContiene(ADELANTA_TU_SALDO),
            WaitForResponse.withText(POSTPAGO));
  }

  @Y("VALIDA DIRECCIONAMIENTO CORRECTO")
  public void validaDireccionamientoCorrectol() {
    theActorInTheSpotlight().attemptsTo(AdelantaSaldo.validarDireccionamiento());
  }

  @Entonces("VERIFICA POPUP ADELANTO")
  public void verificaPopupAdelanto() {
    theActorInTheSpotlight().attemptsTo(PopupAdelanto.verificarPopup());
  }

  @Entonces("VALIDA DIRECCIONAMIENTO AGENDAR TURNOS")
  public void validaDireccionamientoAgendarTurnos() {
    theActorInTheSpotlight().attemptsTo(AgendarTurnos.validarDireccionamiento());
  }

  @Entonces("INGRESA A FAMILIA Y AMIGOS Y GESTIONA NUMEROS")
  public void ingresaAFamiliaYAmigosYGestionaNumeros() {
    theActorInTheSpotlight().attemptsTo(DesactivarFamiliaYAmigos.gestionarFamiliaYAmigos());
  }

  @Entonces("VALIDA DIRECCIONAMIENTO A ROAMING")
  public void validaDireccionamientoARoaming() {
    theActorInTheSpotlight().attemptsTo(ValidarDireccionamientoARoaming.validarDireccionamiento());
  }

  @Y("INGRESA A PORTABILIDAD")
  public void ingresaAPortabilidad() {
    EvidenciaUtils.registrarCaptura("Hacer clic en Portabilidad");

    theActorInTheSpotlight()
        .attemptsTo(
            ClickTextoQueContengaX.elTextoContiene(PORTABILIDAD),
            WaitForResponse.withText(ESTADO_DE_PORTABILIDAD));
  }

  @Y("VALIDA VERSION DE MINIPROGRAMA PORTABILIDAD")
  public void validaVersionDeMiniprogramaPortabilidad() {
    theActorInTheSpotlight().attemptsTo(MiniprogramaPortabilidad.validarVersion());
  }

  @Entonces("VALIDA ESTADO DE PORTABILIDAD Y REDIRECCION")
  public void validaEstadoDePortabilidadYRedireccion() {
    theActorInTheSpotlight().attemptsTo(EstadoPortabilidad.validarEstadoYRedireccion());
  }

  @Entonces("VALIDA ENVIO DE SIM CARD Y REDIRECCION")
  public void validaEnvioSimCardYRedireccion() {
    theActorInTheSpotlight().attemptsTo(EnvioSimCard.validarEnvioSimCard());
  }

  @Entonces("VALIDA PORTABILIDAD PREPAGO Y REDIRECCION")
  public void validaPortabilidadPrepagoYRedireccion() {
    theActorInTheSpotlight().attemptsTo(PortabilidadPrepago.validarPortabilidadPrepago());
  }

  @Entonces("VALIDA PORTABILIDAD POSTPAGO Y REDIRECCION")
  public void validaPortabilidadPostpagoYRedireccion() {
    theActorInTheSpotlight().attemptsTo(PortabilidadPostpago.validarPortabilidadPostpago());
  }

  @Y("INGRESA A ADQUIRIR PRODUCTOS")
  public void ingresaAAdquirirProductos() {
    theActorInTheSpotlight().attemptsTo(ScrollHastaTexto.conTexto(ADQUIRIR_PRODUCTOS));

    EvidenciaUtils.registrarCaptura("Hacer clic en Adquirir Productos");

    theActorInTheSpotlight()
        .attemptsTo(
            ClickTextoQueContengaX.elTextoContiene(ADQUIRIR_PRODUCTOS),
            WaitForResponse.withText(POSTPAGO));
  }

  @Y("VALIDA VERSION DE MINIPROGRAMA ADQUIRIR")
  public void validaVersionDeMiniprogramaAdquirir() {
    theActorInTheSpotlight().attemptsTo(MiniprogramaAdquirirProductos.validarVersion());
  }

  @Entonces("VALIDA REDIRECCIONAMIENTO A PAGINA CLARO")
  public void validaRedireccionamientoAPaginaClaro() {
    theActorInTheSpotlight().attemptsTo(ValidarPaginaClaro.validarRedireccionamiento());
  }

  @Y("INGRESA A ESIM CLARO")
  public void ingresaAEsimClaro() {
    theActorInTheSpotlight().attemptsTo(ScrollHastaTexto.conTexto(ESIM_CLARO));

    EvidenciaUtils.registrarCaptura("Hacer clic en eSIM Claro");

    theActorInTheSpotlight()
        .attemptsTo(
            ClickTextoQueContengaX.elTextoContiene(ESIM_CLARO), WaitForResponse.withText(POSTPAGO));
  }

  @Entonces("INGRESAR LINEA POSTPAGO eSIM")
  public void ingresarLineaPostpagoeSIM() {
    theActorInTheSpotlight().attemptsTo(SeleccionarLineaPostEsim.seleccionarLinea());

    ReportHooks.setLinea(user.getNumero());
  }

  @Y("PRESIONA BOTON SIGUIENTE")
  public void presionaBotonSiguiente() {
    EvidenciaUtils.registrarCaptura("Boton Siguiente");

    theActorInTheSpotlight().attemptsTo(WaitFor.aTime(5000));

    EvidenciaUtils.registrarCaptura("Hacer clic en boton Continuar");

    theActorInTheSpotlight().attemptsTo(ClickTextoQueContengaX.elTextoContiene(SIGUIENTE));
  }

  @Y("VALIDA VERSION DE MINIPROGRAMA ESIM")
  public void validaVersionDeMiniprogramaEsim() {
    theActorInTheSpotlight().attemptsTo(MiniprogramaEsim.validarVersion());
  }

  @Entonces("VALIDA DIRECCIONAMIENTO OPCIONES ESIM")
  public void validaDireccionamientoOpcionesEsim() {
    theActorInTheSpotlight().attemptsTo(ValidarOpcionesEsim.validarOpciones());
  }

  @Entonces("GESTIONA CERTIFICACION CUENTA AL DIA")
  public void gestionaCertificacionCuentaAlDia() {
    theActorInTheSpotlight().attemptsTo(CertificacionCuentaAlDia.gestionarCertificacion());
    ReportHooks.setLinea(user.getNumero());
  }

  @Entonces("VALIDA DIRECCIONAMIENTO VEHICULO CONECTADO")
  public void validaDireccionamientoVehiculoConectado() {
    theActorInTheSpotlight().attemptsTo(VehiculoConectado.validarDireccionamiento());
  }

  @Entonces("VALIDA DIRECCIONAMIENTO CORRECTO PAGOS")
  public void validaDireccionamientoCorrectoPagos() {
    theActorInTheSpotlight().attemptsTo(Pagos.validarDireccionamiento());
  }

  @Entonces("VALIDA DIRECCIONAMIENTO CORRECTO LEGALIZACION")
  public void validaDireccionamientoCorrectoLegalizacion() {
    theActorInTheSpotlight().attemptsTo(LegalizacionLineas.validarDireccionamiento());
  }

  @Entonces("^VALIDA DIRECCIONAMIENTO CORRECTO BENEFICIOS$")
  public void validaDireccionamientoCorrectoBeneficios() {
    theActorInTheSpotlight().attemptsTo(Beneficios.validarDireccionamiento());
  }

  @Y("^SELECCIONAR LINEA POSTPAGO QUE PERMITA ADMINISTRAR APPS$")
  public void seleccionarLineaPostpagoQuePermitaAdministrarApps() {
    theActorInTheSpotlight().attemptsTo(DetalleTuPlanLineaEspecifica.seleccionarLinea());
  }

  @Entonces("^ADMINISTRAR Y VALIDAR APLICACIONES INCLUIDAS$")
  public void administrarYValidarAplicacionesIncluidas() {
    theActorInTheSpotlight()
        .attemptsTo(AdministrarAplicacionesIncluidasPermitido.administrarYValidar());
  }

  @Entonces("^ADMINISTRAR Y VALIDAR APLICACIONES NO PERMITIDO$")
  public void administrarYValidarAplicacionesNoPermitido() {
    theActorInTheSpotlight().attemptsTo(AdministrarAplicacionesNoPermitido.administrarYValidar());
  }

  @Y("^VALIDA DETALLE DE TU PLAN LINEA ESPECIFICA$")
  public void validaDetalleDeTuPlanLineaEspecifica() {
    theActorInTheSpotlight()
        .attemptsTo(DetalleTuPlanLineaEspecificaPaquetes.validarDetalleTuPlan());
  }

  @Entonces("^VALIDA POPUP NO TIENE PAQUETES$")
  public void validaPopupNoTienePaquetes() {
    theActorInTheSpotlight().attemptsTo(PaquetesAdicionalesNoTienePaquetes.validarPopup());
  }

  @Entonces("VALIDA VIGENCIA Y PLANES ROAMING")
  public void validaRoamingVigenciaPlanes() {
    theActorInTheSpotlight().attemptsTo(AdministrarRoamingCompleto.validarFlujoCompleto());
  }

  @Entonces("^VALIDA DIRECCIONAMIENTO PAQUETES COMPLEMENTARIOS$")
  public void validaDireccionamientoPaquetesComplementarios() {
    theActorInTheSpotlight().attemptsTo(PaquetesComplementarios.validarDireccionamiento());

    ReportHooks.setLinea(user.getNumero());
  }

  @Y("INGRESAR LINEA POSTPAGO SERVICIO FAMILIA ACTIVO")
  public void ingresarLineaPostpagoServicioActivo() {
    theActorInTheSpotlight()
        .attemptsTo(IngresarLineaPostpagoServicioActivo.ingresarLineaPostpagoServicioActivo());

    ReportHooks.setLinea(user.getNumero());
  }

  @Y("^SELECCIONA METODO DE PAGO PSE$")
  public void botonPseNequi() {
    theActorInTheSpotlight().attemptsTo(
            Click.on(BTN_PSE_NEQUI),
            WaitFor.aTime(2000),
            Click.on(BTN_PAGAR)
    );
  }
  @Y("^SELECCIONA METODO DE PAGO TARJETA$")
  public void botonPagoTajeta() {
    theActorInTheSpotlight().attemptsTo(
            ClickTextoQueContengaX.elTextoContiene(TARJETA_C_D),
            WaitFor.aTime(2000)
    );
  }
  @Y("^SELECCIONA METODO DE PAGO BANCOLOMBIA$")
  public void botonPagoBancolombia() {
    theActorInTheSpotlight().attemptsTo(
            ClickTextoQueContengaX.elTextoContiene(BOTON_BANCOLOMBIA),
            WaitFor.aTime(2000),
            Click.on(BTN_PAGAR)
    );
  }

  @Entonces("^VALIDA REDIRECCION A PSE$")
  public void validaDireccionamientoPSEPagaTuFactura() {
    theActorInTheSpotlight().attemptsTo(PSEPagaTuFactura.validarRedireccionPSEPagaTuFactura());
  }

  @Entonces("^VALIDA REDIRECCION A BANCOLOMBIA$")
  public void validaDireccionamientoBancolombiaPagaTuFactura() {
    theActorInTheSpotlight().attemptsTo(Bancolombia.validarRedireccion());
  }

  @Entonces("^VALIDA REDIRECCION A PAGO CON TARJETA$")
  public void validaDireccionamientoTarjetaPagaTuFactura() {
    theActorInTheSpotlight().attemptsTo(TarjetasPagaTuFactura.validarRedireccion());
  }
}
