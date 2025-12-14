package com.pedidovenda.converter;

import com.pedidovenda.model.Produto;
import com.pedidovenda.repository.data.ProdutoDataRepository;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.FacesConverter;
import jakarta.inject.Inject;

@FacesConverter
public class ProdutoConverter implements Converter<Produto> {

	@Inject
	private ProdutoDataRepository produtoRepository;

	@Override
	public Produto getAsObject(FacesContext context, UIComponent component, String value) {
		return produtoRepository.findById(Long.valueOf(value)).orElse(null);
	}
	
	@Override
	public String getAsString(FacesContext context, UIComponent component, Produto produto) {
		return ( produto.getId() == null ) ? "" : produto.getId().toString();
	}

}