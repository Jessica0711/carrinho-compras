package com.carrinhocompra.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.carrinhocompra.model.CarrinhoCompra;
import com.carrinhocompra.model.enums.StatusCarrinho;

@Repository
public interface CarrinhoCompraRepository extends JpaRepository<CarrinhoCompra, Long>{

	Optional<CarrinhoCompra> findByStatusAndUsuario(StatusCarrinho status, String usuario);
}
