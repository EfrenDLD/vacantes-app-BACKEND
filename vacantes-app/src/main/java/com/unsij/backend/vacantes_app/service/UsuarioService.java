package com.unsij.backend.vacantes_app.service;

import com.unsij.backend.vacantes_app.dto.LoginRequestDTO;
import com.unsij.backend.vacantes_app.dto.LoginResponseDTO;
import com.unsij.backend.vacantes_app.model.Usuario;
import com.unsij.backend.vacantes_app.model.Vacante;
import com.unsij.backend.vacantes_app.repository.UsuarioRepository;
import com.unsij.backend.vacantes_app.service.interfaces.IUsuarioService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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

            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

            // Validar contraseña encriptada
            if (!encoder.matches(loginRequest.getPassword(), usuario.getContrasenia())) {
                return Optional.empty();
            }

            // Validar rol
            if (!"ADMIN".equalsIgnoreCase(usuario.getPerfil())) {
                return Optional.empty();
            }

            // Crear respuesta
            LoginResponseDTO response = new LoginResponseDTO();
            response.setMensaje("Login exitoso");
            response.setUsername(usuario.getUsername());
            response.setPerfil(usuario.getPerfil());

            return Optional.of(response);
        }

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
    public Usuario save(Usuario usuario) {

        try {
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

            // Encriptar contraseña
            usuario.setContrasenia(encoder.encode(usuario.getContrasenia()));

            // Dejar vacantes en null como mencionaste
            usuario.setVacantes(null);

            System.out.println("Guardando usuario: " + usuario.getUsername());

            return usuarioRepository.save(usuario);

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error guardando usuario: " + e.getMessage());
        }
    }


    @Override
    public Usuario create(Map<String, Object> params) throws Exception {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'create'");
    }

    @Override
    public Usuario update(Usuario usuario, Map<String, Object> params) throws Exception {
        try {
            this.build(params, usuario);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalArgumentException("Error al construir el usuario");
        }
        return usuarioRepository.save(usuario);
    }

    @Override
    public Usuario build(Map<String, Object> params, Usuario usuario) throws IllegalArgumentException {
        try {
            String nombre = (String) params.get("nombre");
            if (nombre != null) usuario.setNombre(nombre);

            String email = (String) params.get("email");
            if (email != null) usuario.setEmail(email);

            String username = (String) params.get("username");
            if (username != null) usuario.setUsername(username);

            String perfil = (String) params.get("perfil");
            if (perfil != null) usuario.setPerfil(perfil);

            String estatus = (String) params.get("estatus");
            if (estatus != null) usuario.setEstatus(estatus);

            String contrasenia = (String) params.get("contrasenia");
            if (contrasenia != null && !contrasenia.isEmpty()) {
                BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
                usuario.setContrasenia(encoder.encode(contrasenia));
            }

            // No actualizar vacantes aquí, solo los campos básicos

        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalArgumentException("Error al construir el usuario");
        }
        return usuario;
    }

    @Override
    public Usuario updateInstance(Usuario usuario) throws Exception {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateInstance'");
    }
}
