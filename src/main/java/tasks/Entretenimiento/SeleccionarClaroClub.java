package tasks.Entretenimiento;

import interactions.Click.ClickTextoQueContengaX;
import interactions.Scroll.ScrollHastaTexto;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import utils.EvidenciaUtils;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static utils.Constants.*;

public class SeleccionarClaroClub implements Task {
    private static final String paso = "Scrool hasta tus plataformas favoritas y seleccionar ver mas";
    private static final String paso2 = "Seleccionar Claro Club";

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                ScrollHastaTexto.conTexto(TUS_PLATAFORMAS_FAVORITAS)
        );
        EvidenciaUtils.registrarCaptura(paso);
        actor.attemptsTo(
                ClickTextoQueContengaX.elTextoContiene(VER_MAS)
        );
        EvidenciaUtils.registrarCaptura(paso2);
        actor.attemptsTo(
                ClickTextoQueContengaX.elTextoContiene(CLARO_CLUB)
        );
    }

    public static Performable seleccionar() {
        return instrumented(SeleccionarClaroClub.class);
    }
}
