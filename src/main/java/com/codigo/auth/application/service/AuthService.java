package com.codigo.auth.application.service;

import com.codigo.auth.application.port.input.AuthUseCase;
import com.codigo.auth.application.port.output.UserRepositoryPort;
import com.codigo.auth.domain.model.Usuario;
import com.codigo.auth.infrastructure.config.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

@Service
@Primary
@RequiredArgsConstructor
public class AuthService implements AuthUseCase {

    private final UserRepositoryPort userRepository;
    private final JwtProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Usuario register(Usuario usuario) {
        // Antes de guardar el usuario, encriptamos su contraseña
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        return userRepository.save(usuario);
    }

    @Override
    public String login(String email, String password) {
        Usuario user = userRepository.findByEmail(email);
        if (user != null && passwordEncoder.matches(password, user.getPassword())) {
            return jwtProvider.generateToken(user);
        }
        throw new BadCredentialsException("Credenciales inválidas");
    }

    @Override
    public Usuario validateToken(String token) {
        Usuario user = jwtProvider.validateToken(token);
        if (user == null) {
            throw new BadCredentialsException("Token inválido");
        }
        return user;
    }
}
