import React, { useState } from "react";
import { Container, Row, Col, Card, Form, Button, Alert } from "react-bootstrap";
import { FaMapMarkerAlt, FaPhoneAlt, FaEnvelope, FaGlobe } from "react-icons/fa";

const ContactUs = () => {
  const [form, setForm] = useState({ name: "", email: "", message: "" });
  const [success, setSuccess] = useState("");
  const [error, setError] = useState("");

  const handleChange = (e) => {
    setForm({ ...form, [e.target.name]: e.target.value });
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    if (!form.name || !form.email || !form.message) {
      setError("All fields are required!");
      setSuccess("");
      return;
    }
    setError("");
    setSuccess("Thank you! Your message has been sent.");
    setForm({ name: "", email: "", message: "" });
  };

  return (
    <Container className="py-5">
      <h2 className="text-center mb-5 fw-bold text-primary">Contact Us</h2>
      <Row className="g-4">
        <Col md={7}>
          <Card
            className="shadow-lg p-4 border-0"
            style={{
              borderRadius: "15px",
              transition: "transform 0.3s ease, box-shadow 0.3s ease",
            }}
            onMouseEnter={(e) => {
              e.currentTarget.style.transform = "translateY(-5px)";
              e.currentTarget.style.boxShadow = "0 12px 30px rgba(0,0,0,0.2)";
            }}
            onMouseLeave={(e) => {
              e.currentTarget.style.transform = "translateY(0)";
              e.currentTarget.style.boxShadow = "0 6px 15px rgba(0,0,0,0.1)";
            }}
          >
            <h4 className="mb-4 text-center text-success fw-semibold">
              Get in Touch
            </h4>
            {success && <Alert variant="success">{success}</Alert>}
            {error && <Alert variant="danger">{error}</Alert>}

            <Form onSubmit={handleSubmit}>
              <Form.Group className="mb-3">
                <Form.Label className="fw-semibold">Your Name</Form.Label>
                <Form.Control
                  type="text"
                  name="name"
                  value={form.name}
                  onChange={handleChange}
                  placeholder="Enter your name"
                  className="rounded-pill"
                />
              </Form.Group>

              <Form.Group className="mb-3">
                <Form.Label className="fw-semibold">Your Email</Form.Label>
                <Form.Control
                  type="email"
                  name="email"
                  value={form.email}
                  onChange={handleChange}
                  placeholder="Enter your email"
                  className="rounded-pill"
                />
              </Form.Group>

              <Form.Group className="mb-3">
                <Form.Label className="fw-semibold">Message</Form.Label>
                <Form.Control
                  as="textarea"
                  rows={4}
                  name="message"
                  value={form.message}
                  onChange={handleChange}
                  placeholder="Write your message here..."
                  className="rounded"
                />
              </Form.Group>

              <div className="d-grid">
                <Button type="submit" variant="primary" className="rounded-pill py-2">
                  Send Message
                </Button>
              </div>
            </Form>
          </Card>
        </Col>
        <Col md={5}>
          <Card
            className="shadow-lg p-4 border-0 bg-primary text-white contact-card bg-dark"
            style={{
              borderRadius: "15px",
              transition: "all 0.3s ease",
               marginTop: "60px",
               background: "linear-gradient(180deg, #1f1c2c, #1e3a8a, #928dab)", 
                boxShadow: "4px 0 15px rgba(0,0,0,0.2)",
            }}
            onMouseEnter={(e) => {
              e.currentTarget.style.transform = "translateY(-5px) scale(1.02)";
              e.currentTarget.style.boxShadow = "0 12px 30px rgba(0,0,0,0.3)";
            }}
            onMouseLeave={(e) => {
              e.currentTarget.style.transform = "translateY(0) scale(1)";
              e.currentTarget.style.boxShadow = "0 6px 15px rgba(0,0,0,0.15)";
            }}
          >
            <h4 className="mb-4 fw-semibold">Cozy Haven Hotel Booking</h4>
            <p>
              We are here to assist you with your bookings, feedback, and queries. Contact us anytime!
            </p>
            <ul className="list-unstyled">
              <li className="mb-3 d-flex align-items-center contact-item">
                <FaMapMarkerAlt className="me-2 fs-5" />
                123 Main Street, Chennai, India
              </li>
              <li className="mb-3 d-flex align-items-center contact-item">
                <FaPhoneAlt className="me-2 fs-5" />
                +91 98765 43210
              </li>
              <li className="mb-3 d-flex align-items-center contact-item">
                <FaEnvelope className="me-2 fs-5" />
                support@cozyhaven.com
              </li>
              <li className="d-flex align-items-center contact-item">
                <FaGlobe className="me-2 fs-5" />
                www.cozyhaven.com
              </li>
            </ul>
          </Card>
        </Col>
      </Row>
    </Container>
  );
};

export default ContactUs;
