package com.codigo.auth.infrastructure.controller;

import com.codigo.auth.application.port.input.AuthUseCase;
import com.codigo.auth.domain.model.Usuario;
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
    public ResponseEntity<Usuario> validate(@RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(authUseCase.validateToken(token.replace("Bearer ", "")));
    }
    @GetMapping("/prueba")
    public ResponseEntity<String> getPrueba(){ return ResponseEntity.ok (valorPropiedad);
    }

}
