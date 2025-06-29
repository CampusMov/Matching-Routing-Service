package com.campusmov.platform.matchingroutingservice.matchingrouting.interfaces.rest.swagger;

import com.campusmov.platform.matchingroutingservice.matchingrouting.interfaces.rest.dto.CreateRouteResource;
import com.campusmov.platform.matchingroutingservice.matchingrouting.interfaces.rest.dto.RouteResource;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RequestMapping(value = "/routes", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Routes", description = "Endpoints for managing routes")
public interface IntersectionController {
    @PostMapping("/shortest/dijkstra")
    @Operation(summary = "Find the shortest path using Dijkstra's algorithm", description = "Find the shortest path between two locations using Dijkstra's algorithm")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Shortest path found successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request"),
            @ApiResponse(responseCode = "404", description = "Path not found")
    })
    ResponseEntity<RouteResource> findShortestPathWithDijkstra(@RequestBody CreateRouteResource resource);

    @PostMapping("/shortest/a-star")
    @Operation(summary = "Find the shortest path using A* algorithm", description = "Find the shortest path between two locations using A* algorithm")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Shortest path found successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request"),
            @ApiResponse(responseCode = "404", description = "Path not found")
    })
    ResponseEntity<RouteResource> findShortestPathWithAStar(@RequestBody CreateRouteResource resource);
}
