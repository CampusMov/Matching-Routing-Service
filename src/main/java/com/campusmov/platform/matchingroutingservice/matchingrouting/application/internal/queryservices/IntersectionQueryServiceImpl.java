package com.campusmov.platform.matchingroutingservice.matchingrouting.application.internal.queryservices;

import com.campusmov.platform.matchingroutingservice.matchingrouting.domain.model.aggregates.Intersection;
import com.campusmov.platform.matchingroutingservice.matchingrouting.domain.model.queries.FindShortestRouteWithAQuery;
import com.campusmov.platform.matchingroutingservice.matchingrouting.domain.model.queries.FindShortestRouteWithDijkstraQuery;
import com.campusmov.platform.matchingroutingservice.matchingrouting.domain.model.queries.GetTotalDistanceRouteForShortestRouteWithAQuery;
import com.campusmov.platform.matchingroutingservice.matchingrouting.domain.model.queries.GetTotalDistanceRouteForShortestRouteWithDijkstraQuery;
import com.campusmov.platform.matchingroutingservice.matchingrouting.domain.services.IntersectionQueryService;
import com.campusmov.platform.matchingroutingservice.matchingrouting.infrastructure.persistence.neo4j.repositories.IntersectionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;

@RequiredArgsConstructor
@Service
public class IntersectionQueryServiceImpl implements IntersectionQueryService {
    private final IntersectionRepository intersectionRepository;

    @Override
    public Collection<Intersection> handle(FindShortestRouteWithAQuery query) {
        return intersectionRepository.findAStarRoute(
                query.startLatitude(),
                query.startLongitude(),
                query.endLatitude(),
                query.endLongitude()
        );
    }

    @Override
    public Collection<Intersection> handle(FindShortestRouteWithDijkstraQuery query) {
        return intersectionRepository.findDijkstraRoute(
                query.startLatitude(),
                query.startLongitude(),
                query.endLatitude(),
                query.endLongitude()
        );
    }

    @Override
    public Double handle(GetTotalDistanceRouteForShortestRouteWithAQuery query) {
        return intersectionRepository.calculateAStarRouteDistance(
                query.startLatitude(),
                query.startLongitude(),
                query.endLatitude(),
                query.endLongitude()
        );
    }

    @Override
    public Double handle(GetTotalDistanceRouteForShortestRouteWithDijkstraQuery query) {
        return intersectionRepository.calculateDijkstraRouteDistance(
                query.startLatitude(),
                query.startLongitude(),
                query.endLatitude(),
                query.endLongitude()
        );
    }
}
