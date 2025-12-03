package com.campusmov.platform.matchingroutingservice.matchingrouting.infrastructure.persistence.googlemaps.services;

import com.campusmov.platform.matchingroutingservice.matchingrouting.domain.model.aggregates.Intersection;
import com.google.maps.DirectionsApi;
import com.google.maps.GeoApiContext;
import com.google.maps.model.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class GoogleMapsRoutingService {

    private final GeoApiContext geoApiContext;

    public GoogleMapsRoutingService(GeoApiContext geoApiContext) {
        this.geoApiContext = geoApiContext;
    }

    public Collection<Intersection> findRoute(Double startLat, Double startLng, Double endLat, Double endLng) {
        try {
            DirectionsResult result = DirectionsApi.newRequest(geoApiContext)
                    .origin(new LatLng(startLat, startLng))
                    .destination(new LatLng(endLat, endLng))
                    .mode(TravelMode.DRIVING)
                    .optimizeWaypoints(false)
                    .await();

            if (result.routes.length > 0) {
                return convertToIntersections(result.routes[0]);
            }
            return new ArrayList<>();
        } catch (Exception e) {
            throw new RuntimeException("Error finding route with Google Maps", e);
        }
    }

    public Double calculateRouteDistance(Double startLat, Double startLng, Double endLat, Double endLng) {
        try {
            DirectionsResult result = DirectionsApi.newRequest(geoApiContext)
                    .origin(new LatLng(startLat, startLng))
                    .destination(new LatLng(endLat, endLng))
                    .mode(TravelMode.DRIVING)
                    .await();

            if (result.routes.length > 0) {
                long distanceInMeters = 0;
                for (DirectionsLeg leg : result.routes[0].legs) {
                    distanceInMeters += leg.distance.inMeters;
                }
                return (double) distanceInMeters;
            }
            return 0.0;
        } catch (Exception e) {
            throw new RuntimeException("Error calculating route distance with Google Maps", e);
        }
    }

    public Double calculateRouteDuration(Double startLat, Double startLng, Double endLat, Double endLng) {
        try {
            DirectionsResult result = DirectionsApi.newRequest(geoApiContext)
                    .origin(new LatLng(startLat, startLng))
                    .destination(new LatLng(endLat, endLng))
                    .mode(TravelMode.DRIVING)
                    .await();

            if (result.routes.length > 0) {
                long durationInSeconds = 0;
                for (DirectionsLeg leg : result.routes[0].legs) {
                    durationInSeconds += leg.duration.inSeconds;
                }
                return (double) durationInSeconds;
            }
            return 0.0;
        } catch (Exception e) {
            throw new RuntimeException("Error calculating route duration with Google Maps", e);
        }
    }

    private Collection<Intersection> convertToIntersections(DirectionsRoute route) {
        List<Intersection> intersections = new ArrayList<>();
        int stepCount = 0;

        for (DirectionsLeg leg : route.legs) {
            for (DirectionsStep step : leg.steps) {
                Intersection intersection = new Intersection(
                        "step_" + stepCount++,
                        step.startLocation.lat,
                        step.startLocation.lng
                );
                intersections.add(intersection);
            }

            DirectionsStep lastStep = leg.steps[leg.steps.length - 1];
            Intersection endIntersection = new Intersection(
                    "step_" + stepCount,
                    lastStep.endLocation.lat,
                    lastStep.endLocation.lng
            );
            intersections.add(endIntersection);
        }

        return intersections;
    }
}
