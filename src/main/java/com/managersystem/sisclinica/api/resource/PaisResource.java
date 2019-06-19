package com.managersystem.sisclinica.api.resource;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.managersystem.sisclinica.api.exception.PaisInexistenteException;
import com.managersystem.sisclinica.api.exception.PaisJaExistenteException;
import com.managersystem.sisclinica.api.exception.TokenExpiradoException;
import com.managersystem.sisclinica.api.exception.TokenInexistenteException;
import com.managersystem.sisclinica.api.exception.TokenNaoAdministradorException;
import com.managersystem.sisclinica.api.model.Pais;
import com.managersystem.sisclinica.api.repository.filtro.PaisFiltro;
import com.managersystem.sisclinica.api.repository.filtro.PaisFiltroExcluir;
import com.managersystem.sisclinica.api.repository.filtro.TokenFiltro;
import com.managersystem.sisclinica.api.service.validarPais;

@RestController
@RequestMapping("/pais")
public class PaisResource {
	
	@Autowired
	private validarPais paisService;
	
	@GetMapping("/pesquisar")
	public ResponseEntity<List<Pais>> pesquisar(PaisFiltro paisFiltro) {
		List<Pais> lista = null;
		lista = paisService.pesquisar(paisFiltro.getToken(), paisFiltro);		
		return ResponseEntity.status(HttpStatus.OK).body(lista);
	}
	
	@GetMapping("/listar")
	public ResponseEntity<List<Pais>> listar(TokenFiltro tokenFiltro) { 
		List<Pais> lista = null;
		lista = paisService.listar(tokenFiltro.getToken());
		return ResponseEntity.status(HttpStatus.OK).body(lista);
	}
	
	@PostMapping("/salvar")
	public ResponseEntity<Pais> salvar(TokenFiltro tokenFiltro, @Valid @RequestBody Pais pais, HttpServletResponse response) {
		Pais paisSalvo = null;
		if (pais.getId() > 0) {
			paisSalvo = paisService.atualizar(tokenFiltro.getToken(), pais);
			return ResponseEntity.status(HttpStatus.OK).body(paisSalvo);
		} else {				
			paisSalvo = paisService.salvar(tokenFiltro.getToken(), pais);
			return ResponseEntity.status(HttpStatus.CREATED).body(paisSalvo);
		}		
	}
	
	@GetMapping("/excluir")
	public ResponseEntity<Object> remover(PaisFiltroExcluir filtroExcluir, HttpServletResponse response) {
		paisService.excluir(filtroExcluir);
		return ResponseEntity.status(HttpStatus.OK).body(true);
	}
	
	@ExceptionHandler({ TokenNaoAdministradorException.class })
	public ResponseEntity<Object> handleTokenNaoAdministradorException(TokenNaoAdministradorException ex) {
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
	}
	
	@ExceptionHandler({ PaisInexistenteException.class })
	public ResponseEntity<Object> handlePaisInexistenteException(PaisInexistenteException ex) {
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
	}
	
	@ExceptionHandler({ TokenInexistenteException.class })
	public ResponseEntity<Object> handleTokenInexistenteException(TokenInexistenteException ex) {
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
	}
	
	@ExceptionHandler({ TokenExpiradoException.class })
	public ResponseEntity<Object> handleTokenExpiradoException(TokenExpiradoException ex) {
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
	}
	
	@ExceptionHandler({ PaisJaExistenteException.class })
	public ResponseEntity<Object> handlePaisJaExistenteException(PaisJaExistenteException ex) {
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
	}
	
}
