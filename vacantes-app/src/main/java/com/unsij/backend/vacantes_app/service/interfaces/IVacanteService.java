package com.unsij.backend.vacantes_app.service.interfaces;

import java.util.List;
import java.util.Map;

import com.unsij.backend.vacantes_app.model.Vacante;

public interface IVacanteService {
    List<Vacante> findAll();

    Vacante findById(Long id);

    Vacante save(Vacante vacante) throws Exception;

    Vacante create(Map<String, Object> params) throws Exception;

    Vacante update(Vacante vacante, Map<String, Object> params) throws Exception;

    Vacante build(Map<String, Object> params, Vacante vacante) throws IllegalArgumentException;

    Vacante updateInstance(Vacante vacante) throws Exception;

    void deleteById(Long id);
}
