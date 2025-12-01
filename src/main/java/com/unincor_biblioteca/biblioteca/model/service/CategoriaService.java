package com.unincor_biblioteca.biblioteca.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unincor_biblioteca.biblioteca.exceptions.BibliotecaException;
import com.unincor_biblioteca.biblioteca.model.domain.Categoria;
import com.unincor_biblioteca.biblioteca.model.repository.CategoriaRepository;

import java.util.List;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    public List<Categoria> listar() {
        return categoriaRepository.findAll();
    }

    public Categoria consultar(Integer id) {
        return categoriaRepository.findById(id).orElse(null);
    }

    @Transactional
    public Categoria salvar(Categoria categoria) {
        if (categoria == null) {
            throw new BibliotecaException("Categoria inválida.");
        }
        if (categoria.getNome() == null || categoria.getNome().isBlank()) {
            throw new BibliotecaException("Nome da categoria é obrigatório.");
        }
        return categoriaRepository.save(categoria);
    }

    @Transactional
    public void excluir(Integer id) {
        if (!categoriaRepository.existsById(id)) {
            throw new BibliotecaException("Categoria não encontrada.");
        }
        categoriaRepository.deleteById(id);
    }
}
