package com.unsij.backend.vacantes_app.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.unsij.backend.vacantes_app.model.Vacante;
import com.unsij.backend.vacantes_app.service.VacanteServiceJPA;

@RestController
@RequestMapping("/vacantes") // Ruta principal para todas las operaciones de vacantes
public class VacanteController {

    @Autowired
    private VacanteServiceJPA vacanteServiceJPA; 
    // Servicio que maneja toda la lógica de negocio y acceso a datos

    /**
     * Crear una nueva vacante.
     * Aquí podrían agregarse:
     * - Validaciones con @Valid
     * - Uso de un DTO para evitar recibir campos innecesarios
     * - Lógica para registrar quién creó la vacante
     */
    @PostMapping
    public ResponseEntity<?> create(@RequestBody Map<String, Object> params) {
        try {
            Vacante vacante = vacanteServiceJPA.create(params);
            return ResponseEntity.status(HttpStatus.CREATED).body(vacante);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error interno del servidor: CREATE");
        }
    }

    /**
     * Eliminar una vacante por ID.
     * Se podría mejorar:
     * - Cambiar a DELETE /vacantes/{id} para mayor claridad
     * - Registrar quién eliminó la vacante
     * - Cambiar a "eliminación lógica" en lugar de eliminar de la BD
     */
    @DeleteMapping
    public ResponseEntity<?> delete(@RequestParam Long id) {
        try {
            vacanteServiceJPA.deleteById(id);
            return ResponseEntity.ok().body("Vacante eliminada correctamente");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error interno del servidor: DELETE");
        }
    }

    /**
     * Actualizar una vacante completamente o parcialmente.
     * Recibe un Map para permitir actualizaciones parciales.
     * Posibles mejoras:
     * - Crear un DTO de actualización
     * - Agregar validaciones por campo
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Map<String, Object> params) {
        try {
            Vacante v = vacanteServiceJPA.findById(id);
            Vacante vacanteUpdated = vacanteServiceJPA.update(v, params);
            return ResponseEntity.status(HttpStatus.CREATED).body(vacanteUpdated);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error interno del servidor: UPDATE");
        }
    }

    /**
     * Obtener todas las vacantes.
     * Mejoras posibles:
     * - Agregar paginación (page, size)
     * - Agregar filtros (activo, usuario, fechas)
     */
    @GetMapping
    public ResponseEntity<?> getAll() {
        try {
            List<Vacante> vacantes = vacanteServiceJPA.getAll();
            return ResponseEntity.ok(vacantes);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error interno del servidor: GET ALL");
        }
    }

    /**
     * Cambiar únicamente el estado de una vacante (activo/inactivo).
     * Se usa una ruta específica para mayor claridad.
     * Posibles mejoras:
     * - Registrar cuándo y quién actualizó el estado
     * - Validar que solo administradores puedan hacerlo
     */
    @PutMapping("/{id}/estado")
    public ResponseEntity<?> cambiarEstado(@PathVariable Long id, @RequestBody Map<String, Boolean> body) {
        try {
            Boolean activo = body.get("activo");
            if (activo == null) {
                return ResponseEntity.badRequest().body("El campo 'activo' es obligatorio.");
            }

            Vacante vacanteActualizada = vacanteServiceJPA.cambiarEstado(id, activo);
            return ResponseEntity.ok(vacanteActualizada);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error interno del servidor: CAMBIAR ESTADO");
        }
    }

    // ==========================================================
    //      FUNCIONALIDADES DE BÚSQUEDA Y CONSULTA INDIVIDUAL
    // ==========================================================

    /**
     * Obtener una vacante por ID.
     * Esta operación es frecuentemente usada al abrir detalles en el frontend.
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        try {
            Vacante vacante = vacanteServiceJPA.findById(id);
            if (vacante == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(vacante);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error interno del servidor: GET BY ID");
        }
    }

    /**
     * Buscar vacantes por palabra clave.
     * Este método puede ampliarse para:
     * - Buscar por múltiples campos
     * - Buscar con filtros combinados
     * - Buscar con ordenamiento
     */
    @GetMapping("/buscar")
    public ResponseEntity<?> buscar(@RequestParam(required = false) String keyword) {
        try {
            List<Vacante> vacantes = vacanteServiceJPA.buscarPorPalabraClave(keyword);
            return ResponseEntity.ok(vacantes);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error interno del servidor: BUSCAR");
        }
    }

    /**
     * Obtener todas las vacantes activas (estado activo = true).
     * Posibles mejoras:
     * - Agregar paginación
     * - Filtrar por fecha, categoría u otros campos
     */
    @GetMapping("/activas")
    public ResponseEntity<?> getActivas() {
        try {
            List<Vacante> vacantes = vacanteServiceJPA.obtenerVacantesActivas();
            return ResponseEntity.ok(vacantes);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error interno del servidor: GET ACTIVAS");
        }
    }

    // ==========================================================
    //      POSIBLES FUNCIONALIDADES FUTURAS (GUÍA DE EXPANSIÓN)
    // ==========================================================

    /*
     * 1. Paginación general:
     *    GET /vacantes?page=1&size=10
     *    Útil cuando la cantidad de vacantes crece.
     *
     * 2. Filtrar por estado, usuario creador, o rango de fechas:
     *    GET /vacantes/filtrar?activo=true&fechaInicio=...&fechaFin=...
     *
     * 3. Subir archivos como PDF de la descripción:
     *    POST /vacantes/{id}/archivo
     *
     * 4. Relacionar vacantes con usuarios (por ejemplo, creador o responsables):
     *    GET /vacantes/usuario/{id}
     *
     * 5. Implementar eliminación lógica:
     *    vacante.setEliminada(true);
     *    Evitar borrar registros permanentemente.
     *
     * 6. Documentar con Swagger todos los endpoints.
     *
     * 7. Agregar seguridad con JWT para proteger los endpoints.
     *
     * 8. Crear un DTO de entrada y uno de salida para mayor seguridad.
     *
     */

}
