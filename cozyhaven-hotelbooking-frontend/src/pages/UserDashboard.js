import React, { useState, useEffect } from "react";
import { Container, Row, Col, Card, Form, Button, Alert, Nav, Table } from "react-bootstrap";
import { FaBars } from "react-icons/fa";
import axiosInstance from "../utils/axiosInstance";

const UserDashboard = () => {
  const [sidebarOpen, setSidebarOpen] = useState(true);
  const [activeTab, setActiveTab] = useState("booking");


  const [roomId, setRoomId] = useState("");
  const [checkInDate, setCheckInDate] = useState("");
  const [checkOutDate, setCheckOutDate] = useState("");
  const [guests, setGuests] = useState([{ name: "", age: "" }]);
  const [success, setSuccess] = useState("");
  const [error, setError] = useState("");

  
  const [bookings, setBookings] = useState([]);
  const [loadingBookings, setLoadingBookings] = useState(false);

  const userId = localStorage.getItem("userId"); 

useEffect(() => {
  const fetchBookings = async () => {
    if (activeTab !== "myBookings" || !userId) return;
    setLoadingBookings(true);
    try {
      const res = await axiosInstance.get(`/bookings/user/${userId}`);
      setBookings(res.data);
    } catch (err) {
      setError("Failed to load bookings");
    } finally {
      setLoadingBookings(false);
    }
  };
  fetchBookings();
}, [activeTab, userId]);

// const cancelBooking = async (bookingId) => {
//   try {
//     await axiosInstance.post(`/bookings/${bookingId}/cancel`);
//     setBookings((prev) =>
//       prev.map((b) =>
//         b.bookingId === bookingId ? { ...b, status: "CANCELLED" } : b
//       )
//     );
//   } catch (err) {
//     setError("Failed to cancel booking");
//   }
// };

  
  const handleGuestChange = (index, field, value) => {
    const updated = [...guests];
    updated[index][field] = value;
    setGuests(updated);
  };

  const addGuest = () => setGuests([...guests, { name: "", age: "" }]);
  const removeGuest = (index) => setGuests(guests.filter((_, i) => i !== index));


  const handleBooking = async (e) => {
    e.preventDefault();
    if (!roomId || !checkInDate || !checkOutDate || guests.some((g) => !g.name || !g.age)) {
      setError("All fields are required");
      setSuccess("");
      return;
    }

    const payload = {
      roomId: Number(roomId),
      checkInDate,
      checkOutDate,
      adults: guests.filter((g) => g.age >= 15).length,
      children: guests.filter((g) => g.age < 15).length,
      guests: guests.map((g) => ({ name: g.name, age: Number(g.age) })),
    };

    try {
      const response = await axiosInstance.post("/bookings", payload);
      setSuccess(`Booking successful! Booking ID: ${response.data.bookingId}`);
      setError("");
      setRoomId("");
      setCheckInDate("");
      setCheckOutDate("");
      setGuests([{ name: "", age: "" }]);
    } catch (err) {
      setError(err.response?.data?.message || "Booking failed. Please check details.");
      setSuccess("");
    }
  };

  return (
    <Container fluid style={{ minHeight: "100vh", padding: 0 }}>
      <Row className="g-0">
        {/* Sidebar */}
        <Col md={sidebarOpen ? 3 : 1} className="vh-100 position-relative d-flex flex-column p-3"
          style={{
            background: "linear-gradient(180deg, #1f1c2c, #1e3a8a, #928dab)",
            color: "#fff",
            transition: "width 0.3s",
            overflow: "hidden",
          }}
        >
          <div className="d-flex justify-content-between align-items-center mb-4">
            {sidebarOpen && <h3 className="mb-0">Welcome User</h3>}
            <FaBars onClick={() => setSidebarOpen(!sidebarOpen)} style={{ cursor: "pointer", fontSize: "1.5rem", color: "#fff" }} />
          </div>

          {sidebarOpen && (
            <Nav className="flex-column" variant="pills" activeKey={activeTab} onSelect={(k) => setActiveTab(k)}>
              <Nav.Item><Nav.Link eventKey="booking">Book a Room</Nav.Link></Nav.Item>
              <Nav.Item><Nav.Link eventKey="myBookings">My Bookings</Nav.Link></Nav.Item>
              <Nav.Item><Nav.Link eventKey="reviews">My Reviews</Nav.Link></Nav.Item>
              <Nav.Item><Nav.Link eventKey="hotels">Hotels</Nav.Link></Nav.Item>
              <Nav.Item className="mt-auto px-2 pb-3" >
                <Button variant="danger" className="w-100 mt-4" onClick={() => { localStorage.removeItem("token"); window.location.reload(); }}>Logout</Button>
              </Nav.Item>
            </Nav>
          )}
        </Col>

    
        <Col md={sidebarOpen ? 9 : 11} className="p-5" style={{ backgroundColor: "#f1f5f9" }}>
          
        
          {activeTab === "booking" && (
            <Card className="p-4 shadow-sm border-0 rounded mx-auto mb-4" style={{ maxWidth: "600px" }}>
              <h3 className="mb-4 text-primary">Book a Room</h3>
              {error && <Alert variant="danger">{error}</Alert>}
              {success && <Alert variant="success">{success}</Alert>}
              <Form onSubmit={handleBooking}>
                <Form.Group className="mb-3">
                  <Form.Label>Room ID</Form.Label>
                  <Form.Control type="number" value={roomId} onChange={(e) => setRoomId(e.target.value)} required />
                </Form.Group>
                <Row className="mb-3">
                  <Col>
                    <Form.Label>Check-In Date</Form.Label>
                    <Form.Control type="date" value={checkInDate} onChange={(e) => setCheckInDate(e.target.value)} required />
                  </Col>
                  <Col>
                    <Form.Label>Check-Out Date</Form.Label>
                    <Form.Control type="date" value={checkOutDate} onChange={(e) => setCheckOutDate(e.target.value)} required />
                  </Col>
                </Row>
                <h5 className="mb-2 text-secondary">Guests</h5>
                {guests.map((guest, index) => (
                  <Row key={index} className="mb-2 g-2 align-items-center">
                    <Col>
                      <Form.Control placeholder="Name" value={guest.name} onChange={(e) => handleGuestChange(index, "name", e.target.value)} required />
                    </Col>
                    <Col>
                      <Form.Control type="number" placeholder="Age" value={guest.age} onChange={(e) => handleGuestChange(index, "age", e.target.value)} required />
                    </Col>
                    <Col xs="auto">{index > 0 && <Button variant="danger" onClick={() => removeGuest(index)}>Remove</Button>}</Col>
                  </Row>
                ))}
                <Button variant="secondary" onClick={addGuest} className="mb-3">Add Guest</Button>
                <div className="d-grid">
                  <Button type="submit" variant="success" size="lg">Book Now</Button>
                </div>
              </Form>
            </Card>
          )}

        
      {activeTab === "myBookings" && (
  <Card className="p-4 shadow-sm">
    <h3 className="text-primary">My Bookings</h3>
    {error && <Alert variant="danger">{error}</Alert>}
    {loadingBookings ? (
      <p>Loading bookings...</p>
    ) : (
      <Table striped bordered hover responsive className="mt-3">
        <thead>
          <tr>
            <th>BookingID</th>
            <th>Hotel</th>
            <th>Room</th>
            <th>Check-In</th>
            <th>Check-Out</th>
            <th>Status</th>
            <th>Total Price</th>
            <th>Action</th>
          </tr>
        </thead>
        <tbody>
          {bookings.length === 0 && (
            <tr>
              <td colSpan="8" className="text-center">
                No bookings found.
              </td>
            </tr>
          )}
          {bookings.map((b) => (
            <tr key={b.bookingId}>
              <td>{b.bookingId}</td>
              <td>{b.hotelName}</td>
              <td>{b.roomType}</td>
              <td>{b.checkInDate}</td>
              <td>{b.checkOutDate}</td>
              <td>
                <span
                  className={`badge ${
                    b.status === "CONFIRMED"
                      ? "bg-success"
                      : b.status === "BOOKED"
                      ? "bg-warning text-dark"
                      : "bg-secondary"
                  }`}
                >
                  {b.status}
                </span>
              </td>
              <td>₹{b.totalPrice}</td>
              <td>
                {b.status !== "CANCELLED" ? (
                  <Button
                    variant="danger"
                    size="sm"
                    onClick={async () => {
                      try {
                        await axiosInstance.put(`/bookings/${b.bookingId}/cancel`);
                        setBookings((prev) =>
                          prev.map((bk) =>
                            bk.bookingId === b.bookingId
                              ? { ...bk, status: "CANCELLED" }
                              : bk
                          )
                        );
                      } catch (err) {
                        alert(err.response?.data?.message || "Failed to cancel booking");
                      }
                    }}
                  >
                    Cancel
                  </Button>
                ) : (
                  "-"
                )}
              </td>
            </tr>
          ))}
        </tbody>
      </Table>
    )}
  </Card>
)}



          {activeTab === "reviews" && (
  <Card
    className="p-4 shadow-sm rounded mx-auto"
    style={{ maxWidth: "600px", width: "100%" }}
  >
    <h3 className="mb-4 text-primary">My Reviews</h3>

    <Form
      onSubmit={(e) => {
        e.preventDefault();
        alert("Review submitted!");
      }}
    >
      <Form.Group className="mb-3">
        <Form.Label>Hotel Name</Form.Label>
        <Form.Control type="text" placeholder="Enter hotel name" required />
      </Form.Group>

      <Form.Group className="mb-3">
        <Form.Label>Rating</Form.Label>
        <Form.Select required>
          <option value="">Select rating</option>
          {[1, 2, 3, 4, 5].map((r) => (
            <option key={r} value={r}>
              {r} Star{r > 1 ? "s" : ""}
            </option>
          ))}
        </Form.Select>
      </Form.Group>

      <Form.Group className="mb-3">
        <Form.Label>Review</Form.Label>
        <Form.Control
          as="textarea"
          rows={4}
          placeholder="Write your review..."
          required
        />
      </Form.Group>

      <div className="d-grid">
        <Button type="submit" variant="success" size="lg">
          Submit Review
        </Button>
      </div>
    </Form>

    
  </Card>
)}

 
         {activeTab === "hotels" && (
  <>

    <Form className="mb-4" onSubmit={(e) => e.preventDefault()}>
      <Form.Control type="text" placeholder="Search hotels..." />
    </Form>

     <div className="row">
        <div className="col-md-4 mb-4">
          <div className="card shadow">
            <img
              src="https://images.unsplash.com/photo-1542314831-068cd1dbfeeb?w=600&auto=format&fit=crop&q=60&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxzZWFyY2h8MTF8fGhvdGVsfGVufDB8fDB8fHww"
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
              src="https://images.unsplash.com/photo-1520250497591-112f2f40a3f4?w=600&auto=format&fit=crop&q=60&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxzZWFyY2h8MTB8fGhvdGVsfGVufDB8fDB8fHww"
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
              src="https://plus.unsplash.com/premium_photo-1661964402307-02267d1423f5?w=600&auto=format&fit=crop&q=60&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxzZWFyY2h8MTN8fGhvdGVsfGVufDB8fDB8fHww"
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
  </>
)}

        </Col>
      </Row>
    </Container>
  );
};

export default UserDashboard;