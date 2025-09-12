import React from "react";
import { Link } from "react-router-dom";

export default function OwnerLandingPage() {
     const backgroundStyle = {
    backgroundImage: "url('https://png.pngtree.com/thumb_back/fh260/background/20241007/pngtree-tranquil-coastal-resort-with-a-beautiful-swimming-pool-beach-umbrella-and-image_16320952.jpg')", 
    backgroundSize: "cover",
    backgroundPosition: "center",
    minHeight: "100vh",
  };

  return (
    <div
    style={backgroundStyle}
    className="d-flex flex-column justify-content-center align-items-center min-vh-100 bg-light">
      <h1 className="display-4 fw-bold mb-4">Welcome, Hotel Owner!</h1>
      <p className="lead text-center mb-4">
        Manage your hotels, rooms, and bookings with ease.
      </p>
      <div className="d-flex gap-3">
        <Link to="/owner" className="btn btn-success">
          Go to Dashboard
        </Link>
        <Link to="/login" className="btn btn-secondary">
          Login
        </Link>
      </div>
    </div>
  );
}
