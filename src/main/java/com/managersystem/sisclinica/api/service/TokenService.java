package com.managersystem.sisclinica.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.managersystem.sisclinica.api.model.Token;
import com.managersystem.sisclinica.api.repository.token.TokenRepository;

@Service
public class TokenService {

	@Autowired
	private TokenRepository tokenRepository;
	
	public Token salvar(Token token) {
		return tokenRepository.saveAndFlush(token);
	}
}
