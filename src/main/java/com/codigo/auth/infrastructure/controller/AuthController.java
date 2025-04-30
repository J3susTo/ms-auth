package com.codigo.auth.infrastructure.controller;

import com.codigo.auth.application.port.input.AuthUseCase;
import com.codigo.auth.domain.model.Usuario;
import com.codigo.auth.infrastructure.client.dto.UsuarioAuthDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Log4j2
public class AuthController {

    private final AuthUseCase authUseCase;
    @Value("${dato.propiedad}")
    private  String valorPropiedad;

    @PostMapping("/register")
    public ResponseEntity<Usuario> register(@RequestBody Usuario usuario) {
        return ResponseEntity.ok(authUseCase.register(usuario));
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestParam String email, @RequestParam String password) {
        return ResponseEntity.ok(authUseCase.login(email, password));
    }


    @GetMapping("/validate")
    public ResponseEntity<UsuarioAuthDTO> validate(@RequestHeader("Authorization") String token) {
        Usuario usuario = authUseCase.validateToken(token.replace("Bearer ", ""));

        if (usuario != null) {
            UsuarioAuthDTO dto = UsuarioAuthDTO.builder()
                    .id(usuario.getId())
                    .email(usuario.getEmail())
                    .nombre(usuario.getNombre())
                    .rol(usuario.getRol())
                    .isValid(true)
                    .build();
            return ResponseEntity.ok(dto);
        }

        return ResponseEntity.ok(UsuarioAuthDTO.builder().isValid(false).build());
    }


    @GetMapping("/prueba")
    public ResponseEntity<String> getPrueba(){ return ResponseEntity.ok (valorPropiedad);
    }

}
