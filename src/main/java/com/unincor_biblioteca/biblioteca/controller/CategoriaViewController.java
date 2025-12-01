package com.unincor_biblioteca.biblioteca.controller;

import com.unincor_biblioteca.biblioteca.model.domain.Categoria;
import com.unincor_biblioteca.biblioteca.model.service.CategoriaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/categorias-site")
public class CategoriaViewController {

    @Autowired
    private CategoriaService categoriaService;

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("categorias", categoriaService.listar());
        return "categorias/lista";
    }

    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("categoria", new Categoria());
        return "categorias/form";
    }

    @PostMapping
    public String salvar(Categoria categoria, RedirectAttributes ra, Model model) {
        try {
            categoriaService.salvar(categoria);
            ra.addFlashAttribute("ok", "Categoria salva com sucesso!");
            return "redirect:/categorias-site";
        } catch (Exception e) {
            model.addAttribute("erro", e.getMessage());
            return "categorias/form";
        }
    }

    @PostMapping("/{id}/excluir")
    public String excluir(@PathVariable Integer id, RedirectAttributes ra) {
        categoriaService.excluir(id);
        ra.addFlashAttribute("ok", "Categoria excluída com sucesso!");
        return "redirect:/categorias-site";
    }
}
