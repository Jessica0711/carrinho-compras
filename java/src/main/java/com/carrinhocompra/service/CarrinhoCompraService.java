package com.carrinhocompra.service;

import static com.carrinhocompra.model.enums.StatusCarrinho.ATIVO;
import static com.carrinhocompra.model.enums.StatusCarrinho.FINALIZADO;
import static com.google.common.base.Preconditions.checkArgument;
import static java.math.BigDecimal.ZERO;
import static java.math.BigDecimal.valueOf;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.carrinhocompra.model.CarrinhoCompra;
import com.carrinhocompra.model.Produto;
import com.carrinhocompra.model.ProdutoCarrinhoCompra;
import com.carrinhocompra.repository.CarrinhoCompraRepository;

@Service
public class CarrinhoCompraService {

	@Autowired
	private CarrinhoCompraRepository repository;

	@Transactional(rollbackFor = Exception.class)
	public CarrinhoCompra adicionarProdutoCarrinho(Produto produto, int quantidade, String usuario) {
		CarrinhoCompra carrinhoCompra = findByAtivoAndUsuarioId(usuario).orElse(null);
		if (isNull(carrinhoCompra)) {
			carrinhoCompra = new CarrinhoCompra(usuario, new ArrayList<>(), ATIVO, ZERO);
		}
		addProdutoCarrinho(produto, quantidade, carrinhoCompra);
		carrinhoCompra.setValorTotal(calcularValorTotal(carrinhoCompra));
		return repository.save(carrinhoCompra);
	}

	@Transactional(rollbackFor = Exception.class)
	public CarrinhoCompra removerProdutoCarrinhoCompra(Produto produto, int quantidade, String usuario) {
		CarrinhoCompra carrinhoCompra = findByAtivoAndUsuarioId(usuario).orElse(null);
		checkArgument(nonNull(carrinhoCompra), "Não foi encontrado nenhum carrinho de compras ativo");
		if (quantidade == 0) {
			zerarProduto(carrinhoCompra, produto.getId());
		} else {
			atualizarProduto(produto, quantidade, carrinhoCompra);
		}
		return salvarOrExcluirCarrinho(carrinhoCompra);
	}

	@Transactional(readOnly = true)
	public Optional<CarrinhoCompra> findByAtivoAndUsuarioId(String usuario) {
		return repository.findByStatusAndUsuario(ATIVO, usuario);
	}

	@Transactional(rollbackFor = Exception.class, readOnly = true)
	public Boolean existsById(Long id) {
		return repository.existsById(id);
	}

	@Transactional(rollbackFor = Exception.class)
	public void deleteById(Long id) {
		repository.deleteById(id);
	}

	@Transactional(rollbackFor = Exception.class)
	public CarrinhoCompra finalizarCarrinho(CarrinhoCompra carrinho) {
		carrinho.setStatus(FINALIZADO);
		//montar pedido de compra e enviar para pagamento
		return repository.save(carrinho);
	}
	
	private CarrinhoCompra salvarOrExcluirCarrinho(CarrinhoCompra carrinhoCompra) {
		if (carrinhoCompra.getProdutos().isEmpty()) {
			repository.deleteById(carrinhoCompra.getId());
			return null;
		}
		carrinhoCompra.setValorTotal(calcularValorTotal(carrinhoCompra));
		return repository.save(carrinhoCompra);
	}

	private void addProdutoCarrinho(Produto produto, int quantidade, CarrinhoCompra carrinhoCompra) {
		if (produtoJaAdicionado(produto, carrinhoCompra)) {
			atualizarProduto(produto, quantidade, carrinhoCompra);
			return;
		}
		ProdutoCarrinhoCompra produtoCarrinhoCompra = new ProdutoCarrinhoCompra(produto, quantidade, calcularSubValorProduto(produto, quantidade), carrinhoCompra);
		carrinhoCompra.getProdutos().add(produtoCarrinhoCompra);
	}

	private void atualizarProduto(Produto produto, int quantidade, CarrinhoCompra carrinhoCompra) {
		ProdutoCarrinhoCompra produtoCarrinhoExistente = carrinhoCompra.getProdutos().stream().filter(item -> item.getProduto().equals(produto)).findFirst().get();
		int indexProduto = carrinhoCompra.getProdutos().indexOf(produtoCarrinhoExistente);
		produtoCarrinhoExistente.setQuantidade(quantidade);
		produtoCarrinhoExistente.setSubTotalProduto(calcularSubValorProduto(produto, produtoCarrinhoExistente.getQuantidade()));
		carrinhoCompra.getProdutos().set(indexProduto, produtoCarrinhoExistente);
	}

	private BigDecimal calcularSubValorProduto(Produto produto, int quantidade) {
		return produto.getPreco().multiply(valueOf(quantidade));
	}

	private BigDecimal calcularValorTotal(CarrinhoCompra carrinhoCompra) {
		return carrinhoCompra.getProdutos().stream().map(item -> item.getSubTotalProduto()).reduce((x, y) -> x.add(y)).get();
	}

	private boolean produtoJaAdicionado(Produto produto, CarrinhoCompra carrinhoCompra) {
		return carrinhoCompra.getProdutos().stream().anyMatch(item -> item.getProduto().equals(produto));
	}

	private void zerarProduto(CarrinhoCompra carrinhoCompra, Long produtoId) {
		ProdutoCarrinhoCompra produtoCarrinho = carrinhoCompra.getProdutos().stream().filter(item -> item.getProduto().getId().equals(produtoId)).findFirst().get();
		carrinhoCompra.getProdutos().remove(produtoCarrinho);
	}
}
