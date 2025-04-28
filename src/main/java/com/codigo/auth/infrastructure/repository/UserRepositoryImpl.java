package com.codigo.auth.infrastructure.repository;

import com.codigo.auth.domain.model.Usuario;
import com.codigo.auth.infrastructure.entity.UsuarioEntity;
import com.codigo.auth.infrastructure.mapper.UsuarioMapper;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepositoryImpl implements UserRepository {

    private final UsuarioRepositoryJpa usuarioRepositoryJpa;

    public UserRepositoryImpl(UsuarioRepositoryJpa usuarioRepositoryJpa) {
        this.usuarioRepositoryJpa = usuarioRepositoryJpa;
    }

    @Override
    public Usuario findByEmail(String email) {
        return usuarioRepositoryJpa.findByEmail(email)
                .map(UsuarioMapper::toDomain)
                .orElse(null);
    }

    @Override
    public boolean existsByEmail(String email) {
        return usuarioRepositoryJpa.findByEmail(email).isPresent();
    }

    @Override
    public Usuario save(Usuario usuario) {
        UsuarioEntity entity = UsuarioMapper.toEntity(usuario);
        UsuarioEntity savedEntity = usuarioRepositoryJpa.save(entity);
        return UsuarioMapper.toDomain(savedEntity);
    }
}
