package com.codigo.auth.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder 
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {

    private Long id;
    private String nombre;
    private String email;
    private String password;
    private Rol rol;

    // Enum para definir los roles de usuario
    public enum Rol {
        SUPERADMIN, ADMIN, USUARIO
    }
}
