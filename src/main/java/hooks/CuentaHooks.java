package hooks;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import utils.ContextoST;
import utils.CuentaManager;

/**
 * Selecciona la cuenta del escenario segun el tag @cuenta_<id> (por ejemplo @cuenta_secundaria).
 * Sin tag, arranca con "principal". Alternativa: el step EL USUARIO CAMBIA A LA CUENTA "...".
 */
public class CuentaHooks {

  @Before(order = 1)
  public void seleccionarCuenta(Scenario scenario) {
    CuentaManager.activarDesdeTags(scenario.getSourceTagNames());
    System.out.println(
        "\uD83D\uDC64 [CUENTA] Escenario '" + scenario.getName()
            + "' inicia con la cuenta: " + CuentaManager.getIdCuentaActiva());
  }

  @After(order = 0)
  public void limpiarCuenta(Scenario scenario) {
    // El registro va ANTES del reset: reset() devuelve la cuenta activa a "principal"
    // y dejaria a ContextoST informando la cuenta equivocada.
    ContextoST.registrarEscenario(scenario);
    CuentaManager.reset();
  }
}
