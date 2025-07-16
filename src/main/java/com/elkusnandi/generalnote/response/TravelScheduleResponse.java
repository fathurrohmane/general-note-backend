package com.elkusnandi.generalnote.response;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Data
public class TravelScheduleResponse {

    private UUID id;

    private LocalDate date;

    private LocalTime time;

}
