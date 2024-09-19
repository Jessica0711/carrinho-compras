package com.carrinhocompra.model;

import java.math.BigDecimal;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;

import org.springframework.data.jpa.domain.AbstractPersistable;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "produto_carrinho_compra")
public class ProdutoCarrinhoCompra extends AbstractPersistable<Long> {

	@ManyToOne
	@JoinColumn(name = "produto_id")
	private Produto produto;

	@NotNull(message = "A quantidade não pode ser nula")
	private Integer quantidade = 1;
	
	@Digits(fraction = 2, integer = 13)
	@Column(name = "subtotal_produto")
	private BigDecimal subTotalProduto;
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "carrinho_id")
	private CarrinhoCompra carrinho;
}
