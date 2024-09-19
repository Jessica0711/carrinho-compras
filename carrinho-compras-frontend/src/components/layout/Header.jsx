import React from 'react';

const Title = ({ data }) => {
  return <div className='page-title'>{data || '{insira um titulo}'}</div>;
};

export default Title;