package com.elkusnandi.generalnote.service;

import com.elkusnandi.generalnote.request.TravelBookingRequest;
import com.elkusnandi.generalnote.response.TravelBookingResponse;

import java.util.List;
import java.util.UUID;

public interface TravelBookingService {

    List<TravelBookingResponse> getBookingByUser(Long userId);

    List<TravelBookingResponse> getBookingByScheduleId(UUID scheduleId);

    TravelBookingResponse createBooking(TravelBookingRequest request);

    void cancelBooking(UUID travelBookingId);

}
