package com.elkusnandi.generalnote.controller;

import com.elkusnandi.generalnote.common.base.BaseResponse;
import com.elkusnandi.generalnote.request.TravelRequest;
import com.elkusnandi.generalnote.request.TravelRouteEditRequest;
import com.elkusnandi.generalnote.request.TravelRouteRequest;
import com.elkusnandi.generalnote.response.TravelResponse;
import com.elkusnandi.generalnote.response.TravelRouteResponse;
import com.elkusnandi.generalnote.service.TravelService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/shuttle/admin/travel")
public class ShuttleTravelController {

    private final TravelService service;

    public ShuttleTravelController(TravelService service) {
        this.service = service;
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

}
