import React, { useEffect, useState } from 'react';
import { getAllBookings, deleteBooking } from '../api/booking';

function Profile() {
  const [bookings, setBookings] = useState([]);
  const [error, setError] = useState('');

  useEffect(() => {
    async function fetchBookings() {
      try {
        const res = await getAllBookings();
        setBookings(res.data);
      } catch (err) {
        console.log(err);
      }
    }
    fetchBookings();
  }, []);

  const handleCancel = async (id) => {
    try {
      await deleteBooking(id);
      setBookings(bookings.filter(b => b.bookingId !== id));
    } catch (err) {
      setError(err.response?.data?.message || 'Cancellation failed');
    }
  };

  return (
    <div>
      <h3>My Bookings</h3>
      {error && <div className="alert alert-danger">{error}</div>}
      {bookings.length === 0 ? (
        <p>No bookings yet.</p>
      ) : (
        <table className="table">
          <thead>
            <tr>
              <th>Hotel</th>
              <th>Room</th>
              <th>Check-In</th>
              <th>Check-Out</th>
              <th>Adults</th>
              <th>Children</th>
              <th>Status</th>
              <th>Action</th>
            </tr>
          </thead>
          <tbody>
            {bookings.map(b => (
              <tr key={b.bookingId}>
                <td>{b.room.hotel.name}</td>
                <td>{b.room.roomType}</td>
                <td>{b.checkInDate}</td>
                <td>{b.checkOutDate}</td>
                <td>{b.adults}</td>
                <td>{b.children}</td>
                <td>{b.status}</td>
                <td>
                  {b.status === 'CONFIRMED' && (
                    <button className="btn btn-danger btn-sm" onClick={() => handleCancel(b.bookingId)}>Cancel</button>
                  )}
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      )}
    </div>
  );
}

export default Profile; //first
