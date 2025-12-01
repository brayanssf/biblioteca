package com.unincor_biblioteca.biblioteca.controller;

import com.unincor_biblioteca.biblioteca.model.domain.Emprestimo;
import com.unincor_biblioteca.biblioteca.model.repository.EmprestimoRepository;
import com.unincor_biblioteca.biblioteca.model.service.EmprestimoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/emprestimos")
public class EmprestimoController {

    @Autowired
    private EmprestimoRepository emprestimoRepository;

    @Autowired
    private EmprestimoService emprestimoService;

    @GetMapping
    public List<Emprestimo> listar() {
        return emprestimoRepository.findAll();
    }

    @PostMapping
    public ResponseEntity<?> registrar(@RequestBody Emprestimo emprestimo) {
        try {
            Emprestimo salvo = emprestimoService.registrar(emprestimo);
            return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/{id}/devolver")
    public ResponseEntity<?> devolver(@PathVariable Integer id) {
        try {
            Emprestimo salvo = emprestimoService.registrarDevolucao(id);
            return ResponseEntity.ok(salvo);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
