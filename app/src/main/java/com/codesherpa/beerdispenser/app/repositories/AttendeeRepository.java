package com.codesherpa.beerdispenser.app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.codesherpa.beerdispenser.app.models.Attendee;
public interface AttendeeRepository extends JpaRepository<Attendee, Integer> {
}