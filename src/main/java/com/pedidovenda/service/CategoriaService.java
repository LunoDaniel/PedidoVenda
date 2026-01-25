package com.pedidovenda.service;

import com.pedidovenda.exceptions.NegocioException;
import com.pedidovenda.model.Categoria;
import com.pedidovenda.repository.data.CategoriaRepository;
import com.pedidovenda.repository.data.ProdutoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.io.Serializable;
import java.util.Optional;

/**
 * Serviço para operações de negócio com Categorias
 */
@ApplicationScoped
public class CategoriaService implements Serializable {

    @Inject
    private CategoriaRepository categoriaRepository;

    @Inject
    private ProdutoRepository produtoRepository;

    /**
     * Salva ou atualiza uma categoria
     */
    @Transactional
    public Categoria salvar(Categoria categoria) {
        validarCategoria(categoria);
        return categoriaRepository.save(categoria);
    }

    /**
     * Remove uma categoria
     */
    @Transactional
    public void remover(Long categoriaId) {
        Optional<Categoria> categoria = Optional.ofNullable(categoriaRepository.find(Categoria.class, categoriaId));
        if (categoria.isPresent()) {
            // Verificar se categoria tem produtos ou subcategorias antes de remover
            long produtos = produtoRepository.countProdutosByCategoria(categoriaId);
            long subcategorias = categoriaRepository.countByCategoriaPai(categoria.get());
            
            if (produtos > 0) {
                throw new NegocioException("Categoria não pode ser excluída pois possui produtos associados.");
            }
            
            if (subcategorias > 0) {
                throw new NegocioException("Categoria não pode ser excluída pois possui subcategorias.");
            }
            
            categoriaRepository.removeById(categoriaId, Categoria.class);
        } else {
            throw new NegocioException("Categoria não encontrada.");
        }
    }

    /**
     * Busca categoria por ID
     */
    public Optional<Categoria> buscarPorId(Long id) {
        return Optional.ofNullable(categoriaRepository.find(Categoria.class, id));
    }

    /**
     * Verifica se nome da categoria já existe
     */
    public boolean nomeJaExiste(String nome) {
        return categoriaRepository.existsByNome(nome);
    }

    /**
     * Validações de negócio da categoria
     */
    private void validarCategoria(Categoria categoria) {
		if (categoria.getDescricao() == null || categoria.getDescricao().trim().isEmpty()) {
            throw new NegocioException("Nome da categoria é obrigatório.");
        }

        // Verificar se nome já existe (apenas para novas categorias ou mudança de nome)
		if (categoria.getId() == null || !categoria.getDescricao()
				.equals(buscarPorId(categoria.getId()).map(Categoria::getDescricao).orElse(""))) {
			if (nomeJaExiste(categoria.getDescricao())) {
                throw new NegocioException("Já existe uma categoria com este nome.");
            }
        }

        // Validar hierarquia (evitar referência circular)
        if (categoria.getCategoriaPai() != null) {
            validarHierarquia(categoria, categoria.getCategoriaPai());
        }
    }

    /**
     * Valida hierarquia para evitar referências circulares
     */
    private void validarHierarquia(Categoria categoria, Categoria categoriaPai) {
        if (categoria.getId() != null && categoria.getId().equals(categoriaPai.getId())) {
            throw new NegocioException("Uma categoria não pode ser pai de si mesma.");
        }

        // Verificar se a categoria pai não é descendente da categoria atual
        Categoria atual = categoriaPai;
        while (atual != null && atual.getCategoriaPai() != null) {
            if (categoria.getId() != null && categoria.getId().equals(atual.getCategoriaPai().getId())) {
                throw new NegocioException("Referência circular detectada na hierarquia de categorias.");
            }
            atual = atual.getCategoriaPai();
        }
    }
}
