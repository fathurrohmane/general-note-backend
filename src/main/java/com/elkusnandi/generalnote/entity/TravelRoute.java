package com.elkusnandi.generalnote.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"travel_id", "order"})
        }
)
@Data
@NoArgsConstructor
public class TravelRoute {
    @Id
    @Column(name = "id")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "travel_id")
    private Travel travel;

    @ManyToOne
    @JoinColumn(name = "location_id")
    private ShuttleOutletLocation location;

    @Column(name = "order")
    private int order;

    @Column(name = "is_pickup_location")
    private Boolean isPickupLocation;

    @Column(name = "is_drop_location")
    private Boolean isDropLocation;
}
