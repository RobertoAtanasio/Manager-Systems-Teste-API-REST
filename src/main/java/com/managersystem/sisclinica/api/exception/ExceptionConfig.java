package com.managersystem.sisclinica.api.exception;

import java.nio.file.AccessDeniedException;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionConfig {
	
	@ExceptionHandler({EmptyResultDataAccessException.class})
	public ResponseEntity<Object> errorNotFound() {
        return ResponseEntity.notFound().build();
    }
	
	@ExceptionHandler({IllegalArgumentException.class})
	public ResponseEntity<Object> errorBadRequest() {
	    return ResponseEntity.badRequest().build();
	}
	
	@ExceptionHandler({AccessDeniedException.class})
	public ResponseEntity<Object> accessDenied() {
	    return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new Error("Acesso negado"));
	}
	
	@ExceptionHandler({ 
		TokenNaoAdministradorException.class,
		PaisInexistenteException.class,
		TokenInexistenteException.class,
		TokenExpiradoException.class,
		PaisJaExistenteException.class
		})
	public ResponseEntity<Object> handleNaoAutorizado() {
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
	}

}
