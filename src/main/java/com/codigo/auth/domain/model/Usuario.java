package com.codigo.auth.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder // Usamos el patrón Builder aquí
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {

    private Long id;               // ID del usuario (clave primaria)
    private String nombre;         // Nombre del usuario
    private String email;          // Correo electrónico
    private String password;       // Contraseña
    private Rol rol;               // Rol del usuario

    // Enum para definir los roles de usuario
    public enum Rol {
        SUPERADMIN, ADMIN, USUARIO
    }
}
