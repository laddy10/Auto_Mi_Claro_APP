package stepDefinitions;

import static net.serenitybdd.screenplay.actors.OnStage.theActorInTheSpotlight;
import static userinterfaces.MediosPagoPage.BTN_PSE_RECARGAS;
import static userinterfaces.PagosYConsultasPage.*;
import static utils.Constants.*;
import static utils.ConstantsMiniVersiones.Versiones.MINI_VERSION_RECARGAS_Y_PAQUETES_PRE;

import cucumber.api.java.ast.Y;
import cucumber.api.java.es.Entonces;
import hooks.ReportHooks;
import interactions.Click.ClickElementByText;
import interactions.Click.ClickTextoQueContengaX;
import interactions.comunes.Atras;
import interactions.validations.ValidarTexto;
import interactions.validations.ValidarTextoQueContengaX;
import interactions.validations.VerificarVersionModulo;
import interactions.wait.WaitForResponse;
import models.User;
import net.serenitybdd.screenplay.actions.Click;
import tasks.MediosDePagos.*;
import tasks.Prepago.RecargasyPaquetes.*;
import utils.EvidenciaUtils;
import utils.TestDataProvider;

public class PagosyConsultasPreDefinitions {

  private final User user = TestDataProvider.getRealUser();

  @Y("^SELECCIONA LINEA PREPAGO$")
  public void seleccionaLineaPrepago() {
    theActorInTheSpotlight().attemptsTo(SeleccionLineaPrepago.seleccionar());

    ReportHooks.setLinea(user.getNumeroPrepago());
  }

  @Y("^VALIDAR LA MINI VERSION DE RECARGAS$")
  public void validarMiniVersionRecargas() {
    theActorInTheSpotlight()
        .attemptsTo(
            Click.on(BTN_TRES_PUNTOS_MAS),
            ClickTextoQueContengaX.elTextoContiene(ACERCA_DE),
            WaitForResponse.withText(DECLARACION_SERVICIO),
            ValidarTexto.validarTexto(RECARGAS_Y_PAQUETES),
            ValidarTexto.validarTexto(DECLARACION_SERVICIO),
            ValidarTextoQueContengaX.elTextoContiene(VER),
            VerificarVersionModulo.conLaEsperada(MINI_VERSION_RECARGAS_Y_PAQUETES_PRE));

    EvidenciaUtils.registrarCaptura("Validar Mini Versión de Recargas");

    theActorInTheSpotlight().attemptsTo(Atras.irAtras());

    ReportHooks.setLinea(user.getNumeroPrepago());
  }

  @Y("^ARMA PAQUETE PERSONALIZADO$")
  public void armaPaquetePersonalizado() {
    theActorInTheSpotlight().attemptsTo(ArmarPaquete.armar(), VerResumenPaquete.validarResumen());
  }

  @Y("^VALIDA PAQUETE SELECCIONADO$")
  public void validaPaqueteSeleccionado() {
    theActorInTheSpotlight().attemptsTo(ValidarPaqueteSeleccionado.validarPaquete());
  }

  @Y("^INGRESAR A TARJETA CREDITO DEBITO$")
  public void ingresarTDC() {
    EvidenciaUtils.registrarCaptura("Seleccionar medio de pago: Tarjeta Crédito/débito");

    theActorInTheSpotlight()
        .attemptsTo(
            ClickTextoQueContengaX.elTextoContiene(TARJETA_C_D),
            WaitForResponse.withText(RESUMEN_COMPRA));
  }

  @Y("^VALIDAR EL RESUMEN DE LA COMPRA$")
  public void validaResumenCompra() {
    theActorInTheSpotlight().attemptsTo(ResumenCompra.validar());
  }

  @Y("^VALIDAR DIRECCIONAMIENTO TARJETA CREDITO DEBITO$")
  public void direccionamientoTCD() {
    theActorInTheSpotlight().attemptsTo(TarjetaCreditoDebito.validarRedireccion());
  }

