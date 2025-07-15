package com.elkusnandi.generalnote.service;

import com.elkusnandi.generalnote.entity.TravelRoute;

import java.util.List;
import java.util.UUID;

public interface TravelService {

    String createNewTravel(String name);

    void toggleTravelVisibility(UUID travelId, Boolean visible);

    String createTravelRoute(UUID travelId, List<TravelRoute> travelRoutes);

    String editTravelRoute(UUID travelId, List<TravelRoute> travelRoutes);

}
