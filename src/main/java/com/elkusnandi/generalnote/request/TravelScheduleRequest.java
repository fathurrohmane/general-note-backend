package com.elkusnandi.generalnote.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class TravelScheduleRequest {

    @NotNull
    private LocalDate date;

    @NotNull
    private LocalTime time;

}
