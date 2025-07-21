package com.elkusnandi.generalnote.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "travel_booking")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TravelBooking {

    @Id
    @Column(name = "id")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Users customer;

    @ManyToOne
    @JoinColumn(name = "schedule_id")
    private TravelSchedule schedule;

    @Column(name = "seat_number")
    private String seatNumber;

    @Column(name = "status")
    private String status; // Booked, Pending, Canceled

}
