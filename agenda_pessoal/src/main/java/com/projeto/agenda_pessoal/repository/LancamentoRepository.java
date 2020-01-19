package com.projeto.agenda_pessoal.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.projeto.agenda_pessoal.model.Lancamento;
import com.projeto.agenda_pessoal.repository.lancamento.LancamentoRepositoryQuery;

public interface LancamentoRepository extends JpaRepository<Lancamento, Long>, LancamentoRepositoryQuery {

}
