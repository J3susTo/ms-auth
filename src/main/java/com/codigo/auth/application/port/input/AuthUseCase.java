package com.codigo.auth.application.port.input;

import com.codigo.auth.domain.model.Usuario;

public interface AuthUseCase {
    Usuario register(Usuario usuario);
    String login(String email, String password);
    Usuario validateToken(String token);
}
