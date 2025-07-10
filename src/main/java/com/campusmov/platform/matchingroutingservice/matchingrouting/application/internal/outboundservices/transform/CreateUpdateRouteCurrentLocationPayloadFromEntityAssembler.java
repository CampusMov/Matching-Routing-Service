package com.campusmov.platform.matchingroutingservice.matchingrouting.application.internal.outboundservices.transform;

import com.campusmov.platform.matchingroutingservice.matchingrouting.domain.model.aggregates.Route;
import com.campusmov.platform.matchingroutingservice.matchingrouting.domain.model.payloads.UpdateRouteCurrentLocationPayload;

public class CreateUpdateRouteCurrentLocationPayloadFromEntityAssembler {
    public static UpdateRouteCurrentLocationPayload toResourceFromEntity(Route route) {
        return UpdateRouteCurrentLocationPayload.builder()
                .id(route.getId())
                .carpoolId(route.getCarpoolId().carpoolId())
                .realStartedTime(route.getRealStartedTime())
                .estimatedEndedTime(route.getEstimatedEndedTime())
                .estimatedDurationMinutes(route.getEstimatedDurationMinutes())
                .estimatedDistanceKm(route.getEstimatedDistanceKm())
                .originName(route.getOrigin().getName())
                .originAddress(route.getOrigin().getAddress())
                .originLongitude(route.getOrigin().getLongitude())
                .originLatitude(route.getOrigin().getLatitude())
                .destinationName(route.getDestination().getName())
                .destinationAddress(route.getDestination().getAddress())
                .destinationLongitude(route.getDestination().getLongitude())
                .destinationLatitude(route.getDestination().getLatitude())
                .carpoolCurrentName(route.getCarpoolCurrentLocation().getName())
                .carpoolCurrentAddress(route.getCarpoolCurrentLocation().getAddress())
                .carpoolCurrentLongitude(route.getCarpoolCurrentLocation().getLongitude())
                .carpoolCurrentLatitude(route.getCarpoolCurrentLocation().getLatitude())
                .build();
    }
}
