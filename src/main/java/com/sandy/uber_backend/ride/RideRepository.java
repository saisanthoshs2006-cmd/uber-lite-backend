package com.sandy.uber_backend.ride;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RideRepository extends JpaRepository<Ride, UUID> {
}