  @Y("^INGRESAR A PSE$")
  public void ingresarPSE() {
    EvidenciaUtils.registrarCaptura("Seleccionar PSE como medio de pago");

    theActorInTheSpotlight()
        .attemptsTo(Click.on(BTN_PSE_RECARGAS), WaitForResponse.withText(RESUMEN_COMPRA));
  }

  @Y("^VALIDAR DIRECCIONAMIENTO PSE$")
  public void direccionamientoPSE() {
    theActorInTheSpotlight().attemptsTo(PSE.validarRedireccion());
  }

  @Y("^INGRESAR A PAGAR CON MI SALDO$")
  public void pagarConMiSaldo() {
    EvidenciaUtils.registrarCaptura("Seleccionar Pagar con mi saldo");

    theActorInTheSpotlight()
        .attemptsTo(
            ClickTextoQueContengaX.elTextoContiene(PAGAR_CON_MI_SALDO),
            WaitForResponse.withText(RESUMEN_COMPRA));
  }

  @Y("^VALIDAR PAGO CON EL SALDO$")
  public void pagoConElSaldo() {
    theActorInTheSpotlight().attemptsTo(ValidarNotificacion.deCompra());
  }

  @Y("^INGRESAR A BANCOLOMBIA$")
  public void ingresarBancolombia() {
    EvidenciaUtils.registrarCaptura("Seleccionar medio de pago: Bancolombia");

    theActorInTheSpotlight()
        .attemptsTo(
            ClickTextoQueContengaX.elTextoContiene(BOTON_BANCOLOMBIA),
            WaitForResponse.withText(RESUMEN_COMPRA));
  }

  @Y("^VALIDAR DIRECCIONAMIENTO BANCOLOMBIA$")
  public void direccionamientoBancolombia() {
    theActorInTheSpotlight().attemptsTo(Bancolombia.validarRedireccion());
  }

  @Y("^INGRESAR A DAVIPLATA$")
  public void ingresarDaviplata() {
    EvidenciaUtils.registrarCaptura("Seleccionar medio de pago: Daviplata");

    theActorInTheSpotlight()
        .attemptsTo(
            ClickTextoQueContengaX.elTextoContiene(DAVIPLATA),
            WaitForResponse.withText(RESUMEN_COMPRA));
  }

  @Y("^VALIDAR DIRECCIONAMIENTO DAVIPLATA")
  public void direccionamientoDaviplata() {
    theActorInTheSpotlight().attemptsTo(Daviplata.validarRedireccion());
  }

  @Y("^INGRESAR A CODENSA$")
  public void ingresarCodensa() {
    EvidenciaUtils.registrarCaptura("Seleccionar medio de pago: Codensa");

    theActorInTheSpotlight()
        .attemptsTo(
            ClickTextoQueContengaX.elTextoContiene(CODENSA),
            WaitForResponse.withText(RESUMEN_COMPRA));
  }

  @Y("^(?:INGRESAR A OTROS MEDIOS DE PAGO|ELEGIR MEDIO DE PAGO OTROS MEDIOS DE PAGO|Elegir medio de pago Otros medios de pago)$")
  public void ingresarOtrosMediosDePago() {
    theActorInTheSpotlight().attemptsTo(ElegirMedioPagoOtrosMedios.elegir());
  }

  @Y("^DIRECCIONAMIENTO OTROS MEDIOS DE PAGO PSE$")
  public void direccionamientoOtrosMediosDePagoPSEY() {
    theActorInTheSpotlight().attemptsTo(OtrosMediosDePagoPSE.validarOtrosMediosPSE());

    ReportHooks.setLinea(user.getNumeroPrepago());
  }

