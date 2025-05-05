package com.campusmov.platform.matchingroutingservice.matchingrouting.domain.model.valueobjects;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.apache.logging.log4j.util.Strings;

import java.awt.*;

@Getter
@Setter
@Builder
@Embeddable
@AllArgsConstructor
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
