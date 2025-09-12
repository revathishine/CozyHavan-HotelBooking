import axios from "axios";

const BASE_URL = "http://localhost:8081/api/booking-persons";

export const createBookingPerson = (person) =>
  axios.post(BASE_URL, person);

export const getBookingPersonById = (id) => axios.get(`${BASE_URL}/${id}`);

export const getAllBookingPersons = () => axios.get(BASE_URL);

export const getBookingPersonsByBooking = (bookingId) =>
  axios.get(`${BASE_URL}/booking/${bookingId}`);

export const updateBookingPerson = (id, person) =>
  axios.put(`${BASE_URL}/${id}`, person);

export const deleteBookingPerson = (id) => axios.delete(`${BASE_URL}/${id}`);
