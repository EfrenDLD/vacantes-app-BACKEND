package com.unsij.backend.vacantes_app.service;

import com.unsij.backend.vacantes_app.dto.LoginRequestDTO;
import com.unsij.backend.vacantes_app.dto.LoginResponseDTO;
import com.unsij.backend.vacantes_app.model.Usuario;
import com.unsij.backend.vacantes_app.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public LoginResponseDTO login(LoginRequestDTO loginRequest) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findByUsername(loginRequest.getUsername());

        LoginResponseDTO response = new LoginResponseDTO();

        if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();
            // Validamos la contraseña (en producción usar hash y no texto plano)
            if (usuario.getPassword().equals(loginRequest.getPassword())) {
                response.setMensaje("Login exitoso");
                response.setUsername(usuario.getUsername());
                response.setPerfil(usuario.getPerfil());
            } else {
                response.setMensaje("Contraseña incorrecta");
            }
        } else {
            response.setMensaje("Usuario no encontrado");
        }

        return response;
    }
}
