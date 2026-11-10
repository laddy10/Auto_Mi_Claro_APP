package utils;

import io.appium.java_client.MobileBy;
import io.appium.java_client.android.AndroidDriver;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.serenitybdd.screenplay.Actor;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;

/**
 * Lectura y acciones sobre la pantalla con el driver CRUDO de Appium, sin pasar por Serenity.
 *
 * <p>Serenity reimpone la espera implícita (10 s) en cada consulta que pasa por él, así que
 * preguntar por un elemento AUSENTE cuesta 10 s. Aquí se lee el árbol de la pantalla una vez (page
 * source) y se decide sobre ese texto; solo se busca un elemento cuando ya se sabe que está. Con la
 * espera implícita en 0 cada consulta tarda milisegundos.
 *
 * <p>Las acciones de esta clase no generan pasos en el reporte (tampoco escriben lo digitado): las
 * evidencias las deja quien la usa, con {@link EvidenciaUtils}.
 */
public final class PantallaRapida {

  public static final String PAQUETE = "com.clarocolombia.miclaro";
  public static final String ID = PAQUETE + ":id/";

  private static final int ESPERA_IMPLICITA_NORMAL_S = 10;
  private static final long PAUSA_MS = 350L;

  private final AndroidDriver driver;
  private String ultimoError = "";

  private PantallaRapida(AndroidDriver driver) {
    this.driver = driver;
  }

  /** Toma el driver real del actor (crea la sesión de Appium si aún no existe). */
  public static PantallaRapida de(Actor actor) {
    return new PantallaRapida(AndroidObject.androidDriver(actor));
  }

  public AndroidDriver driver() {
    return driver;
  }

  // ─────────────────────────── espera implícita ───────────────────────────

  public void sinEsperaImplicita() {
    esperaImplicita(0);
  }

  public void restaurarEsperaImplicita() {
    esperaImplicita(ESPERA_IMPLICITA_NORMAL_S);
  }

  private void esperaImplicita(int segundos) {
    try {
      driver.manage().timeouts().implicitlyWait(segundos, TimeUnit.SECONDS);
    } catch (RuntimeException e) {
      // no es crítico: solo cambia cuánto tarda una búsqueda sin resultado
    }
  }

  // ─────────────────────────── lectura ───────────────────────────

  /** Árbol de la pantalla actual; cadena vacía si no se pudo leer (se reintenta una vez). */
  public String fuente() {
    for (int intento = 0; intento < 2; intento++) {
      try {
        String xml = driver.getPageSource();
        if (xml != null) {
          return xml;
        }
      } catch (WebDriverException e) {
        String msg = String.valueOf(e.getMessage());
        int salto = msg.indexOf('\n');
        ultimoError = salto > 0 ? msg.substring(0, salto) : msg;
        dormir(500);
      }
    }
    return "";
  }

  /** Primera línea del último error al leer la pantalla (vacío si no hubo). */
  public String ultimoError() {
    return ultimoError;
  }

  /**
   * Relee la pantalla hasta que cumpla la condición.
   *
   * @return el árbol que la cumplió, o {@code null} si se agotó el tiempo.
   */
  public String esperar(Predicate<String> condicion, long maxMs) {
    long fin = System.currentTimeMillis() + maxMs;
    while (true) {
      String xml = fuente();
      if (!xml.isEmpty() && condicion.test(xml)) {
        return xml;
      }
      if (System.currentTimeMillis() >= fin) {
        return null;
      }
      dormir(PAUSA_MS);
    }
  }

  /** ¿El árbol contiene alguna de las claves? */
  public static boolean tiene(String xml, String... claves) {
    if (xml == null || xml.isEmpty()) {
      return false;
    }
    for (String clave : claves) {
      if (xml.contains(clave)) {
        return true;
      }
    }
    return false;
  }

  /** ¿Hay un elemento con ese id (de cualquier paquete) en el árbol? */
  public static boolean tieneId(String xml, String id) {
    return tiene(xml, ":id/" + id + "\"");
  }

  /** ¿Hay un elemento cuyo texto sea exactamente alguno de los indicados? */
  public static boolean tieneTexto(String xml, String... textos) {
    for (String texto : textos) {
      if (tiene(xml, " text=\"" + texto + "\"")) {
        return true;
      }
    }
    return false;
  }

