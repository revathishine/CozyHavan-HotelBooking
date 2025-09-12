import React from "react";
import UserLandingPage from "./UserLandingPage";
import OwnerLandingPage from "./OwnerLandingPage";
export default function LandingPage() {
  return (
    <div>
      <section
        className="vh-100 d-flex flex-column justify-content-center align-items-center text-center text-black"
        style={{
          backgroundImage:
            "url('https://images.themevault.net/images/blog-38.png')",
          backgroundSize: "cover",
          backgroundPosition: "center",
        }}
      >
        <h1 className="display-3 fw-bold text-black mb-3">Welcome to Hotel Haven</h1>
        <p className="lead mb-4 text-white text-opacity-50 fw-bold">
          Your perfect getaway — luxury, comfort, and unforgettable
          experiences.
        </p>
        <div className="text-center">
          <a href="/login" className="btn btn-lg  btn-outline-light btn-dark px-4 me-2 mb-4">
          Book Now
        </a>
        <a href="/register" className="btn btn-lg btn-outline-light btn-dark px-4 mb-4 me-2 ">
          Join Us
        </a>
        </div>
        
    
      <section className="py-5 bg-light text-center">
        <div className="container">
          <h2 className="fw-bold mb-4">Why Choose Hotel Haven?</h2>
          <div className="row">
            <div className="col-md-4 mb-3">
              <div className="card shadow-lg border-0 h-100">
                <div className="card-body">
                  <h5 className="card-title">🌴 Exotic Locations</h5>
                  <p className="card-text">
                    Explore top destinations with handpicked hotels just for you.
                  </p>
                </div>
              </div>
            </div>
            <div className="col-md-4 mb-3">
              <div className="card shadow-lg border-0 h-100">
                <div className="card-body">
                  <h5 className="card-title">⭐ Premium Comfort</h5>
                  <p className="card-text">
                    Experience luxury stays designed to make you feel at home.
                  </p>
                </div>
              </div>
            </div>
            <div className="col-md-4 mb-3">
              <div className="card shadow-lg border-0 h-100">
                <div className="card-body">
                  <h5 className="card-title">💰 Affordable Deals</h5>
                  <p className="card-text">
                    Get the best prices with exclusive offers and discounts.
                  </p>
                </div>
              </div>
            </div>
          </div>
        </div>
      </section>
       </section>
        
             <div className="mb-10">
        
        <UserLandingPage />
      </div>

      <div>
        
        <OwnerLandingPage />
      </div>

    </div>
  );
}
