package com.managersystem.sisclinica.api.resource;

import java.util.Arrays;
import java.util.List;

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

import com.managersystem.sisclinica.api.exception.UsuarioJaExistenteException;
import com.managersystem.sisclinica.api.exception.SisclinicaExceptionHandler.Mensagem;
import com.managersystem.sisclinica.api.model.Usuario;
import com.managersystem.sisclinica.api.repository.usuario.UsuarioRepository;
import com.managersystem.sisclinica.api.service.UsuarioService;

@RestController
@RequestMapping("/usuario")
public class UsuarioResource {

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private MessageSource messageSource;
	
	@GetMapping("/listar")
	public List<Usuario> listar() { 
		return usuarioRepository.findAll();
	}
	
	@PostMapping("/salvar")
	public ResponseEntity<Usuario> salvar(@Valid @RequestBody Usuario usuario, HttpServletResponse response) {
		Usuario usuarioSalvo = null;
		usuarioSalvo = usuarioService.salvar(usuario);
		return ResponseEntity.status(HttpStatus.CREATED).body(usuarioSalvo);
	}
	
	@ExceptionHandler({ UsuarioJaExistenteException.class })
	public ResponseEntity<Object> handleUsuarioJaExistenteException(UsuarioJaExistenteException ex) {
		String mensagem = messageSource.getMessage("usuario.jaexistente", null, LocaleContextHolder.getLocale());
		List<Mensagem> erros = Arrays.asList(new Mensagem(mensagem));
		return ResponseEntity.badRequest().body(erros);
	}
}
