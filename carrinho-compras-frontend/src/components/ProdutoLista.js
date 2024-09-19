import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { useAuth } from '../context/AuthContext';
import './ProdutoLista.css';

const ProdutoLista = () => {
  const [products, setProducts] = useState([]);
  const { token } = useAuth();

  useEffect(() => {
    const fetchProducts = async () => {
      const response = await axios.get('/produtos');
      setProducts(response.data);
    };

    fetchProducts();
  }, []);

  const addToCart = async (productId, quantity) => {
    try {
      await axios.post(`/carrinho-compras/produtos/${productId}`, {
        quantity,
        token: token || null,
      });
    } catch (error) {
      console.error("Erro ao adicionar ao carrinho", error);
    }
  };

  return (
    <div>
      <h2 className="title">Produtos</h2>
      <div className='container'>
      {products.map(product => (
        <div key={product.id} className="card">
          <h3>{product.nome}</h3>
          <p>Preço: R${product.preco}</p>
          <input className='quantidade' type="number" min="1" placeholder="Quantidade" id={`qty-${product.id}`} />
          <button className="add" onClick={() => {
            const quantity = document.getElementById(`qty-${product.id}`).value;
            addToCart(product.id, quantity);
          }}>
            Adicionar ao Carrinho
          </button>
        </div>
        
      ))}
      </div>
    </div>
  );
};

export default ProdutoLista;
