package stepDefinitions;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import hooks.ReportHooks;
import interactions.Click.ClickElementByText;
import interactions.Click.ClickTextoQueContengaX;
import interactions.comunes.Atras;
import interactions.validations.ValidarTexto;
import interactions.validations.ValidarTextoQueContengaX;
import interactions.wait.WaitForResponse;
import models.User;
import net.serenitybdd.screenplay.actions.Click;
import tasks.MediosDePagos.*;
import tasks.Prepago.RecargasyPaquetes.*;
import utils.EvidenciaUtils;
import utils.TestDataProvider;

import static net.serenitybdd.screenplay.actors.OnStage.theActorInTheSpotlight;
import static userinterfaces.MediosPagoPage.BTN_PSE_RECARGAS;
import static userinterfaces.PagosYConsultasPage.*;
import static utils.Constants.*;

public class PagosyConsultasPreDefinitions {

    private final User user = TestDataProvider.getRealUser();


    @And("^SELECCIONA LINEA PREPAGO$")
    public void seleccionaLineaPrepago() {
        theActorInTheSpotlight().attemptsTo(
                SeleccionLineaPrepago.seleccionar()
        );

        ReportHooks.setLinea(user.getNumeroPrepago());
    }

    @And("^VALIDAR LA MINI VERSION DE RECARGAS$")
    public void validarMiniVersionRecargas() {
        theActorInTheSpotlight().attemptsTo(
                Click.on(BTN_TRES_PUNTOS_MAS),
                ClickTextoQueContengaX.elTextoContiene(ACERCA_DE),
                WaitForResponse.withText(DECLARACION_SERVICIO),
                ValidarTexto.validarTexto(RECARGAS_Y_PAQUETES),
                ValidarTexto.validarTexto(DECLARACION_SERVICIO),
                ValidarTextoQueContengaX.elTextoContiene(VER));

        EvidenciaUtils.registrarCaptura("Validar Mini Versión de Recargas");

        theActorInTheSpotlight().attemptsTo(
                Atras.irAtras()
        );


        ReportHooks.setLinea(user.getNumeroPrepago());
    }

    @And("^ARMA PAQUETE PERSONALIZADO$")
    public void armaPaquetePersonalizado() {
        theActorInTheSpotlight().attemptsTo(
                ArmarPaquete.armar(),
                VerResumenPaquete.validarResumen()
        );
    }

    @And("^VALIDA PAQUETE SELECCIONADO$")
    public void validaPaqueteSeleccionado() {
        theActorInTheSpotlight().attemptsTo(
                ValidarPaqueteSeleccionado.validarPaquete()
        );
    }


    @And("^INGRESAR A TARJETA CREDITO DEBITO$")
    public void ingresarTDC() {
        EvidenciaUtils.registrarCaptura("Seleccionar medio de pago: Tarjeta Crédito/débito");

        theActorInTheSpotlight().attemptsTo(
                ClickTextoQueContengaX.elTextoContiene(TARJETA_C_D),
                WaitForResponse.withText(RESUMEN_COMPRA)
        );
    }

    @And("^VALIDAR EL RESUMEN DE LA COMPRA$")
    public void validaResumenCompra() {
        theActorInTheSpotlight().attemptsTo(
                ResumenCompra.validar()
        );
    }

    @And("^VALIDAR DIRECCIONAMIENTO TARJETA CREDITO DEBITO$")
    public void direccionamientoTCD() {
        theActorInTheSpotlight().attemptsTo(
                TarjetaCreditoDebito.validarRedireccion()
        );
    }

    @And("^INGRESAR A PSE$")
    public void ingresarPSE() {
        EvidenciaUtils.registrarCaptura("Seleccionar PSE como medio de pago");

        theActorInTheSpotlight().attemptsTo(
                Click.on(BTN_PSE_RECARGAS),
                WaitForResponse.withText(RESUMEN_COMPRA)
        );
    }

    @And("^VALIDAR DIRECCIONAMIENTO PSE$")
    public void direccionamientoPSE() {
        theActorInTheSpotlight().attemptsTo(
                PSE.validarRedireccion()
        );
    }

    @And("^INGRESAR A PAGAR CON MI SALDO$")
    public void pagarConMiSaldo() {
        EvidenciaUtils.registrarCaptura("Seleccionar Pagar con mi saldo");

        theActorInTheSpotlight().attemptsTo(
                ClickTextoQueContengaX.elTextoContiene(PAGAR_CON_MI_SALDO),
                WaitForResponse.withText(RESUMEN_COMPRA)
        );
    }

    @And("^VALIDAR PAGO CON EL SALDO$")
    public void pagoConElSaldo() {
        theActorInTheSpotlight().attemptsTo(
                ValidarNotificacion.deCompra()
        );
    }

    @And("^INGRESAR A BANCOLOMBIA$")
    public void ingresarBancolombia() {
        EvidenciaUtils.registrarCaptura("Seleccionar medio de pago: Bancolombia");

        theActorInTheSpotlight().attemptsTo(
                ClickTextoQueContengaX.elTextoContiene(BOTON_BANCOLOMBIA),
                WaitForResponse.withText(RESUMEN_COMPRA)
        );
    }

