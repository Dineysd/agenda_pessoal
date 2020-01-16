package com.projeto.agenda_pessoal.exceptions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@ControllerAdvice
class AgendaExceptionHandler extends ResponseEntityExceptionHandler{
	
	@Autowired
	private MessageSource message;
	
	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		// TODO Auto-generated method stub
		String mensagemUsario = message.getMessage("mensagem.invalida",null, LocaleContextHolder.getLocale());
		String mensagemDeveloper = ex.getCause() != null? ex.getCause().toString(): ex.toString();
		List<Erro> erros = Arrays.asList(new Erro(mensagemUsario,mensagemDeveloper));
		return handleExceptionInternal(ex, erros, headers, HttpStatus.BAD_REQUEST, request);
	}
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		// TODO Auto-generated method stub
		List<Erro> erros = criarListaErros(ex.getBindingResult());
		return handleExceptionInternal(ex, erros, headers, HttpStatus.BAD_REQUEST, request);
	}
	
	@ExceptionHandler({EmptyResultDataAccessException.class})
	public ResponseEntity<Object> handleEmptyResultDataAccessException(EmptyResultDataAccessException ex, WebRequest request) {
		
		String mensagemUsario = message.getMessage("recurso.nao_encontrado",null, LocaleContextHolder.getLocale());
		String mensagemDeveloper = ex.toString();
		List<Erro> erros = Arrays.asList(new Erro(mensagemUsario,mensagemDeveloper));
		return handleExceptionInternal(ex, erros, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
	}
	
	private List<Erro> criarListaErros(BindingResult bind){
		 List<Erro> erros = new ArrayList<>();
		 
		 for(FieldError field: bind.getFieldErrors()) {
			 String mensagemUsario = message.getMessage(field, LocaleContextHolder.getLocale());
			 String mensagemDeveloper = field.toString();
			 erros.add(new Erro(mensagemUsario,mensagemDeveloper));
		 }
		 
		 return erros;
	}
	
	public static class Erro {
		private String mensagemUsario;
		private String mensagemDeveloper;
		
		public Erro(String mensagemUsario, String mensagemDeveloper) {
			 this.mensagemDeveloper = mensagemDeveloper;
			 this.mensagemUsario = mensagemUsario;
		}

		public String getMensagemUsario() {
			return mensagemUsario;
		}

		public String getMensagemDeveloper() {
			return mensagemDeveloper;
		}
		
		
	}

}
