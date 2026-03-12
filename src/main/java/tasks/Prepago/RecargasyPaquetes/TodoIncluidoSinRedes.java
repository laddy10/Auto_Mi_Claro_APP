package tasks.Prepago.RecargasyPaquetes;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static userinterfaces.PagosyConsultasPrePage.LBL_VER_DETALLE_1;
import static userinterfaces.PagosyConsultasPrePage.LBL_VER_DETALLE_2;
import static utils.AndroidObject.scrollCorto2;
import static utils.Constants.*;
import static utils.ConstantsPaquetes.*;
import static utils.ConstantsPaquetes.PAQUETES_TODO_INCLUIDO;

import interactions.Click.ClickElementByText;
import interactions.Scroll.Scroll;
import interactions.validations.ValidarTexto;
import interactions.wait.WaitForResponse;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import utils.EvidenciaUtils;

public class TodoIncluidoSinRedes implements Task {

  private static final String paso1 = "Seleccionar Paquetes todo incluido sin redes";
  private static final String paso2 = "Validar primer paquete 10 GB";
  private static final String paso3 = "Validar segundo paquete 20 GB";
  private static final String paso4 = "Validar tercer paquete 30 GB";

  @Override
  public <T extends Actor> void performAs(T actor) {

    // Paso 1: Seleccionar categoría
    EvidenciaUtils.registrarCaptura(paso1);

    actor.attemptsTo(
        ClickElementByText.clickElementByText(PAQUETES_TODO_INCLUIDO),
        WaitForResponse.withText(ELIGE_TU_PAQUETE_IDEAL));

    // Paso 2: Validar primer paquete 10 GB
    actor.attemptsTo(
        ValidarTexto.validarTexto(LABEL_PAQUETES),
        ValidarTexto.validarTexto("10 GB"),
        ValidarTexto.validarTexto(PRECIO),
        ValidarTexto.validarTexto(PRECIO_$13000),
        ValidarTexto.validarTexto("Min ILIMITADOS"),
        ValidarTexto.validarTexto("SMS ILIMITADOS"),
        Click.on(LBL_VER_DETALLE_1),
        ValidarTexto.validarTexto(TODO_INCLUIDO_7DIAS_10GB));

    EvidenciaUtils.registrarCaptura(paso2);

    // Paso 3: Validar segundo paquete 20 GB
    scrollCorto2(
        actor,
        "Este paquete Todo Incluido incluye 20GB, ilimitados en Minutos y SMS , Vigencia 15 dias");

    actor.attemptsTo(
        ValidarTexto.validarTexto(LABEL_PAQUETES),
        ValidarTexto.validarTexto("20 GB"),
        ValidarTexto.validarTexto(PRECIO),
        ValidarTexto.validarTexto(PRECIO_$23000),
        ValidarTexto.validarTexto("Min ILIMITADOS"),
        ValidarTexto.validarTexto("SMS ILIMITADOS"),
        Click.on(LBL_VER_DETALLE_1),
        ValidarTexto.validarTexto(TODO_INCLUIDO_15DIAS_20GB));

    EvidenciaUtils.registrarCaptura(paso3);

    // Paso 4: Validar tercer paquete 30 GB
    actor.attemptsTo(
        Scroll.scrollUnaVista(),
        ValidarTexto.validarTexto(LABEL_PAQUETES),
        ValidarTexto.validarTexto("30 GB"),
        ValidarTexto.validarTexto(PRECIO),
        ValidarTexto.validarTexto(PRECIO_$33000),
        ValidarTexto.validarTexto("Min ILIMITADOS"),
        ValidarTexto.validarTexto("SMS ILIMITADOS"),
        Click.on(LBL_VER_DETALLE_2),
        Scroll.scrollUnaVista(),
        ValidarTexto.validarTexto(TODO_INCLUIDO_30DIAS_30GB));

    EvidenciaUtils.registrarCaptura(paso4);
  }

  public static Performable validar() {
    return instrumented(TodoIncluidoSinRedes.class);
  }
}
