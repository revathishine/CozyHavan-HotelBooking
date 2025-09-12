import React from "react";
import { Link } from "react-router-dom";

const topDestinations = [
  {
    id: 1,
    name: "Goa",
    description: "Beach paradise with vibrant nightlife and water sports.",
    image: "https://assets.serenity.co.uk/58000-58999/58031/720x480.jpg",
  },
  {
    id: 2,
    name: "Jaipur",
    description: "The Pink City with historic forts, palaces, and culture.",
    image: "https://cdn.tajhotels.com/images/ocl5w36p/prod5/02d5266ba2e7a05097c8aa5c6f5533095f8b50fc-3840x1860.jpg",
  },
  {
    id: 3,
    name: "Kerala",
    description: "Backwaters, greenery, and serene houseboats for relaxation.",
    image: "https://media-cdn.tripadvisor.com/media/photo-s/1d/5a/bf/58/the-leaf-munnar.jpg",
  },
];

export default function UserLandingPage() {
  return (
    <div className="container py-5">
      <div className="text-center mb-5">
        <h1 className="display-4 fw-bold">Welcome, Guest!</h1>
        <p className="lead">
          Discover amazing hotels, book rooms instantly, and enjoy your stay.
        </p>
      </div>

      <div className="row">
        <div className="col-md-4 mb-4">
          <div className="card shadow">
            <img
              src="https://images.unsplash.com/photo-1551882547-ff40c63fe5fa?w=600&auto=format&fit=crop&q=60&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxzZWFyY2h8M3x8aG90ZWx8ZW58MHx8MHx8fDA%3D"
              className="card-img-top"
              alt="Hotel"
            />
            <div className="card-body">
              <h5 className="card-title">Grand Palace Hotel</h5>
              <p className="card-text">
                Luxury hotel with modern amenities and excellent service.
              </p>
              <button className="btn btn-primary">View Details</button>
            </div>
          </div>
        </div>

        <div className="col-md-4 mb-4">
          <div className="card shadow">
            <img
              src="https://images.unsplash.com/photo-1584132967334-10e028bd69f7?w=600&auto=format&fit=crop&q=60&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxzZWFyY2h8MTl8fGhvdGVsfGVufDB8fDB8fHww"
              className="card-img-top"
              alt="Hotel"
            />
            <div className="card-body">
              <h5 className="card-title">Seaside Resort</h5>
              <p className="card-text">
                Enjoy the ocean breeze with beachside views and comfort.
              </p>
              <button className="btn btn-primary">View Details</button>
            </div>
          </div>
        </div>

        <div className="col-md-4 mb-4">
          <div className="card shadow">
            <img
              src="https://images.unsplash.com/photo-1445019980597-93fa8acb246c?w=600&auto=format&fit=crop&q=60&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxzZWFyY2h8NHx8aG90ZWx8ZW58MHx8MHx8fDA%3D"
              className="card-img-top"
              alt="Hotel"
            />
            <div className="card-body">
              <h5 className="card-title">Mountain Retreat</h5>
              <p className="card-text">
                Escape to the hills with cozy rooms and breathtaking views.
              </p>
              <button className="btn btn-primary">View Details</button>
            </div>
          </div>
        </div>
      </div>

      <div className="text-center mt-5 mb-4">
        <Link to="/user" className="btn btn-success me-3">
          Go to Dashboard
        </Link>
        <Link to="/login" className="btn btn-secondary">
          Login
        </Link>
      </div>


      <h2 className="text-center mb-4 fw-bold">Top Destinations in India</h2>
      <div className="row g-4">
        {topDestinations.map((dest) => (
          <div key={dest.id} className="col-md-4">
            <div
              className="card h-100 border-0 text-white overflow-hidden"
              style={{
                borderRadius: "15px",
                position: "relative",
                cursor: "pointer",
                transition: "transform 0.3s, box-shadow 0.3s",
              }}
              onMouseEnter={(e) => {
                e.currentTarget.style.transform = "scale(1.05)";
                e.currentTarget.style.boxShadow = "0 15px 40px rgba(0,0,0,0.4)";
              }}
              onMouseLeave={(e) => {
                e.currentTarget.style.transform = "scale(1)";
                e.currentTarget.style.boxShadow = "0 6px 15px rgba(0,0,0,0.15)";
              }}
            >
              <img
                src={dest.image}
                className="card-img"
                alt={dest.name}
                style={{ height: "220px", objectFit: "cover" }}
              />
              <div
                className="card-img-overlay d-flex flex-column justify-content-end"
                style={{
                  background:
                    "linear-gradient(0deg, rgba(0,0,0,0.6), rgba(0,0,0,0.1))",
                  borderRadius: "15px",
                  padding: "20px",
                }}
              >
                <h5 className="card-title fw-bold">{dest.name}</h5>
                <p className="card-text">{dest.description}</p>
                <Link
                  to={`/hotels?destination=${dest.name}`}
                  className="btn btn-warning text-dark w-100 mt-2 fw-semibold"
                >
                  Explore Hotels
                </Link>
              </div>
            </div>
          </div>
        ))}
      </div>
    </div>
  );
}
