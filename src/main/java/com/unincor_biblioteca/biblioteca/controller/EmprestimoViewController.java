package com.unincor_biblioteca.biblioteca.controller;

import com.unincor_biblioteca.biblioteca.model.domain.Emprestimo;
import com.unincor_biblioteca.biblioteca.model.repository.EmprestimoRepository;
import com.unincor_biblioteca.biblioteca.model.repository.UsuarioRepository;
import com.unincor_biblioteca.biblioteca.model.repository.LivroRepository;
import com.unincor_biblioteca.biblioteca.model.service.EmprestimoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/emprestimos-site")
public class EmprestimoViewController {

    @Autowired
    private EmprestimoRepository emprestimoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private LivroRepository livroRepository;

    @Autowired
    private EmprestimoService emprestimoService;

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("emprestimos", emprestimoRepository.findAll());
        return "emprestimos/lista";
    }

    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("emprestimo", new Emprestimo());
        model.addAttribute("usuarios", usuarioRepository.findAll());
        model.addAttribute("livros", livroRepository.findByStatusIgnoreCase("DISPONÍVEL"));
        return "emprestimos/form";
    }

    @PostMapping
    public String salvar(Emprestimo emprestimo, RedirectAttributes ra, Model model) {
        try {
            emprestimoService.registrar(emprestimo);
            ra.addFlashAttribute("ok", "Empréstimo registrado com sucesso!");
            return "redirect:/emprestimos-site";

        } catch (Exception e) {
            model.addAttribute("erro", e.getMessage());

            model.addAttribute("usuarios", usuarioRepository.findAll());
            model.addAttribute("livros", livroRepository.findByStatusIgnoreCase("DISPONÍVEL"));

            return "emprestimos/form";
        }
    }

    @PostMapping("/{id}/devolver")
    public String devolver(@PathVariable Integer id, RedirectAttributes ra) {
        try {
            emprestimoService.registrarDevolucao(id);
            ra.addFlashAttribute("ok", "Livro devolvido com sucesso!");

        } catch (Exception e) {
            ra.addFlashAttribute("erro", e.getMessage());
        }

        return "redirect:/emprestimos-site";
    }
}
