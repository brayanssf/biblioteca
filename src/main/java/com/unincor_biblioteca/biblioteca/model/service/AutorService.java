package com.unincor_biblioteca.biblioteca.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.unincor_biblioteca.biblioteca.exceptions.BibliotecaException;
import com.unincor_biblioteca.biblioteca.model.domain.Autor;
import com.unincor_biblioteca.biblioteca.model.repository.AutorRepository;

import java.util.List;

@Service
public class AutorService {

    @Autowired
    private AutorRepository autorRepository;

    public List<Autor> listar() {
        return autorRepository.findAll();
    }

    public Autor consultar(Integer id) {
        return autorRepository.findById(id).orElse(null);
    }

    @Transactional
    public Autor salvar(Autor autor) {
        if (autor == null) {
            throw new BibliotecaException("Autor inválido.");
        }
        if (autor.getNome() == null || autor.getNome().isBlank()) {
            throw new BibliotecaException("Nome do autor é obrigatório.");
        }
        return autorRepository.save(autor);
    }

    @Transactional
    public void excluir(Integer id) {
        if (!autorRepository.existsById(id)) {
            throw new BibliotecaException("Autor não encontrado.");
        }
        autorRepository.deleteById(id);
    }
}
