package com.campusmov.platform.matchingroutingservice.matchingrouting.interfaces.rest.swagger;

import com.campusmov.platform.matchingroutingservice.matchingrouting.interfaces.rest.dto.CreatePassengerRequestResource;
import com.campusmov.platform.matchingroutingservice.matchingrouting.interfaces.rest.dto.PassengerRequestResource;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RequestMapping(value = "/passenger-requests", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Passenger Requests", description = "Endpoints for managing passenger requests")
public interface PassengerRequestController {
    @PostMapping
    @Operation(summary = "Create a new passenger request", description = "Create a new passenger request")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Passenger request created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request"),
    })
    ResponseEntity<PassengerRequestResource> createPassengerRequest(@RequestBody CreatePassengerRequestResource resource);

    @PostMapping("/{passengerRequestId}/accept")
    @Operation(summary = "Accept a passenger request", description = "Accept a passenger request")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Passenger request accepted successfully"),
            @ApiResponse(responseCode = "404", description = "Passenger request not found"),
    })
    ResponseEntity<PassengerRequestResource> acceptPassengerRequest(@PathVariable String passengerRequestId);

    @PostMapping("/{passengerRequestId}/reject")
    @Operation(summary = "Reject a passenger request", description = "Reject a passenger request")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Passenger request rejected successfully"),
            @ApiResponse(responseCode = "404", description = "Passenger request not found"),
    })
    ResponseEntity<PassengerRequestResource> rejectPassengerRequest(@PathVariable String passengerRequestId);
}
