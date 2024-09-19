import React, { createContext, useState } from 'react';

const CarrinhoContext = createContext();

export const CarrinhoProvider = ({ children }) => {
    const [cart, setCart] = useState([]);

    const addToCart = (product) => {
        setCart((prevCart) => [...prevCart, product]);
    };

    const removeFromCart = (id) => {
        setCart((prevCart) => prevCart.filter(item => item.id !== id));
    };

    return (
        <CarrinhoContext.Provider value={{ cart, addToCart, removeFromCart }}>
            {children}
        </CarrinhoContext.Provider>
    );
};

export const useCart = () => React.useContext(CarrinhoContext);
