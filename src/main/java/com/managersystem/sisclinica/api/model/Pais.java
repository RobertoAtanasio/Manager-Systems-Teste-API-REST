package com.managersystem.sisclinica.api.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModelProperty;

@Entity
@Table(name = "pais")
public class Pais {

	@JsonIgnore
	@Transient
	final String URL_BASE = "http://localhost:8080/sisclinica-api/rest/pais/";
	
	@JsonIgnore
	@ApiModelProperty(hidden = true)
	@Transient
	private List<Link> links;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@NotNull
	@Size(min = 3, max = 20)
	@Column(name = "nome")
	private String nome;
	
	@NotNull
	@Size(min = 2, max = 10)
	@Column(name = "sigla")
	private String sigla;
	
	@Column(name = "gentilico")
	private String gentilico;

	public Pais() {
		this(-1l, "", "", "");
	}
	
	public Pais(Long id, @NotNull @Size(min = 3, max = 20) String nome, @NotNull @Size(min = 2, max = 10) String sigla,
			String gentilico) {
		super();
		this.id = id;
		this.nome = nome;
		this.sigla = sigla;
		this.gentilico = gentilico;
		
		links = new ArrayList<Link>();
		links.add(new Link("consulta", URL_BASE + getId()));
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
		links = new ArrayList<Link>();
		links.add(new Link("consulta", URL_BASE + getId()));
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSigla() {
		return sigla;
	}

	public void setSigla(String sigla) {
		this.sigla = sigla;
	}

	public String getGentilico() {
		return gentilico;
	}

	public void setGentilico(String gentilico) {
		this.gentilico = gentilico;
	}

	public List<Link> getLinks() {
		return links;
	}

	public void setLinks(List<Link> links) {
		this.links = links;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pais other = (Pais) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}
