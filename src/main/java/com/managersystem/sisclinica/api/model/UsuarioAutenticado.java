package com.managersystem.sisclinica.api.model;

public class UsuarioAutenticado {

	private String login;
	private String nome;
	private String token;
	private Boolean administrador;
	private Boolean autenticado;
	
	public String getLogin() {
		return login;
	}
	
	public void setLogin(String login) {
		this.login = login;
	}
	
	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public String getToken() {
		return token;
	}
	
	public void setToken(String token) {
		this.token = token;
	}
	
	public Boolean getAdministrador() {
		return administrador;
	}
	
	public void setAdministrador(Boolean administrador) {
		this.administrador = administrador;
	}
	
	public Boolean getAutenticado() {
		return autenticado;
	}
	
	public void setAutenticado(Boolean autenticado) {
		this.autenticado = autenticado;
	}

}
