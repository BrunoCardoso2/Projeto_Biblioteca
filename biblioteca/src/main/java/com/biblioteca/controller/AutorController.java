package com.biblioteca.controller;

import com.biblioteca.model.Autor;
import com.biblioteca.repository.AutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/autores")
public class AutorController {

    @Autowired
    private AutorRepository autorRepository;

    // Criar um novo autor
    @PostMapping
    public ResponseEntity<Autor> criarAutor(@RequestBody Autor autor) {
        Autor novoAutor = autorRepository.save(autor);
        return ResponseEntity.ok(novoAutor);
    }

        // Deletar  Autor
    @DeleteMapping("/deletar")
    public ResponseEntity<List<Autor>> deletarAutor(@RequestBody Map<String, String> body) {
        String nome = body.get("nome");
    
        List<Autor> autores = autorRepository.findByNomeIgnoreCase(nome);
        if (autores.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
    
        autorRepository.deleteAll(autores);
        return ResponseEntity.ok(autores);
    }

    // Buscar autor por nome
    @GetMapping("/nome/{nome}")
    public ResponseEntity<List<Autor>> buscarPorNome(@PathVariable String nome) {
        List<Autor> autores = autorRepository.findByNomeContainingIgnoreCase(nome);
        if (autores.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(autores);
    }

    // Listar todos os autores
    @GetMapping
    public ResponseEntity<List<Autor>> listarAutores() {
        List<Autor> autores = autorRepository.findAll();
        return ResponseEntity.ok(autores);
    }

    @PutMapping("/atualizar")
    public ResponseEntity<List<Autor>> atualizarAutor(@RequestBody Autor autorAtualizado) {
        if (autorAtualizado.getNome() == null) {
            return ResponseEntity.badRequest().build(); // Nome é necessário
        }
    
        List<Autor> autores = autorRepository.findByNomeIgnoreCase(autorAtualizado.getNome());
    
        if (autores.isEmpty()) {
            return ResponseEntity.notFound().build(); // Nenhum autor encontrado com o nome fornecido
        }
    
        for (Autor autor : autores) {
            autor.setNome(autorAtualizado.getNome()); // Atualiza o nome (pode manter ou mudar)
            autorRepository.save(autor);
        }
    
        return ResponseEntity.ok(autores);
    }
    

}
