
import { jwtDecode } from 'jwt-decode';

export const logout = () => localStorage.removeItem("token");

export const getUserRole = () => {
    const token = getToken();
    if (!token) return null;
    const decoded = jwtDecode(token);
    return decoded.role; 
};

export const setToken = (token) => {
  localStorage.setItem("token", token);
};

export const getToken = () => {
  return localStorage.getItem("token"); 
};

export const isAuthenticated = () => !!getToken();



