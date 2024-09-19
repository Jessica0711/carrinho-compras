package com.carrinhocompra.model;

import java.math.BigDecimal;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.data.jpa.domain.AbstractPersistable;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Produto extends AbstractPersistable<Long> {

	@NotEmpty(message = "Nome deve ser informado")
	private String nome;
	
	@NotNull(message = "Preço deve ser informado")
	@Digits(fraction = 2, integer = 13)
	private BigDecimal preco;
	
	private String urlImagem;
	
}
