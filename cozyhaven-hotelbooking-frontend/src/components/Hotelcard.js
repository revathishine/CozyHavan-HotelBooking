import React from 'react';
import { Link } from 'react-router-dom';

function HotelCard({ hotel }) {
  return (
    <div className="card mb-3">
      <img src={hotel.image || 'https://plus.unsplash.com/premium_photo-1661964071015-d97428970584?fm=jpg&q=60&w=3000&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxzZWFyY2h8MXx8aG90ZWx8ZW58MHx8MHx8fDA%3D'} className="card-img-top" alt={hotel.name} />
      <div className="card-body">
        <h5 className="card-title">{hotel.name}</h5>
        <p className="card-text">{hotel.location}</p>
        <p className="card-text">Amenities: {hotel.amenities?.join(', ')}</p>
        <Link to={`/hotels/${hotel.hotelId}`} className="btn btn-primary">View Details</Link>
      </div>
    </div>
  );
}

export default HotelCard;
