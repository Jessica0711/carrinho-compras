import React, { useState } from 'react';
import { AuthProvider } from './context/AuthContext';
import ProdutoLista from './components/lista-produtos/ProdutoLista';
import LoginModal from './components/LoginModal';
import CarrinhoCompras from './components/carrinho/CarrinhoCompras';
import './App.css'

const App = () => {
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [viewCart, setViewCart] = useState(false);

  return (
    <AuthProvider>
      <div>
        <header>
          <button className='button' onClick={() => setViewCart(!viewCart)}>{viewCart? 'Produtos' : 'Carrinho'}</button>
        </header>
        {viewCart ? <CarrinhoCompras /> : <ProdutoLista />}
        <LoginModal isOpen={isModalOpen} onRequestClose={() => setIsModalOpen(false)} />
      </div>
    </AuthProvider>
  );
};

export default App;
