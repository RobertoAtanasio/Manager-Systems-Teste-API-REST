package com.managersystem.sisclinica.api.repository.token;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.managersystem.sisclinica.api.model.Token;

public interface TokenRepository extends JpaRepository<Token, Long> {
	
	public Optional<Token> findByLogin(String login);

	public Optional<Token> findByToken(String token);
}