  @Entonces("^VALIDAR DIRECCIONAMIENTO OTROS MEDIOS DE PAGO PSE$")
  public void direccionamientoOtrosMediosDePagoPSE() {
    theActorInTheSpotlight().attemptsTo(OtrosMediosDePagoPSE.validarOtrosMediosPSE());

    ReportHooks.setLinea(user.getNumeroPrepago());
  }

  @Entonces("^(?:VALIDAR DIRECCIONAMIENTO OTROS MEDIOS DE PAGO TARJETA DE CREDITO|VALIDAR DIRECCIONAMIENTO OTROS MEDIOS DE PAGO TARJETA DE CRÉDITO)$")
  public void direccionamientoOtrosMediosDePagoTC() {
    theActorInTheSpotlight().attemptsTo(OtrosMediosDePagoTC.validarOtrosMediosTC());

    ReportHooks.setLinea(user.getNumeroPrepago());
  }

  @Entonces("^VALIDAR DIRECCIONAMIENTO OTROS MEDIOS DE PAGO BANCOLOMBIA$")
  public void direccionamientoOtrosMediosDePagoBancolombia() {
    theActorInTheSpotlight().attemptsTo(OtrosMediosDePagoBancolombia.validarOtrosMediosBancolombia());

    ReportHooks.setLinea(user.getNumeroPrepago());
  }

  @Y("^VALIDAR DIRECCIONAMIENTO OTROS MEDIOS DE PAGO$")
  public void direccionamientoOtrosMediosDePago() {
    theActorInTheSpotlight().attemptsTo(OtrosMediosDePago.validarOtrosMedios());

    ReportHooks.setLinea(user.getNumeroPrepago());
  }

  @Y("^(?:SELECCIONAR EL TIPO DE PAQUETE TODO INCLUIDO SIN REDES|SELECCIONAR EL PAQUETE TODO INCLUIDO SIN REDES)$")
  public void seleccionarTipoPaqueteTodoIncluidoSinRedes() {
    theActorInTheSpotlight().attemptsTo(SeleccionarPaqueteTodoIncluidoSinRedes.seleccionar());
  }

  @Y("^(?:COMPRA DEL PAQUETE TODO INCLUIDO 7 DIAS CON MINUTOS ILIMITADOS 10GB WIN SPORTS|COMPRA DE PAQUETE TODO INCLUIDO 7 DIAS CON MINUTOS ILIMITADOS 10GB WIN SPORTS)$")
  public void compraPaqueteTodoIncluido7DiasWinSports() {
    theActorInTheSpotlight().attemptsTo(CompraPaqueteTodoIncluido7DiasWinSports.comprar());
  }

  @Y("^SELECCIONAR EL MENU PAQUETES$")
  public void seleccionarPaquetes() {
    EvidenciaUtils.registrarCaptura("Ingresar a Paquetes");

    theActorInTheSpotlight()
        .attemptsTo(
            ClickElementByText.clickElementByText(PAQUETES),
            WaitForResponse.withText(ELIGE_TIPO_PAQUETE),
            ValidarTextoQueContengaX.elTextoContiene(ELIGE_TIPO_PAQUETE),
            ValidarTextoQueContengaX.elTextoContiene(user.getNumeroPrepago()));

    EvidenciaUtils.registrarCaptura("Direccionamiento exitoso");

    theActorInTheSpotlight()
        .attemptsTo(Click.on(CBX_TIPO_PAQUETE_RECARGAS), WaitForResponse.withText(TIPO_PAQUETE));
  }

  @Entonces("^SELECCIONAR EL TIPO DE PAQUETE TODO INCLUIDO CON REDES$")
  public void seleccionarPaqueteTodoIncluidoRedes() {
    theActorInTheSpotlight().attemptsTo(TodoIncluidoConRedes.validar());
  }

  @Entonces("REALIZA LA VALIDACIÓN DE PAQUETES TODO INCLUIDO SIN REDES")
  public void validarPaquetesTodoIncluidoSinRedes() {
    theActorInTheSpotlight().attemptsTo(TodoIncluidoSinRedes.validar());
  }

