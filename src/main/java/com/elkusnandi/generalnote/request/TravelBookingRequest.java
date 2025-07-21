package com.elkusnandi.generalnote.request;

import lombok.Data;

import java.util.UUID;

@Data
public class TravelBookingRequest {

    private UUID scheduleId;

    private Long userId;

    private String seatNumber;

}
