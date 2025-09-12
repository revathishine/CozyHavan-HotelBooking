import React, { useState } from "react";
import axios from "axios";
import { Container, Form, Button, Alert } from "react-bootstrap";

const BASE_URL = "http://localhost:8081";

const BookingForm = () => {
  const [roomId, setRoomId] = useState("");
  const [checkInDate, setCheckInDate] = useState("");
  const [checkOutDate, setCheckOutDate] = useState("");
  const [guestName, setGuestName] = useState("");
  const [guestAge, setGuestAge] = useState("");
  const [success, setSuccess] = useState("");
  const [error, setError] = useState("");

  const token = localStorage.getItem("token"); 

  const handleBooking = async (e) => {
    e.preventDefault();
    if (!roomId || !checkInDate || !checkOutDate || !guestName || !guestAge) {
      setError("All fields are required");
      return;
    }

    try {
      const payload = {
        roomId: Number(roomId),
        checkInDate,
        checkOutDate,
        guests: [{ name: guestName, age: Number(guestAge) }],
      };

      await axios.post(`${BASE_URL}/api/v1/bookings`, payload, {
        headers: { Authorization: `Bearer ${token}` },
      });

      setSuccess("Booking successful!");
      setError("");
      setRoomId("");
      setCheckInDate("");
      setCheckOutDate("");
      setGuestName("");
      setGuestAge("");
    } catch (err) {
      console.error(err);
      setError("Booking failed. Please check the details.");
      setSuccess("");
    }
  };

  return (
    <Container style={{ maxWidth: "500px", marginTop: "50px" }}>
      <h3>Book a Room</h3>
      {error && <Alert variant="danger">{error}</Alert>}
      {success && <Alert variant="success">{success}</Alert>}

      <Form onSubmit={handleBooking}>
        <Form.Group className="mb-3">
          <Form.Label>Room ID</Form.Label>
          <Form.Control
            type="number"
            value={roomId}
            onChange={(e) => setRoomId(e.target.value)}
            required
          />
        </Form.Group>

        <Form.Group className="mb-3">
          <Form.Label>Check-In Date</Form.Label>
          <Form.Control
            type="date"
            value={checkInDate}
            onChange={(e) => setCheckInDate(e.target.value)}
            required
          />
        </Form.Group>

        <Form.Group className="mb-3">
          <Form.Label>Check-Out Date</Form.Label>
          <Form.Control
            type="date"
            value={checkOutDate}
            onChange={(e) => setCheckOutDate(e.target.value)}
            required
          />
        </Form.Group>

        <Form.Group className="mb-3">
          <Form.Label>Guest Name</Form.Label>
          <Form.Control
            type="text"
            value={guestName}
            onChange={(e) => setGuestName(e.target.value)}
            required
          />
        </Form.Group>

        <Form.Group className="mb-3">
          <Form.Label>Guest Age</Form.Label>
          <Form.Control
            type="number"
            value={guestAge}
            onChange={(e) => setGuestAge(e.target.value)}
            required
          />
        </Form.Group>

        <Button type="submit">Book Now</Button>
      </Form>
    </Container>
  );
};

export default BookingForm;
