package com.unsij.backend.vacantes_app.controller;

import java.util.List;
import java.util.Map;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.unsij.backend.vacantes_app.model.Vacante;
import com.unsij.backend.vacantes_app.service.VacanteServiceJPA;

@RestController
@RequestMapping("/vacantes")
public class VacanteController {
    @Autowired
    private VacanteServiceJPA vacanteServiceJPA;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Map<String, Object> params) {
        try {
            Vacante vacante = vacanteServiceJPA.create(params);
            return ResponseEntity.status(HttpStatus.CREATED).body(vacante);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno del servidor: CREATE");
        }
    }

    @DeleteMapping
    public ResponseEntity<?> delete(@RequestParam Long id) {
        try {
            vacanteServiceJPA.deleteById(id);
            return ResponseEntity.ok().body("Vacante eliminada correctamente");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno del servidor: DELETE");
        }
    }

    @GetMapping
    public ResponseEntity<?> getAll() {
        try {
            List<Vacante> vacantes = vacanteServiceJPA.getAll();
            return ResponseEntity.ok(vacantes); // siempre devuelve lista, vacía o con datos
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error interno del servidor: GET ALL");
        }
    }

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


    // LOGICA DE BUSQUEDA DE VACANTES
    /**
     * Obtener vacante por ID
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
     * Buscar vacantes por palabra clave
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
     * Obtener todas las vacantes activas
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

}
