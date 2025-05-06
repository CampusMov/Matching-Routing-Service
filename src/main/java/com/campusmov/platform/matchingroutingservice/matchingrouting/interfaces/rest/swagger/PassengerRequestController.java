package com.campusmov.platform.matchingroutingservice.matchingrouting.interfaces.rest.swagger;

import com.campusmov.platform.matchingroutingservice.matchingrouting.interfaces.rest.dto.CreatePassengerRequestResource;
import com.campusmov.platform.matchingroutingservice.matchingrouting.interfaces.rest.dto.PassengerRequestResource;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
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
}
