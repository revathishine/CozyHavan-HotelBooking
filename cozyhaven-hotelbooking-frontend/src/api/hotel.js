import axios from "axios";

const BASE_URL = "http://localhost:8081/api/hotels";
export const createHotel = (hotel) => axios.post(BASE_URL, hotel);

export const getHotelById = (id) => axios.get(`${BASE_URL}/${id}`);


export const getAllHotels = () => axios.get(BASE_URL);

export const updateHotel = (id, hotel) => axios.put(`${BASE_URL}/${id}`, hotel);

export const deleteHotel = (id) => axios.delete(`${BASE_URL}/${id}`);

export const getHotelsByLocation = (location) => 
  axios.get(`${BASE_URL}?location=${location}`); 

