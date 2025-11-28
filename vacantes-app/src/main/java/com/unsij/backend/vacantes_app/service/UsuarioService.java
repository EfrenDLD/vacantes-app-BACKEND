package com.unsij.backend.vacantes_app.service;

import com.unsij.backend.vacantes_app.dto.LoginRequestDTO;
import com.unsij.backend.vacantes_app.dto.LoginResponseDTO;
import com.unsij.backend.vacantes_app.model.Usuario;
import com.unsij.backend.vacantes_app.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public Optional<LoginResponseDTO> login(LoginRequestDTO loginRequest) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findByUsername(loginRequest.getUsername());

        if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();

            // Validar contraseña
            if (!usuario.getContrasenia().equals(loginRequest.getPassword())) {
                return Optional.empty();
            }

            // Validar que el usuario sea administrador
            if (!"ADMIN".equalsIgnoreCase(usuario.getPerfil())) { // Cambia "ADMIN" si tu valor es distinto
                return Optional.empty();
            }

            // Armar respuesta
            LoginResponseDTO response = new LoginResponseDTO();
            response.setMensaje("Login exitoso");
            response.setUsername(usuario.getUsername());
            response.setPerfil(usuario.getPerfil());
            return Optional.of(response);
        }

        // Usuario no encontrado
        return Optional.empty();
    }

    public List<Usuario> getAll() {
        return usuarioRepository.findAll();
    }
}
