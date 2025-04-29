package com.codigo.auth.application.service;

import com.codigo.auth.application.port.input.AuthUseCase;
import com.codigo.auth.application.port.output.UserRepositoryPort;
import com.codigo.auth.domain.model.Usuario;
import com.codigo.auth.infrastructure.config.JwtProvider;
import org.springframework.stereotype.Service;

public class AuthServiceImpl implements AuthUseCase {

    private final UserRepositoryPort userRepository;
    private final JwtProvider jwtProvider;

    public AuthServiceImpl(UserRepositoryPort userRepository, JwtProvider jwtProvider) {
        this.userRepository = userRepository;
        this.jwtProvider = jwtProvider;
    }

    @Override
    public Usuario register(Usuario usuario) {
        return userRepository.save(usuario);
    }

    @Override
    public String login(String email, String password) {
        Usuario user = userRepository.findByEmail(email);
        if (user != null && user.getPassword().equals(password)) {
            return jwtProvider.generateToken(user); // Generar token al hacer login
        }
        throw new RuntimeException("Credenciales inválidas");
    }

    @Override
    public Usuario validateToken(String token) {
        Usuario user = jwtProvider.validateToken(token); // Ahora retorna un Usuario
        if (user == null) {
            throw new RuntimeException("Token inválido");
        }
        return user;
    }
}
