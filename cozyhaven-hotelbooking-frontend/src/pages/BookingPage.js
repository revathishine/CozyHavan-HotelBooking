import { useState, useEffect } from "react";
import { useParams, useNavigate } from "react-router-dom";
import { getRoomById } from "../api/room";
import { createBooking } from "../api/booking";

export default function BookingPage() {
  const { roomId } = useParams();
  const [room, setRoom] = useState(null);
  const [checkInDate, setCheckInDate] = useState("");
  const [checkOutDate, setCheckOutDate] = useState("");
  const [adults, setAdults] = useState(1);
  const [children, setChildren] = useState(0);
  const navigate = useNavigate();

  useEffect(() => {
    getRoomById(roomId).then(setRoom).catch(console.error);
  }, [roomId]);

  const handleBooking = async (e) => {
    e.preventDefault();
    const token = localStorage.getItem("token");
    if (!token) return navigate("/login");

    try {
      await createBooking(
        { roomId: room.roomId, checkInDate, checkOutDate, numAdults: adults, numChildren: children },
        token
      );
      alert("Booking successful");
      navigate("/dashboard");
    } catch (err) {
      alert("Booking failed");
    }
  };

  if (!room) return <div>Loading room details...</div>;

  return (
    <div className="container mt-5 d-flex justify-content-center">
      <form onSubmit={handleBooking} className="card p-4" style={{ width: "24rem" }}>
        <h2 className="card-title mb-3">Book Room</h2>
        <p className="mb-3">Room: {room.roomSize} | Bed: {room.bedType}</p>

        <div className="mb-3">
          <label className="form-label">Check-in Date</label>
          <input type="date" className="form-control" value={checkInDate} onChange={(e) => setCheckInDate(e.target.value)} required />
        </div>

        <div className="mb-3">
          <label className="form-label">Check-out Date</label>
          <input type="date" className="form-control" value={checkOutDate} onChange={(e) => setCheckOutDate(e.target.value)} required />
        </div>

        <div className="mb-3">
          <label className="form-label">Adults</label>
          <input type="number" min="1" className="form-control" value={adults} onChange={(e) => setAdults(e.target.value)} required />
        </div>

        <div className="mb-3">
          <label className="form-label">Children</label>
          <input type="number" min="0" className="form-control" value={children} onChange={(e) => setChildren(e.target.value)} />
        </div>

        <button type="submit" className="btn btn-primary w-100">Confirm Booking</button>
      </form>
    </div>
  );
}
