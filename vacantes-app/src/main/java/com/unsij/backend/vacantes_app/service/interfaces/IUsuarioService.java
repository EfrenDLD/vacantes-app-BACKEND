package com.unsij.backend.vacantes_app.service.interfaces;

import java.util.List;
import java.util.Map;

import com.unsij.backend.vacantes_app.model.Usuario;

public interface IUsuarioService {
    List<Usuario> findAll();

    Usuario findById(Long id);

    Usuario save(Usuario usuario) throws Exception;

    Usuario create(Map<String, Object> params) throws Exception;

    Usuario update(Usuario usuario, Map<String, Object> params) throws Exception;

    Usuario build(Map<String, Object> params, Usuario usuario) throws IllegalArgumentException;

    Usuario updateInstance(Usuario usuario) throws Exception;

    void deleteById(Long id);
}
