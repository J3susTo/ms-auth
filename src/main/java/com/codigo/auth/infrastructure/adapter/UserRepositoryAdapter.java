package com.codigo.auth.infrastructure.adapter;

import com.codigo.auth.application.port.output.UserRepositoryPort;
import com.codigo.auth.domain.model.Usuario;
import com.codigo.auth.infrastructure.entity.UsuarioEntity;
import com.codigo.auth.infrastructure.mapper.UsuarioMapper;
import com.codigo.auth.infrastructure.repository.UsuarioRepositoryJpa;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserRepositoryAdapter implements UserRepositoryPort {

    private final UsuarioRepositoryJpa usuarioRepositoryJpa;

    @Override
    public Usuario save(Usuario usuario) {
        UsuarioEntity entity = UsuarioMapper.toEntity(usuario);
        return UsuarioMapper.toDomain(usuarioRepositoryJpa.save(entity));
    }

    @Override
    public Usuario findByEmail(String email) {
        return usuarioRepositoryJpa.findByEmail(email)
                .map(UsuarioMapper::toDomain)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    }

    @Override
    public boolean existsByEmail(String email) {
        return usuarioRepositoryJpa.existsByEmail(email); // Asumiendo que existe un método en tu repositorio Jpa
    }
}
