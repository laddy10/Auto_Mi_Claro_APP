package tasks.Prepago.RecargasyPaquetes;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static userinterfaces.PagosyConsultasPrePage.LBL_VER_DETALLE_2;
import static utils.AndroidObject.scrollCorto2;
import static utils.Constants.*;
import static utils.ConstantsPaquetes.*;
import static utils.ConstantsPaquetes.PAQUETES_RELEVO_COMUNIDAD_SORDA;

import interactions.Click.ClickElementByText;
import interactions.Click.ClickTextoQueContengaX;
import interactions.Scroll.Scroll;
import interactions.Scroll.ScrollHastaTexto;
import interactions.validations.ValidarTexto;
import interactions.validations.ValidarTextoQueContengaX;
import interactions.wait.WaitForResponse;
import models.User;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import utils.EvidenciaUtils;
import utils.TestDataProvider;

public class PaquetesRelevoComunidadSorda implements Task {

  private static final User user = TestDataProvider.getRealUser();
  private static final String paso1 = "Seleccionar Paquetes Relevo comunidad sorda";
  private static final String paso2 = "Validar primer paquete 800MB - Ver detalle";
  private static final String paso3 = "Validar segundo paquete 2,5GB - Ver detalle";
  private static final String paso4 = "Validar tercer paquete 6GB - Ver detalle";

  @Override
  public <T extends Actor> void performAs(T actor) {

    // PASO 1: Seleccionar tipo de paquete "Paquetes Relevo comunidad sorda"
    actor.attemptsTo(Scroll.scrollUnaVista());

    EvidenciaUtils.registrarCaptura(paso1);

    actor.attemptsTo(
        Click.on(
            "/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.RelativeLayout/android.widget.RelativeLayout[2]/android.widget.RelativeLayout/android.widget.RelativeLayout/android.view.ViewGroup/android.widget.FrameLayout/android.webkit.WebView/android.webkit.WebView/android.view.View/android.view.View/android.view.View[2]"),
        ScrollHastaTexto.conTexto(PAQUETES_RELEVO_COMUNIDAD_SORDA),
        ClickTextoQueContengaX.elTextoContiene(PAQUETES_RELEVO_COMUNIDAD_SORDA),
        WaitForResponse.withText(ELIGE_TU_PAQUETE_IDEAL),
        ValidarTextoQueContengaX.elTextoContiene(user.getNumeroPrepago()),
        ValidarTexto.validarTexto(PAQUETES_RELEVO_COMUNIDAD_SORDA));

    // PAQUETE 1: Validar primer paquete 800MB ($7.500)

    actor.attemptsTo(
        ValidarTexto.validarTexto(LABEL_PAQUETES),
        ValidarTexto.validarTexto("800 MB"),
        ValidarTexto.validarTexto(PRECIO),
        ValidarTexto.validarTexto(PRECIO_$8000),
        ValidarTexto.validarTexto("SMS ILIMITADOS"),
        ValidarTexto.validarTexto(APPS_INCLUIDAS),
        ClickElementByText.clickElementByText(VER_DETALLE_DEL_PAQUETE),
        ValidarTexto.validarTexto(PAQUETE_RELEVO_800MB_7DIAS));

    EvidenciaUtils.registrarCaptura(paso2);

    // PAQUETE 2: Segundo paquete 2,5GB ($15.500)
    scrollCorto2(actor, "2,5 GB");

    actor.attemptsTo(
        ValidarTexto.validarTexto(LABEL_PAQUETES),
        ValidarTexto.validarTexto("2,5 GB"),
        ValidarTexto.validarTexto(PRECIO),
        ValidarTexto.validarTexto(PRECIO_$16500),
        ValidarTexto.validarTexto("SMS ILIMITADOS"),
        ValidarTexto.validarTexto(APPS_INCLUIDAS),
        ClickElementByText.clickElementByText(VER_DETALLE_DEL_PAQUETE),
        ValidarTexto.validarTexto(PAQUETE_RELEVO_2_5GB_15DIAS));

    EvidenciaUtils.registrarCaptura(paso3);

    // PAQUETE 3: Tercer paquete 6GB ($30.500)

    actor.attemptsTo(
        Scroll.scrollUnaVista(),
        ValidarTexto.validarTexto(LABEL_PAQUETES),
        ValidarTexto.validarTexto("6 GB"),
        ValidarTexto.validarTexto(PRECIO),
        ValidarTexto.validarTexto(PRECIO_$31500),
        ValidarTexto.validarTexto("SMS ILIMITADOS"),
        ValidarTexto.validarTexto(APPS_INCLUIDAS),
        Click.on(LBL_VER_DETALLE_2),
        Scroll.scrollUnaVista(),
        ValidarTexto.validarTexto(PAQUETE_RELEVO_6GB_30DIAS));

    EvidenciaUtils.registrarCaptura(paso4);
  }

  public static Performable validar() {
    return instrumented(PaquetesRelevoComunidadSorda.class);
  }
}
