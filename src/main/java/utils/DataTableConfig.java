package utils;

import io.cucumber.java.DataTableType;
import models.User;

import java.util.Map;

public class DataTableConfig {

    @DataTableType
    public User userEntry(Map<String, String> entry) {
        User user = new User();

        user.setEmail(entry.getOrDefault("email", ""));
        user.setCedula(entry.getOrDefault("cedula", ""));
        user.setPassword(entry.getOrDefault("password", ""));
        user.setNombreUsuario(entry.getOrDefault("nombreUsuario", ""));
        user.setNumero(entry.getOrDefault("numero", ""));
        user.setMontoPagoParcial(entry.getOrDefault("montoPagoParcial", ""));
        user.setContrasena(entry.getOrDefault("contrasena", ""));
        user.setTipoPaquete(entry.getOrDefault("tipoPaquete", ""));
        user.setValorRecarga(entry.getOrDefault("valorRecarga", ""));
        user.setNumeroFamiliayAmigos(entry.getOrDefault("numeroFamiliayAmigos", ""));

        return user;
    }
}