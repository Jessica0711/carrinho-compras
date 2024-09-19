import React from 'react';

const Resumo = ({ total }) => {
  return (
    <>
      <div className='box'>
        <header>Resumo da compra</header>
        <div className='info'>
          <div>
            <span>Sub-total</span>
            <span>R$ {total}</span>
          </div>
          <div>
            <span>Frete</span>
            <span>Gratuito</span>
          </div>
        </div>
        <footer>
          <span>Total</span>
          <span>R$ {total}</span>
        </footer>
      </div>
      <button>Finalizar Compra</button>
    </>
  );
};

export default Resumo;