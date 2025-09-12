
import './App.css';

import React from 'react';
import { BrowserRouter, Routes, Route, Navigate } from 'react-router-dom';

import Header from "./components/Header"; 
import Footer from "./components/Footer";
import LandingPage from "./components/LandingPage";
import Login from "./pages/Login";
import Register from "./pages/Register";
import UserDashboard from "./pages/UserDashboard";
import OwnerDashboard from "./pages/OwnerDashboard";
import HotelList from "./pages/Hotellist";
import HotelDetails from "./pages/Hoteldetails";
import Booking from "./pages/Booking";
import Profile from "./pages/Profile";
import ContactUs from './pages/ContactUs';
// import BookingForm from "./pages/Bookinguser";
import UserLandingPage from './components/UserLandingPage';
import OwnerLandingPage from './components/OwnerLandingPage';

import { isAuthenticated, getUserRole } from "./utils/auth";

const PrivateRoute = ({ children, role }) => {
  if (!isAuthenticated()) return <Navigate to="/login" />;
  if (role && getUserRole() !== role) return <Navigate to="/login" />;
  return children;
};

function App() {
  return (
    <BrowserRouter>
      <Header/>
      <Routes>
      
         <Route path="/" element={<LandingPage />} />
        <Route path="/user-landing" element={<UserLandingPage />} />
        <Route path="/owner-landing" element={<OwnerLandingPage />} />
        <Route path="/login" element={<Login />} />
        <Route path="/register" element={<Register />} />
        <Route path="/hotels" element={<HotelList />} />
        <Route path="/hotels/:id" element={<HotelDetails />} />
        <Route path="/booking" element={<Booking />} />
        <Route path="/ContactUs" element={<ContactUs />} />

        
  
        <Route 
          path="/user" 
          element={
            <PrivateRoute role="USER">
              <UserDashboard />
            </PrivateRoute>
          } 
        />
        <Route 
          path="/owner" 
          element={
            <PrivateRoute role="HOTEL_OWNER">
              <OwnerDashboard />
            </PrivateRoute>
          } 
        />
        <Route 
          path="/profile" 
          element={
            <PrivateRoute>
              <Profile />
            </PrivateRoute>
          } 
        />

        <Route path="*" element={<Navigate to="/" />} />
      </Routes>
      <Footer />
    </BrowserRouter>
  );
}

export default App;

