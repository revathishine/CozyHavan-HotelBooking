import React, { useEffect, useState } from 'react';
import { Container, Table, Button, Alert } from 'react-bootstrap';
import { getBookings, cancelBooking } from '../api';

export default function MyBookings() {
  const [bookings, setBookings] = useState([]);
  const [msg, setMsg] = useState(null);

  useEffect(() => {
    async function fetchBookings() {
      try {
        const res = await getBookings();
        setBookings(res.data || []);
      } catch (err) {
        console.error(err);
      }
    }
    fetchBookings();
  }, []);

  const handleCancel = async (id) => {
    try {
      await cancelBooking(id);
      setMsg({ type: 'success', text: 'Booking cancelled' });
      setBookings(bookings.filter(b => b.bookingId !== id));
    } catch (err) {
      setMsg({ type: 'danger', text: 'Cancellation failed' });
    }
  };

  return (
    <Container className="mt-4">
      <h2>My Bookings</h2>
      {msg && <Alert variant={msg.type}>{msg.text}</Alert>}
      {bookings.length === 0 ? (
        <p>No bookings found.</p>
      ) : (
        <Table striped bordered hover>
          <thead>
            <tr>
              <th>Booking ID</th>
              <th>Room</th>
              <th>Check-In</th>
              <th>Check-Out</th>
              <th>Status</th>
              <th>Total</th>
              <th>Action</th>
            </tr>
          </thead>
          <tbody>
            {bookings.map(b => (
              <tr key={b.bookingId}>
                <td>{b.bookingId}</td>
                <td>{b.room?.roomType || b.roomId}</td>
                <td>{b.checkInDate}</td>
                <td>{b.checkOutDate}</td>
                <td>{b.status}</td>
                <td>{b.totalPrice}</td>
                <td>
                  {b.status !== 'CANCELLED' && (
                    <Button
                      variant="danger"
                      size="sm"
                      onClick={() => handleCancel(b.bookingId)}
                    >
                      Cancel
                    </Button>
                  )}
                </td>
              </tr>
            ))}
          </tbody>
        </Table>
      )}
    </Container>
  );
}
