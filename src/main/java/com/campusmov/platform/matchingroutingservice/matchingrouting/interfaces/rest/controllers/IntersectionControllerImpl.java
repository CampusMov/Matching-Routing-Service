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
        var durationQuery = CreateGetTotalDurationRouteForShortestRouteWithDijkstraQueryFromResourceAssembler.toQueryFromResource(resource);
        var intersections = intersectionQueryService.handle(intersectionsQuery);
        var distance = intersectionQueryService.handle(distanceQuery);
        var duration = intersectionQueryService.handle(durationQuery);
        if (intersectionsQuery == null || distanceQuery == null || durationQuery == null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        var routeResource = RouteResourceFromEntityAssembler.toResourceFromEntity(intersections, distance, duration);
        return new ResponseEntity<>(routeResource, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<RouteResource> findShortestPathWithAStar(CreateRouteResource resource) {
        var intersectionsQuery = CreateFindShortestRouteWithAQueryFromResourceAssembler.toQueryFromResource(resource);
        var distanceQuery = CreateGetTotalDistanceRouteForShortestRouteWithAQueryFromResourceAssembler.toQueryFromResource(resource);
        var durationQuery = CreateGetTotalDurationRouteForShortestRouteWithAQueryFromResourceAssembler.toQueryFromResource(resource);
        var intersections = intersectionQueryService.handle(intersectionsQuery);
        var distance = intersectionQueryService.handle(distanceQuery);
        var duration = intersectionQueryService.handle(durationQuery);
        if (intersectionsQuery == null || distanceQuery == null || durationQuery == null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        var routeResource = RouteResourceFromEntityAssembler.toResourceFromEntity(intersections, distance, duration);
        return new ResponseEntity<>(routeResource, HttpStatus.OK);
    }
}
