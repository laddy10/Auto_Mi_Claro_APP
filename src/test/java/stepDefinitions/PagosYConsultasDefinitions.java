package stepDefinitions;

import interactions.Click.ClickTextoQueContengaX;
import interactions.Scroll.ScrollHastaTexto;
import interactions.validations.ValidarTexto;
import interactions.validations.ValidarTextoQueContengaX;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import models.User;
import tasks.PagosYConsultas.*;
import utils.EvidenciaUtils;
import utils.TestDataProvider;

import static net.serenitybdd.screenplay.actors.OnStage.theActorInTheSpotlight;
import static utils.Constants.*;

public class PagosYConsultasDefinitions {

    private final User user = TestDataProvider.getRealUser();

    @And("^INGRESA AL MENU PAGOS Y CONSULTAS$")
    public void menuPagosYConsultas() {
        final String paso = "Menu Pagos y consultas";
        theActorInTheSpotlight().attemptsTo(
                ScrollHastaTexto.conTexto(PAGOS_Y_CONSULTAS),
                ClickTextoQueContengaX.elTextoContiene(VER_MAS)
        );
        EvidenciaUtils.registrarCaptura(paso);
    }

    @And("^INGRESA AL PORTAL DE PAGA TU FACTURA$")
    public void portalPagaTuFactura() {
        theActorInTheSpotlight().attemptsTo(
                PagaTuFactura.pagaTuFactura()
        );
    }

    @And("^VALIDAR REDIRECCION BOTON PAGAR FACTURA$")
    public void botonPagarFactura() {
        theActorInTheSpotlight().attemptsTo(
                RedireccionPagarFactura.redireccionPagarFactura()
        );
    }

 /*   @Then("^CONFIRMA REDIRECCION DE LOS MEDIOS DE PAGO$")
    public void direccionMediosPago() {
        theActorInTheSpotlight().attemptsTo(
                RedireccionMedioPago.redireccionMedioPago()

        );
    } */

    @And("INGRESA AL PORTAL DE RECARGAS Y PAQUETES")
    public void ingresaPortalRecargas() {
        theActorInTheSpotlight().attemptsTo(
                AccesoRecargasYPaquetes.accederRecargasYPaquetes(),
                ValidarInfoRecargas.validarInfoRecargas()
        );
    }

    @Then("VALIDA PAQUETES LDI O DATOS")
    public void validaPaquetesLDIODatoas() {
        theActorInTheSpotlight().attemptsTo(
                PaquetesLDI.seleccionarPaquetes()
        );
    }

    @Then("VALIDAR REDIRECCION DE LOS MEDIOS DE PAGO")
    public void redireccionMediosPago() {
        theActorInTheSpotlight().attemptsTo(
                RedireccionMedioPago.redireccionarMediosDePago()
        );
    }

    @And("^REALIZA PAGO PARCIAL$")
    public void realizaPagoParcial() {
        theActorInTheSpotlight().attemptsTo(
                RealizarPagoParcial.conLosDatos()
        );
    }

    @Then("VALIDA LA REDIRECCION A MEDIOS DE PAGO DISPONIBLES")
    public void redireccionMediosPagoDisponibles() {
        final String paso5 = "Validar redirección a los medios de pago";

        theActorInTheSpotlight().attemptsTo(
                ValidarTexto.validarTexto(ELEGIR_OTRO_MEDIO_PAGO),
                ValidarTextoQueContengaX.elTextoContiene(TARJETA_C_D),
                ValidarTextoQueContengaX.elTextoContiene(BOTON_BANCOLOMBIA),
                ValidarTextoQueContengaX.elTextoContiene(CODENSA),
                ValidarTextoQueContengaX.elTextoContiene(OTROS_MEDIOS)
        );

        EvidenciaUtils.registrarCaptura(paso5);

    }

    @Then("^VALIDA PAGOS AUTOMATICOS$")
    public void validaPagosAutomaticos() {
        theActorInTheSpotlight().attemptsTo(
                PagosAutomaticos.validarPagosAutomaticos()
        );
    }

    @Then("^DESCARGA FACTURA$")
    public void descargaFactura() {
        theActorInTheSpotlight().attemptsTo(
                DescargaFactura.descargarFactura()
        );
    }

    @Then("^ENVIA FACTURA POR CORREO$")
    public void enviaFacturaPorCorreo() {
        theActorInTheSpotlight().attemptsTo(
                EnviarFacturaPorCorreo.enviarFacturaPorCorreo()
        );
    }

    @Then("^VALIDA DETALLE ULTIMA FACTURACION$")
    public void validaDetalleUltimaFacturacion() {
        theActorInTheSpotlight().attemptsTo(
                DetalleUltimaFacturacion.validarDetalleUltimaFacturacion()
        );
    }

    @Then("^VALIDA HISTORIAL DE PAGOS$")
    public void validaHistorialDePagos() {
        theActorInTheSpotlight().attemptsTo(
                HistorialDePagos.validarHistorialDePagos()
        );
    }

    @Then("^SELECCIONA RECARGAS$")
    public void seleccionaRecargas() {
        theActorInTheSpotlight().attemptsTo(
                Recargas.seleccionarRecargas()
        );
    }

    @Then("^GESTIONA COMPRAS RECURRENTES$")
    public void gestionaComprasRecurrentes() {
        theActorInTheSpotlight().attemptsTo(
                GestionarComprasRecurrentes.validarGestionarComprasRecurrentes()
        );
    }

    @Then("^VALIDA DETALLE DE TU PLAN$")
    public void validaDetalleTuPlan() {
        theActorInTheSpotlight().attemptsTo(
                DetalleTuPlan.validarDetalleTuPlan()
        );
    }

}
