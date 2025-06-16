package com.pedidovenda.controller;

import com.pedidovenda.events.PedidoAlateradoEvent;
import com.pedidovenda.model.*;
import com.pedidovenda.repository.ClienteRepository;
import com.pedidovenda.repository.ProdutoRepository;
import com.pedidovenda.repository.UsuarioRepository;
import com.pedidovenda.service.CadastroPedidoService;
import com.pedidovenda.util.jsf.FacesUtil;
import com.pedidovenda.validation.SKU;
import jakarta.enterprise.event.Observes;
import jakarta.enterprise.inject.Produces;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

@Named
@ViewScoped
public class CadastroPedidoBean {

    @Inject
    private UsuarioRepository usuarios;

    @Inject
    private ClienteRepository clientes;

    @Inject
    ProdutoRepository produtos;

    @Inject
    CadastroPedidoService cadastroPedidoService;

    @Produces
    @PedidoEdicao
    private Pedido pedido;
    
    private List<Usuario> vendedores;
    private Produto produtoLinhaEditavel;
    private String sku;
    
    public void inicializar() {
        if (FacesUtil.isNotPostback()) {
            this.vendedores = this.usuarios.findAll();
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
        return this.clientes.getByName(name);
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
        return produtos.getByNome(nome);
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
            this.produtoLinhaEditavel = this.produtos.porSku(this.sku);
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
    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public List<Usuario> getVendedores() {
        return vendedores;
    }

    public FormaPagamento[] getFormasPagamento() {
        return FormaPagamento.values();
    }

    public boolean isEditando() {
        return this.pedido.getId() != null;
    }

    public Produto getProdutoLinhaEditavel() {
        return produtoLinhaEditavel;
    }

    public void setProdutoLinhaEditavel(Produto produtoLinhaEditavel) {
        this.produtoLinhaEditavel = produtoLinhaEditavel;
    }

    @SKU
    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }
}
