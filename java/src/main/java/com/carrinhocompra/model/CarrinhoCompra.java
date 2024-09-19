package com.carrinhocompra.model;

import static com.carrinhocompra.model.enums.StatusCarrinho.ATIVO;
import static jakarta.persistence.CascadeType.ALL;
import static jakarta.persistence.EnumType.STRING;

import java.math.BigDecimal;
import java.util.List;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotEmpty;

import org.springframework.data.jpa.domain.AbstractPersistable;

import com.carrinhocompra.model.enums.StatusCarrinho;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToMany;
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
@Table(name = "carrinho_compra")
public class CarrinhoCompra extends AbstractPersistable<Long> {

	@NotEmpty(message = "o usuário não pode ser vazio")
	private String usuario;

	@OneToMany(mappedBy = "carrinho", cascade = ALL, orphanRemoval = true)
	private List<ProdutoCarrinhoCompra> produtos;
	
	@Enumerated(STRING)
	private StatusCarrinho status = ATIVO;

	@Digits(fraction = 2, integer = 20)
	@Column(name = "valor_total")
	private BigDecimal valorTotal;
	
}
