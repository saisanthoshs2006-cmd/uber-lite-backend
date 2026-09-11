package com.sandy.uber_backend.ride;

import java.net.URI;
import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

import com.sandy.uber_backend.ride.RideDtos.AssignDriverRequest;
import com.sandy.uber_backend.ride.RideDtos.ChangeStatusRequest;
import com.sandy.uber_backend.ride.RideDtos.CreateRideRequest;
import com.sandy.uber_backend.ride.RideDtos.RideResponse;

@RestController
@RequestMapping("/api/rides")
@Validated
public class RideController {

	private final RideService rideService;

	public RideController(RideService rideService) {
		this.rideService = rideService;
	}

	@PostMapping
	public ResponseEntity<RideResponse> createRide(@Valid @RequestBody CreateRideRequest request) {
		RideResponse response = rideService.createRide(request);
		return ResponseEntity.created(URI.create("/api/rides/" + response.id())).body(response);
	}

	@GetMapping
	public List<RideResponse> listRides() {
		return rideService.listRides();
	}

	@GetMapping("/{rideId}")
	public RideResponse getRide(@PathVariable UUID rideId) {
		return rideService.getRide(rideId);
	}

	@PatchMapping("/{rideId}/driver")
	public RideResponse assignDriver(
			@PathVariable UUID rideId,
			@Valid @RequestBody AssignDriverRequest request) {
		return rideService.assignDriver(rideId, request);
	}

	@PatchMapping("/{rideId}/status")
	public RideResponse changeStatus(
			@PathVariable UUID rideId,
			@Valid @RequestBody ChangeStatusRequest request) {
		return rideService.changeStatus(rideId, request);
	}
}
