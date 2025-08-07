package com.hexaware.cozyhavenproject.entities;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Hotel {

     @Id
      @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer hotelId;

    private String name;
    private String location;
    private String description;

       @ManyToOne
       @JoinColumn(name = "owner_id")
    private User owner;

       @Lob
    private String amenities;
       
       @OneToMany(mappedBy = "hotel")
       private List<Room> rooms;

       @OneToMany(mappedBy = "hotel")
       private List<Review> reviews;


    // Getters and Setters
    public Integer getHotelId() { return hotelId; }
    public void setHotelId(Integer hotelId) { this.hotelId = hotelId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public User getOwner() { return owner; }
    public void setOwner(User owner) { this.owner = owner; }

    public String getAmenities() { return amenities; }
    public void setAmenities(String amenities) { this.amenities = amenities; }

}
