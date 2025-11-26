package com.unsij.backend.vacantes_app.controller;

import java.util.List;
import org.springframework.web.bind.annotation.*;

import com.unsij.backend.vacantes_app.model.Usuario;
import com.unsij.backend.vacantes_app.service.UsuarioService;

@RestController
@RequestMapping("/usuarios")
@CrossOrigin(origins = "*") 
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public List<Usuario> getAllUsuarios() {
        return usuarioService.getAll();
    }
}
