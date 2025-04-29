package com.biblioteca.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Emprestimo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Livro livro;

    @ManyToOne
    private Usuario usuario;

    private LocalDate dataEmprestimo;
    private LocalDate dataDevolucaoPrevista;
    private LocalDate dataDevolucaoReal;

    public boolean isDevolvido() {
        return dataDevolucaoReal != null;
    }

    public boolean isAtrasado() {
        return !isDevolvido() && LocalDate.now().isAfter(dataDevolucaoPrevista);
    }

    public long diasDeAtraso() {
        if (!isAtrasado()) return 0;
        return java.time.temporal.ChronoUnit.DAYS.between(dataDevolucaoPrevista, LocalDate.now());
    }
}
