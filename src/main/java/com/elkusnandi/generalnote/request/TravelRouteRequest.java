package com.elkusnandi.generalnote.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class TravelRouteRequest {

    @NotNull
    private UUID shuttleLocationId;

    @Min(value = 0)
    private int order;

    @NotNull
    private Boolean isPickupLocation;

    @NotNull
    private Boolean isDropLocation;
}
