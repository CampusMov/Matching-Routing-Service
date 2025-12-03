package com.campusmov.platform.matchingroutingservice.matchingrouting.domain.services;

import com.campusmov.platform.matchingroutingservice.matchingrouting.domain.model.aggregates.Intersection;
import com.campusmov.platform.matchingroutingservice.matchingrouting.domain.model.queries.FindShortestRouteWithAQuery;
import com.campusmov.platform.matchingroutingservice.matchingrouting.domain.model.queries.FindShortestRouteWithDijkstraQuery;
import com.campusmov.platform.matchingroutingservice.matchingrouting.domain.model.queries.GetTotalDistanceRouteForShortestRouteWithAQuery;
import com.campusmov.platform.matchingroutingservice.matchingrouting.domain.model.queries.GetTotalDistanceRouteForShortestRouteWithDijkstraQuery;
import com.campusmov.platform.matchingroutingservice.matchingrouting.domain.model.queries.GetTotalDurationRouteForShortestRouteWithAQuery;
import com.campusmov.platform.matchingroutingservice.matchingrouting.domain.model.queries.GetTotalDurationRouteForShortestRouteWithDijkstraQuery;

import java.util.Collection;

public interface IntersectionQueryService {
    Collection<Intersection> handle(FindShortestRouteWithAQuery query);
    Collection<Intersection> handle(FindShortestRouteWithDijkstraQuery query);
    Double handle(GetTotalDistanceRouteForShortestRouteWithAQuery query);
    Double handle(GetTotalDistanceRouteForShortestRouteWithDijkstraQuery query);
    Double handle(GetTotalDurationRouteForShortestRouteWithAQuery query);
    Double handle(GetTotalDurationRouteForShortestRouteWithDijkstraQuery query);
}
