package org.example.retospringbootweb.seguridad;

import jakarta.servlet.http.HttpServletResponse;
import org.example.retospringbootweb.dto.ErrorResponseDTO;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import tools.jackson.databind.ObjectMapper;

import java.time.LocalDateTime;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Mantener deshabilitado para desarrollo
                .authorizeHttpRequests(auth -> auth
                        // PERMITIR registro y login a todo el mundo (GET y POST)
                        .requestMatchers("/login", "/registro", "/css/**", "/js/**").permitAll()

                        // PERMITIR ver las rutas a todo el mundo
                        .requestMatchers(HttpMethod.GET, "/rutas/**", "/ruta_id/**").permitAll()

                        // EL RESTO (Añadir rutas, editar, etc.) requiere autenticación
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .loginProcessingUrl("/login") // Spring Security gestiona este POST solo
                        .defaultSuccessUrl("/rutas", true)
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout")
                        .permitAll()
                );

        return http.build();
    }

    @Bean
    public AuthenticationEntryPoint customAuthenticationEntryPoint() {
        return ((request, response, authException) -> {
            response.setContentType("application/json");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            ErrorResponseDTO error = new ErrorResponseDTO(LocalDateTime.now(),401,"Usuario no autorizado", "El usuario y la contraseña no coinciden");
            ObjectMapper mapper = new ObjectMapper();
            response.getWriter().write(mapper.writeValueAsString(error));
        });
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
