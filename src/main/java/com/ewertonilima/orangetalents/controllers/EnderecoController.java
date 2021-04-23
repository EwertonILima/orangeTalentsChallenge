package com.ewertonilima.orangetalents.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ewertonilima.orangetalents.entities.Endereco;
import com.ewertonilima.orangetalents.services.EnderecoService;

@RestController
@RequestMapping(value = "/enderecos")
public class EnderecoController {

	@Autowired
	EnderecoService enderecoService;

	@PostMapping
	public ResponseEntity<Void> insert(@RequestBody Endereco endereco) {
		endereco = enderecoService.insert(endereco);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

}
