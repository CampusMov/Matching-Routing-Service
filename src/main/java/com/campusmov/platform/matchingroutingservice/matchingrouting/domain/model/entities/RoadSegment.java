package com.campusmov.platform.matchingroutingservice.matchingrouting.domain.model.entities;

import com.campusmov.platform.matchingroutingservice.matchingrouting.domain.model.aggregates.Intersection;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoadSegment {
    private String id;
    private String ref;
    private Long osmid;
    private String name;
    private Double length;
    private String highway;
    private Boolean oneway;
    private Intersection targetIntersection;

    public RoadSegment() {}
}
