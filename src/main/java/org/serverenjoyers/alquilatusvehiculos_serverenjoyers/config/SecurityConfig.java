package org.serverenjoyers.alquilatusvehiculos_serverenjoyers.config;

import org.serverenjoyers.alquilatusvehiculos_serverenjoyers.repository.ClienteRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.serverenjoyers.alquilatusvehiculos_serverenjoyers.repository.UsuarioRepository; // <-- CAMBIO

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    /**
     * Define el "cifrador" de contraseñas.
     * Usaremos BCrypt, que es el estándar de la industria.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Le dice a Spring Security cómo buscar a nuestros usuarios.
     * Inyectamos tu ClienteRepository y le decimos que busque por email.
     */
    @Bean
    public UserDetailsService userDetailsService(UsuarioRepository usuarioRepository) { // <-- CAMBIO
        return email -> usuarioRepository.findByEmail(email) // <-- CAMBIO
                .orElseThrow(() -> new UsernameNotFoundException("No se encontró usuario con email: " + email));
    }

    /**
     * Aquí definimos las REGLAS de seguridad de nuestra aplicación.
     * Este método reemplaza por completo tu LoginController manual.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize -> authorize
                        // 1. Definimos las rutas PÚBLICAS (que no necesitan login)
                        .requestMatchers(
                                "/",
                                "/home",
                                "/registro",    // Permitimos ver el formulario de registro
                                "/login",       // Permitimos ver el formulario de login
                                "/assets/**",   // Todas las imágenes, logos, etc.
                                "/style.css"    // Tu hoja de estilos principal
                        ).permitAll()

                        .requestMatchers("/clientes/**", "/vehiculos/**").hasRole("ADMIN")  // las rutas del admin

                        .requestMatchers("/perfil", "/alquileres/**").hasAnyRole("USER", "ADMIN")

                        // 2. Todo lo demás (anyRequest) debe estar AUTENTICADO (requiere login)
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        // 3. Le decimos a Spring dónde está nuestra página de login personalizada
                        .loginPage("/login")    // La ruta que definimos en LoginController (GET)
                        .loginProcessingUrl("/login") // La ruta a la que el formulario HTML hace POST
                        .defaultSuccessUrl("/perfil", true) // A dónde ir después de un login exitoso
                        .permitAll() // La página de login debe ser pública
                )
                .logout(logout -> logout
                        // 4. Configuramos el logout (esto reemplaza tu método /logout en LoginController)
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout") // Redirige a /login con un parámetro
                        .permitAll()
                );

        return http.build();
    }
}
