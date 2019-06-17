package com.managersystem.sisclinica.api.repository.usuario;

import java.util.List;

import com.managersystem.sisclinica.api.model.PermissoesUsuario;

public interface UsuarioRepositoryQuery {
	
	List<PermissoesUsuario> listarPermissoesUsuario(Long id);

}
