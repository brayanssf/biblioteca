package com.unincor_biblioteca.biblioteca.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unincor_biblioteca.biblioteca.exceptions.BibliotecaException;
import com.unincor_biblioteca.biblioteca.model.domain.*;
import com.unincor_biblioteca.biblioteca.model.repository.*;

@Service
public class LivroService {

    @Autowired
    private LivroRepository livroRepository;

    @Autowired
    private AutorRepository autorRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Transactional
    public Livro salvar(Livro livro) {

        if (livro == null) {
            throw new BibliotecaException("Livro inválido.");
        }

        if (livro.getTitulo() == null || livro.getTitulo().isBlank()) {
            throw new BibliotecaException("Título é obrigatório.");
        }

        if (livro.getAutor() == null || livro.getAutor().getId() == null) {
            throw new BibliotecaException("Autor deve ser informado.");
        }

        Autor autor = autorRepository.findById(livro.getAutor().getId())
                .orElseThrow(() -> new BibliotecaException("Autor não encontrado."));

        livro.setAutor(autor);

        if (livro.getCategoria() != null && livro.getCategoria().getId() != null) {
            Categoria cat = categoriaRepository.findById(livro.getCategoria().getId())
                    .orElseThrow(() -> new BibliotecaException("Categoria não encontrada."));
            livro.setCategoria(cat);
        } else {
            livro.setCategoria(null);
        }

        boolean exists = livroRepository.existsByTituloAndAutorNome(
                livro.getTitulo(),
                autor.getNome()
        );

        if (exists && (livro.getId() == null || !isSameEntity(livro))) {
            throw new BibliotecaException("Já existe esse livro cadastrado para o autor informado.");
        }

        if (livro.getStatus() == null || livro.getStatus().isBlank()) {
            livro.setStatus("DISPONÍVEL");
        }

        return livroRepository.save(livro);
    }

    private boolean isSameEntity(Livro livro) {
        return livroRepository.findById(livro.getId())
                .map(l -> l.getTitulo().equalsIgnoreCase(livro.getTitulo())
                && l.getAutor().getId().equals(livro.getAutor().getId()))
                .orElse(false);
    }

    @Transactional
    public void excluir(Integer id) {
        if (!livroRepository.existsById(id)) {
            throw new BibliotecaException("Livro não encontrado.");
        }
        livroRepository.deleteById(id);
    }
}
