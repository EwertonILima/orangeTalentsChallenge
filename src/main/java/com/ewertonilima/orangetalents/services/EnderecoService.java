package com.ewertonilima.orangetalents.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ewertonilima.orangetalents.entities.Endereco;
import com.ewertonilima.orangetalents.repositories.EnderecoRepository;

@Service
public class EnderecoService {

	@Autowired
	EnderecoRepository enderecoRepository;

	@Transactional
	public Endereco insert(Endereco endereco) {
		endereco.setId(null);
		endereco = enderecoRepository.save(endereco);
		return endereco;
	}

}
