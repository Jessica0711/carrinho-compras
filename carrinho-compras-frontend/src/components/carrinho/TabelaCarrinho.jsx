import React from 'react';

const TabelaCarrinho = ({ data, handleRemoveItem, handleUpdateItem }) => {
  return (
    <tr>
      <td>{data.produto.nome}</td>
      <td>R$ {data.produto.preco}</td>
      <td>
        <div className='qty'>
          <button
            onClick={() => {
              handleUpdateItem(data, 'decrease');
            }}
          >
            <i className='bx bx-minus'></i>
          </button>
          <span>{data.quantidade}</span>
          <button
            onClick={() => {
              handleUpdateItem(data, 'increase');
            }}
          >
            <i className='bx bx-plus'></i>
          </button>
        </div>
      </td>
      <td>R$ {data.subTotalProduto}</td>
      <td>
        <button
          className='remove'
          onClick={() => {
            handleRemoveItem(data);
          }}
        >
          <i className='bx bx-x'></i>
        </button>
      </td>
    </tr>
  );
};

export default TabelaCarrinho;