package com.managersystem.sisclinica.api.service;

import java.time.LocalDateTime;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.managersystem.sisclinica.api.exception.UsuarioInexistenteException;
import com.managersystem.sisclinica.api.exception.UsuarioSenhaInvalidaException;
import com.managersystem.sisclinica.api.model.GerarToken;
import com.managersystem.sisclinica.api.model.Token;
import com.managersystem.sisclinica.api.model.Usuario;
import com.managersystem.sisclinica.api.model.UsuarioAutenticado;
import com.managersystem.sisclinica.api.model.UsuarioPesquisa;
import com.managersystem.sisclinica.api.repository.token.TokenRepository;
import com.managersystem.sisclinica.api.repository.usuario.UsuarioRepository;

@Service
public class UsuarioService {
	
	@Autowired
	private TokenService tokenService;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private TokenRepository tokenRepository;
	
	public Usuario salvar(@Valid Usuario usuario) {
		Optional<Usuario> usuarioExistente = usuarioRepository.findByLogin(usuario.getLogin());
		if (!usuarioExistente.isPresent()) {
			throw new UsuarioInexistenteException();
		}
		usuarioExistente = usuarioRepository.findByLoginAndSenha(usuario.getLogin(), usuario.getSenha());
		if (!usuarioExistente.isPresent()) {
			throw new UsuarioSenhaInvalidaException();
		}
		return usuarioRepository.saveAndFlush(usuario);
	}

	public UsuarioAutenticado autenticar(UsuarioPesquisa usuario) {
		
		UsuarioAutenticado usuarioRetorno = new UsuarioAutenticado();
		usuarioRetorno.setLogin(usuario.getLogin());

		Optional<Usuario> usuarioExistente = usuarioRepository.findByLogin(usuario.getLogin());
		if (!usuarioExistente.isPresent()) {
			usuarioRetorno.setNome("");
			usuarioRetorno.setToken("");
			usuarioRetorno.setAutenticado(false);
			usuarioRetorno.setAdministrador(false);
		} else {			
			LocalDateTime localDateTime = LocalDateTime.now();
			localDateTime = localDateTime.plusMinutes(5);
			
			usuarioRetorno.setNome(usuarioExistente.get().getNome());
			usuarioRetorno.setToken(GerarToken.gerarToken());
			usuarioRetorno.setAutenticado(true);
			usuarioRetorno.setAdministrador(usuarioExistente.get().getAdministrador());
			
			Token token = new Token();
			token.setToken(usuarioRetorno.getToken());
			token.setLogin(usuario.getLogin());
			token.setExpiracao(localDateTime);
			token.setAdministrador(usuarioRetorno.getAdministrador());
			tokenService.salvar(token);
		}
		
		return usuarioRetorno;
	}

	public Boolean renovarTicket(String tokenCodigo) {
		Optional<Token> token = tokenRepository.findByToken(tokenCodigo);
		if (!token.isPresent()) {
			return false;
		}
		LocalDateTime localDateTime = token.get().getExpiracao();
		localDateTime = localDateTime.plusMinutes(5);
		token.get().setExpiracao(localDateTime);
		tokenService.salvar(token.get());
		return true;
	}
}
