package com.pedidovenda.controller;

import com.pedidovenda.events.PedidoAlateradoEvent;
import com.pedidovenda.model.*;
import com.pedidovenda.repository.data.ClienteDataRepository;
import com.pedidovenda.repository.data.ProdutoDataRepository;
import com.pedidovenda.repository.data.UsuarioDataRepository;
import com.pedidovenda.service.CadastroPedidoService;
import com.pedidovenda.util.jsf.FacesUtil;
import com.pedidovenda.validation.SKU;
import jakarta.enterprise.event.Observes;
import jakarta.enterprise.inject.Produces;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.List;

@Named
@ViewScoped
public class CadastroPedidoBean implements Serializable {

    @Inject
    private UsuarioDataRepository usuarios;

    @Inject
    private ClienteDataRepository clientes;

    @Inject
    private ProdutoDataRepository produtos;

    @Inject
    CadastroPedidoService cadastroPedidoService;

    @Getter
    @Setter
    @Produces
    @PedidoEdicao
    private Pedido pedido;
    
    @Getter
    private List<Usuario> vendedores;
    private Produto produtoLinhaEditavel;
    @Setter
    private String sku;
    
    public void inicializar() {
        if (FacesUtil.isNotPostback()) {
            this.vendedores = this.usuarios.findAllByOrderByNome();
            this.pedido.adicionarItemVazio();
            this.recalcularPedido();
        }
    }

    public void salvar() {
    	this.pedido.removerItemVazio();
    	try {
    		this.pedido = this.cadastroPedidoService.salvar(this.pedido);
            FacesUtil.addInfoMessage("Pedido Salvo com Sucesso!");	
		} finally {
			this.pedido.adicionarItemVazio();
		}
    }

    public List<Cliente> completarCliente(String name) {
        return this.clientes.findByNomeOrEmail(name);
    }

    public void limpar() {
        this.pedido = new Pedido();
        this.pedido.setEnderecoEntrega(new EnderecoEntrega());
        this.pedido.setUsuario(new Usuario());
    }

    public CadastroPedidoBean() {
        limpar();
    }
    
    public void pedidoAlterado(@Observes PedidoAlateradoEvent event){
    	this.pedido = event.getPedido();
    }

    public void recalcularPedido() {
        if (this.pedido != null) {
            this.pedido.recalcularValorTotal();
        }
    }

    public List<Produto> completarProdutos(String nome) {
        return produtos.findByNomeOrSku(nome);
    }

    public void carregarProdutoLinhaEditavel() {
        ItemPedido item = this.pedido.getItens().get(0);
        if (this.produtoLinhaEditavel != null) {
            if (this.existeItemComProduto(this.produtoLinhaEditavel)) {
                FacesUtil.addErrorMessage("Já existe um Item com o Produto Informado.");
            } else {
                item.setProduto(produtoLinhaEditavel);
                item.setValorUnitario(produtoLinhaEditavel.getValorUnitario());

                this.pedido.adicionarItemVazio();
                this.produtoLinhaEditavel = null;

                this.pedido.recalcularValorTotal();
            }

        }
    }

    private boolean existeItemComProduto(Produto produto) {
        boolean existeItem = false;
        for (ItemPedido item : this.getPedido().getItens()) {
            if (produto.equals(item.getProduto())) {
                existeItem = true;
                break;
            }
        }
        return existeItem;
    }

    public void getBySKU() {
        if (StringUtils.isNotEmpty(this.sku)) {
            this.produtoLinhaEditavel = this.produtos.findBySku(this.sku);
            this.carregarProdutoLinhaEditavel();
        }
    }
    
    public void atualizarQuantidade(ItemPedido item,int linha){
    	if(item.getQuantidade()  < 1){
    		if(linha == 0){
    			item.setQuantidade(1);
    		}else{
    			this.getPedido().getItens().remove(linha);
    			this.recalcularPedido();
    		}
    	}
    }

    public FormaPagamento[] getFormasPagamento() {
        return FormaPagamento.values();
    }

    public boolean isEditando() {
        return this.pedido.getId() != null;
    }

    @SKU
    public String getSku() {
        return sku;
    }

}
