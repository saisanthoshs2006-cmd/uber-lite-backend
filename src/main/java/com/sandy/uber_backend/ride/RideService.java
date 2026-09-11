package com.sandy.uber_backend.ride;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sandy.uber_backend.ride.RideDtos.AssignDriverRequest;
import com.sandy.uber_backend.ride.RideDtos.ChangeStatusRequest;
import com.sandy.uber_backend.ride.RideDtos.CreateRideRequest;
import com.sandy.uber_backend.ride.RideDtos.RideResponse;

@Service
@Transactional
public class RideService {

	private final RideRepository rideRepository;

	public RideService(RideRepository rideRepository) {
		this.rideRepository = rideRepository;
	}

	public RideResponse createRide(CreateRideRequest request) {
		Ride ride = new Ride(
				request.passengerId(),
				request.pickupLocation(),
				request.dropoffLocation(),
				request.estimatedFare());
		return RideResponse.from(rideRepository.save(ride));
	}

	@Transactional(readOnly = true)
	public RideResponse getRide(UUID rideId) {
		return RideResponse.from(findRide(rideId));
	}

	@Transactional(readOnly = true)
	public List<RideResponse> listRides() {
		return rideRepository.findAll().stream().map(RideResponse::from).toList();
	}

	public RideResponse assignDriver(UUID rideId, AssignDriverRequest request) {
		Ride ride = findRide(rideId);
		ride.assignDriver(request.driverId());
		return RideResponse.from(ride);
	}

	public RideResponse changeStatus(UUID rideId, ChangeStatusRequest request) {
		Ride ride = findRide(rideId);
		ride.changeStatus(request.status());
		return RideResponse.from(ride);
	}

	private Ride findRide(UUID rideId) {
		return rideRepository.findById(rideId)
				.orElseThrow(() -> new RideNotFoundException(rideId));
	}
}
