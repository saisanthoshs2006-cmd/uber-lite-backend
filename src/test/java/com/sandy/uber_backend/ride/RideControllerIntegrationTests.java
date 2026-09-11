package com.sandy.uber_backend.ride;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class RideControllerIntegrationTests {

	@Autowired
	private MockMvc mockMvc;

	@Test
	void createsRideAndAssignsDriver() throws Exception {
		String response = mockMvc.perform(post("/api/rides")
				.contentType(MediaType.APPLICATION_JSON)
				.content("""
						{
						  "passengerId": "passenger-1",
						  "pickupLocation": "Koramangala",
						  "dropoffLocation": "Indiranagar",
						  "estimatedFare": 245.50
						}
						"""))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.status").value("REQUESTED"))
				.andReturn()
				.getResponse()
				.getContentAsString();

		String rideId = response.replaceAll(".*\"id\":\"([^\"]+)\".*", "$1");

		mockMvc.perform(patch("/api/rides/{rideId}/driver", rideId)
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"driverId\":\"driver-7\"}"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.driverId").value("driver-7"))
				.andExpect(jsonPath("$.status").value("ACCEPTED"));
	}

	@Test
	void rejectsInvalidRideRequest() throws Exception {
		mockMvc.perform(post("/api/rides")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"passengerId\":\"\",\"estimatedFare\":0}"))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.message").value("Validation failed"));
	}
}
