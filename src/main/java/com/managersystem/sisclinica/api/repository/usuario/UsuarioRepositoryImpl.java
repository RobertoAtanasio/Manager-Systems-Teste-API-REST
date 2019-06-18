package com.managersystem.sisclinica.api.repository.usuario;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import com.managersystem.sisclinica.api.model.Usuario;

public class UsuarioRepositoryImpl implements UsuarioRepositoryQuery {

	@PersistenceContext
	private EntityManager manager;

	@Override
	public Usuario acessarUsuario(String login, String senha) {

		CriteriaBuilder criteriaBuilder = manager.getCriteriaBuilder();
		
		CriteriaQuery<Usuario> criteriaQuery = criteriaBuilder.createQuery(Usuario.class);
		
		Root<Usuario> root = criteriaQuery.from(Usuario.class);
		
		criteriaQuery.where(
				criteriaBuilder.equal(root.get("login"), login),
				criteriaBuilder.equal(root.get("senha"), senha));
		
		TypedQuery<Usuario> typedQuery = manager.createQuery(criteriaQuery);
		
		Usuario usuario = typedQuery.getSingleResult();
		
		return usuario;
	}

}
