Feature: Ingreso a la Super App

  @SA001
    Scenario: Login exitoso con correo electrónico
    Given EL USUARIO ABRE LA SUPER APP
    When  REALIZA EL INGRESO
    Then  VERIFICA VERSION DE LA SUPER APP

  @SA002
  Scenario: Login exitoso con cédula
    Given EL USUARIO ABRE LA SUPER APP
    When  REALIZA EL INGRESO CON CEDULA
    Then  VERIFICA VERSION DE LA SUPER APP

