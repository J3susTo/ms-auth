package com.codigo.auth.infrastructure.repository;

import com.codigo.auth.domain.model.Usuario;

public interface UserRepository {
    Usuario findByEmail(String email);
    boolean existsByEmail(String email);
    Usuario save(Usuario usuario);
}