package org.serverenjoyers.alquilatusvehiculos_serverenjoyers.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Formulario de login para autenticación de usuario")
public class LoginForm {

    @Schema(description = "Email o nombre de usuario", example = "juan.perez@example.com")
    private String usuario;

    @Schema(description = "Contraseña del usuario", example = "1234")
    private String password;

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
