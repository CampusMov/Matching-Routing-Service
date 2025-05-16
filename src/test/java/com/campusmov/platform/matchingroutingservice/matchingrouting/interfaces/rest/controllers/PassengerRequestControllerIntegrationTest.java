package com.campusmov.platform.matchingroutingservice.matchingrouting.interfaces.rest.controllers;

import com.campusmov.platform.matchingroutingservice.matchingrouting.interfaces.rest.dto.CreateLocationResource;
import com.campusmov.platform.matchingroutingservice.matchingrouting.interfaces.rest.dto.CreatePassengerRequestResource;
import com.campusmov.platform.matchingroutingservice.matchingrouting.interfaces.rest.dto.PassengerRequestResource;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PassengerRequestControllerIntegrationTest {
    @Container
    @ServiceConnection
    static MySQLContainer<?> mySQLContainer = new MySQLContainer<>("mysql:9.2.0");

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    void TestCreatePassengerRequest_ValidAllContentRequest_ShouldPass() {
        // Given
        CreatePassengerRequestResource createPassengerRequestResource = CreatePassengerRequestResource
                .builder()
                .passengerId("12345678-1234-5678-1234-567812345678")
                .carpoolId("87654321-4321-8765-4321-876543214321")
                .pickupLocation(CreateLocationResource.builder()
                        .name("Pickup Location")
                        .address("123 Pickup St")
                        .latitude(12.345678)
                        .longitude(98.765432)
                        .build())
                .requestedSeats(2)
                .build();

        // When
        ResponseEntity<PassengerRequestResource> response = testRestTemplate.postForEntity("/passenger-requests", createPassengerRequestResource, PassengerRequestResource.class);

        // Then
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        Assertions.assertThat(response.getBody()).isNotNull();
        Assertions.assertThat(response.getBody().passengerId()).isEqualTo(createPassengerRequestResource.passengerId());
        Assertions.assertThat(response.getBody().carpoolId()).isEqualTo(createPassengerRequestResource.carpoolId());
        Assertions.assertThat(response.getBody().pickupLocationName()).isEqualTo(createPassengerRequestResource.pickupLocation().name());
        Assertions.assertThat(response.getBody().pickupLocationAddress()).isEqualTo(createPassengerRequestResource.pickupLocation().address());
        Assertions.assertThat(response.getBody().requestedSeats()).isEqualTo(createPassengerRequestResource.requestedSeats());
        Assertions.assertThat(response.getBody().status()).isEqualTo("PENDING");
    }

    @Test
    void TestCreatePassengerRequest_ValidAllContentRequestWithNullRequestedSeats_ShouldPass() {
        // Given
        CreatePassengerRequestResource createPassengerRequestResource = CreatePassengerRequestResource
                .builder()
                .passengerId("12345678-1234-5678-1234-567812345678")
                .carpoolId("87654321-4321-8765-4321-876543214321")
                .pickupLocation(CreateLocationResource.builder()
                        .name("Pickup Location")
                        .address("123 Pickup St")
                        .latitude(12.345678)
                        .longitude(98.765432)
                        .build())
                .requestedSeats(null)
                .build();

        // When
        ResponseEntity<PassengerRequestResource> response = testRestTemplate.postForEntity("/passenger-requests", createPassengerRequestResource, PassengerRequestResource.class);

        // Then
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        Assertions.assertThat(response.getBody()).isNotNull();
        Assertions.assertThat(response.getBody().passengerId()).isEqualTo(createPassengerRequestResource.passengerId());
        Assertions.assertThat(response.getBody().carpoolId()).isEqualTo(createPassengerRequestResource.carpoolId());
        Assertions.assertThat(response.getBody().pickupLocationName()).isEqualTo(createPassengerRequestResource.pickupLocation().name());
        Assertions.assertThat(response.getBody().pickupLocationAddress()).isEqualTo(createPassengerRequestResource.pickupLocation().address());
        Assertions.assertThat(response.getBody().requestedSeats()).isEqualTo(1);
        Assertions.assertThat(response.getBody().status()).isEqualTo("PENDING");
    }
}