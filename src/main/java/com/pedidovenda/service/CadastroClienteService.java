package com.pedidovenda.service;

import com.pedidovenda.exceptions.NegocioException;
import com.pedidovenda.model.Cliente;
import com.pedidovenda.repository.data.ClienteDataRepository;
import jakarta.transaction.Transactional;
import jakarta.inject.Inject;

import java.io.Serializable;

public class CadastroClienteService implements Serializable {

	@Inject
	private ClienteDataRepository clientes;

	@Transactional
	public Cliente salvar(Cliente cliente) {
		Cliente clienteExistente = (cliente.getId() != null) ? clientes.findByEmail(cliente.getEmail()) : null;

		try {
			if (clienteExistente != null && !clienteExistente.equals(cliente)) {
				throw new NegocioException("Já existe um cliente com esses dados.");
			}

			setEnderecosCliente(cliente);
			return this.clientes.save(cliente);
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