package com.carrinhocompra.controller;

import static com.carrinhocompra.model.enums.StatusCarrinho.ATIVO;
import static com.google.common.base.Preconditions.checkArgument;
import static java.util.Objects.nonNull;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.ResponseEntity.noContent;
import static org.springframework.http.ResponseEntity.notFound;
import static org.springframework.http.ResponseEntity.status;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.carrinhocompra.config.TokenService;
import com.carrinhocompra.model.CarrinhoCompra;
import com.carrinhocompra.model.Produto;
import com.carrinhocompra.repository.ProdutoRepository;
import com.carrinhocompra.service.CarrinhoCompraService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/carrinho-compras")
public class CarrinhoCompraController {

	@Autowired
	private CarrinhoCompraService service;

	@Autowired
	private ProdutoRepository produtoRepository;

	@Autowired
	private TokenService tokenService;

	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@GetMapping("/ativo")
	public ResponseEntity<CarrinhoCompra> obterCarrinhoCompra(HttpServletRequest request) {
		String usuario = extrairUsuarioLogin(request);
		Optional<CarrinhoCompra> CarrinhoCompra = service.findByAtivoAndUsuarioId(usuario);
		return CarrinhoCompra.map(ResponseEntity::ok).orElseGet(() -> notFound().build());
	}

	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deletarCarrinhoCompra(@PathVariable Long id) {
		if (!service.existsById(id)) {
			return notFound().build();
		}
		service.deleteById(id);
		return noContent().build();
	}

	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@PostMapping("/produtos/{produtoId}")
	public ResponseEntity<CarrinhoCompra> adicionarProdutoCarrinhoCompra( @PathVariable Long produtoId, @RequestParam int quantidade, HttpServletRequest request) {
		Produto produto = produtoRepository.findById(produtoId).orElse(null);
		checkArgument(nonNull(produto), "Produto não encontrado");
		String usuario = extrairUsuarioLogin(request);
		CarrinhoCompra salvo = service.adicionarProdutoCarrinho(produto, quantidade, usuario);
		return status(CREATED).body(salvo);
	}

	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@PostMapping("/produtos/retirar/{produtoId}")
	public ResponseEntity<CarrinhoCompra> removerProdutoCarrinhoCompra(@PathVariable Long produtoId, @RequestParam int quantidade, HttpServletRequest request) {
		Produto produto = produtoRepository.findById(produtoId).orElse(null);
		checkArgument(nonNull(produto), "Produto não encontrado");
		String usuario = extrairUsuarioLogin(request);
		CarrinhoCompra carrinho = service.removerProdutoCarrinhoCompra(produto, quantidade, usuario);
		return status(OK).body(carrinho);
	}
	
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@PutMapping
	public ResponseEntity<CarrinhoCompra> finalizarCarrinho(@RequestBody CarrinhoCompra carrinhoCompra) {
		checkArgument(ATIVO.equals(carrinhoCompra.getStatus()), "Carrinho precisa estar ativo para finalização");
		CarrinhoCompra salvo = service.finalizarCarrinho(carrinhoCompra);
		return status(OK).body(salvo);
	}

	private String extrairUsuarioLogin(HttpServletRequest request) {
		String token = recuperarToken(request);
		return nonNull(token) ? tokenService.validateToken(token) : request.getRemoteAddr();
	}

	private String recuperarToken(HttpServletRequest request) {
		var authHeader = request.getHeader("Authorization");
		return nonNull(authHeader) ? authHeader.replace("Bearer ", "") : null;
	}
}
