package com.campusmov.platform.matchingroutingservice.matchingrouting.domain.model.entities;

import com.campusmov.platform.matchingroutingservice.matchingrouting.domain.model.aggregates.Intersection;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.neo4j.core.schema.*;

@Getter
@Setter
@RelationshipProperties
public class RoadSegment {
    @Id
    @GeneratedValue
    private String id;

    @Property("ref")
    private String ref;

    @Property("osmid")
    private Long osmid;

/*    @Property("lanes")
    private Object lanes;*/

    @Property("name")
    private String name;

    @Property("length")
    private Double length;

   /* @Property("max_speed")
    private Object maxSpeed;*/

    @Property("highway")
    private String highway;

    @Property("oneway")
    private Boolean oneway;

    @TargetNode
    private Intersection targetIntersection;

    public RoadSegment() {}
}
