import React from 'react';
import { Link } from 'react-router-dom';

function RoomCard({ room }) {
  return (
    <div className="card mb-3">
      <div className="card-body">
        <h5 className="card-title">{room.roomType}</h5>
        <p className="card-text">Bed: {room.bedType}</p>
        <p className="card-text">Size: {room.size} m²</p>
        <p className="card-text">Base Fare: ${room.baseFare}</p>
        <Link to={`/booking/${room.roomId}`} className="btn btn-success">Book Now</Link>
      </div>
    </div>
  );
}

export default RoomCard;
