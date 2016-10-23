package com.pedidovenda.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.pedidovenda.model.Produto;
import com.pedidovenda.repository.ProdutoRepository;
import com.pedidovenda.util.cdi.CDIServiceLocator;

@FacesConverter(forClass=Produto.class)
public class ProdutoConverter implements Converter {

	private ProdutoRepository produtoRepository;
	
	public ProdutoConverter() {
		this.produtoRepository = CDIServiceLocator.getBean(ProdutoRepository.class);
	}
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		return (value != null) ? produtoRepository.getById(new Long(value)) : null;
	}
	
	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if(value != null){
			Produto produto = (Produto) value;
			return ( produto.getId() == null ) ? null : produto.getId().toString();
		}
		return  "";
	}

}