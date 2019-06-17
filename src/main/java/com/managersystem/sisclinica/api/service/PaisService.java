package com.managersystem.sisclinica.api.service;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.managersystem.sisclinica.api.exception.PaisInexistenteException;
import com.managersystem.sisclinica.api.exception.PaisJaExistenteException;
import com.managersystem.sisclinica.api.model.Pais;
import com.managersystem.sisclinica.api.repository.pais.PaisRepository;

@Service
public class PaisService {

	@Autowired
	private PaisRepository paisRepository;
	
	public Pais salvar(@Valid Pais pais) {
		Optional<Pais> paisExistente = paisRepository.findByNome(pais.getNome());
		if (paisExistente.isPresent()) {
			throw new PaisJaExistenteException();
		}
		return paisRepository.saveAndFlush(pais);
	}
	
	public Pais atualizar(Long codigo, Pais pais) {
		Optional<Pais> paisSalvo = paisRepository.findById(pais.getId());
		if (!paisSalvo.isPresent()) {
			throw new PaisInexistenteException();
		}
		return paisRepository.saveAndFlush(pais);
	}
}