  @Y("REALIZA LA VALIDACIÓN DE PAQUETES DE DATOS")
  public void validarPaquetesDeDatos() {
    theActorInTheSpotlight().attemptsTo(PaquetesDeDatos.validar());
  }

  @Entonces("^REALIZA RECORRIDO COMPLETO PAQUETES DE VOZ$")
  public void realizaRecorridoCompletoPaquetesDeVoz() {
    theActorInTheSpotlight().attemptsTo(PaquetesDeVoz.validar());
  }

  @Entonces("^REALIZA RECORRIDO COMPLETO PAQUETES DE APPS$")
  public void realizaRecorridoCompletoPaquetesDeApps() {
    theActorInTheSpotlight().attemptsTo(PaquetesApps.validar());
  }

  @Entonces("^REALIZA RECORRIDO COMPLETO PAQUETES RELEVO COMUNIDAD SORDA$")
  public void realizaRecorridoCompletoPaquetesRelevoComunidadSorda() {
    theActorInTheSpotlight().attemptsTo(PaquetesRelevoComunidadSorda.validar());
  }

  @Y("^SELECCIONAR LA LINEA EN PREPAGO$")
  public void seleccionarLineaEnPrepago() {
    theActorInTheSpotlight().attemptsTo(SeleccionarLaLineaEnPrepago.seleccionar());

    ReportHooks.setLinea(user.getNumeroPrepago());
  }

  @Y("^SELECCIONAR EL PAQUETE DE DATOS$")
  public void seleccionarElPaqueteDeDatos() {
    theActorInTheSpotlight().attemptsTo(SeleccionarPaqueteDeDatos.seleccionar());
  }

  @Y("^COMPRA DEL PAQUETE NAVEGACIÓN 2GB WHATSAPP FACEBOOK TWITTER 7 DIAS$")
  public void compraDelPaqueteNavegacion2GB() {
    theActorInTheSpotlight()
        .attemptsTo(
            Navegacion2GBWhatsappFacebookTwitter7Dias
                .navegacion2GBWhatsappFacebookTwitter7Dias());
  }

  @Y(
      "^(?:ELEGIR MEDIO DE PAGO TARJETA DE CRÉDITO/DÉBITO|ELEGIR MEDIO DE PAGO TARJETA DE CREDITO/DEBITO|ELEGIR MEDIO DE PAGO Tarjeta de crédito/débito)$")
  public void elegirMedioPagoTarjetaCredito() {
    theActorInTheSpotlight().attemptsTo(ElegirMedioPagoTarjetaCredito.elegir());
  }

  @Y(
      "^(?:VALIDAR EL RESUMEN DE LA COMPRA DE PAQUETE DE DATOS|VALIDAR EL RESUMEN DE LA COMPRA DEL PAQUETE DE DATOS)$")
  public void validarElResumenDeLaCompraPaqueteDeDatos() {
    theActorInTheSpotlight().attemptsTo(ValidarResumenCompraPaqueteDatos.validar());
  }

  @Y("^TERMINOS Y CONDICIONES$")
  public void terminosYCondiciones() {
    theActorInTheSpotlight().attemptsTo(TerminosYCondicionesPaquetes.validarTerminosYAceptar());
  }

  @Y("^(?:Clic boton pagar|CLIC BOTON PAGAR)$")
  public void clicBotonPagar() {
    theActorInTheSpotlight().attemptsTo(ClicBotonPagar.pagar());
  }

  @Entonces(
      "^(?:Validar direccionamiento y diligenciar formulario de pago de T\\.C|VALIDAR DIRECCIONAMIENTO Y DILIGENCIAR FORMULARIO DE PAGO DE T\\.C)$")
  public void validarDireccionamientoYDiligenciarFormularioTC() {
    theActorInTheSpotlight().attemptsTo(DiligenciarFormularioTarjetaCredito.diligenciar());

    ReportHooks.setLinea(user.getNumeroPrepago());
  }

