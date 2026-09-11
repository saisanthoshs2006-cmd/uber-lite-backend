package com.sandy.uber_backend.ride;

import java.util.UUID;

public class RideNotFoundException extends RuntimeException {

	public RideNotFoundException(UUID rideId) {
		super("Ride not found: " + rideId);
	}
}
