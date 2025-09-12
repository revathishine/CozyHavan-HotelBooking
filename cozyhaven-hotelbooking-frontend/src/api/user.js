import axios from "axios";

const BASE_URL = "http://localhost:8081/api/users";


export const createUser = (user) => axios.post(BASE_URL, user);


export const getUserById = (id) => axios.get(`${BASE_URL}/${id}`);


export const getAllUsers = () => axios.get(BASE_URL);

export const updateUser = (id, user) => axios.put(`${BASE_URL}/${id}`, user);
export const deleteUser = (id) => axios.delete(`${BASE_URL}/${id}`);
