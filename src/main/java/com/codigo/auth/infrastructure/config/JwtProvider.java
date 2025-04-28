package com.codigo.auth.infrastructure.config;

import com.codigo.auth.application.port.output.UserRepositoryPort;
import com.codigo.auth.domain.model.Usuario;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

@Component
public class JwtProvider {

    private final String SECRET_KEY = "mysecretkey"; // Cambia esta clave por una más segura
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
        String username = getUsernameFromToken(token);
        if (username != null) {
            return userRepository.findByEmail(username); // Busca el usuario en la base de datos
        }
        return null; // Token inválido o no se encontró el usuario
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
