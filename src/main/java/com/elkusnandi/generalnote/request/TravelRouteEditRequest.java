package com.elkusnandi.generalnote.request;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class TravelRouteEditRequest {

    private UUID travelId;

    private UUID shuttleLocationId;

    private int order;

    private Boolean isPickupLocation;

    private Boolean isDropLocation;
}
