

import React, { useEffect, useState } from 'react';
import { Row, Col, Spinner, Alert, Form, Button } from 'react-bootstrap';
import HotelCard from '../components/Hotelcard';
import { getAllHotels } from '../api/hotel'; 

export default function Hotellist() {
  const [hotels, setHotels] = useState([]);
  const [loading, setLoading] = useState(true);
  const [location, setLocation] = useState('');
  const [checkInDate, setCheckInDate] = useState('');
  const [checkOutDate, setCheckOutDate] = useState('');
  const [adults, setAdults] = useState(1);
  const [children, setChildren] = useState(0);

  useEffect(() => {
    fetchAll();
  }, []);

  const fetchAll = async () => {
    setLoading(true);
    try {
      const res = await getAllHotels();
      setHotels(res.data);
    } catch (err) {
      console.error(err);
    } finally {
      setLoading(false);
    }
  };

  const handleSearch = async (e) => {
    e.preventDefault();
    setLoading(true);
    try {
      if (!location || !checkInDate || !checkOutDate) {
        fetchAll();
        return;
      }

      const res = await getAllHotels({
        location,
        checkInDate,
        checkOutDate,
        adults,
        children
      });

      setHotels(res.data || []);
    } catch (err) {
      console.error(err);
      setHotels([]);
    } finally {
      setLoading(false);
    }
  };

  return (
    <div>
      <h2>Hotels</h2>
      <Form className="mb-3" onSubmit={handleSearch}>
        <Form.Control
          placeholder="Location"
          value={location}
          onChange={e => setLocation(e.target.value)}
          className="mb-2"
        />
        <Form.Control
          type="date"
          value={checkInDate}
          onChange={e => setCheckInDate(e.target.value)}
          className="mb-2"
        />
        <Form.Control
          type="date"
          value={checkOutDate}
          onChange={e => setCheckOutDate(e.target.value)}
          className="mb-2"
        />
        <Form.Control
          type="number"
          value={adults}
          min={1}
          onChange={e => setAdults(parseInt(e.target.value))}
          className="mb-2"
          placeholder="Adults"
        />
        <Form.Control
          type="number"
          value={children}
          min={0}
          onChange={e => setChildren(parseInt(e.target.value))}
          className="mb-2"
          placeholder="Children"
        />
        <Button type="submit" className="mt-2">Search</Button>
        <Button
          variant="outline-secondary"
          className="ms-2 mt-2"
          onClick={() => {
            setLocation('');
            setCheckInDate('');
            setCheckOutDate('');
            setAdults(1);
            setChildren(0);
            fetchAll();
          }}
        >
          Reset
        </Button>
      </Form>

      {loading ? (
        <Spinner animation="border" />
      ) : hotels.length === 0 ? (
        <Alert variant="info">No hotels found</Alert>
      ) : (
        <Row>
          {hotels.map(h => (
            <Col md={4} key={h.hotelId}>
              <HotelCard hotel={h} />
            </Col>
          ))}
        </Row>
      )}
    </div>
  );
}          

