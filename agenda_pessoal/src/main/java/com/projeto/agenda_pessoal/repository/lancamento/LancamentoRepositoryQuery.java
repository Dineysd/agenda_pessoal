package com.projeto.agenda_pessoal.repository.lancamento;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.projeto.agenda_pessoal.model.Lancamento;
import com.projeto.agenda_pessoal.repository.filter.LancamentoFilter;

public interface LancamentoRepositoryQuery {
	
	public Page<Lancamento> filtrar(LancamentoFilter filter, Pageable page);

}
