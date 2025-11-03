package com.unsij.backend.vacantes_app.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
        }  catch (Exception e) {
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
            if (vacantes.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No hay vacantes registradas.");
            }
            return ResponseEntity.ok(vacantes);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error interno del servidor: GET ALL");
        }
    }
}
