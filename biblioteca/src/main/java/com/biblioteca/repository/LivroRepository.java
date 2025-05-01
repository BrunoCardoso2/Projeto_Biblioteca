package com.biblioteca.repository;

import com.biblioteca.model.Livro;
import com.biblioteca.model.EstadoLivro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LivroRepository extends JpaRepository<Livro, Long> {
    List<Livro> findByEstado(EstadoLivro estado);
    List<Livro> findByNomeContainingIgnoreCase(String nome);
    List<Livro> findByNomeIgnoreCase(String nome);
    List<Livro> findByAutorId(Long autorId);
}
