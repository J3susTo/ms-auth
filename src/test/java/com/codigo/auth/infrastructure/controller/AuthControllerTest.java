package com.codigo.auth.infrastructure.controller;

import com.codigo.auth.application.port.input.AuthUseCase;
import com.codigo.auth.domain.model.Usuario;
import com.codigo.auth.infrastructure.client.dto.UsuarioAuthDTO;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class AuthControllerTest {

    // Creacion mock
    private final AuthUseCase authUseCase = mock(AuthUseCase.class);

    // instanciando el controlador, inyectando el mock
    private final AuthController authController = new AuthController(authUseCase);

    @Test
    void testRegister() {
        // usuario
        Usuario usuario = new Usuario(1L, "Ana", "ana@mail.com", "password", Usuario.Rol.ADMIN);

        // Configuracion del comportamiento del mock para register
        when(authUseCase.register(any(Usuario.class))).thenReturn(usuario);

        // Llamamos al método register del controlador
        ResponseEntity<Usuario> response = authController.register(usuario);

        // Verificacion del status sea 200 OK
        assertEquals(HttpStatus.OK, response.getStatusCode());

        // Se iguala la respuesta con el body
        assertEquals(usuario, response.getBody());
    }

    @Test
    void testLogin() {
        // Datos de entrada
        String email = "ana@mail.com";
        String password = "password";

        // Configuracion del mock para devolver un token falso
        when(authUseCase.login(email, password)).thenReturn("mocked-jwt-token");

        // Llamamos al método login del controlador
        ResponseEntity<String> response = authController.login(email, password);

        // Verificacion que el status sea 200 OK
        assertEquals(HttpStatus.OK, response.getStatusCode());

        // Verificacion del token
        assertEquals("mocked-jwt-token", response.getBody());
    }

    @Test
    void testValidate() {
        // Creamos un usuario simulado que representa el usuario autenticado
        Usuario usuario = new Usuario(1L, "Ana", "ana@mail.com", "password", Usuario.Rol.ADMIN);

        // Configuramos el mock para que al validar el token, retorne el usuario simulado
        when(authUseCase.validateToken(anyString())).thenReturn(usuario);

        // Llamamos al método validate del controlador con un token simulado
        ResponseEntity<UsuarioAuthDTO> response = authController.validate("Bearer mocked-jwt-token");

        // Verificamos que el status sea 200 OK
        assertEquals(HttpStatus.OK, response.getStatusCode());

        // Obtenemos el cuerpo de la respuesta (el DTO)
        UsuarioAuthDTO usuarioAuthDTO = response.getBody();

        // Verificamos que el DTO no sea nulo
        assertNotNull(usuarioAuthDTO);

        // Verificamos que los campos del DTO coincidan con los del usuario original
        assertEquals(usuario.getId(), usuarioAuthDTO.getId());
        assertEquals(usuario.getNombre(), usuarioAuthDTO.getNombre());
        assertEquals(usuario.getEmail(), usuarioAuthDTO.getEmail());

        // Comparamos el enum de tipo Rol (usamos el objeto directamente, no String)
        assertEquals(Usuario.Rol.ADMIN, usuario.getRol());

        // Verificamos que el DTO marque el token como válido
        assertTrue(usuarioAuthDTO.isValid());
    }
}
