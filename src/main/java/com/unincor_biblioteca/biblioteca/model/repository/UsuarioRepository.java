package com.unincor_biblioteca.biblioteca.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.unincor_biblioteca.biblioteca.model.domain.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

}
