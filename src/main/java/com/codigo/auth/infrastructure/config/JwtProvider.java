package com.codigo.auth.infrastructure.config;

import com.codigo.auth.application.port.output.UserRepositoryPort;
import com.codigo.auth.domain.model.Usuario;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtProvider {

    @Value("${key.signature}")
    private String SECRET_KEY;

    private final UserRepositoryPort userRepository; // Suponiendo que tienes un repositorio de usuarios

    public JwtProvider(UserRepositoryPort userRepository) {
        this.userRepository = userRepository;
    }

    // Método para obtener el username desde el token
    public String getUsernameFromToken(String token) {
        return getClaimsFromToken(token).getSubject(); // El subject en el JWT es el username
    }

    // Método para validar el token y retornar el Usuario asociado
    public Usuario validateToken(String token) {
        try {
            String username = getUsernameFromToken(token);
            if (username != null) {
                return userRepository.findByEmail(username);
            }
        } catch (Exception e) {
            System.out.println("Token inválido o expirado: " + e.getMessage());
        }
        return null;
    }

    // Método para obtener los Claims del token
    private Claims getClaimsFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
    }

    // Método para generar un token JWT
    public String generateToken(Usuario usuario) {
        return Jwts.builder()
                .setSubject(usuario.getEmail()) // Suponiendo que usas el email como el subject
                .setIssuedAt(new java.util.Date())
                .setExpiration(new java.util.Date(System.currentTimeMillis() + 1000 * 60 * 60)) // Expiración de 1 hora
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .compact();
    }
}
