package com.campusmov.platform.matchingroutingservice.matchingrouting.interfaces.rest.controllers;

import com.campusmov.platform.matchingroutingservice.matchingrouting.domain.model.queries.GetAllWayPointsByRouteIdQuery;
import com.campusmov.platform.matchingroutingservice.matchingrouting.domain.services.RouteQueryService;
import com.campusmov.platform.matchingroutingservice.matchingrouting.interfaces.rest.dto.WayPointResource;
import com.campusmov.platform.matchingroutingservice.matchingrouting.interfaces.rest.swagger.WayPointController;
import com.campusmov.platform.matchingroutingservice.matchingrouting.interfaces.rest.transform.WayPointResourceFromEntityAssembler;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequiredArgsConstructor
public class WayPointControllerImpl implements WayPointController {
    private final RouteQueryService routeQueryService;

    @Override
    public ResponseEntity<Collection<WayPointResource>> getWayPointsByRouteId(String routeId) {
        var query = new GetAllWayPointsByRouteIdQuery(routeId);
        var wayPoints = routeQueryService.handle(query);
        var wayPointResources = wayPoints.stream()
                .map(WayPointResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return new ResponseEntity<>(wayPointResources, HttpStatus.OK);
    }
}
