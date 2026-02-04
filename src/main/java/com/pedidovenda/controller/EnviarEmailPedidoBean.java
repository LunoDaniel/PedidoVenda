package com.pedidovenda.controller;

import com.pedidovenda.model.Pedido;
import com.pedidovenda.model.StatusPedido;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;


//@Named
//@ViewScoped
public class EnviarEmailPedidoBean implements Serializable {

//	@Inject
//	private EmailService emailService;
//
//	@Inject
//	private TemplateService templateService;

	public void enviarEmailConfirmacao(Pedido pedido) {
		try {
			Map<String, Object> variaveis = new HashMap<>();
			variaveis.put("pedido", pedido);
			variaveis.put("cliente", pedido.getCliente());

			String assunto = "Confirmação de Pedido #" + pedido.getId();
//			String corpo = templateService.processarTemplate("confirmacao_pedido", variaveis);
//
//			emailService.enviarEmail(pedido.getCliente().getEmail(), assunto, corpo);
		} catch (Exception e) {
			throw new RuntimeException("Falha ao enviar e-mail de confirmação", e);
		}
	}

	public void enviarEmailAlteracaoStatus(Pedido pedido, StatusPedido statusAnterior) {
		try {
			Map<String, Object> variaveis = new HashMap<>();
			variaveis.put("pedido", pedido);
			variaveis.put("cliente", pedido.getCliente());
			variaveis.put("statusAnterior", statusAnterior);
			variaveis.put("novoStatus", pedido.getStatus());

			String assunto = "Atualização do Pedido #" + pedido.getId();
//			String corpo = templateService.processarTemplate("alteracao_status", variaveis);
//
//			emailService.enviarEmail(pedido.getCliente().getEmail(), assunto, corpo);
		} catch (Exception e) {
			throw new RuntimeException("Falha ao enviar e-mail de alteração de status", e);
		}
	}
}
