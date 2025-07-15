package com.elkusnandi.generalnote.repository;

import com.elkusnandi.generalnote.entity.ShuttleOutletLocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ShuttleOutletLocationRepository extends JpaRepository<ShuttleOutletLocation, UUID> {

    @Query("""
            SELECT l FROM TravelRoute r
            JOIN r.location l
            WHERE l.id = r.location.id AND r.isPickupLocation = true
            """)
    List<ShuttleOutletLocation> findDepartureLocations();

    @Query("""
                SELECT r.location
                FROM TravelRoute r
                WHERE r.travel.id = (
                      SELECT r2.travel.id FROM TravelRoute r2
                      WHERE r2.location.id = :shuttleOutletLocationId AND r2.isPickupLocation = true
                      ) AND r.isDropLocation = true
            """)
    List<ShuttleOutletLocation> findArrivalLocation(@Param("shuttleOutletLocationId") UUID shuttleOutletLocationId);

}