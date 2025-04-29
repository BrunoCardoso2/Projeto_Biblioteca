package com.biblioteca.controller;

import com.biblioteca.model.Livro;
import com.biblioteca.model.EstadoLivro;
import com.biblioteca.repository.LivroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/livros")
public class LivroController {

    @Autowired
    private LivroRepository livroRepository;

    // Criar um novo livro
    @PostMapping
    public ResponseEntity<Livro> criarLivro(@RequestBody Livro livro) {
        if (livro.getAutor() == null || livro.getAutor().getId() == null) {
            return ResponseEntity.badRequest().body(null); // Retorna 400 se o autor não for válido
        }
        Livro novoLivro = livroRepository.save(livro);
        return ResponseEntity.ok(novoLivro);
    }
    
    // Buscar livro por nome
    @GetMapping("/nome/{nome}")
    public ResponseEntity<List<Livro>> buscarPorNome(@PathVariable String nome) {
        List<Livro> livros = livroRepository.findByNomeContainingIgnoreCase(nome);
        if (livros.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(livros);
    }

    // Buscar livros pelo estado (DISPONIVEL, EMPRESTADO, etc.)
    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<Livro>> buscarPorEstado(@PathVariable EstadoLivro estado) {
        List<Livro> livros = livroRepository.findByEstado(estado);
        if (livros.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(livros);
    }

    // Atualizar estado de um livro (exemplo: de DISPONIVEL para EMPRESTADO)
    @PutMapping("/{id}/estado")
    public ResponseEntity<Livro> atualizarEstado(@PathVariable Long id, @RequestParam EstadoLivro estado) {
        return livroRepository.findById(id)
                .map(livro -> {
                    livro.setEstado(estado);
                    Livro updatedLivro = livroRepository.save(livro);
                    return ResponseEntity.ok(updatedLivro);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Listar todos os livros
    @GetMapping
    public ResponseEntity<List<Livro>> listarLivros() {
        List<Livro> livros = livroRepository.findAll();
        return ResponseEntity.ok(livros);
    }
     // Buscar livros por autor (usando o ID do autor)
     @GetMapping("/autor/{autorId}")
     public ResponseEntity<List<Livro>> listarLivrosPorAutor(@PathVariable Long autorId) {
         List<Livro> livros = livroRepository.findByAutorId(autorId);
         if (livros.isEmpty()) {
             return ResponseEntity.notFound().build(); // Retorna 404 se não encontrar livros
         }
         return ResponseEntity.ok(livros);
     }
}