  @Y("^(?:ELEGIR MEDIO DE PAGO PSE|Elegir medio de pago PSE)$")
  public void elegirMedioPagoPSE() {
    theActorInTheSpotlight().attemptsTo(ElegirMedioPagoPSE.elegir());
  }

  @Entonces(
      "^(?:VALIDAR DIRECCIONAMIENTO Y DILIGENCIAR FORMULARIO DE PAGO PSE BANCO POPULAR|Validar direccionamiento y diligenciar formulario de pago PSE Banco Popular)$")
  public void validarDireccionamientoYDiligenciarFormularioPSEBancoPopular() {
    theActorInTheSpotlight().attemptsTo(DiligenciarFormularioPSEBancoPopular.diligenciar());

    ReportHooks.setLinea(user.getNumeroPrepago());
  }

  @Y("^(?:SELECCIONAR EL PAQUETE DE VOZ|SELECCIONA EL PAQUETE DE VOZ)$")
  public void seleccionarElPaqueteDeVoz() {
    theActorInTheSpotlight().attemptsTo(SeleccionarPaqueteDeVoz.seleccionar());
  }

  @Y("^(?:COMPRA DEL PAQUETE 1000 MINUTOS|COMPRA DE PAQUETE 1000 MINUTOS)$")
  public void compraDelPaquete1000Minutos() {
    theActorInTheSpotlight().attemptsTo(CompraPaquete1000Minutos.comprar());
  }

  @Y("^(?:ELEGIR MEDIO DE PAGO BANCOLOMBIA|Elegir medio de pago Bancolombia)$")
  public void elegirMedioPagoBancolombia() {
    theActorInTheSpotlight().attemptsTo(ElegirMedioPagoBancolombia.elegir());
  }

  @Entonces(
      "^(?:VALIDAR DIRECCIONAMIENTO Y DILIGENCIAR FORMULARIO DE PAGO BANCOLOMBIA|Validar direccionamiento y diligenciar formulario de pago Bancolombia)$")
  public void validarDireccionamientoYDiligenciarFormularioBancolombia() {
    theActorInTheSpotlight().attemptsTo(DiligenciarFormularioBancolombia.diligenciar());

    ReportHooks.setLinea(user.getNumeroPrepago());
  }

  @Y("^(?:SELECCIONAR EL PAQUETE DE APPS|SELECCIONA EL PAQUETE DE APPS)$")
  public void seleccionarElPaqueteDeApps() {
    theActorInTheSpotlight().attemptsTo(SeleccionarPaqueteDeApps.seleccionar());
  }

  @Y(
      "^(?:COMPRA DEL PAQUETE SALUD EN LINEA 4 BENEFICIARIOS VIG 30 DIAS|COMPRA DE PAQUETE SALUD EN LINEA 4 BENEFICIARIOS VIG 30 DIAS)$")
  public void compraDelPaqueteSalud4Beneficiarios() {
    theActorInTheSpotlight().attemptsTo(CompraPaqueteSalud4Beneficiarios.comprar());
  }

  @Y("^(?:ELEGIR MEDIO DE PAGO DAVIPLATA|Elegir medio de pago Daviplata)$")
  public void elegirMedioPagoDaviplata() {
    theActorInTheSpotlight().attemptsTo(ElegirMedioPagoDaviplata.elegir());
  }

  @Entonces(
      "^(?:VALIDAR DIRECCIONAMIENTO Y DILIGENCIAR FORMULARIO DE PAGO DAVIPLATA|Validar direccionamiento y diligenciar formulario de pago Daviplata)$")
  public void validarDireccionamientoYDiligenciarFormularioDaviplata() {
    theActorInTheSpotlight().attemptsTo(DiligenciarFormularioDaviplata.diligenciar());

    ReportHooks.setLinea(user.getNumeroPrepago());
  }
}
