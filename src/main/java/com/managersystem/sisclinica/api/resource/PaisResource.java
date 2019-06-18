package com.managersystem.sisclinica.api.resource;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
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
import com.managersystem.sisclinica.api.exception.SisclinicaExceptionHandler.Mensagem;
import com.managersystem.sisclinica.api.exception.TokenExpiradoException;
import com.managersystem.sisclinica.api.exception.TokenInexistenteException;
import com.managersystem.sisclinica.api.exception.TokenNaoAdministradorException;
import com.managersystem.sisclinica.api.model.Pais;
import com.managersystem.sisclinica.api.repository.filtro.PaisFiltro;
import com.managersystem.sisclinica.api.repository.filtro.PaisFiltroExcluir;
import com.managersystem.sisclinica.api.repository.filtro.TokenFiltro;
import com.managersystem.sisclinica.api.repository.pais.PaisRepository;
import com.managersystem.sisclinica.api.service.PaisService;

@RestController
@RequestMapping("/pais")
public class PaisResource {

	@Autowired
	private PaisRepository paisRepository;
	
	@Autowired
	private PaisService paisService;
	
	@Autowired
	private MessageSource messageSource;

	@GetMapping("/pesquisar")
	public List<Pais> pesquisar(PaisFiltro filtro) {
		List<Pais> lista = null;
		try {
			lista = paisRepository.findByNomeContaining(filtro.getNome());			
		} catch (TokenInexistenteException e) {
			
		}
		return lista;
	}
	
	@GetMapping("/listar")
	public ResponseEntity<List<Pais>> listar(TokenFiltro filtro) { 
		List<Pais> lista = null;
		try {
			lista = paisService.listar(filtro);
		} catch (TokenInexistenteException e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		} catch (TokenExpiradoException e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
		return ResponseEntity.status(HttpStatus.OK).body(lista);
	}
	
	@PostMapping("/salvar")
	public ResponseEntity<Pais> salvar(TokenFiltro filtro, @Valid @RequestBody Pais pais, HttpServletResponse response) {
		Pais paisSalvo = null;
		if (pais.getId() > 0) {
			try {				
				paisSalvo = paisService.atualizar(filtro.getToken(), pais);
			} catch (TokenInexistenteException e) {
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
			} catch (TokenExpiradoException e) {
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
			} catch (TokenNaoAdministradorException e) {
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
			}
			return ResponseEntity.status(HttpStatus.OK).body(paisSalvo);
		} else {
			try {				
				paisSalvo = paisService.salvar(pais);
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
	public ResponseEntity<Object> remover(PaisFiltroExcluir filtro) {
		Optional<Pais> pais = paisRepository.findById(filtro.getId());
		if (!pais.isPresent()) {
			throw new PaisInexistenteException();
		}
		paisRepository.deleteById(filtro.getId());
		return ResponseEntity.noContent().build();
	}

	@ExceptionHandler({ PaisInexistenteException.class })
	public ResponseEntity<Object> handlePaisInexistenteException(PaisInexistenteException ex) {
		String mensagem = messageSource.getMessage("pais.inexistente", null, LocaleContextHolder.getLocale());
		List<Mensagem> erros = Arrays.asList(new Mensagem(mensagem));
		return ResponseEntity.badRequest().body(erros);
	}
	
	@ExceptionHandler({ PaisJaExistenteException.class })
	public ResponseEntity<Object> handlePaisInexistenteException(PaisJaExistenteException ex) {
		String mensagem = messageSource.getMessage("pais.jaexistente", null, LocaleContextHolder.getLocale());
		List<Mensagem> erros = Arrays.asList(new Mensagem(mensagem));
		return ResponseEntity.badRequest().body(erros);
	}
	
	//TokenInexistenteException
	
}
