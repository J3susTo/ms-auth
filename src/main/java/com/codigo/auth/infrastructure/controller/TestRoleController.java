package com.codigo.auth.infrastructure.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestRoleController {

    @GetMapping("/superadmin")
    @PreAuthorize("hasAuthority('SUPERADMIN')")
    public String superAdmin() {
        return "Acceso de SUPERADMIN exitoso!";
    }

    @GetMapping("/admin")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String admin() {
        return "Acceso de ADMIN exitoso!";
    }

    @GetMapping("/user")
    @PreAuthorize("hasAuthority('USUARIO')")
    public String user() {
        return "Acceso de USUARIO exitoso!";
    }
}
