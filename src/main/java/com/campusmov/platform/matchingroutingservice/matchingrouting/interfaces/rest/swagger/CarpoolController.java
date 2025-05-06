package com.campusmov.platform.matchingroutingservice.matchingrouting.interfaces.rest.swagger;

import com.campusmov.platform.matchingroutingservice.matchingrouting.interfaces.rest.dto.CarpoolResource;
import com.campusmov.platform.matchingroutingservice.matchingrouting.interfaces.rest.dto.CreateCarpoolResource;
import com.campusmov.platform.matchingroutingservice.matchingrouting.interfaces.rest.dto.CreateLocationResource;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RequestMapping(value = "/carpools", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Carpools", description = "Endpoints for managing carpools")
public interface CarpoolController {
    @PostMapping
    @Operation(summary = "Create a new carpool", description = "Create a new carpool")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Carpool created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request"),
    })
    ResponseEntity<CarpoolResource> createCarpool(@RequestBody CreateCarpoolResource resource);

    @GetMapping("/{carpoolId}")
    @Operation(summary = "Get carpool by ID", description = "Get carpool by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Carpool found"),
            @ApiResponse(responseCode = "404", description = "Carpool not found"),
    })
    ResponseEntity<CarpoolResource> getCarpoolById(@PathVariable String carpoolId);

    @GetMapping("/driver/{driverId}/active")
    @Operation(summary = "Get active carpool by driver ID", description = "Get active carpool by driver ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Active carpool found"),
            @ApiResponse(responseCode = "404", description = "Active carpool not found"),
    })
    ResponseEntity<CarpoolResource> getActiveCarpoolByDriverId(@PathVariable String driverId);

    @GetMapping("/driver/{driverId}")
    @Operation(summary = "Get all carpools by driver ID", description = "Get all carpools by driver ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "All carpools found"),
            @ApiResponse(responseCode = "404", description = "No carpools found"),
    })
    ResponseEntity<Collection<CarpoolResource>> getAllCarpoolsByDriverId(@PathVariable String driverId);

    @PostMapping("/available")
    @Operation(summary = "Get all available carpools by schedule ID and pickup location", description = "Get all available carpools by schedule ID and pickup location")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "All available carpools found"),
            @ApiResponse(responseCode = "404", description = "No available carpools found"),
    })
    ResponseEntity<Collection<CarpoolResource>> getAvailableCarpools(@RequestParam String scheduleId, @RequestBody CreateLocationResource resource);

    @PostMapping("/{carpoolId}/start")
    @Operation(summary = "Start a carpool", description = "Start a carpool")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Carpool started successfully"),
            @ApiResponse(responseCode = "404", description = "Carpool not found"),
    })
    ResponseEntity<CarpoolResource> startCarpool(@PathVariable String carpoolId, @RequestBody CreateLocationResource resource);
}
