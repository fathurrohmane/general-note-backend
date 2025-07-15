package com.elkusnandi.generalnote.service;

import com.elkusnandi.generalnote.request.TravelRequest;
import com.elkusnandi.generalnote.request.TravelRouteEditRequest;
import com.elkusnandi.generalnote.request.TravelRouteRequest;
import com.elkusnandi.generalnote.response.TravelResponse;
import com.elkusnandi.generalnote.response.TravelRouteResponse;

import java.util.List;
import java.util.UUID;

public interface TravelService {

    List<TravelResponse> getTravel();

    TravelResponse createNewTravel(TravelRequest travelRequest);

    void toggleTravelVisibility(UUID travelId, Boolean visible);

    List<TravelRouteResponse> createTravelRoute(UUID travelId, List<TravelRouteRequest> travelRoutes);

    List<TravelRouteResponse> editTravelRoute(UUID travelId, List<TravelRouteEditRequest> travelRoutes);

}
