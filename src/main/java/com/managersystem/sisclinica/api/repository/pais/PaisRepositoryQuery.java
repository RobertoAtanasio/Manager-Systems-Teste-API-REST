package com.managersystem.sisclinica.api.repository.pais;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.managersystem.sisclinica.api.model.Pais;
import com.managersystem.sisclinica.api.repository.filtro.PaisFiltro;

public interface PaisRepositoryQuery {	
	public Page<Pais> filtrar(PaisFiltro paisFiltro, Pageable pageable);
}
