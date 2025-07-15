package com.elkusnandi.generalnote.controller;

import com.elkusnandi.generalnote.common.base.BaseResponse;
import com.elkusnandi.generalnote.entity.ShuttleOutletLocation;
import com.elkusnandi.generalnote.service.ShuttleOutletLocationService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/shuttle/")
public class ShuttleUserController {

    private final ShuttleOutletLocationService shuttleOutletLocationService;

    public ShuttleUserController(ShuttleOutletLocationService shuttleOutletLocationService) {
        this.shuttleOutletLocationService = shuttleOutletLocationService;
    }

    @RequestMapping("/location/")
    public BaseResponse<List<ShuttleOutletLocation>> getDepartureLocations() {
        return new BaseResponse<>(
                shuttleOutletLocationService.getAvailableOutletLocation(null),
                HttpStatus.OK,
                true,
                ""
        );
    }

    @RequestMapping("/location/{shuttleOutletLocationId}/arrival")
    public BaseResponse<List<ShuttleOutletLocation>> getArrivalLocations(@PathVariable("shuttleOutletLocationId") UUID shuttleOutletLocationId) {
        return new BaseResponse<>(
                shuttleOutletLocationService.getAvailableOutletLocation(shuttleOutletLocationId),
                HttpStatus.OK,
                true,
                ""
        );
    }
}
