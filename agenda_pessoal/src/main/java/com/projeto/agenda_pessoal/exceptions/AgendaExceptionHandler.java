package com.projeto.agenda_pessoal.exceptions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContext;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
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
		String mensagemDeveloper = ex.getCause().toString();
		return handleExceptionInternal(ex, new Erro(mensagemUsario,mensagemDeveloper), headers, HttpStatus.BAD_REQUEST, request);
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
