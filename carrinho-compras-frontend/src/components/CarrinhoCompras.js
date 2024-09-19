import React from 'react';
import { useCart } from '../context/carrinhoContext';

const CarrrinhoCompras = () => {
    const { cart, removeFromCart } = useCart();

    return (
        <div>
            <h1>Carrinho de Compras</h1>
            <ul>
                {cart.map(item => (
                    <li key={item.id}>
                        {item.name} - ${item.price}
                        <button onClick={() => removeFromCart(item.id)}>Remover</button>
                    </li>
                ))}
            </ul>
            <h2>Total: ${cart.reduce((total, item) => total + item.price, 0)}</h2>
        </div>
    );
};

export default CarrrinhoCompras;
