import React, { useEffect, useState } from 'react';
import {api} from '../../provider';

import './ProdutoLista.scss';


const ProdutoLista = () => {
  const [products, setProducts] = useState([]);
  const [mensagem, setMensagem] = useState('');

  useEffect(() => {
    const fetchProducts = async () => {
      api.get('/produtos').then((response) => setProducts(response.data));
    };

    fetchProducts();
  }, []);

  const addToCart = async (produto, quantidade) => {
    try {
      api.post(`/carrinho-compras/produtos/${produto}?quantidade=${quantidade}`).then((response) => {
        setMensagem('Adicionado ao carrinho com sucesso');
        
        setTimeout(() => {
          setMensagem('');
        }, 3000);
      });
    } catch (error) {
      console.error("Erro ao adicionar ao carrinho", error);
    }
  };

  return (
    <div>
      <h2 className="title">Produtos</h2>
      {mensagem && (
        <div className='mensagem'>
          {mensagem}
        </div>
      )}
      <div className='container'>
      {products.map(produto => (
        <div key={produto.id} className="card">
          <h3>{produto.nome}</h3>
          <p>Preço: R${produto.preco}</p>
          <input className='quantidade' type="number" min="1" placeholder="0" id={`qty-${produto.id}`} />
          <button className="add" onClick={() => {
            const quantidade = document.getElementById(`qty-${produto.id}`).value;
            addToCart(produto.id, quantidade);
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
