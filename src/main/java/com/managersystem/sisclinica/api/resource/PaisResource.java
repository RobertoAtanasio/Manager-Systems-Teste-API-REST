package com.managersystem.sisclinica.api.resource;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.managersystem.sisclinica.api.exception.PaisInexistenteException;
import com.managersystem.sisclinica.api.exception.PaisJaExistenteException;
import com.managersystem.sisclinica.api.exception.TokenExpiradoException;
import com.managersystem.sisclinica.api.exception.TokenInexistenteException;
import com.managersystem.sisclinica.api.exception.TokenNaoAdministradorException;
import com.managersystem.sisclinica.api.model.Pais;
import com.managersystem.sisclinica.api.service.PaisService;

@RestController
@RequestMapping("/pais")
public class PaisResource {
	
	@Autowired
	private PaisService paisService;
	
	@GetMapping("/pesquisar")
	public ResponseEntity<List<Pais>> pesquisar(@RequestParam String token, @RequestParam String nome) {
		List<Pais> lista = null;
		lista = paisService.pesquisar(token, nome);		
		return ResponseEntity.status(HttpStatus.OK).body(lista);
	}
	
	@GetMapping("/listar")
	public ResponseEntity<List<Pais>> listar(@RequestParam String token) { 
		List<Pais> lista = null;
		lista = paisService.listar(token);
		return ResponseEntity.status(HttpStatus.OK).body(lista);
	}
	
	@PostMapping("/salvar")
	public ResponseEntity<Pais> salvar(@RequestParam String token, @Valid @RequestBody Pais pais) {
		Pais paisSalvo = null;
		if (pais.getId() > 0) {
			paisSalvo = paisService.atualizar(token, pais);
			return ResponseEntity.status(HttpStatus.OK).body(paisSalvo);
		} else {				
			paisSalvo = paisService.salvar(token, pais);
			return ResponseEntity.status(HttpStatus.CREATED).body(paisSalvo);
		}		
	}
	
	@GetMapping("/excluir")
	public ResponseEntity<Object> remover(@RequestParam String token, @RequestParam Long id) {
		paisService.excluir(token, id);
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
