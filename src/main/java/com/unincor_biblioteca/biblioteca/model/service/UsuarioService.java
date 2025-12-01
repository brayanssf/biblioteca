package com.unincor_biblioteca.biblioteca.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unincor_biblioteca.biblioteca.exceptions.BibliotecaException;
import com.unincor_biblioteca.biblioteca.model.domain.Usuario;
import com.unincor_biblioteca.biblioteca.model.repository.UsuarioRepository;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Transactional
    public Usuario salvar(Usuario usuario) {

        if (usuario == null) {
            throw new BibliotecaException("Usuário inválido.");
        }

        if (usuario.getNome() == null || usuario.getNome().isBlank()) {
            throw new BibliotecaException("Nome do usuário é obrigatório.");
        }

        return usuarioRepository.save(usuario);
    }

    @Transactional
    public void excluir(Integer id) {
        if (!usuarioRepository.existsById(id)) {
            throw new BibliotecaException("Usuário não encontrado.");
        }
        usuarioRepository.deleteById(id);
    }
}
