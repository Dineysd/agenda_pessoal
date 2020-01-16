package com.projeto.agenda_pessoal.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.projeto.agenda_pessoal.model.Categoria;
import com.projeto.agenda_pessoal.model.Lancamento;

public interface LancamentoRepository extends JpaRepository<Lancamento, Long> {

}
