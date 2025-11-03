package com.unsij.backend.vacantes_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.unsij.backend.vacantes_app.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    
}
