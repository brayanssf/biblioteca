package com.unincor_biblioteca.biblioteca.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.unincor_biblioteca.biblioteca.model.domain.Autor;

public interface AutorRepository extends JpaRepository<Autor, Integer> {

}
