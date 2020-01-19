package com.projeto.agenda_pessoal.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.projeto.agenda_pessoal.model.Pessoa;
import com.projeto.agenda_pessoal.repository.PessoaRepository;

@Service
public class PessoaService {
	
	@Autowired
	private PessoaRepository repository;
	
	public Pessoa update(Long codigo, Pessoa pessoa) {
		Pessoa pessoaSalva = fetchPeopleById(codigo);
		
		BeanUtils.copyProperties(pessoa, pessoaSalva,"codigo");
		
		return repository.save(pessoaSalva);
	}
	
	public void updateActivePerson(Long codigo, Boolean ativo) {
		Pessoa pessoaSalva = fetchPeopleById(codigo);
		pessoaSalva.setAtivo(ativo);
		repository.save(pessoaSalva);
	}

	public Pessoa fetchPeopleById(Long codigo) {
		Pessoa pessoa = repository.findById(codigo)
				.orElseThrow(()-> new EmptyResultDataAccessException(1));
		return pessoa;
	}

}
