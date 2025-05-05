package com.campusmov.platform.matchingroutingservice.matchingrouting.interfaces.rest.controllers;

import com.campusmov.platform.matchingroutingservice.matchingrouting.domain.model.queries.GetCarpoolByDriverIdAndIsActiveQuery;
import com.campusmov.platform.matchingroutingservice.matchingrouting.domain.model.queries.GetCarpoolByIdQuery;
import com.campusmov.platform.matchingroutingservice.matchingrouting.domain.services.CarpoolCommandService;
import com.campusmov.platform.matchingroutingservice.matchingrouting.domain.services.CarpoolQueryService;
import com.campusmov.platform.matchingroutingservice.matchingrouting.interfaces.rest.dto.CarpoolResource;
import com.campusmov.platform.matchingroutingservice.matchingrouting.interfaces.rest.dto.CreateCarpoolResource;
import com.campusmov.platform.matchingroutingservice.matchingrouting.interfaces.rest.swagger.CarpoolController;
import com.campusmov.platform.matchingroutingservice.matchingrouting.interfaces.rest.transform.CarpoolResourceFromEntityAssembler;
import com.campusmov.platform.matchingroutingservice.matchingrouting.interfaces.rest.transform.CreateCarpoolCommandFromResourceAssembler;
import com.campusmov.platform.matchingroutingservice.shared.domain.model.valueobjects.DriverId;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CarpoolControllerImpl implements CarpoolController {
    private final CarpoolCommandService carpoolCommandService;
    private final CarpoolQueryService carpoolQueryService;

    @Override
    public ResponseEntity<CarpoolResource> createCarpool(CreateCarpoolResource resource) {
        var command = CreateCarpoolCommandFromResourceAssembler.toCommandFromResource(resource);
        var carpool = carpoolCommandService.handle(command);
        if (carpool.isEmpty() || carpool.get().getId().isEmpty()) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        var carpoolResource = CarpoolResourceFromEntityAssembler.toResourceFromEntity(carpool.get());
        return new ResponseEntity<>(carpoolResource, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<CarpoolResource> getCarpoolById(String carpoolId) {
        var query = new GetCarpoolByIdQuery(carpoolId);
        var carpool = carpoolQueryService.handle(query);
        if (carpool.isEmpty()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        var carpoolResource = CarpoolResourceFromEntityAssembler.toResourceFromEntity(carpool.get());
        return new ResponseEntity<>(carpoolResource, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<CarpoolResource> getActiveCarpoolByDriverId(String driverId) {
        var query = new GetCarpoolByDriverIdAndIsActiveQuery(new DriverId(driverId));
        var carpool = carpoolQueryService.handle(query);
        if (carpool.isEmpty()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        var carpoolResource = CarpoolResourceFromEntityAssembler.toResourceFromEntity(carpool.get());
        return new ResponseEntity<>(carpoolResource, HttpStatus.OK);
    }
}
