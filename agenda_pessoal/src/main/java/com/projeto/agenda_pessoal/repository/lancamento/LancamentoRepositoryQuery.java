package com.projeto.agenda_pessoal.repository.lancamento;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.projeto.agenda_pessoal.model.Lancamento;
import com.projeto.agenda_pessoal.repository.filter.LancamentoFilter;
import com.projeto.agenda_pessoal.repository.projection.ResumoLancamento;

public interface LancamentoRepositoryQuery {
	
	public Page<Lancamento> filtrar(LancamentoFilter filter, Pageable page);
	public Page<ResumoLancamento> resumir(LancamentoFilter lancamentoFilter, Pageable pageable);

}
