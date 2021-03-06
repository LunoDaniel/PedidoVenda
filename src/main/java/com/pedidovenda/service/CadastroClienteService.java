package com.pedidovenda.service;

import java.io.Serializable;

import javax.inject.Inject;

import com.pedidovenda.model.Cliente;
import com.pedidovenda.repository.ClienteRepository;
import com.pedidovenda.util.jpa.Transactional;

public class CadastroClienteService implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private ClienteRepository clientes;

	@Transactional
	public Cliente salvar(Cliente cliente) {
		Cliente clienteExistente = (cliente.getId() != null) ? clientes.getById(cliente.getId()) : null;

		try {
			if (clienteExistente != null && !clienteExistente.equals(cliente)) {
				throw new NegocioException("Já existe um cliente com esses dados.");
			}

			setEnderecosCliente(cliente);
			return this.clientes.guardar(cliente);
		} catch (NegocioException e) {
			throw new NegocioException("Erro ao cadastrar o Cliente");
		}
	}
	
	public void setEnderecosCliente(Cliente cliente){
		cliente.getEndereco().forEach(endereco->{
			endereco.setCliente(cliente);
		});
	}
}