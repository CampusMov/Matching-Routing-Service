package com.campusmov.platform.matchingroutingservice.matchingrouting.domain.model.aggregates;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Intersection {
    private String id;
    private Object ref;
    private Long osmid;
    private Double latitude;
    private Double longitude;
    private Object highway;
    private Integer streetCount;

    public Intersection() {}

    public Intersection(String id, Double latitude, Double longitude) {
        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
