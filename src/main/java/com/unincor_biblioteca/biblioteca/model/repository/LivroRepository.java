package com.unincor_biblioteca.biblioteca.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.unincor_biblioteca.biblioteca.model.domain.Livro;

public interface LivroRepository extends JpaRepository<Livro, Integer> {

    boolean existsByTituloAndAutorNome(String titulo, String autorNome);
    List<Livro> findByStatusIgnoreCase(String status);

}
