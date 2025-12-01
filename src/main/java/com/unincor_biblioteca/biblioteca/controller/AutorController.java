package com.unincor_biblioteca.biblioteca.controller;

import com.unincor_biblioteca.biblioteca.model.domain.Autor;
import com.unincor_biblioteca.biblioteca.model.service.AutorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/autores")
public class AutorController {

    @Autowired
    private AutorService autorService;

    @GetMapping
    public List<Autor> listar() {
        return autorService.listar();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> consultar(@PathVariable Integer id) {
        Autor autor = autorService.consultar(id);
        if (autor == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Autor não encontrado.");
        }
        return ResponseEntity.ok(autor);
    }

    @PostMapping
    public ResponseEntity<?> salvar(@RequestBody Autor autor) {
        try {
            Autor salvo = autorService.salvar(autor);
            return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> excluir(@PathVariable Integer id) {
        try {
            autorService.excluir(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
