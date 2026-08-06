package hooks;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import utils.CuentaManager;

/**
 * (Opcional) Selecciona la cuenta segun el tag @cuenta_&lt;id&gt; del escenario. Sin tag, arranca con
 * "principal". Util si prefieres marcar el escenario en vez de usar el step de cambio.
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
    CuentaManager.reset();
  }
}
