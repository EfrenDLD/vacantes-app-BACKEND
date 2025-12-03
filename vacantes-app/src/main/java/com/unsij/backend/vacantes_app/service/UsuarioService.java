package com.unsij.backend.vacantes_app.service;

import com.unsij.backend.vacantes_app.dto.LoginRequestDTO;
import com.unsij.backend.vacantes_app.dto.LoginResponseDTO;
import com.unsij.backend.vacantes_app.model.Usuario;
import com.unsij.backend.vacantes_app.model.Vacante;
import com.unsij.backend.vacantes_app.repository.UsuarioRepository;
import com.unsij.backend.vacantes_app.service.interfaces.IUsuarioService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class UsuarioService implements IUsuarioService {

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

    @Override
    public Usuario findById(Long id) {
        return usuarioRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("Usuario no encontrado con el ID: " + id));
    }

    @Override
    public void deleteById(Long id) {
        Usuario usuario = this.findById(id);
        if (usuario != null) {
            usuarioRepository.deleteById(id);
        }
    }

    @Override
    public List<Usuario> findAll() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findAll'");
    }

    @Override
    public Usuario save(Usuario usuario) throws Exception {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'save'");
    }

    @Override
    public Usuario create(Map<String, Object> params) throws Exception {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'create'");
    }

    @Override
    public Usuario update(Usuario usuario, Map<String, Object> params) throws Exception {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public Usuario build(Map<String, Object> params, Usuario usuario) throws IllegalArgumentException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'build'");
    }

    @Override
    public Usuario updateInstance(Usuario usuario) throws Exception {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateInstance'");
    }
}
