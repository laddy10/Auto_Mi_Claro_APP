package tasks.PagosYConsultas.DetalleDeTuPlan;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.StringReader;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import org.junit.Test;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Rectangle;
import java.time.LocalDate;

public class DesactivarServicioRoamingTest {

  @Test
  public void detectaSolicitudNoProcesadaEnTexto() throws Exception {
    assertTrue(coincide("text", "Su solicitud no pudo ser procesada"));
    assertTrue(coincide("text", "SU SOLICITUD NO PUDO SER PROCESADA"));
  }

  @Test
  public void detectaSolicitudNoProcesadaEnDescripcion() throws Exception {
    assertTrue(coincide("content-desc", "Su solicitud no pudo ser procesada"));
  }

  @Test
  public void conservaDeteccionDelAvisoAnterior() throws Exception {
    assertTrue(coincide("text", "No se pudo realizar la solicitud"));
    assertTrue(coincide("content-desc", "NO SE PUDO procesar"));
  }

  @Test
  public void noConfundeConfirmacionExitosaConError() throws Exception {
    assertFalse(coincide("text", "Servicio Roaming desactivado"));
    assertFalse(coincide("content-desc", "Su solicitud fue procesada"));
  }

  @Test
  public void normalizaNumerosYPermiteCambiarLineasPorEscenario() {
    String property = "roaming.sa097.line";
    String original = System.getProperty(property);
    try {
      System.setProperty(property, " +57 310 555 1234 ");
      assertEquals(
          "+573105551234",
          tasks.PagosYConsultas.DetalleDeTuPlan.RoamingTestLines.configured(
              property, "3102628443"));
      System.clearProperty(property);
      assertEquals(
          "3102628443",
          tasks.PagosYConsultas.DetalleDeTuPlan.RoamingTestLines.configured(
              property, "310 262 8443"));
    } finally {
      if (original == null) {
        System.clearProperty(property);
      } else {
        System.setProperty(property, original);
      }
    }
  }

  @Test(expected = IllegalArgumentException.class)
  public void rechazaNumeroDeLineaMalConfigurado() {
    tasks.PagosYConsultas.DetalleDeTuPlan.RoamingTestLines.configured(
        "roaming.sa097.line", "no-es-un-numero");
  }

  @Test
  public void calculaTapEnPantallasDeDistintoTamanoYOrientacion() {
    org.junit.Assert.assertArrayEquals(new int[] {150, 320},
        RoamingScreen.center(new Rectangle(100, 300, 40, 100), new Dimension(720, 1600)));
    org.junit.Assert.assertArrayEquals(new int[] {950, 320},
        RoamingScreen.center(new Rectangle(900, 300, 40, 100), new Dimension(1920, 1080)));
    org.junit.Assert.assertArrayEquals(new int[] {10, 15},
        RoamingScreen.center(new Rectangle(-20, -10, 40, 40), new Dimension(320, 480)));
  }

  @Test(expected = IllegalStateException.class)
  public void noPulsaUnElementoFueraDePantalla() {
    RoamingScreen.center(new Rectangle(900, 300, 40, 100), new Dimension(720, 1600));
  }

  @Test
  public void aceptaVariantesDeInterruptorAndroidSinDependerDeLaResolucion() throws Exception {
    for (String widget : new String[] {"android.widget.ToggleButton", "android.widget.Switch",
        "android.widget.SwitchCompat", "androidx.appcompat.widget.SwitchCompat"}) {
      assertTrue(coincide(RoamingScreen.SWITCH, "class", widget));
    }
    assertFalse(coincide(RoamingScreen.SWITCH, "class", "android.widget.Button"));
  }

  @Test
  public void eligeFechaFuturaEnCambiosDeMesYAno() {
    assertEquals(LocalDate.of(2026, 3, 1),
        RoamingScreen.expiryDate(LocalDate.of(2026, 2, 28), null));
    assertEquals(LocalDate.of(2027, 1, 1),
        RoamingScreen.expiryDate(LocalDate.of(2026, 12, 31), null));
    assertEquals(LocalDate.of(2028, 2, 29),
        RoamingScreen.expiryDate(LocalDate.of(2028, 2, 28), null));
  }

  @Test(expected = IllegalArgumentException.class)
  public void rechazaFechaDeVigenciaPasada() {
    RoamingScreen.expiryDate(LocalDate.of(2026, 11, 12), "2026-11-11");
  }

  private boolean coincide(String atributo, String mensaje) throws Exception {
    return coincide(DesactivarServicioRoaming.MENSAJE_ERROR_DESACTIVACION, atributo, mensaje);
  }

  @Test
  public void noReingresaSiLaVerificacionEsExitosa() {
    int[] calls = {0, 0};
    RoamingScreen.verifyWithSessionRecovery(() -> calls[0]++, () -> calls[1]++);
    assertEquals(1, calls[0]);
    assertEquals(0, calls[1]);
  }

  @Test
  public void reingresaUnaVezSoloSiExpiraLaSesion() {
    int[] calls = {0};
    RoamingScreen.verifyWithSessionRecovery(
        () -> { throw new RoamingScreen.SessionExpiredException(); }, () -> calls[0]++);
    assertEquals(1, calls[0]);
  }

  @Test(expected = RoamingScreen.SessionExpiredException.class)
  public void noRepiteElReingresoSiVuelveAExpirar() {
    RoamingScreen.verifyWithSessionRecovery(
        () -> { throw new RoamingScreen.SessionExpiredException(); },
        () -> { throw new RoamingScreen.SessionExpiredException(); });
  }

  @Test(expected = IllegalStateException.class)
  public void noOcultaRoamingActivoNiOtrosFallos() {
    RoamingScreen.verifyWithSessionRecovery(
        () -> { throw new IllegalStateException("Roaming sigue activo"); },
        () -> org.junit.Assert.fail("No debe reingresar ante otro error"));
  }

  private boolean coincide(org.openqa.selenium.By locator, String atributo, String mensaje) throws Exception {
    Document document =
        DocumentBuilderFactory.newInstance().newDocumentBuilder()
            .parse(new InputSource(new StringReader(
                "<hierarchy><node " + atributo + "=\"" + mensaje + "\"/></hierarchy>")));
    String xpath =
        locator.toString()
            .substring("By.xpath: ".length());
    return (Boolean) XPathFactory.newInstance().newXPath()
        .evaluate(xpath, document, XPathConstants.BOOLEAN);
  }
}
