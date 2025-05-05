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
                .driverId("12345678-1234-5678-1234-567812345678")
                .vehicleId("87654321-4321-8765-4321-876543214321")
                .maxPassengers(4)
                .scheduleId("12345678-1234-5678-1234-567812345678")
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
                .build();

        // When
        ResponseEntity<CarpoolResource> response = testRestTemplate.postForEntity("/carpools", createCarpoolResource, CarpoolResource.class);

        // Then
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        Assertions.assertThat(response.getBody()).isNotNull();
    }

    @Test
    void TestCreateCarpool_NullMaxPassengers_NullRadius_ValidRequest_ShouldPass(){
        // Given
        CreateCarpoolResource createCarpoolResource = CreateCarpoolResource
                .builder()
                .driverId("12345678-1234-5678-1234-567812345678")
                .vehicleId("87654321-4321-8765-4321-876543214321")
                .maxPassengers(null)
                .scheduleId("12345678-1234-5678-1234-567812345678")
                .radius(null)
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
                .build();

        // When
        ResponseEntity<CarpoolResource> response = testRestTemplate.postForEntity("/carpools", createCarpoolResource, CarpoolResource.class);

        // Then
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        Assertions.assertThat(response.getBody()).isNotNull();
        Assertions.assertThat(response.getBody().radius()).isEqualTo(50);
        Assertions.assertThat(response.getBody().status()).isEqualTo("CREATED");
        Assertions.assertThat(response.getBody().originName()).isEqualTo("Origin Location");
        Assertions.assertThat(response.getBody().originAddress()).isEqualTo("123 Origin St");
        Assertions.assertThat(response.getBody().destinationName()).isEqualTo("Destination Location");
        Assertions.assertThat(response.getBody().destinationAddress()).isEqualTo("456 Destination Ave");
        Assertions.assertThat(response.getBody().isVisible()).isEqualTo(true);
    }

    @Test
    void TestCreateCarpool_NullOrigin_ShouldNotPass() {
        // Given
        CreateCarpoolResource createCarpoolResource = CreateCarpoolResource
                .builder()
                .driverId("12345678-1234-5678-1234-567812345678")
                .vehicleId("87654321-4321-8765-4321-876543214321")
                .maxPassengers(4)
                .scheduleId("12345678-1234-5678-1234-567812345678")
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
                .driverId("12345678-1234-5678-1234-567812345678")
                .vehicleId("87654321-4321-8765-4321-876543214321")
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