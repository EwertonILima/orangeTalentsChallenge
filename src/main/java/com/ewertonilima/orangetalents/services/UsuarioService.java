package com.ewertonilima.orangetalents.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ewertonilima.orangetalents.entities.Usuario;
import com.ewertonilima.orangetalents.repositories.UsuarioRepository;
import com.ewertonilima.orangetalents.services.exception.ObjectNotFoundException;

@Service
public class UsuarioService {

	@Autowired
	UsuarioRepository usuarioRepository;

	public Usuario findById(Long id) {
		Optional<Usuario> usuario = usuarioRepository.findById(id);
		return usuario.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Usuario.class.getName()));
	}

	@Transactional
	public Usuario insert(Usuario usuario) {
		usuario.setId(null);
		usuario = usuarioRepository.save(usuario);
		return usuario;
	}

}
