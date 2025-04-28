package com.codigo.auth.domain.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class UsuarioTest {

    @Test
    void testUsuarioBuilder() {
        Usuario usuario = Usuario.builder()
                .id(1L)
                .nombre("Carlos")
                .email("carlos@mail.com")
                .password("securepass")
                .rol(Usuario.Rol.ADMIN)
                .build();

        assertNotNull(usuario);
        assertEquals(1L, usuario.getId());
        assertEquals("Carlos", usuario.getNombre());
        assertEquals("carlos@mail.com", usuario.getEmail());
        assertEquals("securepass", usuario.getPassword());
        assertEquals(Usuario.Rol.ADMIN, usuario.getRol());
    }

    @Test
    void testUsuarioSettersAndGetters() {
        Usuario usuario = new Usuario();
        usuario.setId(2L);
        usuario.setNombre("Lucia");
        usuario.setEmail("lucia@mail.com");
        usuario.setPassword("password123");
        usuario.setRol(Usuario.Rol.SUPERADMIN);

        assertEquals(2L, usuario.getId());
        assertEquals("Lucia", usuario.getNombre());
        assertEquals("lucia@mail.com", usuario.getEmail());
        assertEquals("password123", usuario.getPassword());
        assertEquals(Usuario.Rol.SUPERADMIN, usuario.getRol());
    }
}
