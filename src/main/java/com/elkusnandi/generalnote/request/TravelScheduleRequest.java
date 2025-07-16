package com.elkusnandi.generalnote.request;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class TravelScheduleRequest {

    private LocalDate date;

    private LocalTime time;

}
