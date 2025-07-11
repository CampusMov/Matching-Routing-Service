package com.campusmov.platform.matchingroutingservice.matchingrouting.interfaces.rest.controllers;

import com.campusmov.platform.matchingroutingservice.matchingrouting.domain.model.queries.GetRouteByCarpoolId;
import com.campusmov.platform.matchingroutingservice.matchingrouting.domain.model.queries.GetRouteByIdQuery;
import com.campusmov.platform.matchingroutingservice.matchingrouting.domain.services.RouteCommandService;
import com.campusmov.platform.matchingroutingservice.matchingrouting.domain.services.RouteQueryService;
import com.campusmov.platform.matchingroutingservice.matchingrouting.interfaces.rest.dto.RouteCarpoolResource;
import com.campusmov.platform.matchingroutingservice.matchingrouting.interfaces.rest.dto.UpdateCurrentLocationRouteCarpoolResource;
import com.campusmov.platform.matchingroutingservice.matchingrouting.interfaces.rest.swagger.RouteController;
import com.campusmov.platform.matchingroutingservice.matchingrouting.interfaces.rest.transform.CreateUpdateCurrentLocationCommandFromResourceAssembler;
import com.campusmov.platform.matchingroutingservice.matchingrouting.interfaces.rest.transform.RouteCarpoolResourceFromEntityAssembler;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RouteControllerImpl implements RouteController {
    private final RouteCommandService routeCommandService;
    private final RouteQueryService routeQueryService;

    @Override
    public ResponseEntity<RouteCarpoolResource> getRouteById(String routeId) {
        var query = new GetRouteByIdQuery(routeId);
        var route = routeQueryService.handle(query);
        if (route.isEmpty()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        var routeResource = RouteCarpoolResourceFromEntityAssembler.toResourceFromEntity(route.get());
        return new ResponseEntity<>(routeResource, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<RouteCarpoolResource> getRouteByCarpoolId(String carpoolId) {
        var query = new GetRouteByCarpoolId(carpoolId);
        var route = routeQueryService.handle(query);
        if (route.isEmpty()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        var routeResource = RouteCarpoolResourceFromEntityAssembler.toResourceFromEntity(route.get());
        return new ResponseEntity<>(routeResource, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<RouteCarpoolResource> updateCurrentLocation(String routeId, UpdateCurrentLocationRouteCarpoolResource resource) {
        var command = CreateUpdateCurrentLocationCommandFromResourceAssembler.toCommandFromResource(routeId, resource);
        var updatedRoute = routeCommandService.handle(command);
        if (updatedRoute.isEmpty() || updatedRoute.get().getId().isEmpty()) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        var routeResource = RouteCarpoolResourceFromEntityAssembler.toResourceFromEntity(updatedRoute.get());
        return new ResponseEntity<>(routeResource, HttpStatus.OK);
    }
}
