import axios from "axios";

const BASE_URL = "http://localhost:8081/api/rooms";

export const createRoom = (room) => axios.post(BASE_URL, room);

export const getRoomById = (id) => axios.get(`${BASE_URL}/${id}`);

export const getAllRooms = () => axios.get(BASE_URL);

export const getRoomsByHotel = (hotelId) =>
  axios.get(`${BASE_URL}/hotel/${hotelId}`);

export const getAvailableRooms = () => axios.get(`${BASE_URL}/available`);

export const updateRoom = (id, room) => axios.put(`${BASE_URL}/${id}`, room);

export const deleteRoom = (id) => axios.delete(`${BASE_URL}/${id}`);  
