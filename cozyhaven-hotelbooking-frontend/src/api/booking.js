import axios from "axios";

const BASE_URL = "http://localhost:8081/api/bookings";

export const createBooking = (booking) => axios.post(BASE_URL, booking);

export const getBookingById = (id) => axios.get(`${BASE_URL}/${id}`);

export const getAllBookings = () => axios.get(BASE_URL);
export const getBookingsByUser = (userId) =>
  axios.get(`${BASE_URL}/user/${userId}`);
export const updateBooking = (id, booking) =>
  axios.put(`${BASE_URL}/${id}`, booking);

export const deleteBooking = (id) => axios.delete(`${BASE_URL}/${id}`);     

