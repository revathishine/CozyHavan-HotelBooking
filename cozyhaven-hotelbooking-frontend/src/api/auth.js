import axios from "axios";

const API_URL = "http://localhost:8081/api/v1/auth"; 

export const login = async (email, password) => {
    const res = await axios.post(`${API_URL}/login`, { email, password });
    return res.data; 
};

export const register = async (data) => {
    const res = await axios.post(`${API_URL}/register`, data);
    return res.data;
};      



