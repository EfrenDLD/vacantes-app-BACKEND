package com.unsij.backend.vacantes_app.controller;

import com.unsij.backend.vacantes_app.dto.LoginRequestDTO;
import com.unsij.backend.vacantes_app.dto.LoginResponseDTO;
import com.unsij.backend.vacantes_app.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*") // Permite peticiones desde frontend
public class LoginController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/login")
    public LoginResponseDTO login(@RequestBody LoginRequestDTO loginRequest) {
        return usuarioService.login(loginRequest);
    }
}
