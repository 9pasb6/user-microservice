package emazon.microservice.user_microservice.infrastructure.configuration;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // Deshabilita CSRF para simplificar
                .csrf(csrf -> csrf.disable())
                // Configura las solicitudes HTTP
                .authorizeHttpRequests(authz -> authz
                        // Permite acceso sin autenticación para ciertas rutas y métodos
                        .requestMatchers(HttpMethod.GET, "/api/users").permitAll() // GET a /api/users permitido sin autenticación
                        .requestMatchers(HttpMethod.POST, "/api/users").permitAll() // POST a /api/users permitido sin autenticación
                        .requestMatchers(HttpMethod.GET, "/api/roles").permitAll() // GET a /api/roles requiere rol ADMIN
                        .requestMatchers(HttpMethod.POST, "/api/roles").permitAll() // POST a /api/roles requiere rol ADMIN
                        .anyRequest().authenticated() // Requiere autenticación para cualquier otra solicitud
                )
                // Configura el inicio de sesión
                .formLogin(form -> form
                        .permitAll() // Permite el inicio de sesión para todos
                )
                // Configura el manejo de excepciones
                .exceptionHandling(exceptions -> exceptions
                        .authenticationEntryPoint((request, response, authException) -> {
                            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
                        })
                );
        return http.build();
    }
}