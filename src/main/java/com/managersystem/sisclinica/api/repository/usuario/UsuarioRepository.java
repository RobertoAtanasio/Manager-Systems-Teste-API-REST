package com.managersystem.sisclinica.api.repository.usuario;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.managersystem.sisclinica.api.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>, UsuarioRepositoryQuery {
	
	public Optional<Usuario> findByLogin(String login);

	public Optional<Usuario> findByLoginAndSenha(String login, String senha);
	
}
