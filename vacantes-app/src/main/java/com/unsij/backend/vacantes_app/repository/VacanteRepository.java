package com.unsij.backend.vacantes_app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.unsij.backend.vacantes_app.model.Vacante;

public interface VacanteRepository extends JpaRepository<Vacante,Long> {
    /**
     * Busqueda de vacantes por palabra clave 
     * Busca coincidencias en nombre, descripcion y detalle
     */
    @Query("SELECT v FROM Vacante v WHERE " +
           "LOWER(v.nombre) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(v.descripcion) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(v.detalle) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Vacante> buscarPorPalabraClave(@Param("keyword") String keyword);
    
    /**
     * Obtener todas las vacantes activas ordenadas por fecha de publicación
     */
    @Query("SELECT v FROM Vacante v WHERE v.activo = true ORDER BY v.fechaPublicacion DESC")
    List<Vacante> findAllActivas();
}
