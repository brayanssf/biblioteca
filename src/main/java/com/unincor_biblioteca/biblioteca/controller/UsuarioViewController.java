package com.unincor_biblioteca.biblioteca.controller;

import com.unincor_biblioteca.biblioteca.model.domain.Usuario;
import com.unincor_biblioteca.biblioteca.model.repository.UsuarioRepository;
import com.unincor_biblioteca.biblioteca.model.service.UsuarioService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/usuarios-site")
public class UsuarioViewController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("usuarios", usuarioRepository.findAll());
        return "usuarios/lista";
    }

    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "usuarios/form";
    }

    @GetMapping("/{id}/editar")
    public String editar(@PathVariable Integer id, Model model, RedirectAttributes ra) {
        var opt = usuarioRepository.findById(id);
        if (opt.isEmpty()) {
            ra.addFlashAttribute("erro", "Usuário não encontrado.");
            return "redirect:/usuarios-site";
        }
        model.addAttribute("usuario", opt.get());
        return "usuarios/form";
    }

    @PostMapping
    public String salvar(Usuario usuario, RedirectAttributes ra, Model model) {
        try {
            usuarioService.salvar(usuario);
            ra.addFlashAttribute("ok", "Usuário salvo com sucesso!");
            return "redirect:/usuarios-site";
        } catch (Exception e) {
            model.addAttribute("erro", e.getMessage());
            return "usuarios/form";
        }
    }

    @PostMapping("/{id}/excluir")
    public String excluir(@PathVariable Integer id, RedirectAttributes ra) {
        usuarioRepository.deleteById(id);
        ra.addFlashAttribute("ok", "Usuário excluído com sucesso!");
        return "redirect:/usuarios-site";
    }
}
