package com.unincor_biblioteca.biblioteca.controller;

import com.unincor_biblioteca.biblioteca.model.domain.Autor;
import com.unincor_biblioteca.biblioteca.model.service.AutorService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/autores-site")
public class AutorViewController {

    @Autowired
    private AutorService autorService;

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("autores", autorService.listar());
        return "autores/lista";
    }

    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("autor", new Autor());
        return "autores/form";
    }

    @PostMapping
    public String salvar(Autor autor, RedirectAttributes ra, Model model) {
        try {
            autorService.salvar(autor);
            ra.addFlashAttribute("ok", "Autor salvo com sucesso!");
            return "redirect:/autores-site";
        } catch (Exception e) {
            model.addAttribute("erro", e.getMessage());
            return "autores/form";
        }
    }

    @PostMapping("/{id}/excluir")
    public String excluir(@PathVariable Integer id, RedirectAttributes ra) {
        autorService.excluir(id);
        ra.addFlashAttribute("ok", "Autor excluído com sucesso!");
        return "redirect:/autores-site";
    }
}
