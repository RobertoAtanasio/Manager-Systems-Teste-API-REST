package com.managersystem.sisclinica.api.service;

import java.util.List;

import com.managersystem.sisclinica.api.model.Pais;
import com.managersystem.sisclinica.api.repository.filtro.PaisFiltro;
import com.managersystem.sisclinica.api.repository.filtro.PaisFiltroExcluir;

public interface validarPais {

	Pais salvar(String tokenCodigo, Pais pais);

	Pais atualizar(String tokenCodigo, Pais pais);

	List<Pais> listar(String tokenCodigo);

	List<Pais> pesquisar(String tokenCodigo, PaisFiltro filtro);

	Pais excluir(PaisFiltroExcluir filtroExcluir);

}