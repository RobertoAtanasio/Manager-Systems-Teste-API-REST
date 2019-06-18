package com.managersystem.sisclinica.api.repository.pais;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.managersystem.sisclinica.api.model.Pais;

public interface PaisRepository extends JpaRepository<Pais, Long>, PaisRepositoryQuery {
	
	public Optional<Pais> findByNome(String nome);
	
	public List<Pais> findByNomeContaining(String nome);
	
}
