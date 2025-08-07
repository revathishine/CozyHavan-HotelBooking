package com.hexaware.cozyhavenproject.entities; 
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Booking {

	 @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer bookingId;

  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;

  @ManyToOne
  @JoinColumn(name = "room_id")
  private Room room;

  private LocalDate checkInDate;
  private LocalDate checkOutDate;
  private Integer numAdults;
  private Integer numChildren;
  private BigDecimal totalPrice;

  private LocalDateTime bookingDate;

  @Enumerated(EnumType.STRING)
  private BookingStatus status;
  
  @OneToMany(mappedBy = "booking")
  private List<BookingPerson> bookingPersons;

  // Getters and Setters
  public Integer getBookingId() { return bookingId; }
  public void setBookingId(Integer bookingId) { this.bookingId = bookingId; }

  public User getUser() { return user; }
  public void setUser(User user) { this.user = user; }

  public Room getRoom() { return room; }
  public void setRoom(Room room) { this.room = room; }

  public LocalDate getCheckInDate() { return checkInDate; }
  public void setCheckInDate(LocalDate checkInDate) { this.checkInDate = checkInDate; }

  public LocalDate getCheckOutDate() { return checkOutDate; }
  public void setCheckOutDate(LocalDate checkOutDate) { this.checkOutDate = checkOutDate; }

  public Integer getNumAdults() { return numAdults; }
  public void setNumAdults(Integer numAdults) { this.numAdults = numAdults; }

  public Integer getNumChildren() { return numChildren; }
  public void setNumChildren(Integer numChildren) { this.numChildren = numChildren; }

  public BigDecimal getTotalPrice() { return totalPrice; }
  public void setTotalPrice(BigDecimal totalPrice) { this.totalPrice = totalPrice; }

  public LocalDateTime getBookingDate() { return bookingDate; }
  public void setBookingDate(LocalDateTime bookingDate) { this.bookingDate = bookingDate; }

  public BookingStatus getStatus() { return status; }
  public void setStatus(BookingStatus status) { this.status = status; }

}
