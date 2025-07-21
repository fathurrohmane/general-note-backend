package com.elkusnandi.generalnote.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Entity
@Table(name = "travel_schedule")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TravelSchedule {

    @Id
    @Column(name = "id")
    private UUID id;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "time")
    private LocalTime time;

    @ManyToOne
    @JoinColumn(name = "travel_id")
    private Travel travel; // todo deprecated

    @ManyToOne
    @JoinColumn(name = "travel_route_id")
    private TravelRoute travelRoute;

    public TravelSchedule(UUID id, LocalDate date, LocalTime time, TravelRoute travelRoute) {
        this.id = id;
        this.date = date;
        this.time = time;
        this.travelRoute = travelRoute;
    }
}
