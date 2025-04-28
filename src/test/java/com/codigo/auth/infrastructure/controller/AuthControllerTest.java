package com.codigo.auth.infrastructure.controller;

import com.codigo.auth.application.port.input.AuthUseCase;
import com.codigo.auth.domain.model.Usuario;
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
        Usuario usuario = new Usuario(1L, "Ana", "ana@mail.com", "password", Usuario.Rol.ADMIN);
        when(authUseCase.validateToken(anyString())).thenReturn(usuario);

        ResponseEntity<Usuario> response = authController.validate("Bearer mocked-jwt-token");

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(usuario, response.getBody());
    }
}
