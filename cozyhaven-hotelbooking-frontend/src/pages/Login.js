import { useState } from "react";
import { login } from "../api/auth";
import { setToken, getUserRole } from "../utils/auth";
import { useNavigate } from "react-router-dom";
import { Form, Button, Container, Alert, Card } from "react-bootstrap";

const Login = () => {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState("");
  const navigate = useNavigate();

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const data = await login(email, password);
      setToken(data.token);
      const role = getUserRole();
      if (role === "USER") navigate("/user");
      else if (role === "HOTEL_OWNER") navigate("/owner");
    } catch (err) {
      setError(err.response?.data?.message || "Login failed");
    }
  };

  return (
    <Container style={{ maxWidth: "500px", marginTop: "80px" }}>
      <Card
        className="p-4 shadow-lg rounded-4"
        style={{ backgroundColor: "#f9f9f9" }}
      >
        <h3 className="text-center mb-4">Login</h3>
        {error && <Alert variant="danger">{error}</Alert>}
        <Form onSubmit={handleSubmit}>
          <Form.Group className="mb-3">
            <Form.Label>Email</Form.Label>
            <Form.Control
              type="email"
              value={email}
              onChange={(e) => setEmail(e.target.value)}
              required
              className="border border-success"
            />
          </Form.Group>
          <Form.Group className="mb-3">
            <Form.Label>Password</Form.Label>
            <Form.Control
              type="password"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              required
              className="border border-success"
            />
          </Form.Group>
          <div className="d-grid">
            <Button type="submit" variant="success">
              Login
            </Button>
          </div>
        </Form>
      </Card>
    </Container>
  );
};

export default Login;

