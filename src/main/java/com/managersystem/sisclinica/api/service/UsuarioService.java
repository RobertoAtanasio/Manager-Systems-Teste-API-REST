package com.managersystem.sisclinica.api.service;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.managersystem.sisclinica.api.exception.UsuarioJaExistenteException;
import com.managersystem.sisclinica.api.model.Usuario;
import com.managersystem.sisclinica.api.repository.usuario.UsuarioRepository;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	public Usuario salvar(@Valid Usuario usuario) {
		Optional<Usuario> usuarioExistente = usuarioRepository.findByLogin(usuario.getLogin());
		if (usuarioExistente.isPresent()) {
			throw new UsuarioJaExistenteException();
		}
		return usuarioRepository.saveAndFlush(usuario);
	}

}
