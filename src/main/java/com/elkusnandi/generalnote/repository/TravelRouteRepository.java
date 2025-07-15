package com.elkusnandi.generalnote.repository;

import com.elkusnandi.generalnote.entity.TravelRoute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TravelRouteRepository extends JpaRepository<TravelRoute, UUID> {

    List<TravelRoute> findByTravelId(UUID travelId);

}
