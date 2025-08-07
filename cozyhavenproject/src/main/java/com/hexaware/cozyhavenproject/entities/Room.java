package com.hexaware.cozyhavenproject.entities;

import java.math.BigDecimal;
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
public class Room {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer roomId;

   @ManyToOne
    @JoinColumn(name = "hotel_id")
    private Hotel hotel;

    private String roomSize;

  @Enumerated(EnumType.STRING)
    private BedType bedType;

    private Integer maxOccupancy;
    private BigDecimal baseFare;
    private Boolean isAc;
    private Boolean availabilityStatus;
    
    @OneToMany(mappedBy = "room")
    private List<Booking> bookings;

    // Getters and Setters
    public Integer getRoomId() { return roomId; }
    public void setRoomId(Integer roomId) { this.roomId = roomId; }

    public Hotel getHotel() { return hotel; }
    public void setHotel(Hotel hotel) { this.hotel = hotel; }

    public String getRoomSize() { return roomSize; }
    public void setRoomSize(String roomSize) { this.roomSize = roomSize; }

    public BedType getBedType() { return bedType; }
    public void setBedType(BedType bedType) { this.bedType = bedType; }

    public Integer getMaxOccupancy() { return maxOccupancy; }
    public void setMaxOccupancy(Integer maxOccupancy) { this.maxOccupancy = maxOccupancy; }

    public BigDecimal getBaseFare() { return baseFare; }
    public void setBaseFare(BigDecimal baseFare) { this.baseFare = baseFare; }

    public Boolean getIsAc() { return isAc; }
    public void setIsAc(Boolean isAc) { this.isAc = isAc; }

    public Boolean getAvailabilityStatus() { return availabilityStatus; }
    public void setAvailabilityStatus(Boolean availabilityStatus) { this.availabilityStatus = availabilityStatus; }

}
