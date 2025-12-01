package com.unincor_biblioteca.biblioteca.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.unincor_biblioteca.biblioteca.model.domain.Emprestimo;

public interface EmprestimoRepository extends JpaRepository<Emprestimo, Integer> {

    long countByUsuarioIdAndDataDevolucaoIsNull(Integer usuarioId);

    boolean existsByLivroIdAndDataDevolucaoIsNull(Integer livroId);
}
