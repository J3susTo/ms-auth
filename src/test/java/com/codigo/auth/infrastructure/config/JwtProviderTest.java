package com.codigo.auth.infrastructure.config;

import com.codigo.auth.application.port.output.UserRepositoryPort;
import com.codigo.auth.domain.model.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class JwtProviderTest {

    private JwtProvider jwtProvider;
    private UserRepositoryPort userRepositoryPort;

    @BeforeEach
    void setUp() {
        userRepositoryPort = mock(UserRepositoryPort.class);
        jwtProvider = new JwtProvider(userRepositoryPort);
    }

    @Test
    void testGenerateAndGetUsernameFromToken() {
        Usuario usuario = Usuario.builder()
                .id(1L)
                .nombre("Juan")
                .email("juan@mail.com")
                .password("password123")
                .rol(Usuario.Rol.USUARIO)
                .build();

        String token = jwtProvider.generateToken(usuario);

        assertNotNull(token);
        String username = jwtProvider.getUsernameFromToken(token);
        assertEquals("juan@mail.com", username);
    }

    @Test
    void testValidateTokenWhenUserExists() {
        Usuario usuario = Usuario.builder()
                .id(1L)
                .nombre("Juan")
                .email("juan@mail.com")
                .password("password123")
                .rol(Usuario.Rol.USUARIO)
                .build();

        when(userRepositoryPort.findByEmail("juan@mail.com")).thenReturn(usuario);

        String token = jwtProvider.generateToken(usuario);
        Usuario validatedUser = jwtProvider.validateToken(token);

        assertNotNull(validatedUser);
        assertEquals(usuario.getEmail(), validatedUser.getEmail());
    }

    @Test
    void testValidateTokenWhenUserNotFound() {
        when(userRepositoryPort.findByEmail("nonexistent@mail.com")).thenReturn(null);

        // Generando manualmente un token de un usuario inexistente
        Usuario dummy = Usuario.builder()
                .email("nonexistent@mail.com")
                .build();

        String token = jwtProvider.generateToken(dummy);
        Usuario validatedUser = jwtProvider.validateToken(token);

        assertNull(validatedUser);
    }
}
