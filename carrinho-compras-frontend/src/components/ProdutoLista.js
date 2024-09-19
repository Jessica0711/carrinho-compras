import React, { useEffect, useState } from 'react';
import { useCart } from '../context/carrinhoContext';
import './ProdutoLista.css';

const ProdutoLista = () => {
    const [produtos, setProducts] = useState([]);
    const [quantidade, setQuantidade] = useState(1);
    const { addToCart } = useCart();

    const handleEnviar = async (produto) => {
        try {
            const response = await fetch('/carrinho-compras/produtos', {
                idProduto: produto.id,
                quantidade: quantidade,
            });
        } catch (error) {
            console.error('Erro ao enviar o pedido:', error);
        }
    };

    useEffect(() => {
        const fetchProducts = async () => {
            const response = await fetch('/produtos');
            const data = await response.json();
            setProducts(data);
        };
        fetchProducts();
    }, []);

    return (
        <div>
            <h1 className="title">Lista de Produtos</h1>
            <div className='container'>
            {produtos.map(produto => (
                <div key={produto.id} className="card">
                    <h2>{produto.nome}</h2>
                    <p>Preço: R${produto.preco}</p>
                    <label for="quantity">Quantidade:</label>
                    <input
                        className='quantidade'
                        type="number"
                        value={quantidade}
                        min="1"
                        step="1"
                        onChange={(e) => setQuantidade(e.target.value)}
                    />
                    <button onClick={handleEnviar(produto)} className="add">Adicionar ao carrinho</button>
                </div>
            ))}
            </div>
        </div>
    );
};

export default ProdutoLista;
