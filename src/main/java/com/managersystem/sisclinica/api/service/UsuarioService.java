package com.managersystem.sisclinica.api.service;

import java.time.LocalDateTime;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.managersystem.sisclinica.api.exception.UsuarioInexistenteException;
import com.managersystem.sisclinica.api.exception.UsuarioSenhaInvalidaException;
import com.managersystem.sisclinica.api.model.GerarToken;
import com.managersystem.sisclinica.api.model.Pais;
import com.managersystem.sisclinica.api.model.Token;
import com.managersystem.sisclinica.api.model.Usuario;
import com.managersystem.sisclinica.api.model.UsuarioAutenticado;
import com.managersystem.sisclinica.api.model.UsuarioPesquisa;
import com.managersystem.sisclinica.api.repository.pais.PaisRepository;
import com.managersystem.sisclinica.api.repository.token.TokenRepository;
import com.managersystem.sisclinica.api.repository.usuario.UsuarioRepository;

@Service
public class UsuarioService {
	
	@Autowired
	private TokenService tokenService;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private PaisRepository paisRepository;
	
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
		
		cargaBanco();
		UsuarioAutenticado usuarioAutenticado = new UsuarioAutenticado();
		usuarioAutenticado.setLogin(usuario.getLogin());

		Optional<Usuario> usuarioExistente = usuarioRepository.findByLogin(usuario.getLogin());
		if (!usuarioExistente.isPresent()) {
			throw new UsuarioInexistenteException();
		}
		
		usuarioExistente = usuarioRepository.findByLoginAndSenha(usuario.getLogin(), usuario.getSenha());
		if (!usuarioExistente.isPresent()) {
			throw new UsuarioSenhaInvalidaException();
		}
		
		LocalDateTime localDateTime = LocalDateTime.now();
		localDateTime = localDateTime.plusMinutes(5);

		Optional<Token> token = tokenRepository.findByLogin(usuario.getLogin());
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
			tokenNovo.setLogin(usuario.getLogin());
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
	
	public void cargaBanco () {
		
		Optional<Usuario> usuarioExistente = null;
		
		usuarioExistente = usuarioRepository.findByLogin("admin");
		if (!usuarioExistente.isPresent()) {
			Usuario usuario = new Usuario();
			usuario.setLogin("admin");
			usuario.setSenha("suporte");
			usuario.setNome("Gestor");
			usuario.setAdministrador(true);			
			usuarioRepository.saveAndFlush(usuario);
		}		

		usuarioRepository.findByLogin("convidado");
		if (!usuarioExistente.isPresent()) {
			Usuario usuario = new Usuario();
			usuario.setLogin("convidado");
			usuario.setSenha("manager");
			usuario.setNome("Usuário convidado");
			usuario.setAdministrador(false);			
			usuarioRepository.saveAndFlush(usuario);
		}
		
		Optional<Pais> paisExistente = null;
		
		paisExistente = paisRepository.findByNome("Brasil");
		if (!paisExistente.isPresent()) {
			Pais pais = new Pais();
			pais.setNome("Brasil");
			pais.setSigla("BRA");
			pais.setGentilico("Brasileiro");
			paisRepository.saveAndFlush(pais);
		}
		
		paisExistente = paisRepository.findByNome("Argentina");
		if (!paisExistente.isPresent()) {
			Pais pais = new Pais();
			pais.setNome("Argentina");
			pais.setSigla("ARG");
			pais.setGentilico("Argentino");
			paisRepository.saveAndFlush(pais);
		}
		
		paisExistente = paisRepository.findByNome("Alemanha");
		if (!paisExistente.isPresent()) {
			Pais pais = new Pais();
			pais.setNome("Alemanha");
			pais.setSigla("ALE");
			pais.setGentilico("Alemão");
			paisRepository.saveAndFlush(pais);
		}
	}
}
