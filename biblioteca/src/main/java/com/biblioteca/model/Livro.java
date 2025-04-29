package com.biblioteca.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Livro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String generos;

    @Enumerated(EnumType.STRING)
    private EstadoLivro estado = EstadoLivro.DISPONIVEL;

    @ManyToOne
    private Autor autor;

    @ManyToOne
    private Usuario usuario;

    public Livro(String nome, String generos, Autor autor) {
        this.nome = nome;
        this.generos = generos;
        this.autor = autor;
        this.estado = EstadoLivro.DISPONIVEL;
    }
}
