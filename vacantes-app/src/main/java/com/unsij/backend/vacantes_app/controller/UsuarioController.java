package com.unsij.backend.vacantes_app.controller;

import java.util.List;
import java.util.Map;

import com.unsij.backend.vacantes_app.model.Vacante;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @DeleteMapping
    public ResponseEntity<?> delete(@RequestParam Long id) {
        try {
            usuarioService.deleteById(id);
            return ResponseEntity.ok().body("Usuario eliminado correctamente");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno del servidor: DELETE");
        }
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Usuario usuario) {
        try {
            System.out.println("📥 Controller: Usuario recibido para crear: " + usuario.getUsername());

            Usuario nuevo = usuarioService.save(usuario);

            System.out.println("✅ Controller: Usuario creado con ID: " + nuevo.getId());

            return ResponseEntity.ok(nuevo);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error al crear usuario: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Map<String, Object> params) {
        try {
            Usuario user = usuarioService.findById(id);
            Usuario userUpdated = usuarioService.update(user, params);
            return ResponseEntity.status(HttpStatus.CREATED).body(userUpdated);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno del servidor: UPDATE");
        }
    }


}
