package emazon.microservice.user_microservice.infrastructure.configuration.auth;

import org.springframework.security.core.userdetails.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import static emazon.microservice.user_microservice.aplication.security.TokenJwtConfig.*;

public class JwtValidationFilter extends BasicAuthenticationFilter {

    public JwtValidationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String header = request.getHeader(HEADER_AUTHORIZATION);

        if (header == null || !header.startsWith(PREFIX_TOKEN)) {
            chain.doFilter(request, response);
            return;
        }

        String token = header.replace(PREFIX_TOKEN, "");

        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(SECRET_KEY)  // Usar la clave secreta global
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            String username = claims.getSubject();
            List<SimpleGrantedAuthority> authorities = ((List<String>) claims.get("roles")).stream()
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toList());

            if (username != null) {
                UserDetails userDetails = new User(username, "", authorities);
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, authorities);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }

            chain.doFilter(request, response);

        } catch (JwtException e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Token no válido");
        }
    }
}