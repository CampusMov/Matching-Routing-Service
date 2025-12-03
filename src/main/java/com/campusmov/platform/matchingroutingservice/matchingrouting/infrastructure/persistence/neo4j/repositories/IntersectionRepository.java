package com.campusmov.platform.matchingroutingservice.matchingrouting.infrastructure.persistence.neo4j.repositories;

import com.campusmov.platform.matchingroutingservice.matchingrouting.domain.model.aggregates.Intersection;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface IntersectionRepository extends Neo4jRepository<Intersection, String> {
    @Query("""
    MATCH (start_intersection:Intersection)
    WITH start_intersection, point.distance(start_intersection.location, point({latitude: $start_lat, longitude: $start_lng})) AS start_dist
    ORDER BY start_dist ASC
    LIMIT 1

    MATCH (end_intersection:Intersection)
    WITH start_intersection, end_intersection,
         point.distance(end_intersection.location, point({latitude: $end_lat, longitude: $end_lng})) AS end_dist
    ORDER BY end_dist ASC
    LIMIT 1
   
    CALL (start_intersection, end_intersection) {
        MATCH p = allShortestPaths((start_intersection)-[:ROAD_SEGMENT*]->(end_intersection))
        WHERE all(r IN relationships(p) WHERE r.length IS NOT NULL)
        WITH p,
             toFloat(reduce(g_cost = 0.0, r IN relationships(p) | g_cost + toFloat(r.length))) AS total_g_cost,
             toFloat(reduce(h_cost = 0.0, i IN range(0, size(nodes(p))-1) | h_cost + toFloat(point.distance(nodes(p)[i].location, end_intersection.location))
             )) / toFloat(size(nodes(p))) AS avg_h_cost
 
        RETURN p, toFloat(total_g_cost + avg_h_cost) AS f_cost
        ORDER BY f_cost ASC
        LIMIT 1
    }
    
    RETURN nodes(p) AS intersections
    """)
    Collection<Intersection> findAStarRoute(@Param("start_lat") Double startLat,
                                            @Param("start_lng") Double startLng,
                                            @Param("end_lat") Double endLat,
                                            @Param("end_lng") Double endLng);

    @Query("""
    MATCH (start_intersection:Intersection)
    WITH start_intersection, point.distance(start_intersection.location, point({latitude: $start_lat, longitude: $start_lng})) AS start_dist
    ORDER BY start_dist ASC
    LIMIT 1

    MATCH (end_intersection:Intersection)
    WITH start_intersection, end_intersection,
         point.distance(end_intersection.location, point({latitude: $end_lat, longitude: $end_lng})) AS end_dist
    ORDER BY end_dist ASC
    LIMIT 1
   
    CALL (start_intersection, end_intersection) {
        MATCH p = allShortestPaths((start_intersection)-[:ROAD_SEGMENT*]->(end_intersection))
        WHERE all(r IN relationships(p) WHERE r.length IS NOT NULL)
        WITH p,
             toFloat(reduce(g_cost = 0.0, r IN relationships(p) | g_cost + toFloat(r.length))) AS total_g_cost,
             toFloat(reduce(h_cost = 0.0, i IN range(0, size(nodes(p))-1) | h_cost + toFloat(point.distance(nodes(p)[i].location, end_intersection.location))
             )) / toFloat(size(nodes(p))) AS avg_h_cost

        RETURN p, toFloat(total_g_cost + avg_h_cost) AS f_cost, total_g_cost AS distance
        ORDER BY f_cost ASC
        LIMIT 1
    }
    
    RETURN distance
    """)
    Double calculateAStarRouteDistance(@Param("start_lat") Double startLat,
                                       @Param("start_lng") Double startLng,
                                       @Param("end_lat") Double endLat,
                                       @Param("end_lng") Double endLng);

    @Query("""
    MATCH (start_intersection:Intersection)
    WITH start_intersection, point.distance(start_intersection.location, point({latitude: $start_lat, longitude: $start_lng})) AS start_dist
    ORDER BY start_dist ASC
    LIMIT 1

    MATCH (end_intersection:Intersection)
    WITH start_intersection, end_intersection
    ORDER BY point.distance(end_intersection.location, point({latitude: $end_lat, longitude: $end_lng})) ASC
    LIMIT 1

    MATCH p = shortestPath((start_intersection)-[:ROAD_SEGMENT*]->(end_intersection))
    WHERE all(r IN relationships(p) WHERE r.length IS NOT NULL)

    WITH p, toFloat(reduce(total_distance = 0.0, r IN relationships(p) | total_distance + toFloat(r.length))) AS total_cost
    ORDER BY total_cost ASC
    LIMIT 1

    RETURN nodes(p) AS intersections
    """)
    Collection<Intersection> findDijkstraRoute(@Param("start_lat") Double startLat,
                                               @Param("start_lng") Double startLng,
                                               @Param("end_lat") Double endLat,
                                               @Param("end_lng") Double endLng);

    @Query("""
    MATCH (start_intersection:Intersection)
    WITH start_intersection, point.distance(start_intersection.location, point({latitude: $start_lat, longitude: $start_lng})) AS start_dist
    ORDER BY start_dist ASC
    LIMIT 1

    MATCH (end_intersection:Intersection)
    WITH start_intersection, end_intersection
    ORDER BY point.distance(end_intersection.location, point({latitude: $end_lat, longitude: $end_lng})) ASC
    LIMIT 1

    MATCH p = shortestPath((start_intersection)-[:ROAD_SEGMENT*]->(end_intersection))
    WHERE all(r IN relationships(p) WHERE r.length IS NOT NULL)

    WITH p, toFloat(reduce(total_distance = 0.0, r IN relationships(p) | total_distance + toFloat(r.length))) AS total_cost
    ORDER BY total_cost ASC
    LIMIT 1

    RETURN total_cost AS distance
    """)
    Double calculateDijkstraRouteDistance(@Param("start_lat") Double startLat,
                                          @Param("start_lng") Double startLng,
                                          @Param("end_lat") Double endLat,
                                          @Param("end_lng") Double endLng);
}
