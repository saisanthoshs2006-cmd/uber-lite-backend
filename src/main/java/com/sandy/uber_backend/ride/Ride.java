package com.sandy.uber_backend.ride;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "rides")
public class Ride {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	@Column(nullable = false, length = 100)
	private String passengerId;

	@Column(length = 100)
	private String driverId;

	@Column(nullable = false, length = 255)
	private String pickupLocation;

	@Column(nullable = false, length = 255)
	private String dropoffLocation;

	@Column(nullable = false, precision = 10, scale = 2)
	private BigDecimal estimatedFare;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 30)
	private RideStatus status;

	@Column(nullable = false, updatable = false)
	private Instant createdAt;

	@Column(nullable = false)
	private Instant updatedAt;

	protected Ride() {
	}

	public Ride(String passengerId, String pickupLocation, String dropoffLocation, BigDecimal estimatedFare) {
		this.passengerId = passengerId;
		this.pickupLocation = pickupLocation;
		this.dropoffLocation = dropoffLocation;
		this.estimatedFare = estimatedFare;
		this.status = RideStatus.REQUESTED;
	}

	public void assignDriver(String driverId) {
		if (status != RideStatus.REQUESTED) {
			throw new IllegalStateException("Only requested rides can be assigned");
		}
		this.driverId = driverId;
		this.status = RideStatus.ACCEPTED;
	}

	public void changeStatus(RideStatus nextStatus) {
		if (!isValidTransition(status, nextStatus)) {
			throw new IllegalStateException("Cannot change ride status from " + status + " to " + nextStatus);
		}
		this.status = nextStatus;
	}

	private boolean isValidTransition(RideStatus current, RideStatus next) {
		return switch (current) {
			case REQUESTED -> next == RideStatus.CANCELLED;
			case ACCEPTED -> next == RideStatus.IN_PROGRESS || next == RideStatus.CANCELLED;
			case IN_PROGRESS -> next == RideStatus.COMPLETED;
			case COMPLETED, CANCELLED -> false;
		};
	}

	public UUID getId() {
		return id;
	}

	public String getPassengerId() {
		return passengerId;
	}

	public String getDriverId() {
		return driverId;
	}

	public String getPickupLocation() {
		return pickupLocation;
	}

	public String getDropoffLocation() {
		return dropoffLocation;
	}

	public BigDecimal getEstimatedFare() {
		return estimatedFare;
	}

	public RideStatus getStatus() {
		return status;
	}

	public Instant getCreatedAt() {
		return createdAt;
	}

	public Instant getUpdatedAt() {
		return updatedAt;
	}

	@jakarta.persistence.PrePersist
	void onCreate() {
		Instant now = Instant.now();
		createdAt = now;
		updatedAt = now;
	}

	@jakarta.persistence.PreUpdate
	void onUpdate() {
		updatedAt = Instant.now();
	}
}
