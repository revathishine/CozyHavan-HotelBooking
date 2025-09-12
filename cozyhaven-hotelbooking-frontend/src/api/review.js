import axios from "axios";

const BASE_URL = "http://localhost:8081/api/reviews";

export const createReview = (review) => axios.post(BASE_URL, review);

export const getReviewById = (id) => axios.get(`${BASE_URL}/${id}`);

export const getAllReviews = () => axios.get(BASE_URL);

export const getReviewsByHotel = (hotelId) =>
  axios.get(`${BASE_URL}/hotel/${hotelId}`);

export const updateReview = (id, review) =>
  axios.put(`${BASE_URL}/${id}`, review);

export const deleteReview = (id) => axios.delete(`${BASE_URL}/${id}`);    
