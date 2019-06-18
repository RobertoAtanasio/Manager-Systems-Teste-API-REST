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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.managersystem.sisclinica.api.exception.PaisInexistenteException;
import com.managersystem.sisclinica.api.exception.PaisJaExistenteException;
import com.managersystem.sisclinica.api.exception.SisclinicaExceptionHandler.Mensagem;
import com.managersystem.sisclinica.api.model.Pais;
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

	@GetMapping("/pesquisar/{nome}")
	public List<Pais> pesquisar(@PathVariable String nome) {
		return paisRepository.findByNomeContaining(nome);
	}
	
	@GetMapping("/listar")
	public List<Pais> listar() { 
		return paisRepository.findAll();
	}

	@GetMapping("/pesquisarPorId/{id}")
	public ResponseEntity<Optional<Pais>> buscarPorId(@PathVariable Long id) {
		Optional<Pais> pais = paisRepository.findById(id);
		if (!pais.isPresent()) {
			throw new PaisInexistenteException();
		}
		return ResponseEntity.status(HttpStatus.OK).body(pais);
	}
	
	@PostMapping("/salvar")
	public ResponseEntity<Pais> salvar(@Valid @RequestBody Pais pais, HttpServletResponse response) {
		Pais paisSalvo = paisService.salvar(pais);
		return ResponseEntity.status(HttpStatus.CREATED).body(paisSalvo);
	}
	
	
	@PutMapping("/atualizar/{id}")
//	@PreAuthorize("hasAuthority('ROLE_CADASTRAR_PESSOA') and #oauth2.hasScope('write')")
	public ResponseEntity<Pais> atualizar(@PathVariable Long id, @Valid @RequestBody Pais pais) {
		Pais paisSalvo = paisService.atualizar(id, pais);	
		return ResponseEntity.status(HttpStatus.OK).body(paisSalvo);
	}
	
	@DeleteMapping("/excluir/{id}")
	public ResponseEntity<Object> remover(@PathVariable Long id) {
		Optional<Pais> pais = paisRepository.findById(id);
		if (!pais.isPresent()) {
			throw new PaisInexistenteException();
		}
		paisRepository.deleteById(id);
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
}
