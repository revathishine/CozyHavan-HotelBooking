
import React, { useState, useEffect } from 'react';
import { Row, Col, Spinner, Alert, Form, Button } from 'react-bootstrap';
import HotelCard from '../components/Hotelcard';
import { getAllHotels } from '../api/hotel';

export default function Hotellist() {
  const [hotels, setHotels] = useState([]);
  const [loading, setLoading] = useState(true);
  const [location, setLocation] = useState('');

  useEffect(() => { fetchAll(); }, []);

  const fetchAll = async () => {
    setLoading(true);
    try {
      const res = await getAllHotels();
      setHotels(res.data);
    } catch (err) {
      console.error(err);
    } finally { setLoading(false); }
  };

  const handleSearch = async (e) => {
    e.preventDefault();
    if (!location) { fetchAll(); return; }
    setHotels(prev => prev.filter(h => h.location?.toLowerCase().includes(location.toLowerCase())));
  };

  return (
    <div>
      <h2>Hotels</h2>
      <Form className="mb-3 d-flex" onSubmit={handleSearch}>
        <Form.Control placeholder="Search by location" value={location} onChange={e => setLocation(e.target.value)} />
        <Button className="ms-2" type="submit">Search</Button>
        <Button className="ms-2" variant="outline-secondary" onClick={() => { setLocation(''); fetchAll(); }}>Reset</Button>
      </Form>

      {loading ? <Spinner animation="border" /> :
        hotels.length === 0 ? <Alert variant="info">No hotels found</Alert> :
        <Row>
          {hotels.map(h => (
            <Col md={4} key={h.hotelId}>
              <HotelCard hotel={h} />
            </Col>
          ))}
        </Row>
      }
    </div>
  );
}           

