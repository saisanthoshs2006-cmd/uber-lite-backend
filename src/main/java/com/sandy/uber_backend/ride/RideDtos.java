package com.sandy.uber_backend.ride;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public final class RideDtos {

	private RideDtos() {
	}

	public record CreateRideRequest(
			@NotBlank String passengerId,
			@NotBlank String pickupLocation,
			@NotBlank String dropoffLocation,
			@NotNull @DecimalMin("0.01") BigDecimal estimatedFare) {
	}

	public record AssignDriverRequest(@NotBlank String driverId) {
	}

	public record ChangeStatusRequest(@NotNull RideStatus status) {
	}

	public record RideResponse(
			UUID id,
			String passengerId,
			String driverId,
			String pickupLocation,
			String dropoffLocation,
			BigDecimal estimatedFare,
			RideStatus status,
			Instant createdAt,
			Instant updatedAt) {

		static RideResponse from(Ride ride) {
			return new RideResponse(
					ride.getId(),
					ride.getPassengerId(),
					ride.getDriverId(),
					ride.getPickupLocation(),
					ride.getDropoffLocation(),
					ride.getEstimatedFare(),
					ride.getStatus(),
					ride.getCreatedAt(),
					ride.getUpdatedAt());
		}
	}
}
