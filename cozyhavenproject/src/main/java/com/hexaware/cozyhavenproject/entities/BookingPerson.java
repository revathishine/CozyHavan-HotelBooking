package com.hexaware.cozyhavenproject.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class BookingPerson {
	  
	@Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer personId;

  @ManyToOne
  @JoinColumn(name = "booking_id")
  private Booking booking;
  


  private Integer age;
  private Boolean isChild;

  private String name;

  // Getters and Setters
  public Integer getPersonId() { return personId; }
  public void setPersonId(Integer personId) { this.personId = personId; }

  public Booking getBooking() { return booking; }
  public void setBooking(Booking booking) { this.booking = booking; }

  public Integer getAge() { return age; }
  public void setAge(Integer age) { this.age = age; }

  public Boolean getIsChild() { return isChild; }
  public void setIsChild(Boolean isChild) { this.isChild = isChild; }
  
  public void setName(String name) {
	
	this.name=name;
  }

}
