import { Navbar, Nav, Container } from "react-bootstrap";
import { Link, useNavigate } from "react-router-dom";
import { logout, getUserRole, isAuthenticated } from "../utils/auth";

const NavBar = () => {
  const navigate = useNavigate();
  const role = getUserRole();

  const handleLogout = () => {
    logout();
    navigate("/login");
  };

  return (
    <Navbar bg="dark" variant="dark" expand="lg">
      <Container>
        <Navbar.Brand as={Link} to="/">CozyHaven</Navbar.Brand>
        <Nav className="ms-auto">
          {!isAuthenticated() && <Nav.Link as={Link} to="/login">Login</Nav.Link>}
          {isAuthenticated() && role === "USER" && (
            <Nav.Link as={Link} to="/user">Dashboard</Nav.Link>
          )}
          {isAuthenticated() && role === "HOTEL_OWNER" && (
            <Nav.Link as={Link} to="/owner">Owner Dashboard</Nav.Link>
          )}
          {isAuthenticated() && <Nav.Link onClick={handleLogout}>Logout</Nav.Link>}
        </Nav>
      </Container>
    </Navbar>
  );
};

export default NavBar;

