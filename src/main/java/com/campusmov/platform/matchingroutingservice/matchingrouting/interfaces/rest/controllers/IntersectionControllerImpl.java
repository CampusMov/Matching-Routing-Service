package com.campusmov.platform.matchingroutingservice.matchingrouting.interfaces.rest.controllers;

import com.campusmov.platform.matchingroutingservice.matchingrouting.domain.services.IntersectionQueryService;
import com.campusmov.platform.matchingroutingservice.matchingrouting.interfaces.rest.dto.CreateRouteResource;
import com.campusmov.platform.matchingroutingservice.matchingrouting.interfaces.rest.dto.RouteResource;
import com.campusmov.platform.matchingroutingservice.matchingrouting.interfaces.rest.swagger.IntersectionController;
import com.campusmov.platform.matchingroutingservice.matchingrouting.interfaces.rest.transform.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class IntersectionControllerImpl implements IntersectionController {
    private final IntersectionQueryService intersectionQueryService;

    @Override
    public ResponseEntity<RouteResource> findShortestPathWithDijkstra(CreateRouteResource resource) {
        var intersectionsQuery = CreateFindShortestRouteWithDijkstraQueryFromResourceAssembler.toQueryFromResource(resource);
        var distanceQuery = CreateGetTotalDistanceRouteForShortestRouteWithDijkstraQueryFromResourceAssembler.toQueryFromResource(resource);
        var intersections = intersectionQueryService.handle(intersectionsQuery);
        var distance = intersectionQueryService.handle(distanceQuery);
        // TODO: Implement duration calculation
        var duration = 0.0;
        if (intersectionsQuery == null || distanceQuery == null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        var routeResource = RouteResourceFromEntityAssembler.toResourceFromEntity(intersections, distance, duration);
        return new ResponseEntity<>(routeResource, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<RouteResource> findShortestPathWithAStar(CreateRouteResource resource) {
        var intersectionsQuery = CreateFindShortestRouteWithAQueryFromResourceAssembler.toQueryFromResource(resource);
        var distanceQuery = CreateGetTotalDistanceRouteForShortestRouteWithAQueryFromResourceAssembler.toQueryFromResource(resource);
        var intersections = intersectionQueryService.handle(intersectionsQuery);
        var distance = intersectionQueryService.handle(distanceQuery);
        // TODO: Implement duration calculation
        var duration = 0.0;
        if (intersectionsQuery == null || distanceQuery == null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        var routeResource = RouteResourceFromEntityAssembler.toResourceFromEntity(intersections, distance, duration);
        return new ResponseEntity<>(routeResource, HttpStatus.OK);
    }
}
