package com.managersystem.sisclinica.api.model;

public class PermissoesUsuario {

	private String descricao;
	
	public PermissoesUsuario() {
	}

	public PermissoesUsuario(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
}
