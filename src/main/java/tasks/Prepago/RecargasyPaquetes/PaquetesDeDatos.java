package tasks.Prepago.RecargasyPaquetes;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static userinterfaces.PagosyConsultasPrePage.LBL_VER_DETALLE_1;
import static userinterfaces.PagosyConsultasPrePage.LBL_VER_DETALLE_2;
import static utils.AndroidObject.scrollCorto2;
import static utils.Constants.*;
import static utils.ConstantsPaquetes.*;
import static utils.ConstantsPaquetes.PAQUETES_DE_DATOS;

import interactions.Click.ClickElementByText;
import interactions.Click.ClickTextoQueContengaX;
import interactions.Scroll.Scroll;
import interactions.validations.ValidarTexto;
import interactions.wait.WaitForResponse;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import utils.EvidenciaUtils;

public class PaquetesDeDatos implements Task {

  private static final String paso1 = "Seleccionar Paquetes de datos";
  private static final String paso2 = "Validar paquete ilimitado 2 horas";
  private static final String paso3 = "Validar paquete 200MB sin redes";
  private static final String paso4 = "Validar paquete 400MB con WhatsApp";
  private static final String paso5 = "Ir a segunda página de paquetes de datos";
  private static final String paso6 = "Validar paquete 800MB";
  private static final String paso7 = "Validar paquete 2GB";

  @Override
  public <T extends Actor> void performAs(T actor) {

    // Paso 1: Seleccionar categoría
    EvidenciaUtils.registrarCaptura(paso1);

    actor.attemptsTo(
        ClickTextoQueContengaX.elTextoContiene(PAQUETES_DE_DATOS),
        WaitForResponse.withText(ELIGE_TU_PAQUETE_IDEAL));

    // Paso 2: Validar paquete ilimitado
    actor.attemptsTo(
        ValidarTexto.validarTexto(LABEL_PAQUETES),
        ValidarTexto.validarTexto("Ilimitada"),
        ValidarTexto.validarTexto(PRECIO),
        ValidarTexto.validarTexto(PRECIO_$6000),
        ClickElementByText.clickElementByText(VER_DETALLE_DEL_PAQUETE),
        ValidarTexto.validarTexto(NAVEGACION_ILIMITADA_2H));

    EvidenciaUtils.registrarCaptura(paso2);

    /*    actor.attemptsTo(
            ClickElementByText.clickElementByText(VER_DETALLE_DEL_PAQUETE)
    );*/

    // Paso 3: Validar paquete 200MB  OJO EL SCROLL ES MUY LARGO
    scrollCorto2(actor, "Apps incluidas");

    actor.attemptsTo(
        ValidarTexto.validarTexto(LABEL_PAQUETES),
        ValidarTexto.validarTexto("200 MB"),
        ValidarTexto.validarTexto(PRECIO),
        ValidarTexto.validarTexto(PRECIO_$2000),
        ValidarTexto.validarTexto("Apps incluidas"),
        Click.on(LBL_VER_DETALLE_1),
        ValidarTexto.validarTexto(NAVEGACION_200MB_1DIA));

    EvidenciaUtils.registrarCaptura(paso3);

    // Paso 4: Validar paquete 400MB

    actor.attemptsTo(
        Scroll.scrollUnaVista(),
        ValidarTexto.validarTexto(LABEL_PAQUETES),
        ValidarTexto.validarTexto("400 MB"),
        ValidarTexto.validarTexto(PRECIO),
        ValidarTexto.validarTexto(PRECIO_$3000),
        ValidarTexto.validarTexto("Apps incluidas"),
        Click.on(LBL_VER_DETALLE_2),
        ValidarTexto.validarTexto(NAVEGACION_400MB_1DIA));

    EvidenciaUtils.registrarCaptura(paso4);

    // Paso 5: Ir a segunda página
    Scroll.scrollUnaVista();

    EvidenciaUtils.registrarCaptura(paso5);

    actor.attemptsTo(
        ClickTextoQueContengaX.elTextoContiene(ULTIMO),
        WaitForResponse.withText(ELIGE_TU_PAQUETE_IDEAL));

    // Paso 6: Validar paquete 800MB
    actor.attemptsTo(
        ValidarTexto.validarTexto(LABEL_PAQUETES),
        ValidarTexto.validarTexto("800 MB"),
        ValidarTexto.validarTexto(PRECIO),
        ValidarTexto.validarTexto(PRECIO_$5000),
        ValidarTexto.validarTexto("Apps incluidas"),
        ClickElementByText.clickElementByText(VER_DETALLE_DEL_PAQUETE),
        ValidarTexto.validarTexto(NAVEGACION_800MB_3DIAS));

    EvidenciaUtils.registrarCaptura(paso6);

    // Paso 7: Validar paquete 2GB

    actor.attemptsTo(
        Scroll.scrollUnaVista(),
        ValidarTexto.validarTexto(LABEL_PAQUETES),
        ValidarTexto.validarTexto("2 GB"),
        ValidarTexto.validarTexto(PRECIO),
        ValidarTexto.validarTexto(PRECIO_$8000),
        ValidarTexto.validarTexto("Apps incluidas"),
        Click.on(LBL_VER_DETALLE_2),
        ValidarTexto.validarTexto(NAVEGACION_2GB_7DIAS));

    EvidenciaUtils.registrarCaptura(paso7);
  }

  public static Performable validar() {
    return instrumented(PaquetesDeDatos.class);
  }
}
