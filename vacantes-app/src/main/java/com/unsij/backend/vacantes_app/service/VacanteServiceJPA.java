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

    /**
     * Obtiene todas las vacantes almacenadas en la base de datos.
     *
     * Posible mejora:
     * - Implementar paginación para evitar devolver demasiados registros.
     * - Agregar filtros por fecha, usuario, estatus, etc.
     */
    @Override
    public List<Vacante> findAll() {
        return vacanteRepository.findAll();
    }

    /**
     * Busca una vacante por ID.
     *
     * Posible mejora:
     * - Añadir roles que restrinjan quién puede consultar algunas vacantes.
     */
    @Override
    public Vacante findById(Long id) {
        return vacanteRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("Vacante no encontrado con el ID: " + id));
    }

    /**
     * Método utilizado por el controlador para obtener todas las vacantes.
     * Equivalente a findAll().
     */
    public List<Vacante> getAll() {
        return vacanteRepository.findAll();
    }

    /**
     * Guarda una vacante ya construida y validada.
     *
     * Posible mejora:
     * - Registrar la fecha de actualización.
     * - Verificar permisos del usuario que realiza la acción.
     */
    @Override
    @Transactional
    public Vacante save(Vacante vacante) throws Exception {
        return vacanteRepository.save(vacante);
    }

    /**
     * Construye una vacante a partir de un mapa de parámetros y la guarda.
     *
     * Posible mejora:
     * - Validar duplicados.
     * - Enviar notificación a otros módulos (como bolsa de trabajo).
     */
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

    /**
     * Actualiza una vacante ya existente con parámetros enviados desde el frontend.
     *
     * Posible mejora:
     * - Registrar un historial de cambios.
     * - Notificar al usuario propietario de la vacante.
     */
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

    /**
     * Construye o actualiza una instancia de Vacante a partir de los parámetros recibidos.
     * Solo se actualizan los campos permitidos.
     *
     * Posible mejora:
     * - Validar longitud de campos texto.
     * - Validar que el usuario tenga permiso para asignarse a la vacante.
     */
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

    /**
     * Método opcional para futuras implementaciones si se quiere recibir un objeto
     * completo sin Map.
     */
    @Override
    public Vacante updateInstance(Vacante vacante) throws Exception {
        throw new UnsupportedOperationException("Unimplemented method 'updateInstance'");
    }

    /**
     * Elimina una vacante por su ID.
     *
     * Posible mejora:
     * - Evitar eliminaciones físicas y usar un campo "eliminado" lógico.
     */
    @Override
    public void deleteById(Long id) {
        Vacante vacante = this.findById(id);
        if (vacante != null) {
            vacanteRepository.deleteById(id);
        }
    }

    /**
     * Cambia el estado activo/inactivo de la vacante.
     *
     * Posible mejora:
     * - Agregar registro de quién realizó el cambio.
     * - Notificar al usuario dueño de la vacante.
     */
    public Vacante cambiarEstado(Long id, boolean activo) {
        Vacante vacante = vacanteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No se encontró la vacante con ID: " + id));

        vacante.setActivo(activo);
        return vacanteRepository.save(vacante);
    }

    // -----------------------------
    // MÉTODOS DE BÚSQUEDA
    // -----------------------------

    /**
     * Busca vacantes mediante palabra clave en nombre, descripción y detalle.
     *
     * Posible mejora:
     * - Implementar búsquedas más avanzadas (por ejemplo, por categorías o salario).
     * - Añadir relevancia en los resultados.
     */
    public List<Vacante> buscarPorPalabraClave(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return vacanteRepository.findAllActivas();
        }
        return vacanteRepository.buscarPorPalabraClave(keyword.trim());
    }

    /**
     * Devuelve únicamente las vacantes activas.
     *
     * Posible mejora:
     * - Permitir ordenar por fecha, nombre o usuario.
     */
    public List<Vacante> obtenerVacantesActivas() {
        return vacanteRepository.findAllActivas();
    }

}
