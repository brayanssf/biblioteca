package com.unincor_biblioteca.biblioteca.controller;

import com.unincor_biblioteca.biblioteca.model.domain.Livro;
import com.unincor_biblioteca.biblioteca.model.repository.LivroRepository;
import com.unincor_biblioteca.biblioteca.model.service.LivroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/livros")
public class LivroController {

    @Autowired
    private LivroRepository livroRepository;

    @Autowired
    private LivroService livroService;

    @GetMapping
    public List<Livro> listar() {
        return livroRepository.findAll();
    }

    @PostMapping
    public ResponseEntity<?> salvar(@RequestBody Livro livro) {
        try {
            Livro salvo = livroService.salvar(livro);
            return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> excluir(@PathVariable Integer id) {
        if (!livroRepository.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Livro não encontrado.");
        }
        livroRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
