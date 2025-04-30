package com.codigo.auth.infrastructure.client.dto;

import com.codigo.auth.domain.model.Usuario;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UsuarioAuthDTO {
    private Long id;
    private String nombre;
    private String email;
    private Usuario.Rol rol;
    private boolean isValid;
}
