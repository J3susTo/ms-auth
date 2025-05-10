package com.codigo.auth.infrastructure.mapper;

import com.codigo.auth.domain.model.Usuario;
import com.codigo.auth.infrastructure.entity.UsuarioEntity;

public class UsuarioMapper {

    // Convertir de dominio Usuario a entidad UsuarioEntity
    public static UsuarioEntity toEntity(Usuario usuario) {
        if (usuario == null) {
            return null;
        }

        return UsuarioEntity.builder()
                .nombre(usuario.getNombre())
                .email(usuario.getEmail())
                .password(usuario.getPassword())
                .rol(usuario.getRol())
                .build();
    }

    // Convertir de entidad UsuarioEntity a dominio Usuario
    public static Usuario toDomain(UsuarioEntity entity) {
        if (entity == null) {
            return null;
        }

        return Usuario.builder()
                .id(entity.getId())
                .nombre(entity.getNombre())
                .email(entity.getEmail())
                .password(entity.getPassword())
                .rol(entity.getRol())
                .build();
    }
}
