package com.codigo.auth.application.port.output;

import com.codigo.auth.domain.model.Usuario;

public interface UserRepositoryPort {
    Usuario save(Usuario usuario);
    Usuario findByEmail(String email);
}
