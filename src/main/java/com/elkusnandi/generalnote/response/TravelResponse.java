package com.elkusnandi.generalnote.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
public class TravelResponse {

    private UUID id;

    private String name;

    private Boolean visible;

    private List<TravelRouteResponse> routes;

}
