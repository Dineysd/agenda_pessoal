package com.projeto.agenda_pessoal.controller;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.projeto.agenda_pessoal.event.RecursoCriadoEvent;
import com.projeto.agenda_pessoal.exceptions.AgendaExceptionHandler.Erro;
import com.projeto.agenda_pessoal.exceptions.PessoaInativaException;
import com.projeto.agenda_pessoal.model.Lancamento;
import com.projeto.agenda_pessoal.repository.LancamentoRepository;
import com.projeto.agenda_pessoal.repository.filter.LancamentoFilter;
import com.projeto.agenda_pessoal.service.LancamentoService;

@RestController
@RequestMapping("/lancamentos")
public class LancamentoController {
	
	@Autowired
	private LancamentoRepository repository;
	
	@Autowired
	private LancamentoService service;
	
	@Autowired
	private MessageSource messageSource;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@GetMapping
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_LANCAMENTO') and #oauth2.hasScope('read')")
	public Page<Lancamento> pesquisar(LancamentoFilter filter, Pageable page){
		return repository.filtrar(filter, page);
	}
	
	//@GetMapping
	//public List<Lancamento> listar(){
	//	return repository.findAll();
	//}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	@PreAuthorize("hasAuthority('ROLE_CADASTRAR_LANCAMENTO') and #oauth2.hasScope('write')")
	public ResponseEntity<Lancamento> criar(@Valid @RequestBody Lancamento lancamento, HttpServletResponse response) {
		Lancamento lancamentoSalva = service.insert(lancamento);
		//criando evento para header location
		publisher.publishEvent(new RecursoCriadoEvent(this, response, lancamentoSalva.getCodigo()));
		
		return ResponseEntity.status(HttpStatus.CREATED).body(lancamentoSalva);
	}
	
	@GetMapping("/{codigo}")
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_LANCAMENTO') and #oauth2.hasScope('read')")
	public ResponseEntity<Lancamento> buscarPorCodigo(@PathVariable Long codigo) {
		return repository.findById(codigo)
				.map(lancamento -> ResponseEntity.ok(lancamento))
			      .orElse(ResponseEntity.notFound().build());
	}
	
	@ExceptionHandler({PessoaInativaException.class})
	protected ResponseEntity<Object> handlePessoaInativaException(PessoaInativaException ex) {
		// TODO Auto-generated method stub
		String mensagemUsario = messageSource.getMessage("pessoa.inativa",null, LocaleContextHolder.getLocale());
		String mensagemDeveloper = ex.toString();
		List<Erro> erros = Arrays.asList(new Erro(mensagemUsario,mensagemDeveloper));
		return ResponseEntity.badRequest().body(erros);
	}
	
	@DeleteMapping("/{codigo}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@PreAuthorize("hasAuthority('ROLE_REMOVER_LANCAMENTO') and #oauth2.hasScope('write')")
	public void excluir(@PathVariable Long codigo) {
		 repository.deleteById(codigo);
	}
	
	
	
	
}
