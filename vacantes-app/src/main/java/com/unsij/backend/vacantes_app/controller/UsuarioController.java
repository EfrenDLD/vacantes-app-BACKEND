package com.unsij.backend.vacantes_app.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.unsij.backend.vacantes_app.model.Usuario;
import com.unsij.backend.vacantes_app.service.UsuarioService;

@RestController
@RequestMapping("/usuarios")
@CrossOrigin(origins = "*") // Permitir acceso desde cualquier frontend
public class UsuarioController {

    // Servicio que contiene la lógica de negocio para usuarios
    private final UsuarioService usuarioService;

    // Inyección de dependencias
    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    // Obtener todos los usuarios
    // Aquí podrían agregarse parámetros de filtrado o búsqueda en el futuro
    @GetMapping
    public List<Usuario> getAllUsuarios() {
        return usuarioService.getAll();
    }

    // Eliminar usuario por ID (usando RequestParam)
    // Si se desea mayor claridad en la ruta, se puede cambiar a DELETE /usuarios/{id}
    @DeleteMapping
    public ResponseEntity<?> delete(@RequestParam Long id) {
        try {
            usuarioService.deleteById(id);
            return ResponseEntity.ok().body("Usuario eliminado correctamente");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error interno del servidor: DELETE");
        }
    }

    // Crear un nuevo usuario
    // Aquí podría añadirse validación con @Valid más adelante
    // También se podría agregar lógica para asignar roles o generar contraseñas
    @PostMapping
    public ResponseEntity<?> create(@RequestBody Usuario usuario) {
        try {
            System.out.println("Controller: Usuario recibido para crear: " + usuario.getUsername());

            Usuario nuevo = usuarioService.save(usuario);

            System.out.println("Controller: Usuario creado con ID: " + nuevo.getId());

            return ResponseEntity.ok(nuevo);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error al crear usuario: " + e.getMessage());
        }
    }

    // Actualizar usuario por ID
    // Este método recibe un Map, por lo que admite actualizaciones parciales
    // Si en el futuro se quiere un control más estricto, se puede crear un DTO específico
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id,
                                    @RequestBody Map<String, Object> params) {
        try {
            Usuario user = usuarioService.findById(id);

            Usuario userUpdated = usuarioService.update(user, params);

            return ResponseEntity.status(HttpStatus.CREATED).body(userUpdated);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error interno del servidor: UPDATE");
        }
    }

    /*
     * =========================
     * POSIBLES FUNCIONALIDADES FUTURAS
     * =========================
     *
     * 1. Obtener usuario por ID
     *    - Método GET /usuarios/{id}
     *    - Útil cuando se necesita mostrar el perfil de un usuario
     *
     * 2. Buscar usuarios por username o correo
     *    - Método GET /usuarios/buscar?username=...
     *    - Útil para validaciones antes de crear usuarios
     *
     * 3. Cambiar contraseña de usuario
     *    - Método PUT /usuarios/{id}/password
     *    - Recibe contraseña actual y nueva
     *    - Necesario en sistemas con gestión de cuentas
     *
     * 4. Activar o desactivar cuentas
     *    - Método PUT /usuarios/{id}/estado?activo=true/false
     *    - Permite suspender usuarios sin eliminarlos
     *
     * 5. Asignar o cambiar roles
     *    - Método PUT /usuarios/{id}/rol
     *    - Sirve para sistemas con administración, empleados, etc.
     *
     * 6. Implementar paginación en la lista de usuarios
     *    - Método GET /usuarios?page=1&size=10
     *    - Recomendado cuando la tabla crezca mucho
     *
     * 7. Implementar búsqueda con filtros avanzados
     *    - Método GET /usuarios/filtrar?activo=true&rol=ADMIN
     *    - Útil para paneles de administración
     *
     * 8. Agregar validaciones con anotaciones @Valid
     *    - Requiere crear clases DTO para mayor seguridad
     *
     * 9. Integración con autenticación JWT o roles con Spring Security
     *    - Requiere configuración adicional en la capa de seguridad
     *
     */

}
