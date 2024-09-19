import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { useAuth } from '../context/AuthContext';

const CarrinhoCompras = () => {
  const [cartItems, setCartItems] = useState([]);
  const [total, setTotal] = useState(0);
  const { token } = useAuth();

  useEffect(() => {
    const fetchCart = async () => {
      try {
        const response = await axios.get('http://localhost:8080/carrinho-compras/ativo', {
          headers: { Authorization: token ? `Bearer ${token}` : undefined },
        });
        setCartItems(response.data.produtos);
        setTotal(response.data.valorTotal);
      } catch (error) {
        console.error("Erro ao buscar o carrinho", error);
      }
    };

    fetchCart();
  }, [token]);

  return (
    <div>
      <h2>Carrinho</h2>
      {cartItems.map(item => (
        <div key={item.id}>
          <h3>{item.nome}</h3>
          <p>Subtotal: R${item.subtotal}</p>
        </div>
      ))}
      <h3>Total: R${total}</h3>
    </div>
  );
};

export default CarrinhoCompras;
