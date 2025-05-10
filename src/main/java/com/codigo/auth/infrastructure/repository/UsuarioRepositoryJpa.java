package com.codigo.auth.infrastructure.repository;

import com.codigo.auth.infrastructure.entity.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UsuarioRepositoryJpa extends JpaRepository<UsuarioEntity, Long> {

    Optional<UsuarioEntity> findByEmail(String email);

    boolean existsByEmail(String email); // Aqui Verificamos si un usuario con este correo existe
}
