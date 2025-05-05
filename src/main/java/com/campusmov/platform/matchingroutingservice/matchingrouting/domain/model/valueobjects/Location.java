package com.campusmov.platform.matchingroutingservice.matchingrouting.domain.model.valueobjects;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.apache.logging.log4j.util.Strings;

import java.awt.*;

@Embeddable
public class Location {
    @NotBlank
    private String name;

    @NotBlank
    private String address;

    @NotNull
    private Point coordinates;

    public Location() {
        this.name = Strings.EMPTY;
        this.address = Strings.EMPTY;
        this.coordinates = new Point(0, 0);
    }
}
