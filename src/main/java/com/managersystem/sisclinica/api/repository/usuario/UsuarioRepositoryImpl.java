package com.managersystem.sisclinica.api.repository.usuario;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.managersystem.sisclinica.api.model.PermissoesUsuario;

public class UsuarioRepositoryImpl implements UsuarioRepositoryQuery {

	@PersistenceContext
	private EntityManager manager;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<PermissoesUsuario> listarPermissoesUsuario(Long id) {		
		List<PermissoesUsuario> permissoes = manager.createNamedQuery("Usuario.listarPermissoes").getResultList();
		return permissoes;
	}

}
