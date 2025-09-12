
import { useState, useEffect } from "react";
import { getUserBookings } from "../api/booking";
import { getUserReviews } from "../api/review";

export default function Dashboard() {
  const [bookings, setBookings] = useState([]);
  const [reviews, setReviews] = useState([]);
  const token = localStorage.getItem("token");

  useEffect(() => {
    if (!token) return;
    getUserBookings(token).then(setBookings).catch(console.error);
    getUserReviews(token).then(setReviews).catch(console.error);
  }, [token]);

  if (!token) return <div className="container mt-5">Please login to view your dashboard</div>;

  return (
    <div className="container mt-5">
      <h2 className="mb-4">My Dashboard</h2>

      <section className="mb-5">
        <h3 className="mb-3">My Bookings</h3>
        {bookings.length === 0 ? (
          <p>No bookings yet</p>
        ) : (
          bookings.map((b) => (
            <div key={b.bookingId} className="card mb-3 shadow-sm">
              <div className="card-body">
                <p className="mb-1"><strong>Hotel:</strong> {b.room.hotel.name}</p>
                <p className="mb-1"><strong>Room:</strong> {b.room.roomSize} | <strong>Bed:</strong> {b.room.bedType}</p>
                <p className="mb-1"><strong>Check-in:</strong> {b.checkInDate} | <strong>Check-out:</strong> {b.checkOutDate}</p>
                <p className="mb-0"><strong>Status:</strong> {b.status}</p>
              </div>
            </div>
          ))
        )}
      </section>

      <section>
        <h3 className="mb-3">My Reviews</h3>
        {reviews.length === 0 ? (
          <p>No reviews yet</p>
        ) : (
          reviews.map((r) => (
            <div key={r.reviewId} className="card mb-3 shadow-sm">
              <div className="card-body">
                <p className="mb-1"><strong>Hotel:</strong> {r.hotel.name}</p>
                <p className="mb-1"><strong>Rating:</strong> {r.rating}</p>
                <p className="mb-0"><strong>Comment:</strong> {r.comment}</p>
              </div>
            </div>
          ))
        )}
      </section>
    </div>
  );
}
