package com.managersystem.sisclinica.api.repository.pais;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

import com.managersystem.sisclinica.api.model.Pais;
import com.managersystem.sisclinica.api.repository.Ordenacao;
import com.managersystem.sisclinica.api.repository.Paginacao;
import com.managersystem.sisclinica.api.repository.filtro.PaisFiltro;

public class PaisRepositoryImpl implements PaisRepositoryQuery {

	@PersistenceContext
	private EntityManager manager;
	
	@Autowired
	Paginacao paginacao;
	
	@Autowired
	Ordenacao ordenacao;
	
	@Override
	public Page<Pais> filtrar(PaisFiltro paisFiltro, Pageable pageable) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Pais> criteriaQuery = builder.createQuery(Pais.class);
		Root<Pais> root = criteriaQuery.from(Pais.class);
		
		Predicate[] predicates = criarRestricoes(paisFiltro, builder, root);
		
		criteriaQuery.where(predicates);
		
		ordenacao.ordenacao(pageable, builder, criteriaQuery, root);
		
		TypedQuery<Pais> query = manager.createQuery(criteriaQuery);
		
		paginacao.paginacao(pageable, query);
		
		Page<Pais> pagina = new PageImpl<>(query.getResultList(), pageable, total(paisFiltro));
		
		manager.close();
		
		return pagina;	
	}

	private Long total(PaisFiltro paisFiltro) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
		Root<Pais> root = criteriaQuery.from(Pais.class);
		
		Predicate[] predicates = criarRestricoes(paisFiltro, builder, root);
		criteriaQuery.where(predicates);
		criteriaQuery.select(builder.count(root));
		
		Long quantidade = manager.createQuery(criteriaQuery).getSingleResult();
		
		manager.close();
		
		return quantidade;
	}
	
	private Predicate[] criarRestricoes(com.managersystem.sisclinica.api.repository.filtro.PaisFiltro paisFiltro, CriteriaBuilder builder, Root<Pais> root) {
		List<Predicate> listaPredicate = new ArrayList<>();
		
		if (!StringUtils.isEmpty(paisFiltro.getNome())) {
			listaPredicate.add(builder.like(builder.lower(root.get("nome")), "%" + paisFiltro.getNome() + "%"));
		}
		
		Predicate[] predicates = listaPredicate.toArray(new Predicate[listaPredicate.size()]);
		return predicates;
	}
	
}
