package com.managersystem.sisclinica.api.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.managersystem.sisclinica.api.exception.PaisInexistenteException;
import com.managersystem.sisclinica.api.exception.PaisJaExistenteException;
import com.managersystem.sisclinica.api.exception.TokenExpiradoException;
import com.managersystem.sisclinica.api.exception.TokenInexistenteException;
import com.managersystem.sisclinica.api.exception.TokenNaoAdministradorException;
import com.managersystem.sisclinica.api.model.Pais;
import com.managersystem.sisclinica.api.model.Token;
import com.managersystem.sisclinica.api.repository.pais.PaisRepository;
import com.managersystem.sisclinica.api.repository.token.TokenRepository;

@Service
public class PaisService  {

	@Autowired
	private PaisRepository paisRepository;
	
	@Autowired
	private TokenRepository tokenRepository;
	
	
	public Pais salvar(String tokenCodigo, Pais pais) {
		Token token = validarToken(tokenCodigo);
		Optional<Pais> paisExistente = paisRepository.findByNome(pais.getNome());
		if (paisExistente.isPresent()) {
			throw new PaisJaExistenteException();
		}
		if (!token.getAdministrador()) {
			throw new TokenNaoAdministradorException();
		}
		return paisRepository.saveAndFlush(pais);
	}
	
	
	public Pais atualizar(String tokenCodigo, Pais pais) {
		Token token = validarToken(tokenCodigo);
		Optional<Pais> paisSalvo = paisRepository.findById(pais.getId());
		if (!paisSalvo.isPresent()) {
			throw new PaisInexistenteException();
		}
		if (!token.getAdministrador()) {
			throw new TokenNaoAdministradorException();
		}
		return paisRepository.saveAndFlush(pais);
	}

	
	public List<Pais> listar(String tokenCodigo) {
		validarToken(tokenCodigo);
		return paisRepository.findAll();
	}

	
	public List<Pais> pesquisar(String tokenCodigo, String nome) {
		validarToken(tokenCodigo);
		return paisRepository.findByNomeContaining(nome);	
	}

	
	public Pais excluir(String tokenCodigo, Long id) {
		Token token = validarToken(tokenCodigo);
		
		Optional<Pais> pais = paisRepository.findById(id);
		if (!pais.isPresent()) {
			throw new PaisInexistenteException();
		}
		if (!token.getAdministrador()) {
			throw new TokenNaoAdministradorException();
		}
		paisRepository.deleteById(id);
		return null;
	}
	
	private Token validarToken(String tokenCodigo) {
		Optional<Token> token = tokenRepository.findByToken(tokenCodigo);
		if (!token.isPresent()) {
			throw new TokenInexistenteException();
		}
		LocalDateTime localDateTime = LocalDateTime.now();
		if (localDateTime.isAfter(token.get().getExpiracao())) {
			throw new TokenExpiradoException();
		}
		return token.get();
	}

}
