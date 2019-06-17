package com.managersystem.sisclinica.api.config.token;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.managersystem.sisclinica.api.model.PermissoesUsuario;
import com.managersystem.sisclinica.api.model.Usuario;
import com.managersystem.sisclinica.api.repository.usuario.UsuarioRepository;

@Service
public class AppUserDetailsService implements UserDetailsService {

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Override
	public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
		Optional<Usuario> usuarioOptional = usuarioRepository.findByLogin(login);
		System.out.println(">>>>>>>>>>>>> Login: " + login);
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		String senhaCrypt = null;
		if (usuarioOptional.isPresent()) {
			senhaCrypt = encoder.encode(usuarioOptional.get().getSenha());
			usuarioOptional.get().setSenha(senhaCrypt);
		}
		Usuario usuario = usuarioOptional.orElseThrow(() -> new UsernameNotFoundException("Usuário e/ou senha incorretos"));
		return new UsuarioSistema(usuario, getPermissoes(usuario));
	}

	private Collection<? extends GrantedAuthority> getPermissoes(Usuario usuario) {
		Set<SimpleGrantedAuthority> authorities = new HashSet<>();
		
		List<PermissoesUsuario> lista = usuarioRepository.listarPermissoesUsuario(usuario.getId());
		
		lista.forEach(p -> authorities.add(
				new SimpleGrantedAuthority(p.getDescricao().toUpperCase())
				));
		return authorities;
	}
	
}
