import React, { useState } from 'react';
import { AuthProvider } from './context/AuthContext';
import ProdutoLista from './components/ProdutoLista';
import LoginModal from './components/LoginModal';
import CarrinhoCompras from './components/CarrinhoCompras';

const App = () => {
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [viewCart, setViewCart] = useState(false);

  return (
    <AuthProvider>
      <div>
        <header>
          <button onClick={() => setIsModalOpen(true)}>Login</button>
          <button onClick={() => setViewCart(!viewCart)}>Carrinho</button>
        </header>
        {viewCart ? <CarrinhoCompras /> : <ProdutoLista />}
        <LoginModal isOpen={isModalOpen} onRequestClose={() => setIsModalOpen(false)} />
      </div>
    </AuthProvider>
  );
};

export default App;
