package com.projeto.agenda_pessoal.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.projeto.agenda_pessoal.model.Pessoa;

public interface PessoaRepository extends JpaRepository<Pessoa, Long> {

}