  /** Etiqueta completa del primer elemento con ese id (de cualquier paquete), o null. */
  public static String nodo(String xml, String id) {
    if (xml == null) {
      return null;
    }
    Matcher m =
        Pattern.compile("<[^>]*\\sresource-id=\"[^\"]*:id/" + Pattern.quote(id) + "\"[^>]*>")
            .matcher(xml);
    return m.find() ? m.group() : null;
  }

  /** Texto del primer elemento con ese id; cadena vacía si no está o no tiene texto. */
  public static String textoDe(String xml, String id) {
    return atributo(nodo(xml, id), "text");
  }

  /** Id completo (con paquete) del primer elemento cuyo id termina así, o null. */
  public static String idCompleto(String xml, String id) {
    String valor = atributo(nodo(xml, id), "resource-id");
    return valor.isEmpty() ? null : valor;
  }

  public static String atributo(String nodo, String nombre) {
    if (nodo == null) {
      return "";
    }
    Matcher m = Pattern.compile("\\s" + Pattern.quote(nombre) + "=\"([^\"]*)\"").matcher(nodo);
    return m.find() ? desescapar(m.group(1)).trim() : "";
  }

  /** Textos que se ven en pantalla (sin campos de entrada), para explicar un fallo. */
  public static String textosVisibles(String xml) {
    List<String> textos = new ArrayList<>();
    if (xml != null) {
      Matcher m = Pattern.compile("<([\\w.$]+)\\s[^>]*>").matcher(xml);
      while (m.find() && textos.size() < 25) {
        if (m.group(1).contains("EditText")) {
          continue;
        }
        String texto = atributo(m.group(), "text").replaceAll("\\s+", " ");
        if (!texto.isEmpty() && !textos.contains(texto)) {
          textos.add(texto);
        }
      }
    }
    return textos.isEmpty() ? "(sin textos)" : String.join(" | ", textos);
  }

  private static String desescapar(String s) {
    return s.replace("&#10;", " ")
        .replace("&quot;", "\"")
        .replace("&apos;", "'")
        .replace("&lt;", "<")
        .replace("&gt;", ">")
        .replace("&amp;", "&");
  }

  // ─────────────────────────── acciones ───────────────────────────

  /** Pulsa el elemento de la app con ese id. Devuelve false si no está. */
  public boolean tocarId(String id) {
    return tocar(By.id(ID + id));
  }

  /** Pulsa el primer elemento cuyo texto sea exactamente alguno de los indicados. */
  public boolean tocarTexto(String... textos) {
    for (String texto : textos) {
      if (tocar(porUiSelector("new UiSelector().text(\"" + texto + "\")"))) {
        return true;
      }
    }
    return false;
  }

  /** Pulsa el primer elemento cuyo texto cumpla la expresión regular (sin comillas dobles). */
  public boolean tocarTextoQueCumpla(String regex) {
    return tocar(porUiSelector("new UiSelector().textMatches(\"" + regex + "\")"));
  }

  public boolean tocar(By localizador) {
    for (int intento = 0; intento < 2; intento++) {
      try {
        List<WebElement> encontrados = buscar(localizador);
        if (encontrados.isEmpty()) {
          return false;
        }
        encontrados.get(0).click();
        return true;
      } catch (WebDriverException e) {
        dormir(300); // el elemento cambió justo al pulsarlo: se busca de nuevo una vez
      }
    }
    return false;
  }

  /**
   * Reemplaza el contenido del campo con ese id. No deja rastro del valor en el reporte.
   *
   * <p>No se pulsa el campo antes de escribir: al darle el foco, el autocompletar de Android
   * (correos y contraseñas guardados) abre una ventana encima de la app que le quita el foco.
   */
  public boolean escribir(String id, String valor) {
    for (int intento = 0; intento < 2; intento++) {
      try {
        List<WebElement> campos = buscar(By.id(ID + id));
        if (campos.isEmpty()) {
          return false;
        }
        WebElement campo = campos.get(0);
        campo.clear();
        campo.sendKeys(valor);
        return true;
      } catch (WebDriverException e) {
        dormir(300);
      }
    }
    return false;
  }

  public void atras() {
    try {
      driver.navigate().back();
    } catch (WebDriverException e) {
      // la pantalla siguiente se reevalúa de todos modos
    }
  }

  public static By porUiSelector(String selector) {
    return MobileBy.AndroidUIAutomator(selector);
  }

  @SuppressWarnings("unchecked")
  private List<WebElement> buscar(By localizador) {
    return (List<WebElement>) driver.findElements(localizador);
  }

  public static void dormir(long ms) {
    try {
      Thread.sleep(ms);
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
    }
  }
}
