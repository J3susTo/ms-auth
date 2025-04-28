package com.codigo.auth.application.service;

import com.codigo.auth.application.port.output.UserRepositoryPort;
import com.codigo.auth.domain.model.Usuario;
import com.codigo.auth.infrastructure.config.JwtProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.authentication.BadCredentialsException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthServiceTest {

    @InjectMocks
    private AuthService authService;

    @Mock
    private UserRepositoryPort userRepository;

    @Mock
    private JwtProvider jwtProvider;

    @Mock
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        authService = new AuthService(userRepository, jwtProvider, passwordEncoder);
    }

    @Test
    void testRegister_success() {
        // Arrange
        Usuario usuario = new Usuario();
        usuario.setNombre("Juan");
        usuario.setEmail("juan@mail.com");
        usuario.setPassword("password123");

        Usuario savedUser = new Usuario();
        savedUser.setNombre("Juan");
        savedUser.setEmail("juan@mail.com");
        savedUser.setPassword("hashedPassword"); // Contraseña ya encriptada simulada

        when(passwordEncoder.encode(anyString())).thenReturn("hashedPassword");
        when(userRepository.save(any(Usuario.class))).thenReturn(savedUser);

        // Act
        Usuario result = authService.register(usuario);

        // Assert
        assertNotNull(result);
        assertEquals("Juan", result.getNombre());
        assertEquals("juan@mail.com", result.getEmail());
        assertEquals("hashedPassword", result.getPassword());

        verify(passwordEncoder, times(1)).encode("password123");
        verify(userRepository, times(1)).save(any(Usuario.class));
    }

    @Test
    void testLogin_success() {
        // Arrange
        String email = "juan@mail.com";
        String password = "password123";
        Usuario user = new Usuario();
        user.setEmail(email);
        user.setPassword("hashedPassword");

        when(userRepository.findByEmail(email)).thenReturn(user);
        when(passwordEncoder.matches(password, "hashedPassword")).thenReturn(true);
        when(jwtProvider.generateToken(user)).thenReturn("mocked-jwt-token");

        // Act
        String token = authService.login(email, password);

        // Assert
        assertNotNull(token);
        assertEquals("mocked-jwt-token", token);

        verify(userRepository, times(1)).findByEmail(email);
        verify(passwordEncoder, times(1)).matches(password, "hashedPassword");
        verify(jwtProvider, times(1)).generateToken(user);
    }

    @Test
    void testLogin_invalidCredentials() {
        // Arrange
        String email = "juan@mail.com";
        String password = "wrongpassword";
        Usuario user = new Usuario();
        user.setEmail(email);
        user.setPassword("hashedPassword");

        when(userRepository.findByEmail(email)).thenReturn(user);
        when(passwordEncoder.matches(password, "hashedPassword")).thenReturn(false);

        // Act & Assert
        assertThrows(BadCredentialsException.class, () -> {
            authService.login(email, password);
        });

        verify(userRepository, times(1)).findByEmail(email);
        verify(passwordEncoder, times(1)).matches(password, "hashedPassword");
        verify(jwtProvider, never()).generateToken(any(Usuario.class));
    }
}
