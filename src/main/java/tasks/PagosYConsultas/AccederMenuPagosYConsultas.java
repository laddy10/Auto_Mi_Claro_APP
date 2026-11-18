package tasks.PagosYConsultas;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static utils.Constants.DISFRUTA_DE_TIEMPO_CON_MICLARO;
import static utils.Constants.HAZLO_TODO_EN_LINEA;
import static utils.Constants.VER_MAS;

import interactions.Click.ClickTextoQueContengaX;
import interactions.Scroll.Scroll;
import interactions.Scroll.ScrollHastaTexto;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import utils.EvidenciaUtils;

public class AccederMenuPagosYConsultas implements Task {
  @Override
  public <T extends Actor> void performAs(T actor) {
    actor.attemptsTo(
        ScrollHastaTexto.conTexto(HAZLO_TODO_EN_LINEA),
        ScrollHastaTexto.conTexto(DISFRUTA_DE_TIEMPO_CON_MICLARO),
        Scroll.scrollMediaVista(),
        ClickTextoQueContengaX.elTextoContiene(VER_MAS));
    EvidenciaUtils.registrarCaptura("Menu Pagos y consultas");
  }

  public static Performable acceder() {
    return instrumented(AccederMenuPagosYConsultas.class);
  }
}
