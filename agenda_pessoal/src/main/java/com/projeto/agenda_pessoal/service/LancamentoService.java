package com.projeto.agenda_pessoal.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.projeto.agenda_pessoal.exceptions.PessoaInativaException;
import com.projeto.agenda_pessoal.model.Lancamento;
import com.projeto.agenda_pessoal.model.Pessoa;
import com.projeto.agenda_pessoal.repository.LancamentoRepository;
import com.projeto.agenda_pessoal.repository.PessoaRepository;

@Service
public class LancamentoService {
	@Autowired
	private LancamentoRepository repository;
	
	@Autowired
	private PessoaRepository pessoadb;
	
	public Lancamento insert(Lancamento lancamento){
		Pessoa pessoa = pessoadb.findById(lancamento.getPessoa().getCodigo()).orElseThrow(()->
		new EmptyResultDataAccessException(1));
		
		if( !pessoa.isAtivo()) {
			throw new PessoaInativaException();
		}
			
		return repository.save(lancamento);
	}

}
