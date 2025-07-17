package com.elkusnandi.generalnote.controller;

import com.elkusnandi.generalnote.common.base.BaseResponse;
import com.elkusnandi.generalnote.request.TravelRequest;
import com.elkusnandi.generalnote.request.TravelRouteEditRequest;
import com.elkusnandi.generalnote.request.TravelRouteRequest;
import com.elkusnandi.generalnote.request.TravelScheduleRequest;
import com.elkusnandi.generalnote.response.TravelResponse;
import com.elkusnandi.generalnote.response.TravelRouteResponse;
import com.elkusnandi.generalnote.response.TravelScheduleResponse;
import com.elkusnandi.generalnote.service.TravelScheduleService;
import com.elkusnandi.generalnote.service.TravelService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/shuttle/admin/travel")
public class ShuttleTravelController {

    private final TravelService service;

    private final TravelScheduleService travelScheduleService;

    public ShuttleTravelController(TravelService service, TravelScheduleService travelScheduleService) {
        this.service = service;
        this.travelScheduleService = travelScheduleService;
    }

    @GetMapping()
    public BaseResponse<List<TravelResponse>> getTravelList() {
        return new BaseResponse<>(service.getTravel(), HttpStatus.OK, true, "");
    }

    @PostMapping()
    public BaseResponse<TravelResponse> createTravel(@RequestBody TravelRequest request) {
        return new BaseResponse<>(service.createNewTravel(request), HttpStatus.OK, true, "");
    }

    @PostMapping("/{travelId}")
    public BaseResponse<List<TravelRouteResponse>> createTravelRoute(
            @PathVariable String travelId,
            @RequestBody List<TravelRouteRequest> request
    ) {
        return new BaseResponse<>(
                service.createTravelRoute(UUID.fromString(travelId), request),
                HttpStatus.OK,
                true,
                ""
        );
    }

    @PutMapping("/{travelId}")
    public BaseResponse<List<TravelRouteResponse>> editTravelRoute(
            @PathVariable String travelId,
            @RequestBody List<TravelRouteEditRequest> request
    ) {
        return new BaseResponse<>(
                service.editTravelRoute(UUID.fromString(travelId), request),
                HttpStatus.OK,
                true,
                ""
        );
    }

    @DeleteMapping("/{travelId}")
    public BaseResponse<List<TravelRouteResponse>> deleteTravel(
            @PathVariable String travelId
    ) {
        service.deleteTravel(UUID.fromString(travelId));

        return new BaseResponse<>(
                null,
                HttpStatus.OK,
                true,
                ""
        );
    }

    @GetMapping("/{travelId}/schedule")
    public BaseResponse<List<TravelScheduleResponse>> getTravelSchedule(
            @PathVariable String travelId,
            @RequestParam(name = "date", required = false) LocalDate date
    ) {
        return new BaseResponse<>(
                travelScheduleService.getTravelSchedule(UUID.fromString(travelId), date),
                HttpStatus.OK,
                true,
                ""
        );
    }

    @PostMapping("/{travelId}/schedule")
    public BaseResponse<TravelScheduleResponse> createTravelSchedule(
            @PathVariable String travelId,
            @RequestBody TravelScheduleRequest travelScheduleRequest
    ) {
        return new BaseResponse<>(
                travelScheduleService.createSchedule(UUID.fromString(travelId), travelScheduleRequest),
                HttpStatus.OK,
                true,
                ""
        );
    }

    @PutMapping("/{travelId}/schedule/{travelScheduleId}")
    public BaseResponse<TravelScheduleResponse> editTravelSchedule(
            @PathVariable String travelId,
            @PathVariable String travelScheduleId,
            @RequestBody TravelScheduleRequest travelScheduleRequest
    ) {
        return new BaseResponse<>(
                travelScheduleService.editSchedule(
                        UUID.fromString(travelId),
                        UUID.fromString(travelScheduleId),
                        travelScheduleRequest
                ),
                HttpStatus.OK,
                true,
                ""
        );
    }

    @DeleteMapping("/{travelId}/schedule/{travelScheduleId}")
    public BaseResponse<?> deleteTravelSchedule(
            @PathVariable String travelId,
            @PathVariable String travelScheduleId
    ) {
        travelScheduleService.deleteSchedule(UUID.fromString(travelId), UUID.fromString(travelScheduleId));

        return new BaseResponse<>(null, HttpStatus.OK, true, "");
    }

}
