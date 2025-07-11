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
    WITH start_intersection
    ORDER BY point.distance(start_intersection.location, point({latitude: $start_lat, longitude: $start_lng})) ASC
    LIMIT 1
    
    MATCH (end_intersection:Intersection)
    WITH start_intersection, end_intersection
    ORDER BY point.distance(end_intersection.location, point({latitude: $end_lat, longitude: $end_lng})) ASC
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
    
    WITH nodes(p) AS route_nodes

    UNWIND route_nodes AS route_node
    WITH route_node, point.distance(route_node.location, point({latitude: $pickup_lat, longitude: $pickup_lng})) AS distance_to_pickup

    ORDER BY distance_to_pickup ASC
    LIMIT 1
    
    RETURN distance_to_pickup <= $radius_meters AS is_feasible
    """)
    Boolean isPickupPointWithinRouteAStar(@Param("start_lat") Double startLat,
                                          @Param("start_lng") Double startLng,
                                          @Param("end_lat") Double endLat,
                                          @Param("end_lng") Double endLng,
                                          @Param("pickup_lat") Double pickupLat,
                                          @Param("pickup_lng") Double pickupLng,
                                          @Param("radius_meters") Double radiusMeters);

    @Query("""
    MATCH (start:Intersection)
    WITH start
    ORDER BY point.distance(start.location, point({latitude: $start_lat, longitude: $start_lng})) ASC
    LIMIT 1
    
    MATCH (end:Intersection)
    WITH start, end
    ORDER BY point.distance(end.location, point({latitude: $end_lat, longitude: $end_lng})) ASC
    LIMIT 1
    
    CALL {
    WITH start, end
    WITH start, end, $pickup_points AS pickups
    WHERE size(pickups) = 0
    
    CALL (start, end) {
        MATCH p = allShortestPaths((start)-[:ROAD_SEGMENT*]->(end))
        WHERE all(r IN relationships(p) WHERE r.length IS NOT NULL)
        WITH p,
             toFloat(reduce(g_cost = 0.0, r IN relationships(p) | g_cost + toFloat(r.length))) AS total_g_cost,
             toFloat(reduce(h_cost = 0.0, i IN range(0, size(nodes(p))-1) |
                h_cost + toFloat(point.distance(nodes(p)[i].location, end.location))
             )) / toFloat(size(nodes(p))) AS avg_h_cost
        RETURN p, toFloat(total_g_cost + avg_h_cost) AS f_cost
        ORDER BY f_cost ASC
        LIMIT 1
    }
    
    RETURN nodes(p) AS route_nodes
    
    UNION

    WITH start, end
    WITH start, end, $pickup_points AS pickups
    WHERE size(pickups) > 0

    UNWIND range(0, size(pickups)-1) AS idx
    WITH start, end, pickups, idx
    MATCH (pickup:Intersection)
    WITH start, end, pickups, idx, pickup,
         point.distance(pickup.location, point({latitude: pickups[idx][0], longitude: pickups[idx][1]})) AS dist
    ORDER BY idx, dist ASC
    WITH start, end, collect({idx: idx, node: pickup})[0..size(pickups)] AS pickup_nodes

    WITH [start] + [p IN pickup_nodes | p.node] + [end] AS all_stops

    UNWIND range(0, size(all_stops)-2) AS segment_idx
    WITH all_stops[segment_idx] AS from_node,
         all_stops[segment_idx+1] AS to_node,
         segment_idx
    
    CALL (from_node, to_node, segment_idx) {
        MATCH p = allShortestPaths((from_node)-[:ROAD_SEGMENT*]->(to_node))
        WHERE all(r IN relationships(p) WHERE r.length IS NOT NULL)
        WITH p,
             toFloat(reduce(g_cost = 0.0, r IN relationships(p) | g_cost + toFloat(r.length))) AS total_g_cost,
             toFloat(reduce(h_cost = 0.0, i IN range(0, size(nodes(p))-1) |
                h_cost + toFloat(point.distance(nodes(p)[i].location, to_node.location))
             )) / toFloat(size(nodes(p))) AS avg_h_cost,
             segment_idx
    
        RETURN segment_idx, nodes(p) AS segment_nodes, toFloat(total_g_cost + avg_h_cost) AS f_cost
        ORDER BY f_cost ASC
        LIMIT 1
    }
    
    WITH segment_idx, segment_nodes
    ORDER BY segment_idx

    WITH collect({idx: segment_idx, nodes: segment_nodes}) AS segments
    WITH reduce(route = [], seg IN segments |
        CASE
            WHEN size(route) = 0 THEN seg.nodes
            ELSE route + tail(seg.nodes)
        END
    ) AS route_nodes
    
    RETURN route_nodes
    }
    
    RETURN route_nodes AS completeRoute
    """)
    Collection<Intersection> generateCompleteRouteAStar(@Param("start_lat") Double startLat,
                                                        @Param("start_lng") Double startLng,
                                                        @Param("end_lat") Double endLat,
                                                        @Param("end_lng") Double endLng,
                                                        @Param("pickup_points") List<List<Double>> pickupPoints);

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

    @Query("""
    MATCH (start_intersection:Intersection)
    WITH start_intersection
    ORDER BY point.distance(start_intersection.location, point({latitude: $start_lat, longitude: $start_lng})) ASC
    LIMIT 1

    MATCH (end_intersection:Intersection)
    WITH start_intersection, end_intersection
    ORDER BY point.distance(end_intersection.location, point({latitude: $end_lat, longitude: $end_lng})) ASC
    LIMIT 1

    MATCH p = shortestPath((start_intersection)-[:ROAD_SEGMENT*]->(end_intersection))
    WHERE all(r IN relationships(p) WHERE r.length IS NOT NULL)
    WITH nodes(p) AS route_nodes

    UNWIND route_nodes AS route_node
    WITH route_node, point.distance(route_node.location, point({latitude: $pickup_lat, longitude: $pickup_lng})) AS distance_to_pickup

    ORDER BY distance_to_pickup ASC
    LIMIT 1
   
    RETURN distance_to_pickup <= $radius_meters AS is_feasible
    """)
    Boolean isPickupPointWithinRouteDijkstra(@Param("start_lat") Double startLat,
                                             @Param("start_lng") Double startLng,
                                             @Param("end_lat") Double endLat,
                                             @Param("end_lng") Double endLng,
                                             @Param("pickup_lat") Double pickupLat,
                                             @Param("pickup_lng") Double pickupLng,
                                             @Param("radius_meters") Double radiusMeters);

    @Query("""
    MATCH (start:Intersection)
    WITH start
    ORDER BY point.distance(start.location, point({latitude: $start_lat, longitude: $start_lng})) ASC
    LIMIT 1
    
    MATCH (end:Intersection)
    WITH start, end
    ORDER BY point.distance(end.location, point({latitude: $end_lat, longitude: $end_lng})) ASC
    LIMIT 1
    
    CALL {
        WITH start, end
        WITH start, end, $pickup_points AS pickups
        WHERE size(pickups) = 0
        MATCH p = shortestPath((start)-[:ROAD_SEGMENT*]->(end))
        WHERE all(r IN relationships(p) WHERE r.length IS NOT NULL)
        RETURN nodes(p) AS route_nodes
    
        UNION
    
        WITH start, end
        WITH start, end, $pickup_points AS pickups
        WHERE size(pickups) > 0
    
        UNWIND range(0, size(pickups)-1) AS idx
        WITH start, end, pickups, idx
        MATCH (pickup:Intersection)
        WITH start, end, pickups, idx, pickup,
             point.distance(pickup.location, point({latitude: pickups[idx][0], longitude: pickups[idx][1]})) AS dist
        ORDER BY idx, dist ASC
        WITH start, end, collect({idx: idx, node: pickup})[0..size(pickups)] AS pickup_nodes
    
        WITH [start] + [p IN pickup_nodes | p.node] + [end] AS all_stops
    
        UNWIND range(0, size(all_stops)-2) AS segment_idx
        WITH all_stops[segment_idx] AS from_node,
             all_stops[segment_idx+1] AS to_node,
             segment_idx
        MATCH p = shortestPath((from_node)-[:ROAD_SEGMENT*]->(to_node))
        WHERE all(r IN relationships(p) WHERE r.length IS NOT NULL)
        WITH segment_idx, nodes(p) AS segment_nodes
        ORDER BY segment_idx
    
        WITH collect({idx: segment_idx, nodes: segment_nodes}) AS segments
        WITH reduce(route = [], seg IN segments |
            CASE
                WHEN size(route) = 0 THEN seg.nodes
                ELSE route + tail(seg.nodes)
            END
        ) AS route_nodes
    
        RETURN route_nodes
    }
    
    RETURN route_nodes AS completeRoute
    """)
    Collection<Intersection> generateCompleteRouteDijkstra(@Param("start_lat") Double startLat,
                                                           @Param("start_lng") Double startLng,
                                                           @Param("end_lat") Double endLat,
                                                           @Param("end_lng") Double endLng,
                                                           @Param("pickup_points") List<List<Double>> pickupPoints);
}
