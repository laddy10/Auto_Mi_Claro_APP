# language: es
Característica: Ingreso a la Super App

  @SA001
    Escenario: Login exitoso con correo electrónico
    Dado EL USUARIO ABRE LA SUPER APP
    Cuando REALIZA EL INGRESO
    Entonces  VERIFICA VERSION DE LA SUPER APP

  @SA002
  Escenario: Login exitoso con cédula
    Dado EL USUARIO ABRE LA SUPER APP
    Cuando REALIZA EL INGRESO CON CEDULA
    Entonces  VERIFICA VERSION DE LA SUPER APP


    ################################################################################################

  #Login

  @LOGIN_CORREO_01
  Escenario: Login exitoso con correo electrónico
    Dado EL USUARIO ABRE LA SUPER APP
    Cuando REALIZA EL INGRESO CON CORREO


  @LOGIN_DOCUMENTO_01
  Escenario: Login exitoso con documento
    Dado EL USUARIO ABRE LA SUPER APP
    Cuando REALIZA EL INGRESO CON DOCUMENTO


  @LOGIN_PIN_01
  Escenario: Login exitoso con pin
    Dado EL USUARIO ABRE LA SUPER APP
    Cuando REALIZA EL INGRESO CON PIN