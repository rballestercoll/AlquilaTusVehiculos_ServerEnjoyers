package org.serverenjoyers.alquilatusvehiculos_serverenjoyers.config;

import org.serverenjoyers.alquilatusvehiculos_serverenjoyers.jwt.JwtAuthenticationFilter;
import org.serverenjoyers.alquilatusvehiculos_serverenjoyers.jwt.JwtDeniedHandler;
import org.serverenjoyers.alquilatusvehiculos_serverenjoyers.jwt.JwtEntryPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private JwtAuthenticationFilter jwtTokenFilter;

    @Autowired
    private JwtEntryPoint jwtEntryPoint;

    @Autowired
    private JwtDeniedHandler jwtDeniedHandler;

    @Bean
    @Order(1)
    public SecurityFilterChain apiSecurity(HttpSecurity http) throws Exception {

        http
                .securityMatcher("/api/**") // Solo afecta a rutas API
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/**").permitAll()
                        .requestMatchers("/swagger-ui/**", "/swagger-ui.html","/v3/api-docs/**").permitAll()
                        .requestMatchers("/api/alquileres/**").hasAnyRole("USER", "ADMIN")
                        .requestMatchers("/api/clientes/**").hasRole("ADMIN")
                        .requestMatchers("/api/vehiculos/**").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .exceptionHandling(handling -> handling
                        .authenticationEntryPoint(jwtEntryPoint)        // 401
                        .accessDeniedHandler(jwtDeniedHandler)          // 403
                )
                .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

//    @Bean
//    @Order(2)
//    public SecurityFilterChain webSecurity(HttpSecurity http) throws Exception {
//
//        http
//                .securityMatcher("/**")
//                .csrf(AbstractHttpConfigurer::disable)
//                .authorizeHttpRequests(auth -> auth
//
//                        .requestMatchers(
//                                "/", "/login", "/registro",
//                                "/assets/**",
//                                "/swagger-ui/**",
//                                "/v3/api-docs/**",
//                                "/swagger-ui.html"
//                        ).permitAll()
//
//                        .requestMatchers("/admin/**").hasRole("ADMIN")
//                        .requestMatchers("/user/**").hasRole("USER")
//                        .requestMatchers("/alquileres/**").hasAnyRole("USER", "ADMIN")
//
//                        .anyRequest().authenticated()
//                )
//                .formLogin(form -> form
//                        .loginPage("/login")
//                        .permitAll()
//                )
//                .logout(logout -> logout.permitAll());
//
//        return http.build();
//    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }
}
