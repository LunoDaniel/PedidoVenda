package com.pedidovenda.controller;

import java.io.Serializable;
import java.util.Locale;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.velocity.tools.generic.NumberTool;

import com.outjected.email.api.MailMessage;
import com.outjected.email.impl.templating.velocity.VelocityTemplate;
import com.pedidovenda.model.Pedido;
import com.pedidovenda.util.jsf.FacesUtil;
import com.pedidovenda.util.mail.Mailer;

@Named
@RequestScoped
public class EnviarEmailPedidoBean implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Inject
	@PedidoEdicao
	private Pedido pedido;
	
	@Inject
	private Mailer mailer;
	
	public void enviarEmailPedido(){
		MailMessage message = mailer.novaMensagem();
		
		message.to(this.pedido.getCliente().getEmail())
			.subject("Pedido " + this.pedido.getId())
			.bodyHtml(new VelocityTemplate(getClass().getResourceAsStream("/emails/pedido.template")))
			.put("pedido", this.pedido)
			.put("numberTool", new NumberTool())
			.put("locale", new Locale("pt", "BR"))
			.send();
		
		FacesUtil.addInfoMessage("Email Enviado com Sucesso.");
	}
}
