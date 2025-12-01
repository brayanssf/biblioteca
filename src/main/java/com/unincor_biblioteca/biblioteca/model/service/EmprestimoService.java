package com.unincor_biblioteca.biblioteca.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unincor_biblioteca.biblioteca.exceptions.BibliotecaException;
import com.unincor_biblioteca.biblioteca.model.domain.*;
import com.unincor_biblioteca.biblioteca.model.repository.*;

import java.time.LocalDate;

@Service
public class EmprestimoService {

    private static final int MAX_EMPRESTIMOS_ATIVOS = 3;

    @Autowired
    private EmprestimoRepository emprestimoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private LivroRepository livroRepository;

    @Transactional
    public Emprestimo registrar(Emprestimo emprestimo) {

        if (emprestimo == null) {
            throw new BibliotecaException("Empréstimo inválido.");
        }

        if (emprestimo.getUsuario() == null || emprestimo.getUsuario().getId() == null) {
            throw new BibliotecaException("Usuário deve ser informado.");
        }

        var usuario = usuarioRepository.findById(emprestimo.getUsuario().getId())
                .orElseThrow(() -> new BibliotecaException("Usuário não encontrado."));

        if (emprestimo.getLivro() == null || emprestimo.getLivro().getId() == null) {
            throw new BibliotecaException("Livro deve ser informado.");
        }

        var livro = livroRepository.findById(emprestimo.getLivro().getId())
                .orElseThrow(() -> new BibliotecaException("Livro não encontrado."));

        long qtdAtivos = emprestimoRepository.countByUsuarioIdAndDataDevolucaoIsNull(usuario.getId());
        if (qtdAtivos >= MAX_EMPRESTIMOS_ATIVOS) {
            throw new BibliotecaException("Usuário atingiu o limite de empréstimos ativos.");
        }

        boolean livroEmprestado = emprestimoRepository.existsByLivroIdAndDataDevolucaoIsNull(livro.getId());
        if (livroEmprestado || "EMPRESTADO".equalsIgnoreCase(livro.getStatus())) {
            throw new BibliotecaException("Livro já está emprestado.");
        }

        emprestimo.setUsuario(usuario);
        emprestimo.setLivro(livro);
        emprestimo.setDataEmprestimo(LocalDate.now());
        Emprestimo salvo = emprestimoRepository.save(emprestimo);

        livro.setStatus("EMPRESTADO");
        livroRepository.save(livro);

        return salvo;
    }

    @Transactional
    public Emprestimo registrarDevolucao(Integer emprestimoId) {
        var emp = emprestimoRepository.findById(emprestimoId)
                .orElseThrow(() -> new BibliotecaException("Empréstimo não encontrado."));
        if (emp.getDataDevolucao() != null) {
            throw new BibliotecaException("Empréstimo já foi devolvido.");
        }
        emp.setDataDevolucao(LocalDate.now());
        Emprestimo salvo = emprestimoRepository.save(emp);

        var livro = emp.getLivro();
        livro.setStatus("DISPONÍVEL");
        livroRepository.save(livro);

        return salvo;
    }
}
