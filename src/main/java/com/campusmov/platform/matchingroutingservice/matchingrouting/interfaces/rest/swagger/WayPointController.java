package com.campusmov.platform.matchingroutingservice.matchingrouting.interfaces.rest.swagger;

import com.campusmov.platform.matchingroutingservice.matchingrouting.interfaces.rest.dto.WayPointResource;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collection;

@RequestMapping("/routes/{routeId}/waypoints")
@Tag(name = "WayPoints", description = "Endpoints for managing waypoints in a route")
public interface WayPointController {
    @GetMapping()
    @Operation(summary = "Get all waypoints in a route", description = "Retrieve all waypoints associated with a specific route by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Waypoints retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Route not found or no waypoints available")
    })
    ResponseEntity<Collection<WayPointResource>> getWayPointsByRouteId(@PathVariable String routeId);
}
