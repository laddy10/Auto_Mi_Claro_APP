package tasks.Entretenimiento.RutasDeAcceso;

import interactions.Click.ClickTextoQueContengaX;
import interactions.Scroll.ScrollHastaTexto;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import utils.EvidenciaUtils;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static utils.Constants.*;

public class AccederVerMasTusServiciosFavoritos implements Task {
    private static final String paso = "Scroll a Tus Servicios Favoritos";
    private static final String paso2 = "Click en Ver más";
    private static final String paso3 = "Scroll a Entretenimiento";

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                ScrollHastaTexto.conTexto(TUS_SERVICIOS_FAVORITOS)
        );
        EvidenciaUtils.registrarCaptura(paso);
        EvidenciaUtils.registrarCaptura(paso2);
        actor.attemptsTo(
                ClickTextoQueContengaX.elTextoContiene(VER_MAS)
        );
        actor.attemptsTo(
                ScrollHastaTexto.conTexto(CLARO_MUSICA)
        );
        EvidenciaUtils.registrarCaptura(paso3);
    }

    public static Performable acceder() {
        return instrumented(AccederVerMasTusServiciosFavoritos.class);
    }
}
