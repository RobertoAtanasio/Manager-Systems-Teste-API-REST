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

	public UsuarioAutenticado autenticar(String login, String senha) {
		
		UsuarioAutenticado usuarioAutenticado = new UsuarioAutenticado();
		usuarioAutenticado.setLogin(login);

		Optional<Usuario> usuarioExistente = usuarioRepository.findByLogin(login);
		if (!usuarioExistente.isPresent()) {
			throw new UsuarioInexistenteException();
		}
		
		usuarioExistente = usuarioRepository.findByLoginAndSenha(login, senha);
		if (!usuarioExistente.isPresent()) {
			throw new UsuarioSenhaInvalidaException();
		}
		
		LocalDateTime localDateTime = LocalDateTime.now();
		localDateTime = localDateTime.plusMinutes(5);

		Optional<Token> token = tokenRepository.findByLogin(login);
		if (token.isPresent()) {			
			usuarioAutenticado.setNome(usuarioExistente.get().getNome());
			usuarioAutenticado.setToken(GerarToken.gerarToken());
			usuarioAutenticado.setAutenticado(true);
			usuarioAutenticado.setAdministrador(usuarioExistente.get().getAdministrador());
			
			token.get().setToken(usuarioAutenticado.getToken());
			token.get().setExpiracao(localDateTime);
			token.get().setAdministrador(usuarioAutenticado.getAdministrador());
			tokenService.salvar(token.get());
		} else {

			usuarioAutenticado.setNome(usuarioExistente.get().getNome());
			usuarioAutenticado.setToken(GerarToken.gerarToken());
			usuarioAutenticado.setAutenticado(true);
			usuarioAutenticado.setAdministrador(usuarioExistente.get().getAdministrador());
			
			Token tokenNovo = new Token();
			tokenNovo.setToken(usuarioAutenticado.getToken());
			tokenNovo.setLogin(login);
			tokenNovo.setExpiracao(localDateTime);
			tokenNovo.setAdministrador(usuarioAutenticado.getAdministrador());
			tokenService.salvar(tokenNovo);		
		}
		return usuarioAutenticado;
	}

	public Boolean renovarTicket(String tokenCodigo) {
		Optional<Token> token = tokenRepository.findByToken(tokenCodigo);
		if (!token.isPresent()) {
			return false;
		}
		LocalDateTime localDateTime = LocalDateTime.now();
		localDateTime = localDateTime.plusMinutes(5);
		token.get().setExpiracao(localDateTime);
		tokenService.salvar(token.get());
		return true;
	}
}
