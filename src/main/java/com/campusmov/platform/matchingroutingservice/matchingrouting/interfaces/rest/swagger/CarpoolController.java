package com.campusmov.platform.matchingroutingservice.matchingrouting.interfaces.rest.swagger;

import com.campusmov.platform.matchingroutingservice.matchingrouting.interfaces.rest.dto.CarpoolResource;
import com.campusmov.platform.matchingroutingservice.matchingrouting.interfaces.rest.dto.CreateCarpoolResource;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

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
}
