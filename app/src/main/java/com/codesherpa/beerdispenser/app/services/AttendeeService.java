package com.codesherpa.beerdispenser.app.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codesherpa.beerdispenser.app.models.Attendee;
import com.codesherpa.beerdispenser.app.repositories.AttendeeRepository;

@Service
public class AttendeeService {

    @Autowired
    private AttendeeRepository attendeeRepository;

    public Attendee createAttendee(Attendee Attendee) {
        return attendeeRepository.save(Attendee);
    }

    public Attendee getAttendee(Long id) {
        return attendeeRepository.findById(id).orElse(null);
    }

    public List<Attendee> getAllAttendees() {
        return attendeeRepository.findAll();
    }

    public void deleteAttendee(Long id) {
        attendeeRepository.deleteById(id);
    }
}
