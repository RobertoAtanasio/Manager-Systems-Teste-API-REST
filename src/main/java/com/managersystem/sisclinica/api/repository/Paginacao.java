package com.managersystem.sisclinica.api.repository;

import javax.persistence.TypedQuery;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
public class Paginacao {

	public void paginacao(Pageable pageable, TypedQuery<?> query) {
		int paginaAtual = pageable.getPageNumber();
		int totalRegistrosPorPagina = pageable.getPageSize();
		int primeiroRegistro = paginaAtual * totalRegistrosPorPagina;
		
		query.setFirstResult(primeiroRegistro);
		query.setMaxResults(totalRegistrosPorPagina);		
	}
}
