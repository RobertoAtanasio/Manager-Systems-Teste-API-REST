package com.managersystem.sisclinica.api.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public class SisclinicaExceptionHandler {

	public static class Mensagem {
		
		private String mensagem;
		
		public Mensagem(String mensagem) {
			this.mensagem = mensagem;
		}
		
		public String getMensagem() {
			return mensagem;
		}
		
	}
	
	public static class Erro {
		
		private String mensagemUsuario;
		private String mensagemDesenvolvedor;
		
		public Erro(String mensagemUsuario, String mensagemDesenvolvedor) {
			this.mensagemUsuario = mensagemUsuario;
			this.mensagemDesenvolvedor = mensagemDesenvolvedor;
		}

		public String getMensagemUsuario() {
			return mensagemUsuario;
		}

		public String getMensagemDesenvolvedor() {
			return mensagemDesenvolvedor;
		}
		
	}
	
}
