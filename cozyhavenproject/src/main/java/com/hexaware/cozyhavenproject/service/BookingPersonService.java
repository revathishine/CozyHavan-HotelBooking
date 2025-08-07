package com.hexaware.cozyhavenproject.service;

import com.hexaware.cozyhavenproject.entities.BookingPerson;
import java.util.List;

public interface BookingPersonService {

	BookingPerson createPerson(BookingPerson person);
    BookingPerson getPersonById(Integer id);
    List<BookingPerson> getAllPersons();
    BookingPerson updatePerson(BookingPerson person);
    void deletePerson(Integer id);
}
