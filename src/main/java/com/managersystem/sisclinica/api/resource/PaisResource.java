package com.managersystem.sisclinica.api.resource;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.managersystem.sisclinica.api.exception.PaisInexistenteException;
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
	public ResponseEntity<List<Pais>> pesquisar(TokenFiltro tokenCodigo, PaisFiltro paisFiltro) {
		List<Pais> lista = null;
		try {
			lista = paisService.pesquisar(tokenCodigo.getToken(), paisFiltro);		
		} catch (TokenInexistenteException e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
		return ResponseEntity.status(HttpStatus.OK).body(lista);
	}
	
	@GetMapping("/listar")
	public ResponseEntity<List<Pais>> listar(TokenFiltro tokenFiltro) { 
		List<Pais> lista = null;
		try {
			lista = paisService.listar(tokenFiltro.getToken());
		} catch (TokenInexistenteException e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		} catch (TokenExpiradoException e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
		return ResponseEntity.status(HttpStatus.OK).body(lista);
	}
	
	@PostMapping("/salvar")
	public ResponseEntity<Pais> salvar(TokenFiltro tokenFiltro, @Valid @RequestBody Pais pais, HttpServletResponse response) {
		Pais paisSalvo = null;
		if (pais.getId() > 0) {
			try {
				paisSalvo = paisService.atualizar(tokenFiltro.getToken(), pais);
			} catch (TokenInexistenteException e) {
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
			} catch (TokenExpiradoException e) {
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
			} catch (TokenNaoAdministradorException e) {
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
			} catch (PaisInexistenteException e) {
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
			}
			return ResponseEntity.status(HttpStatus.OK).body(paisSalvo);
		} else {
			try {				
				paisSalvo = paisService.salvar(tokenFiltro.getToken(), pais);
			} catch (TokenInexistenteException e) {
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
			} catch (TokenExpiradoException e) {
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
			} catch (TokenNaoAdministradorException e) {
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
			}
			return ResponseEntity.status(HttpStatus.CREATED).body(paisSalvo);
		}		
	}
		
	@GetMapping("/excluir")
	public ResponseEntity<Object> remover(PaisFiltroExcluir filtroExcluir, HttpServletResponse response) {
		try {
			paisService.excluir(filtroExcluir);
		} catch (PaisInexistenteException e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		} catch (TokenNaoAdministradorException e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		} catch (TokenInexistenteException e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		} catch (TokenExpiradoException e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
		return ResponseEntity.status(HttpStatus.OK).body(true);
	}
	
}
