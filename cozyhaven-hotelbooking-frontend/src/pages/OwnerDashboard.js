import React, { useState, useEffect } from "react";
import { Container, Row, Col, Card, Form, Button, Alert, Nav } from "react-bootstrap";
import { FaBars } from "react-icons/fa";
import axiosInstance from "../utils/axiosInstance";

const HotelOwnerDashboard = () => {
  const [sidebarOpen, setSidebarOpen] = useState(true);
  const [activeTab, setActiveTab] = useState("createHotel");

  const [hotelName, setHotelName] = useState("");
  const [location, setLocation] = useState("");
  const [description, setDescription] = useState("");
  const [amenities, setAmenities] = useState("WiFi, Parking");

  const [message, setMessage] = useState("");
  const [error, setError] = useState("");

  const [myHotels, setMyHotels] = useState([]);


  const fetchMyHotels = async () => {
    try {
      const response = await axiosInstance.get("http://localhost:8081/api/hotels");
      setMyHotels(response.data);
    } catch (err) {
      console.error(err);
    }
  };

  useEffect(() => {
    if (activeTab === "myHotels") fetchMyHotels();
  }, [activeTab]);

  const handleCreateHotel = async (e) => {
    e.preventDefault();
    setMessage("");
    setError("");

    try {
      const response = await axiosInstance.post(
        "http://localhost:8081/api/hotels", 
        { name: hotelName, location, description, amenities }
      );
      setMessage(`Hotel created successfully! ID: ${response.data.hotelId}`);
      setHotelName("");
      setLocation("");
      setDescription("");
      setAmenities("WiFi, Parking");
    } catch (err) {
      setError(err.response?.data?.message || "Failed to create hotel");
    }
  };

  return (
    <Container fluid style={{ minHeight: "100vh", padding: 0 }}>
      <Row className="g-0">
    
        <Col
          md={sidebarOpen ? 3 : 1}
          className="vh-100 position-relative d-flex flex-column p-3"
          style={{
            background: "linear-gradient(180deg, #1f1c2c, #1e3a8a, #928dab)",
            color: "#fff",
            transition: "width 0.3s",
            overflow: "hidden",
            boxShadow: "4px 0 15px rgba(0,0,0,0.2)",
          }}
        >
          <div className="d-flex justify-content-between align-items-center mb-4">
            {sidebarOpen && <h3 className="mb-0">Welcome Owner</h3>}
            <FaBars
              onClick={() => setSidebarOpen(!sidebarOpen)}
              style={{ cursor: "pointer", fontSize: "1.5rem", color: "#fff" }}
            />
          </div>

          {sidebarOpen ? (
            <Nav
              className="flex-column"
              variant="pills"
              activeKey={activeTab}
              onSelect={(k) => setActiveTab(k)}
            >
              {["createHotel", "myHotels", "activeUsers"].map((tab, idx) => (
                <Nav.Item key={idx} className="mb-2">
                  <Nav.Link
                    eventKey={tab}
                    className={`text-white rounded py-2 px-3 ${
                      activeTab === tab ? "text-dark fw-bold" : ""
                    }`}
                  >
                    {tab === "createHotel"
                      ? "Create Hotel"
                      : tab === "myHotels"
                      ? "My Hotels"
                      : "Active Users"}
                  </Nav.Link>
                </Nav.Item>
              ))}
              <Nav.Item className="mt-auto px-2 pb-3">
                <Button
                  variant="danger"
                  className="w-100 mt-4 "
                  onClick={() => {
                    localStorage.removeItem("token");
                    window.location.reload();
                  }}
                >
                  Logout
                </Button>
              </Nav.Item>
            </Nav>
          ) : null}
        </Col>

  
        <Col md={sidebarOpen ? 9 : 11} className="p-5" style={{ backgroundColor: "#f1f5f9" }}>
          {activeTab === "createHotel" && (
            <Card className="p-4 shadow-sm border-0 rounded mx-auto" style={{ maxWidth: "600px" }}>
              <h3 className="mb-4 text-primary">Create Hotel</h3>
              {message && <Alert variant="success">{message}</Alert>}
              {error && <Alert variant="danger">{error}</Alert>}

              <Form onSubmit={handleCreateHotel}>
                <Form.Group className="mb-3">
                  <Form.Label>Hotel Name</Form.Label>
                  <Form.Control
                    type="text"
                    value={hotelName}
                    onChange={(e) => setHotelName(e.target.value)}
                    required
                  />
                </Form.Group>

                <Form.Group className="mb-3">
                  <Form.Label>Location</Form.Label>
                  <Form.Control
                    type="text"
                    value={location}
                    onChange={(e) => setLocation(e.target.value)}
                    required
                  />
                </Form.Group>

                <Form.Group className="mb-3">
                  <Form.Label>Description</Form.Label>
                  <Form.Control
                    as="textarea"
                    value={description}
                    onChange={(e) => setDescription(e.target.value)}
                    required
                  />
                </Form.Group>

                <Form.Group className="mb-3">
                  <Form.Label>Amenities</Form.Label>
                  <Form.Control
                    type="text"
                    value={amenities}
                    onChange={(e) => setAmenities(e.target.value)}
                    placeholder="WiFi, Parking, Pool"
                    required
                  />
                </Form.Group>

                <div className="d-grid">
                  <Button type="submit" variant="success" size="lg">
                    Create Hotel
                  </Button>
                </div>
              </Form>
            </Card>
          )}

        {activeTab === "myHotels" && (
  <>
    <h3 className="mb-4">My Hotels</h3>
    <Row className="gy-4">
      {myHotels.length === 0 && <p>No hotels found.</p>}
      {myHotels.map((hotel) => (
        <Col md={6} lg={4} key={hotel.hotelId}>
          <Card
            className="shadow-sm h-100 border-0"
            style={{
              background: "linear-gradient(#1f1c2c, #1e3a8a, #928dab)", 
              color: "#fff",
              borderRadius: "15px", 
              overflow: "hidden",
              transition: "transform 0.3s",
            }}
            onMouseEnter={(e) => (e.currentTarget.style.transform = "scale(1.05)")}
            onMouseLeave={(e) => (e.currentTarget.style.transform = "scale(1)")}
          >
            <Card.Body>
              <Card.Title>{hotel.name}</Card.Title>
              <Card.Text>{hotel.description}</Card.Text>
              <Card.Text>
                <strong>Location:</strong> {hotel.location}
              </Card.Text>
              <Card.Text>
                <strong>Amenities:</strong> {hotel.amenities}
              </Card.Text>
            </Card.Body>
          </Card>
        </Col>
      ))}
    </Row>
  </>
)}

         
               
{activeTab === "activeUsers" && (
  <>
    <Row className="mb-4">
      <Col md={3}>
        <Card className="text-center p-3 shadow-sm">
          <h5>Total Users</h5>
          <h3>120</h3>
        </Card>
      </Col>
      <Col md={3}>
        <Card className="text-center p-3 shadow-sm">
          <h5>Total Bookings</h5>
          <h3>345</h3>
        </Card>
      </Col>
      <Col md={3}>
        <Card className="text-center p-3 shadow-sm">
          <h5>Website Visits</h5>
          <h3>2045</h3>
        </Card>
      </Col>
      <Col md={3}>
        <Card className="text-center p-3 shadow-sm">
          <h5>Total Payments</h5>
          <h3>$567,890</h3>
        </Card>
      </Col>
    </Row>

  
    <Card className="p-4 shadow-sm rounded">
      <h3 className="mb-4 text-primary">User Details</h3>
      <table className="table table-striped table-bordered">
        <thead>
          <tr>
            <th>User ID</th>
            <th>Name</th>
            <th>Email</th>
            <th>Bookings</th>
            <th>Payments</th>
          </tr>
        </thead>
        <tbody>
          {[
            { id: 1, name: "John Doe", email: "john@example.com", bookings: 3, payments: 1500 },
            { id: 2, name: "Jane Smith", email: "jane@example.com", bookings: 1, payments: 500 },
          ].map((user) => (
            <tr key={user.id}>
              <td>{user.id}</td>
              <td>{user.name}</td>
              <td>{user.email}</td>
              <td>{user.bookings}</td>
              <td>${user.payments}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </Card>
  </>
)}
        </Col>
      </Row>
    </Container>
  );
};

export default HotelOwnerDashboard;
