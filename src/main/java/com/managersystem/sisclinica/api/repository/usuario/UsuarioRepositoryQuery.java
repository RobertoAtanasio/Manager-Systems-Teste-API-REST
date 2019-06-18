package com.managersystem.sisclinica.api.repository.usuario;

import com.managersystem.sisclinica.api.model.Usuario;

public interface UsuarioRepositoryQuery {
	
	public Usuario acessarUsuario(String login, String senha);

}
