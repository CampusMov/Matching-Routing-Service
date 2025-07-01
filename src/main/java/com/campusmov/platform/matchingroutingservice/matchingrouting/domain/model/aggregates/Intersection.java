package com.campusmov.platform.matchingroutingservice.matchingrouting.domain.model.aggregates;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Property;
import org.springframework.data.neo4j.types.GeographicPoint2d;

@Getter
@Setter
@Node("Intersection")
public class Intersection {
    @Id
    private String id;

    @Property("ref")
    private Object ref;

    @Property("osmid")
    private Long osmid;

    @Property("location")
    private GeographicPoint2d location;

    @Property("highway")
    private Object highway;

    @Property("street_count")
    private Integer streetCount;

    /*@Relationship(type = "ROAD_SEGMENT", direction = Relationship.Direction.OUTGOING)
    private Set<RoadSegment> outgoingRoadSegments;

    @Relationship(type = "ROAD_SEGMENT", direction = Relationship.Direction.INCOMING)
    private Set<RoadSegment> incomingRoadSegments;*/

    public Intersection() {}
}
