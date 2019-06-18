package com.managersystem.sisclinica.api.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.managersystem.sisclinica.api.exception.PaisInexistenteException;
import com.managersystem.sisclinica.api.exception.PaisJaExistenteException;
import com.managersystem.sisclinica.api.exception.TokenExpiradoException;
import com.managersystem.sisclinica.api.exception.TokenInexistenteException;
import com.managersystem.sisclinica.api.exception.TokenNaoAdministradorException;
import com.managersystem.sisclinica.api.model.Pais;
import com.managersystem.sisclinica.api.model.Token;
import com.managersystem.sisclinica.api.repository.filtro.TokenFiltro;
import com.managersystem.sisclinica.api.repository.pais.PaisRepository;
import com.managersystem.sisclinica.api.repository.token.TokenRepository;

@Service
public class PaisService {

	@Autowired
	private PaisRepository paisRepository;
	
	@Autowired
	private TokenRepository tokenRepository;
	
	public Pais salvar(@Valid Pais pais) {
		Optional<Pais> paisExistente = paisRepository.findByNome(pais.getNome());
		if (paisExistente.isPresent()) {
			throw new PaisJaExistenteException();
		}
		return paisRepository.saveAndFlush(pais);
	}
	
	public Pais atualizar(String tokenCodigo, Pais pais) {
		Optional<Token> token = tokenRepository.findByToken(tokenCodigo);
		if (!token.isPresent()) {
			throw new TokenInexistenteException();
		}
		LocalDateTime localDateTime = LocalDateTime.now();
		if (localDateTime.isAfter(token.get().getExpiracao())) {
			throw new TokenExpiradoException();
		}
		Optional<Pais> paisSalvo = paisRepository.findById(pais.getId());
		if (!paisSalvo.isPresent()) {
			throw new PaisInexistenteException();
		}
		if (!token.get().getAdministrador()) {
			throw new TokenNaoAdministradorException();
		}
		return paisRepository.saveAndFlush(pais);
	}

	public List<Pais> listar(TokenFiltro filtro) {
		Optional<Token> token = tokenRepository.findByToken(filtro.getToken());
		if (!token.isPresent()) {
			throw new TokenInexistenteException();
		}
		LocalDateTime localDateTime = LocalDateTime.now();
		if (localDateTime.isAfter(token.get().getExpiracao())) {
			throw new TokenExpiradoException();
		}
		return paisRepository.findAll();
	}
}
