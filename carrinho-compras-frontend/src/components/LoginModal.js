import React, { useState } from 'react';
import Modal from 'react-modal';
import { useAuth } from '../context/AuthContext';
import { api } from '../provider';

Modal.setAppElement('#root');

const LoginModal = ({ isOpen, onRequestClose }) => {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const { login } = useAuth();

  const handleLogin = async () => {
    try {
      const response = await api.post('/login', { username, password });
      login(response.data.token);
      onRequestClose();
    } catch (error) {
      console.error("Erro ao logar", error);
    }
  };

  return (
    <Modal isOpen={isOpen} onRequestClose={onRequestClose}>
      <h2>Login</h2>
      <input 
        type="text" 
        placeholder="Login" 
        value={username} 
        onChange={(e) => setUsername(e.target.value)} 
      />
      <input 
        type="password" 
        placeholder="Senha" 
        value={password} 
        onChange={(e) => setPassword(e.target.value)} 
      />
      <button onClick={handleLogin}>Logar</button>
      <button onClick={onRequestClose}>Fechar</button>
    </Modal>
  );
};

export default LoginModal;
