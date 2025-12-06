package org.serverenjoyers.alquilatusvehiculos_serverenjoyers.controller;

import org.serverenjoyers.alquilatusvehiculos_serverenjoyers.dto.LoginForm;
import org.serverenjoyers.alquilatusvehiculos_serverenjoyers.dto.RegisterForm;
import org.serverenjoyers.alquilatusvehiculos_serverenjoyers.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Autenticación", description = "Operaciones de login, registro y verificación de autenticación")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Operation(summary = "Login de cliente/usuario", description = "Autentica un usuario usando email/password y devuelve un token JWT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Autenticación correcta, token JWT devuelto"),
            @ApiResponse(responseCode = "401", description = "Credenciales incorrectas")
    })
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginForm loginForm) {
        try {
            String jwt = authService.authenticate(loginForm.getUsuario(), loginForm.getPassword());
            return ResponseEntity.ok(jwt);
        } catch (Exception e) {
            return ResponseEntity.status(401).body("Credenciales incorrectas");
        }
    }

    @Operation(summary = "Registro de nuevo cliente", description = "Crea un nuevo cliente + usuario asociado, con email/password")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Cliente registrado correctamente"),
            @ApiResponse(responseCode = "400", description = "Error en los datos de registro")
    })
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterForm registerForm) {
        try {
            authService.registerClient(registerForm);
            return ResponseEntity.status(HttpStatus.CREATED).body("Cliente registrado correctamente");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al registrar el cliente: " + e.getMessage());
        }
    }

    @Operation(summary = "Comprobar autenticación actual", description = "Devuelve información del usuario autenticado (email)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario autenticado"),
            @ApiResponse(responseCode = "401", description = "No autenticado / token inválido")
    })
    @GetMapping("/check-auth")
    public ResponseEntity<String> checkAuth() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        return ResponseEntity.ok("Autenticado como: " + email);
    }
}