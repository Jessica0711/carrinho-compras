import axios from 'axios';
import { useAuth } from './context/AuthContext';

export const api = axios.create({
  baseURL: 'http://localhost:8080/',
  timeout: 10000,
});

// api.interceptors.request.use(async config => {
//   const { token } = useAuth();
//   if (token) {
//     api.defaults.headers.authorization = `Bearer ${token}`;
//   }
//   return config;
// });