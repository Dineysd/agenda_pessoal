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
		Pessoa pessoaSalva = repository.findById(codigo)
				.orElseThrow(()-> new EmptyResultDataAccessException(1));
		
		BeanUtils.copyProperties(pessoa, pessoaSalva,"codigo");
		
		return repository.save(pessoaSalva);
		
	}

}
