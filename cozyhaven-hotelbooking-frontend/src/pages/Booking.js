import React, { useState, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { getRoomById} from '../api/room';
import {createBooking} from '../api/booking';
function Booking() {
  const { id } = useParams();
  const navigate = useNavigate();
  const [room, setRoom] = useState(null);
  const [form, setForm] = useState({
    checkInDate: '',
    checkOutDate: '',
    adults: 1,
    children: 0,
  });
  const [error, setError] = useState('');

  useEffect(() => {
    async function fetchRoom() {
      try {
        const res = await getRoomById(id);
        setRoom(res.data);
      } catch (err) {
        console.log(err);
      }
    }
    fetchRoom();
  }, [id]);

  const handleChange = (e) => setForm({ ...form, [e.target.name]: e.target.value });

  const calculateTotalFare = () => {
    if (!room) return 0;
    let base = room.baseFare;
    const total = parseInt(form.adults) + parseInt(form.children);
    const included = room.bedType === 'SINGLE' ? 1 : room.bedType === 'DOUBLE' ? 2 : 4;
    const extra = total - included > 0 ? total - included : 0;
    const chargeableAdults = Math.min(extra, form.adults);
    const chargeableChildren = Math.max(0, extra - chargeableAdults);
    return base + (chargeableAdults * 0.4 * base) + (chargeableChildren * 0.2 * base);
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const bookingData = {
        roomId: room.roomId,
        checkInDate: form.checkInDate,
        checkOutDate: form.checkOutDate,
        adults: form.adults,
        children: form.children
      };
      await createBooking(bookingData);
      navigate('/profile');
    } catch (err) {
      setError(err.response?.data?.message || 'Booking failed');
    }
  };

  if (!room) return <p>Loading room details...</p>;

  return (
    <div className="col-md-6 mx-auto">
      <h3>Book Room: {room.roomType}</h3>
      {error && <div className="alert alert-danger">{error}</div>}
      <form onSubmit={handleSubmit}>
        <div className="mb-3">
          <label>Check-In Date</label>
          <input type="date" className="form-control" name="checkInDate" value={form.checkInDate} onChange={handleChange} required />
        </div>
        <div className="mb-3">
          <label>Check-Out Date</label>
          <input type="date" className="form-control" name="checkOutDate" value={form.checkOutDate} onChange={handleChange} required />
        </div>
        <div className="mb-3">
          <label>Adults</label>
          <input type="number" className="form-control" name="adults" value={form.adults} onChange={handleChange} min="1" required />
        </div>
        <div className="mb-3">
          <label>Children</label>
          <input type="number" className="form-control" name="children" value={form.children} onChange={handleChange} min="0" />
        </div>
        <p>Total Fare: ${calculateTotalFare()}</p>
        <button className="btn btn-success w-100">Book Now</button>
      </form>
    </div>
  );
}

export default Booking; 
