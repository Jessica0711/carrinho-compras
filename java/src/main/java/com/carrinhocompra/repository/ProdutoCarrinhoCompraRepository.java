package com.carrinhocompra.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.carrinhocompra.model.ProdutoCarrinhoCompra;

public interface ProdutoCarrinhoCompraRepository extends JpaRepository<ProdutoCarrinhoCompra, Long>{

	void deleteByCarrinhoIdAndProdutoId(Long carrinhoId, Long produtoId);
	
	ProdutoCarrinhoCompra findByCarrinhoIdAndProdutoId(Long carrinhoId, Long produtoId);
}
