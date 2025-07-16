package com.elkusnandi.generalnote.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(
        name = "travel_route",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"travel_id", "route_order"}),
                @UniqueConstraint(columnNames = {"travel_id", "location_id"})
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

    @Column(name = "route_order")
    private Integer order;

    @Column(name = "is_pickup_location")
    private Boolean isPickupLocation;

    @Column(name = "is_drop_location")
    private Boolean isDropLocation;

    @Column(name = "eta_to_next_route")
    private Integer etaToNextRoute;
}
