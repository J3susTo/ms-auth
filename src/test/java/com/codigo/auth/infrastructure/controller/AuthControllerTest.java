package com.codigo.auth.infrastructure.controller;

import com.codigo.auth.application.port.input.AuthUseCase;
import com.codigo.auth.domain.model.Usuario;
import com.codigo.auth.infrastructure.client.dto.UsuarioAuthDTO;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class AuthControllerTest {

    private final AuthUseCase authUseCase = mock(AuthUseCase.class);
    private final AuthController authController = new AuthController(authUseCase);

    @Test
    void testRegister() {
        Usuario usuario = new Usuario(1L, "Ana", "ana@mail.com", "password", Usuario.Rol.ADMIN);
        when(authUseCase.register(any(Usuario.class))).thenReturn(usuario);

        ResponseEntity<Usuario> response = authController.register(usuario);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(usuario, response.getBody());
    }

    @Test
    void testLogin() {
        String email = "ana@mail.com";
        String password = "password";
        when(authUseCase.login(email, password)).thenReturn("mocked-jwt-token");

        ResponseEntity<String> response = authController.login(email, password);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("mocked-jwt-token", response.getBody());
    }

    @Test
    void testValidate() {
        // Preparamos el mock de Usuario
        Usuario usuario = new Usuario(1L, "Ana", "ana@mail.com", "password", Usuario.Rol.ADMIN);

        // Modificamos para que el método validateToken retorne un Usuario
        when(authUseCase.validateToken(anyString())).thenReturn(usuario);

        // Llamamos al controlador con el token simulado
        ResponseEntity<UsuarioAuthDTO> response = authController.validate("Bearer mocked-jwt-token");

        // Verificamos que la respuesta sea un UsuarioAuthDTO, no un Usuario
        assertEquals(200, response.getStatusCodeValue());

        // Verificamos que el DTO se haya construido correctamente
        UsuarioAuthDTO usuarioAuthDTO = response.getBody();
        assertNotNull(usuarioAuthDTO);
        assertEquals(usuario.getId(), usuarioAuthDTO.getId());
        assertEquals(usuario.getNombre(), usuarioAuthDTO.getNombre());
        assertEquals(usuario.getEmail(), usuarioAuthDTO.getEmail());
        assertEquals(usuario.getRol().name(), usuarioAuthDTO.getRol());
        assertTrue(usuarioAuthDTO.isValid());
    }
}
