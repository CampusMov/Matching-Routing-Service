package com.campusmov.platform.matchingroutingservice.matchingrouting.interfaces.rest.dto;

public record CreateLocationResource(
        String name,
        String address,
        Integer x,
        Integer y
) {
}
