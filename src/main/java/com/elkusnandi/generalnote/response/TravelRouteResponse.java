package com.elkusnandi.generalnote.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class TravelRouteResponse {
    private UUID id;

    private ShuttleOutletLocationResponse location;

    private int order;

    private Boolean isPickupLocation;

    private Boolean isDropLocation;
}
