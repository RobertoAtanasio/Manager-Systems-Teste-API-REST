package com.managersystem.sisclinica.api.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.managersystem.sisclinica.api.model.UsuarioAutenticado;
import com.managersystem.sisclinica.api.service.UsuarioService;

@RestController
@RequestMapping("/usuario")
public class UsuarioResource {
	
	@Autowired
	private UsuarioService usuarioService;
	
	@PostMapping("/autenticar")
	public ResponseEntity<UsuarioAutenticado> autenticar(@RequestParam String login, @RequestParam String senha) {
		UsuarioAutenticado usuarioAutenticado = usuarioService.autenticar(login, senha);			
		return ResponseEntity.status(HttpStatus.OK).body(usuarioAutenticado);
	}
	
	@GetMapping("/renovar-ticket")
	public ResponseEntity<Object> renovarTicket(@RequestParam String token) {
		Boolean status = usuarioService.renovarTicket(token);
		return ResponseEntity.status(HttpStatus.OK).body(status);
	}
	
}
