package com.codigo.auth.application.service;

import com.codigo.auth.domain.model.Usuario;
import com.codigo.auth.infrastructure.config.JwtProvider;
import com.codigo.auth.infrastructure.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class AuthServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private JwtProvider jwtProvider;

    @InjectMocks
    private AuthServiceImpl authService;

    @Test
    void testLogin_Success() {
        String email = "test@user.com";
        String password = "password";
        String encodedPassword = "$2a$10$7c3AB4p.CytHeYsUPzB60OQdMlzXfwPyeDQ5/UJpUg1H9ZFsT5Tfu"; // Ejemplo de contraseña cifrada

        Usuario user = Usuario.builder()
                .email(email)
                .password(encodedPassword)  // Contraseña cifrada
                .build();

        // Simulamos el comportamiento de los mocks
        when(userRepository.findByEmail(email)).thenReturn(user);
        when(jwtProvider.generateToken(any())).thenReturn("mock-jwt-token");

        String token = authService.login(email, password);

        assertNotNull(token);
        assertEquals("mock-jwt-token", token);
        verify(userRepository, times(1)).findByEmail(email);
        verify(jwtProvider, times(1)).generateToken(any());
    }

    @Test
    void testLogin_InvalidCredentials() {
        String email = "test@user.com";
        String password = "wrong-password";
        String encodedPassword = "$2a$10$7c3AB4p.CytHeYsUPzB60OQdMlzXfwPyeDQ5/UJpUg1H9ZFsT5Tfu"; // Contraseña cifrada

        Usuario user = Usuario.builder()
                .email(email)
                .password(encodedPassword)  // Contraseña cifrada
                .build();

        when(userRepository.findByEmail(email)).thenReturn(user);

        // Verifica que se lanza una excepción específica cuando las credenciales no coinciden
        assertThrows(RuntimeException.class, () -> authService.login(email, password)); // Cambia a una excepción personalizada si la tienes
    }

    @Test
    void testRegister() {
        String email = "new@user.com";
        String password = "new-password";
        Usuario newUser = Usuario.builder()
                .email(email)
                .password(password)
                .build();

        when(userRepository.existsByEmail(email)).thenReturn(false);
        when(userRepository.save(any(Usuario.class))).thenReturn(newUser);

        Usuario registeredUser = authService.register(newUser);

        assertNotNull(registeredUser);
        assertEquals(email, registeredUser.getEmail());
        verify(userRepository, times(1)).save(any(Usuario.class));
    }
}
