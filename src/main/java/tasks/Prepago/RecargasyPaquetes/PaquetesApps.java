package tasks.Prepago.RecargasyPaquetes;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static userinterfaces.PagosyConsultasPrePage.LBL_VER_DETALLE_2;
import static userinterfaces.PagosyConsultasPrePage.LBL_VER_DETALLE_3;
import static utils.Constants.*;
import static utils.ConstantsPaquetes.*;
import static utils.ConstantsPaquetes.PAQUETES_APPS;

import interactions.Click.ClickElementByText;
import interactions.Click.ClickTextoQueContengaX;
import interactions.Scroll.Scroll;
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

public class PaquetesApps implements Task {

  private static final User user = TestDataProvider.getRealUser();
  private static final String paso1 = "Seleccionar Paquetes apps";
  private static final String paso2 = "Página 1 - Validar primer paquete $3.500 - Ver detalle";
  private static final String paso3 = "Página 1 - Validar segundo paquete $5.000 - Ver detalle";
  private static final String paso4 = "Página 1 - Validar tercer paquete $6.500 - Ver detalle";

  @Override
  public <T extends Actor> void performAs(T actor) {

    // PASO 1: Seleccionar tipo de paquete "Paquetes apps"
    EvidenciaUtils.registrarCaptura(paso1);

    actor.attemptsTo(
        ClickTextoQueContengaX.elTextoContiene(PAQUETES_APPS),
        WaitForResponse.withText(ELIGE_TU_PAQUETE_IDEAL),
        ValidarTextoQueContengaX.elTextoContiene(user.getNumeroPrepago()),
        ValidarTexto.validarTexto(PAQUETES_APPS));

    // PAQUETE 1: Validar primer paquete $3.500 (Salud en línea)

    actor.attemptsTo(
        ValidarTexto.validarTexto(LABEL_PAQUETES),
        ValidarTexto.validarTexto(PRECIO),
        ValidarTexto.validarTexto(PRECIO_$3500),
        ClickElementByText.clickElementByText(VER_DETALLE_DEL_PAQUETE),
        ValidarTexto.validarTexto(SALUD_EN_LINEA_30DIAS));

    EvidenciaUtils.registrarCaptura(paso2);

    // PAQUETE 2: Segundo paquete $5.000 (Salud en línea)
    actor.attemptsTo(
        Scroll.scrollUnaVista(),
        ValidarTexto.validarTexto(LABEL_PAQUETES),
        ValidarTexto.validarTexto(PRECIO),
        ValidarTexto.validarTexto(PRECIO_$5000),
        Click.on(LBL_VER_DETALLE_2),
        ValidarTexto.validarTexto(SALUD_EN_LINEA_30DIAS_2BENEFICIARIOS));

    EvidenciaUtils.registrarCaptura(paso3);

    // PAQUETE 3: Tercer paquete $6.500 (Salud en línea)

    actor.attemptsTo(
        Scroll.scrollUnaVista(),
        ValidarTexto.validarTexto(LABEL_PAQUETES),
        ValidarTexto.validarTexto(PRECIO),
        ValidarTexto.validarTexto(PRECIO_$6500),
        Click.on(LBL_VER_DETALLE_3),
        Scroll.scrollUnaVista(),
        ValidarTexto.validarTexto(SALUD_EN_LINEA_30DIAS_4BENEFICIARIOS));

    EvidenciaUtils.registrarCaptura(paso4);
  }

  public static Performable validar() {
    return instrumented(PaquetesApps.class);
  }
}
