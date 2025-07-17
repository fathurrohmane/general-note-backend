package com.elkusnandi.generalnote.service;

import com.elkusnandi.generalnote.request.TravelScheduleRequest;
import com.elkusnandi.generalnote.response.TravelScheduleResponse;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface TravelScheduleService {

    List<TravelScheduleResponse> getTravelSchedule(UUID travelId, LocalDate date);

    TravelScheduleResponse createSchedule(UUID travelId, TravelScheduleRequest schedule);

    TravelScheduleResponse editSchedule(UUID travelId, UUID travelScheduleId, TravelScheduleRequest schedule);

    void deleteSchedule(UUID travelId, UUID travelScheduleId);

}
