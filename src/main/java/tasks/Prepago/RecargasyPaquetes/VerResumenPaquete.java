package tasks.Prepago.RecargasyPaquetes;

import interactions.Click.ClickTextoQueContengaX;
import interactions.validations.ValidarTexto;
import interactions.validations.ValidarTextoQueContengaX;
import interactions.wait.WaitForResponse;
import models.User;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.targets.Target;
import org.openqa.selenium.By;
import utils.EvidenciaUtils;
import utils.TestDataProvider;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static utils.Constants.*;

public class VerResumenPaquete implements Task {

    private static final User user = TestDataProvider.getRealUser();
    private static final String paso1 = "Validar paquete seleccionado y Clic en Ver resumen";
    private static final String paso2 = "Validar información del resumen";
    private static final String paso3 = "Clic Armar paquete";

    @Override
    public <T extends Actor> void performAs(T actor) {

        EvidenciaUtils.registrarCaptura(paso1);

        // Hacer clic en Ver resumen
        actor.attemptsTo(
                ClickTextoQueContengaX.elTextoContiene(VER_RESUMEN),
                WaitForResponse.withText(RESUMEN_DEL_PAQUETE)
        );


        // Validar pantalla de resumen
        actor.attemptsTo(
                ValidarTexto.validarTexto(RESUMEN_DEL_PAQUETE),
                ValidarTexto.validarTexto(user.getCantidadDatosArmar()),
                ValidarTexto.validarTexto(PRECIO),
                ValidarTexto.validarTexto(MIN_ILIMITADOS),
                ValidarTextoQueContengaX.elTextoContiene(user.getDuracionPaqueteArmar()),
                ValidarTextoQueContengaX.elTextoContiene(APPS_INCLUIDAS),
                ValidarTextoQueContengaX.elTextoContiene(TODOS_PAQUETES_INCLUYEN)
        );

        EvidenciaUtils.registrarCaptura(paso2);


        // Hacer clic en DROPDOWN_DURACION para regresar
        actor.attemptsTo(
                Click.on(duracionDinamica(user.getDuracionPaqueteArmar()))
        );

        EvidenciaUtils.registrarCaptura(paso3);

        // Armar paquete
        actor.attemptsTo(
                ClickTextoQueContengaX.elTextoContiene(ARMAR_PAQUETE),
                WaitForResponse.withText(PAQUETE_ARMADO_PARA_LINEA)
        );
    }

    public static Target duracionDinamica(String duracion) {
        return Target.the("Duración " + duracion)
                .located(By.xpath("//android.widget.Button[@text='" + duracion + "']"));
    }


    public static Performable validarResumen() {
        return instrumented(VerResumenPaquete.class);
    }
}