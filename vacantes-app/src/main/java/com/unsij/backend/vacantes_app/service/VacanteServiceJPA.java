package com.unsij.backend.vacantes_app.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.unsij.backend.vacantes_app.model.Usuario;
import com.unsij.backend.vacantes_app.model.Vacante;
import com.unsij.backend.vacantes_app.repository.UsuarioRepository;
import com.unsij.backend.vacantes_app.repository.VacanteRepository;
import com.unsij.backend.vacantes_app.service.interfaces.IVacanteService;
import com.unsij.backend.vacantes_app.utils.JsonUtils;

import jakarta.transaction.Transactional;

@Service
public class VacanteServiceJPA implements IVacanteService {
    @Autowired
    private VacanteRepository vacanteRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public List<Vacante> findAll() {
        return vacanteRepository.findAll();
    }

    @Override
    public Vacante findById(Long id) {
        return vacanteRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("Vacante no encontrado con el ID: " + id));
    }

    public List<Vacante> getAll() {
        return vacanteRepository.findAll(); // ✅ Corregido
    }

    @Override
    @Transactional
    public Vacante save(Vacante vacante) throws Exception {
        return vacanteRepository.save(vacante);
    }

    @Override
    public Vacante create(Map<String, Object> params) throws Exception {
        Vacante vacante = new Vacante();
        try {
            this.build(params, vacante);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalArgumentException("Error al construir el vacante");
        }
        return this.save(vacante);
    }

    @Override
    public Vacante update(Vacante vacante, Map<String, Object> params) throws Exception {
        try {
            this.build(params, vacante);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalArgumentException("Error al construir el ejemplo");
        }
        return this.save(vacante);
    }

    @Override
    public Vacante build(Map<String, Object> params, Vacante vacante) throws IllegalArgumentException {
        try {
            LocalDate fechaPublicacion = JsonUtils.obtLocalDate(params, "fechaPublicacion");
            if (fechaPublicacion == null)
                throw new IllegalArgumentException("La fecha de publicacion es obligatoria");
            vacante.setFechaPublicacion(fechaPublicacion);

            String nombre = JsonUtils.obtString((params), "nombre");
            if (nombre == null)
                throw new IllegalArgumentException("El nombre de la vacante es obligatorio");
            vacante.setNombre(nombre);

            String descripcion = JsonUtils.obtString(params, "descripcion");
            if (descripcion == null)
                throw new IllegalArgumentException("La descripcion es obligatoria");
            vacante.setDescripcion(descripcion);

            String detalle = JsonUtils.obtString(params, "detalle");
            if (detalle == null)
                throw new IllegalArgumentException("Los detalles son obligatorios");
            vacante.setDetalle(detalle);

            Boolean activo = JsonUtils.obtBoolean(params, "activo");
            if (activo == null)
                throw new IllegalArgumentException("El activo es obligatorio");
            vacante.setActivo(activo);

            String idUsuario = JsonUtils.obtString((params), "usuario");
            Usuario usuario = usuarioRepository.findById(Long.parseLong(idUsuario)).orElse(null);
            vacante.setUsuario(usuario);

        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalArgumentException("Error al construir el ejemplo");
        }
        return vacante;
    }

    @Override
    public Vacante updateInstance(Vacante vacante) throws Exception {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateInstance'");
    }

    @Override
    public void deleteById(Long id) {
        Vacante vacante = this.findById(id);
        if (vacante != null) {
            vacanteRepository.deleteById(id);
        }
    }

    public Vacante cambiarEstado(Long id, boolean activo) {
        Vacante vacante = vacanteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No se encontró la vacante con ID: " + id));

        vacante.setActivo(activo);
        return vacanteRepository.save(vacante);
    }

    // METODOS DE BUSQUEDA
    /**
     * Buscar vacantes por palabra clave
     * busqueda parcial en nombre, descripción y detalle
     */
    public List<Vacante> buscarPorPalabraClave(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return vacanteRepository.findAllActivas();
        }
        return vacanteRepository.buscarPorPalabraClave(keyword.trim());
    }

    /**
     * Obtener todas las vacantes activas
     */
    public List<Vacante> obtenerVacantesActivas() {
        return vacanteRepository.findAllActivas();
    }

}
