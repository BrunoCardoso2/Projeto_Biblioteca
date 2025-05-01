package com.biblioteca.controller;

import com.biblioteca.model.Usuario;
import com.biblioteca.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    // Endpoint para adicionar um novo usuário
    @PostMapping
    public Usuario adicionarUsuario(@RequestBody Usuario usuario) {
        return usuarioRepository.save(usuario);
    }
    // Deletar  Autor
    @DeleteMapping("/deletar")
    public ResponseEntity<List<Usuario>> deletarUsuario(@RequestBody Map<String, String> body) {
        String nome = body.get("nome");
    
        List<Usuario> usuarios = usuarioRepository.findByNomeIgnoreCase(nome);
        if (usuarios.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
      
        usuarioRepository.deleteAll(usuarios);
        return ResponseEntity.ok(usuarios);
    }

    //Endpoint para listar todos os usuários
    @GetMapping
    public List<Usuario> listarUsuarios() {
        return usuarioRepository.findAll();
    }
}
