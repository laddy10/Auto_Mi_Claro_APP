package tasks.PagosYConsultas.DetalleDeTuPlan;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.StringReader;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import org.junit.Test;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

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

  private boolean coincide(String atributo, String mensaje) throws Exception {
    Document document =
        DocumentBuilderFactory.newInstance().newDocumentBuilder()
            .parse(new InputSource(new StringReader(
                "<hierarchy><node " + atributo + "=\"" + mensaje + "\"/></hierarchy>")));
    String xpath =
        DesactivarServicioRoaming.MENSAJE_ERROR_DESACTIVACION.toString()
            .substring("By.xpath: ".length());
    return (Boolean) XPathFactory.newInstance().newXPath()
        .evaluate(xpath, document, XPathConstants.BOOLEAN);
  }
}
