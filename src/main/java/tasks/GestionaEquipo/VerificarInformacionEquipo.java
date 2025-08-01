package tasks.GestionaEquipo;

import interactions.validations.ValidarTexto;
import interactions.validations.ValidarTextoQueContengaX;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import utils.EvidenciaUtils;

import static net.serenitybdd.screenplay.Tasks.instrumented;
public class VerificarInformacionEquipo implements Task {

    private static final String paso = "Verificar Información del Equipo";

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                ValidarTextoQueContengaX.elTextoContiene("IMEI:"),
                //ValidarTexto.validarTexto("860479068145924"),
                ValidarTextoQueContengaX.elTextoContiene("Marca:"),
               // ValidarTexto.validarTexto("POCO"),
                ValidarTextoQueContengaX.elTextoContiene("Modelo:"),
               // ValidarTexto.validarTexto("2201116PG"),
                ValidarTextoQueContengaX.elTextoContiene("Estado:"),
                ValidarTextoQueContengaX.elTextoContiene("Este celular ya fue registrado exitosamente")
        );

        EvidenciaUtils.registrarCaptura(paso);
    }

    public static Performable verificar() {
        return instrumented(VerificarInformacionEquipo.class);
    }
}