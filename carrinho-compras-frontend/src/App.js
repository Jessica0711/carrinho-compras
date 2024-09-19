import React from 'react';
import ProdutoLista from './components/ProdutoLista';
import CarrinhoCompras from './components/CarrinhoCompras';

const App = () => {
    return (
        <div>
            <ProdutoLista />
            <CarrinhoCompras />
        </div>
    );
};

export default App;
