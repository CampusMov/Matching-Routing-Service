package com.campusmov.platform.matchingroutingservice.matchingrouting.interfaces.rest.swagger;

import com.campusmov.platform.matchingroutingservice.matchingrouting.interfaces.rest.dto.RouteCarpoolResource;
import com.campusmov.platform.matchingroutingservice.matchingrouting.interfaces.rest.dto.UpdateCurrentLocationRouteCarpoolResource;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RequestMapping(value = "/routes", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Routes", description = "Endpoints for managing routes")
public interface RouteController {
    @GetMapping("/{routeId}")
    @Operation(summary = "Get route by ID", description = "Get route by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Route found"),
            @ApiResponse(responseCode = "404", description = "Route not found"),
    })
    ResponseEntity<RouteCarpoolResource> getRouteById(@PathVariable String routeId);

    @GetMapping()
    @Operation(summary = "Get route by carpool ID", description = "Get route by carpool ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Route found"),
            @ApiResponse(responseCode = "404", description = "Route not found"),
    })
    ResponseEntity<RouteCarpoolResource> getRouteByCarpoolId(@RequestParam String carpoolId);

    @PutMapping("/{routeId}/current-location")
    @Operation(summary = "Update current location of the route", description = "Update current location of the route")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Current location updated successfully"),
            @ApiResponse(responseCode = "404", description = "Route not found"),
    })
    ResponseEntity<RouteCarpoolResource> updateCurrentLocation(@PathVariable String routeId, @RequestBody UpdateCurrentLocationRouteCarpoolResource resource);
}
