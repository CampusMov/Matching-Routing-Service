package com.campusmov.platform.matchingroutingservice.matchingrouting.interfaces.rest.controllers;

import com.campusmov.platform.matchingroutingservice.matchingrouting.interfaces.rest.dto.CarpoolResource;
import com.campusmov.platform.matchingroutingservice.matchingrouting.interfaces.rest.dto.CreateCarpoolResource;
import com.campusmov.platform.matchingroutingservice.matchingrouting.interfaces.rest.dto.CreateLocationResource;
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

import java.time.LocalTime;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CarpoolControllerIntegrationTest {
    @Container
    @ServiceConnection
    static MySQLContainer<?> mySQLContainer = new MySQLContainer<>("mysql:9.2.0");

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    void TestCreateCarpool_ValidAllContentRequest_ShouldPass() {
        // Given
        CreateCarpoolResource createCarpoolResource = CreateCarpoolResource
                .builder()
                .driverId("12345678-1234-5678-1234-5123123123678")
                .vehicleId("87654321-4321-8765-4321-1231231231231")
                .maxPassengers(4)
                .scheduleId("12345678-1234-5678-1234-1231312334")
                .radius(10)
                .origin(CreateLocationResource.builder()
                        .name("Origin Location")
                        .address("123 Origin St")
                        .latitude(12.345678)
                        .longitude(98.765432)
                        .build())
                .destination(CreateLocationResource.builder()
                        .name("Destination Location")
                        .address("456 Destination Ave")
                        .latitude(23.456789)
                        .longitude(87.654321)
                        .build())
                .classDay("MONDAY")
                .startedClassTime(LocalTime.parse("07:00:00"))
                .endedClassTime(LocalTime.parse("08:00:00"))
                .build();

        // When
        ResponseEntity<CarpoolResource> response = testRestTemplate.postForEntity("/carpools", createCarpoolResource, CarpoolResource.class);

        // Then
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        Assertions.assertThat(response.getBody()).isNotNull();
    }

    @Test
    void TestCreateCarpool_NullOrigin_ShouldNotPass() {
        // Given
        CreateCarpoolResource createCarpoolResource = CreateCarpoolResource
                .builder()
                .driverId("12345678-3645-5678-1234-567812345678")
                .vehicleId("87654321-7968-8765-4321-876543214321")
                .maxPassengers(4)
                .scheduleId("12345678-6789-5678-1234-567812345678")
                .radius(10)
                .origin(null)
                .destination(CreateLocationResource.builder()
                        .name("Destination Location")
                        .address("456 Destination Ave")
                        .latitude(23.456789)
                        .longitude(87.654321)
                        .build())
                .build();

        // When
        ResponseEntity<CarpoolResource> response = testRestTemplate.postForEntity("/carpools", createCarpoolResource, CarpoolResource.class);

        // Then
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Test
    void TestCreateCarpool_NullDestination_ShouldNotPass() {
        // Given
        CreateCarpoolResource createCarpoolResource = CreateCarpoolResource
                .builder()
                .driverId("12345678-1234-2435-1234-567812345678")
                .vehicleId("87654321-4321-2535-4321-876543214321")
                .maxPassengers(4)
                .scheduleId("12345678-1234-5678-1234-567812345678")
                .radius(10)
                .origin(CreateLocationResource.builder()
                        .name("Origin Location")
                        .address("123 Origin St")
                        .latitude(12.345678)
                        .longitude(98.765432)
                        .build())
                .destination(null)
                .build();

        // When
        ResponseEntity<CarpoolResource> response = testRestTemplate.postForEntity("/carpools", createCarpoolResource, CarpoolResource.class);

        // Then
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}