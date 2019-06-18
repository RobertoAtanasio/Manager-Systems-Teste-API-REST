package com.managersystem.sisclinica.api.resource;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.managersystem.sisclinica.api.model.UsuarioAutenticado;
import com.managersystem.sisclinica.api.model.UsuarioPesquisa;
import com.managersystem.sisclinica.api.repository.filtro.TokenFiltro;
import com.managersystem.sisclinica.api.service.UsuarioService;

@RestController
@RequestMapping("/usuario")
public class UsuarioResource {
	
	@Autowired
	private UsuarioService usuarioService;
	
	@PostMapping("/autenticar")
	public ResponseEntity<UsuarioAutenticado> autenticar(UsuarioPesquisa usuario) {
		UsuarioAutenticado usuarioAutenticado = usuarioService.autenticar(usuario);
		if (!usuarioAutenticado.getAutenticado()) {	
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(usuarioAutenticado);
		}
		return ResponseEntity.status(HttpStatus.OK).body(usuarioAutenticado);
	}
	
	@GetMapping("/renovar-ticket")
	public ResponseEntity<Object> renovarTicket(TokenFiltro tokenFiltro, HttpServletResponse response) {
		Boolean status = usuarioService.renovarTicket(tokenFiltro.getToken());
		return ResponseEntity.status(HttpStatus.OK).body(status);
	}
	
}
