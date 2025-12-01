package com.unincor_biblioteca.biblioteca.controller;

import com.unincor_biblioteca.biblioteca.model.domain.Livro;
import com.unincor_biblioteca.biblioteca.model.repository.*;
import com.unincor_biblioteca.biblioteca.model.service.LivroService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/livros-site")
public class LivroViewController {

    @Autowired
    private LivroRepository livroRepository;

    @Autowired
    private AutorRepository autorRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private LivroService livroService;

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("livros", livroRepository.findAll());
        return "livros/lista";
    }

    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("livro", new Livro());
        model.addAttribute("autores", autorRepository.findAll());
        model.addAttribute("categorias", categoriaRepository.findAll());
        return "livros/form";
    }

    @GetMapping("/{id}/editar")
    public String editar(@PathVariable Integer id, Model model, RedirectAttributes ra) {
        var opt = livroRepository.findById(id);
        if (opt.isEmpty()) {
            ra.addFlashAttribute("erro", "Livro não encontrado.");
            return "redirect:/livros-site";
        }
        model.addAttribute("livro", opt.get());
        model.addAttribute("autores", autorRepository.findAll());
        model.addAttribute("categorias", categoriaRepository.findAll());
        return "livros/form";
    }

    @PostMapping
    public String salvar(Livro livro, RedirectAttributes ra, Model model) {
        try {
            livroService.salvar(livro);
            ra.addFlashAttribute("ok", "Livro salvo com sucesso!");
            return "redirect:/livros-site";
        } catch (Exception e) {
            model.addAttribute("erro", e.getMessage());
            model.addAttribute("autores", autorRepository.findAll());
            model.addAttribute("categorias", categoriaRepository.findAll());
            return "livros/form";
        }
    }

    @PostMapping("/{id}/excluir")
    public String excluir(@PathVariable Integer id, RedirectAttributes ra) {
        livroRepository.deleteById(id);
        ra.addFlashAttribute("ok", "Livro excluído com sucesso!");
        return "redirect:/livros-site";
    }
}
