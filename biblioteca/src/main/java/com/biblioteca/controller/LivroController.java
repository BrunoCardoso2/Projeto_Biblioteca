package com.biblioteca.controller;

import com.biblioteca.model.Livro;
import com.biblioteca.model.EstadoLivro;
import com.biblioteca.repository.LivroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

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

    // Deletar  livro
    @DeleteMapping("/deletar")
    public ResponseEntity<List<Livro>> deletarLivro(@RequestBody Map<String, String> body) {
        String nome = body.get("nome");
    
        List<Livro> livros = livroRepository.findByNomeIgnoreCase(nome);
        if (livros.isEmpty()) {
            return ResponseEntity.notFound().build(); // Retorna 404 se não encontrar livros
        }
    
        livroRepository.deleteAll(livros);
        return ResponseEntity.ok(livros);
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

    // Buscar livros pelo estado (DISPONIVEL, EMPRESTADO)
    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<Livro>> buscarPorEstado(@PathVariable EstadoLivro estado) {
        List<Livro> livros = livroRepository.findByEstado(estado);
        if (livros.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(livros);
    }

    // Atualizar estado de um livro (ex: DISPONIVEL para EMPRESTADO)
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
             return ResponseEntity.notFound().build();
         }
         return ResponseEntity.ok(livros);
     }

     @PutMapping("/atualizar")
     public ResponseEntity<List<Livro>> atualizarLivro(@RequestBody Livro livroAtualizado) {
         if (livroAtualizado.getNome() == null) {
             return ResponseEntity.badRequest().build(); // Nome é obrigatório
         }
     
         List<Livro> livros = livroRepository.findByNomeIgnoreCase(livroAtualizado.getNome());
     
         if (livros.isEmpty()) {
             return ResponseEntity.notFound().build(); // Nenhum livro com esse nome
         }
     
         for (Livro livro : livros) {
             // Substitui completamente os dados do livro
             livro.setEstado(livroAtualizado.getEstado());
             livro.setAutor(livroAtualizado.getAutor());
             livro.setNome(livroAtualizado.getNome()); // Pode manter ou substituir com o mesmo nome
             livroRepository.save(livro);
         }
     
         return ResponseEntity.ok(livros);
     }
     
}
