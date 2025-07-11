package com.campusmov.platform.matchingroutingservice.matchingrouting.domain.model.aggregates;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class CarpoolUnitTest {
    @Test
    void TestVerifyMaxPassengers_ValidMaxPassengers_ShouldPass() {
        // Given
        Carpool carpool = new Carpool();
        Integer validMaxPassengers = 4;
        // When
        carpool.verifyMaxPassengers(validMaxPassengers);

        // Then
        Assertions.assertThat(carpool.getMaxPassengers()).isEqualTo(validMaxPassengers);
        Assertions.assertThat(carpool.getAvailableSeats()).isEqualTo(validMaxPassengers);
    }

    @Test
    void TestVerifyMaxPassengers_InvalidMaxPassengers_NullValue_ShouldFail() {
        // Given
        Carpool carpool = new Carpool();
        Integer invalidMaxPassengers = 0;

        // When & Then
        Assertions.assertThatThrownBy(() -> carpool.verifyMaxPassengers(invalidMaxPassengers))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Max passengers must be greater than zero");
    }

    @Test
    void TestVerifyMaxPassengers_InvalidMaxPassengers_ExceedingLimit_ShouldFail() {
        // Given
        Carpool carpool = new Carpool();
        Integer invalidMaxPassengers = 5;

        // When & Then
        Assertions.assertThatThrownBy(() -> carpool.verifyMaxPassengers(invalidMaxPassengers))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Max passengers cannot exceed 4");
    }

    @Test
    void TestVerifyRadius_ValidRadius_ShouldPass() {
        // Given
        Carpool carpool = new Carpool();
        Integer validRadius = 100;

        // When
        carpool.verifyRadius(validRadius);

        // Then
        Assertions.assertThat(carpool.getRadius()).isEqualTo(validRadius);
    }

    @Test
    void TestVerifyRadius_InvalidRadius_NullValue_ShouldFail() {
        // Given
        Carpool carpool = new Carpool();
        Integer invalidRadius = 0;

        // When & Then
        Assertions.assertThatThrownBy(() -> carpool.verifyRadius(invalidRadius))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Radius must be greater than zero");
    }

    @Test
    void TestVerifyRadius_InvalidRadius_ExceedingLimit_ShouldFail() {
        // Given
        Carpool carpool = new Carpool();
        Integer invalidRadius = 101;

        // When & Then
        Assertions.assertThatThrownBy(() -> carpool.verifyRadius(invalidRadius))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Radius cannot exceed 100 meters");
    }

    @Test
    void TestHaversineDistanceMeters_ValidCoordinates_ShouldReturnCorrectDistance() {
        // Given
        Carpool carpool = new Carpool();
        carpool.getOrigin().setLatitude(40.7128);
        carpool.getOrigin().setLongitude(-74.0060);
        carpool.getDestination().setLatitude(34.0522);
        carpool.getDestination().setLongitude(-118.2437);

        // When
        Double distance = carpool.haversineDistanceMeters(
                carpool.getOrigin(),
                carpool.getDestination()
        );

        // Then
        Assertions.assertThat(distance).isCloseTo(
                3_935_746.0,
                Assertions.offset(1.000)
        );
    }
}