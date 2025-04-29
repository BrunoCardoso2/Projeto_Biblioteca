package com.biblioteca.repository;

import com.biblioteca.model.Emprestimo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface EmprestimoRepository extends JpaRepository<Emprestimo, Long> {

    List<Emprestimo> findByUsuario_Id(Long usuarioId);

    List<Emprestimo> findByLivro_Id(Long livroId);

    List<Emprestimo> findByDataDevolucaoRealIsNull(); // Empréstimos em andamento

    List<Emprestimo> findByDataDevolucaoPrevistaBeforeAndDataDevolucaoRealIsNull(LocalDate hoje);
}
