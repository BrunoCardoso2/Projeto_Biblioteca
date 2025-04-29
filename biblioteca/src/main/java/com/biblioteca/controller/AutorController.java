package com.biblioteca.controller;

import com.biblioteca.model.Autor;
import com.biblioteca.repository.AutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
}