    @And("^VALIDAR DIRECCIONAMIENTO BANCOLOMBIA$")
    public void direccionamientoBancolombia() {
        theActorInTheSpotlight().attemptsTo(
                Bancolombia.validarRedireccion()
        );
    }

    @And("^INGRESAR A DAVIPLATA$")
    public void ingresarDaviplata() {
        EvidenciaUtils.registrarCaptura("Seleccionar medio de pago: Daviplata");

        theActorInTheSpotlight().attemptsTo(
                ClickTextoQueContengaX.elTextoContiene(DAVIPLATA),
                WaitForResponse.withText(RESUMEN_COMPRA)
        );
    }

    @And("^VALIDAR DIRECCIONAMIENTO DAVIPLATA")
    public void direccionamientoDaviplata() {
        theActorInTheSpotlight().attemptsTo(
                Daviplata.validarRedireccion()
        );
    }

    @And("^INGRESAR A CODENSA$")
    public void ingresarCodensa() {
        EvidenciaUtils.registrarCaptura("Seleccionar medio de pago: Codensa");

        theActorInTheSpotlight().attemptsTo(
                ClickTextoQueContengaX.elTextoContiene(CODENSA),
                WaitForResponse.withText(RESUMEN_COMPRA)
        );
    }


    @And("^INGRESAR A OTROS MEDIOS DE PAGO$")
    public void ingresarOtrosMediosDePago() {
        EvidenciaUtils.registrarCaptura("Seleccionar medio de pago: Otros medios de pago");

        theActorInTheSpotlight().attemptsTo(
                ClickTextoQueContengaX.elTextoContiene(OTROS_MEDIOS),
                WaitForResponse.withText(RESUMEN_COMPRA)
        );
    }

    @And("^DIRECCIONAMIENTO OTROS MEDIOS DE PAGO PSE$")
    public void direccionamientoOtrosMediosDePagoPSE() {
        theActorInTheSpotlight().attemptsTo(
                OtrosMediosDePagoPSE.validarOtrosMediosPSE()
        );

        ReportHooks.setLinea(user.getNumeroPrepago());
    }

    @And("^VALIDAR DIRECCIONAMIENTO OTROS MEDIOS DE PAGO$")
    public void direccionamientoOtrosMediosDePago() {
        theActorInTheSpotlight().attemptsTo(
                OtrosMediosDePago.validarOtrosMedios()
        );

        ReportHooks.setLinea(user.getNumeroPrepago());
    }

    @And("^SELECCIONAR EL MENU PAQUETES$")
    public void seleccionarPaquetes() {
        EvidenciaUtils.registrarCaptura("Ingresar a Paquetes");

        theActorInTheSpotlight().attemptsTo(
                ClickElementByText.clickElementByText(PAQUETES),
                WaitForResponse.withText(ELIGE_TIPO_PAQUETE),
                ValidarTextoQueContengaX.elTextoContiene(ELIGE_TIPO_PAQUETE),
                ValidarTextoQueContengaX.elTextoContiene(user.getNumeroPrepago()));

        EvidenciaUtils.registrarCaptura("Direccionamiento exitoso");

        theActorInTheSpotlight().attemptsTo(
                Click.on(CBX_TIPO_PAQUETE_RECARGAS),
                WaitForResponse.withText(TIPO_PAQUETE)
        );
    }

    @Then("^SELECCIONAR EL TIPO DE PAQUETE TODO INCLUIDO CON REDES$")
    public void seleccionarPaqueteTodoIncluidoRedes() {
        theActorInTheSpotlight().attemptsTo(
                TodoIncluidoConRedes.validar()
        );
    }

    @Then("REALIZA LA VALIDACIÓN DE PAQUETES TODO INCLUIDO SIN REDES")
    public void validarPaquetesTodoIncluidoSinRedes() {
        theActorInTheSpotlight().attemptsTo(
                TodoIncluidoSinRedes.validar()
        );
    }

    @And("REALIZA LA VALIDACIÓN DE PAQUETES DE DATOS")
    public void validarPaquetesDeDatos() {
        theActorInTheSpotlight().attemptsTo(
                PaquetesDeDatos.validar()
        );
    }

    @Then("^REALIZA RECORRIDO COMPLETO PAQUETES DE VOZ$")
    public void realizaRecorridoCompletoPaquetesDeVoz() {
        theActorInTheSpotlight().attemptsTo(
                PaquetesDeVoz.validar()
        );
    }


    @Then("^REALIZA RECORRIDO COMPLETO PAQUETES DE APPS$")
    public void realizaRecorridoCompletoPaquetesDeApps() {
        theActorInTheSpotlight().attemptsTo(
                PaquetesApps.validar()
        );
    }

    @Then("^REALIZA RECORRIDO COMPLETO PAQUETES RELEVO COMUNIDAD SORDA$")
    public void realizaRecorridoCompletoPaquetesRelevoComunidadSorda() {
        theActorInTheSpotlight().attemptsTo(
                PaquetesRelevoComunidadSorda.validar()
        );
    }

    @And("^SELECCIONAR LA LINEA EN PREPAGO$")
    public void seleccionarLineaEnPrepago() {
        theActorInTheSpotlight().attemptsTo(
                SeleccionarLaLineaEnPrepago.seleccionar()
        );

        ReportHooks.setLinea(user.getNumeroPrepago());
    }


}