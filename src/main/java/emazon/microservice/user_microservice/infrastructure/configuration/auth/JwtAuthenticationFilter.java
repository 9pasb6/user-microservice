package emazon.microservice.user_microservice.infrastructure.configuration.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import emazon.microservice.user_microservice.aplication.dto.request.UserLoginRequest;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static emazon.microservice.user_microservice.aplication.security.TokenJwtConfig.*;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            UserLoginRequest loginRequest = new ObjectMapper().readValue(request.getInputStream(), UserLoginRequest.class);
            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
            );
        } catch (IOException e) {
            throw new RuntimeException("Error in request", e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        String username = authResult.getName();
        String token = Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 3600 * 1000)) // 1 hora
                .signWith(SECRET_KEY, SignatureAlgorithm.HS256)  // Usar la clave secreta global
                .compact();

        Map<String, String> body = new HashMap<>();
        body.put("token", token);
        body.put("username", username);
        response.setContentType(CONTENT_TYPE);
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().write(new ObjectMapper().writeValueAsString(body));
    }
}