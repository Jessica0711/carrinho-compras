import Title from '../layout/Header';
import React, { useEffect, useState } from 'react';
import Resumo from './Resumo';
import TabelaCarrinho from './TabelaCarrinho';
import {api} from '../../provider';
import { useAuth } from '../../context/AuthContext';
import './carrinhostyle.scss'

function CarrinhoCompras() {
  const [cart, setCart] = useState([]);
  const { token } = useAuth();

  const fetchData = () => {
    api.get('/carrinho-compras/ativo')
      .then((response) => setCart(response.data))
      .catch((err) => {
        if (err.response && err.response.status === 404) {
          setCart(undefined);
        }
      });
  };

  useEffect(() => {
    fetchData();
  }, []);

  const handleRemoveItem = (item) => {
    api.post(`/carrinho-compras/produtos/retirar/${item.produto.id}?quantidade=0`).then((response) => fetchData());
  };

  const handleUpdateItem = (item, action) => {
    let novaQuantidade = item.quantidade;
    if (action === 'decrease') {
      if (novaQuantidade === 1) {
        handleRemoveItem(item);
        return;
      }
      novaQuantidade -= 1;
      api.post(`/carrinho-compras/produtos/retirar/${item.produto.id}?quantidade=${novaQuantidade}`).then((response) => fetchData());
      return;
    }
    novaQuantidade += 1;
    api.post(`/carrinho-compras/produtos/${item.produto.id}?quantidade=${novaQuantidade}`).then((response) => fetchData());
  };

  return (
    <>
      <main>
        <Title data={'Seu carrinho'} />
        <div className='content'>
          <section>
            <table>
              <thead>
                <tr>
                  <th>Produto</th>
                  <th>Preço</th>
                  <th>Quantidade</th>
                  <th>Total</th>
                  <th>-</th>
                </tr>
              </thead>
              <tbody>
                {cart?.produtos?.map((item) => (
                  <TabelaCarrinho
                    key={item.id}
                    data={item}
                    handleRemoveItem={handleRemoveItem}
                    handleUpdateItem={handleUpdateItem}
                  />
                ))}
                {!cart && (
                  <tr>
                    <td colSpan='5' style={{ textAlign: 'center' }}>
                      <b>Carrinho de compras vazio.</b>
                    </td>
                  </tr>
                )}
              </tbody>
            </table>
          </section>
          <aside>
            <Resumo total={cart?.valorTotal || 0} />
          </aside>
        </div>
      </main>
    </>
  );
}

export default CarrinhoCompras;