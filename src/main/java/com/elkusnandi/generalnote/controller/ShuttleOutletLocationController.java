package com.elkusnandi.generalnote.controller;

import com.elkusnandi.generalnote.common.base.BaseResponse;
import com.elkusnandi.generalnote.entity.ShuttleOutletLocation;
import com.elkusnandi.generalnote.request.ShuttleOutletLocationRequest;
import com.elkusnandi.generalnote.service.ShuttleOutletLocationService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/shuttle/admin/outlet")
public class ShuttleOutletLocationController {

    private final ShuttleOutletLocationService service;

    public ShuttleOutletLocationController(ShuttleOutletLocationService service) {
        this.service = service;
    }

    @GetMapping()
    public BaseResponse<List<ShuttleOutletLocation>> getOutlet() {
        return new BaseResponse<>(service.getShuttleOutletLocations(), HttpStatus.OK, true, "");
    }

    @GetMapping("/{outletId}")
    public BaseResponse<ShuttleOutletLocation> getOutletDetail(@PathVariable String outletId) {
        return new BaseResponse<>(
                service.getShuttleOutletLocationDetail(UUID.fromString(outletId)),
                HttpStatus.OK,
                true,
                ""
        );
    }

    @PutMapping("/{outletId}")
    public BaseResponse<ShuttleOutletLocation> editOutlet(
            @PathVariable String outletId,
            @RequestBody
            ShuttleOutletLocationRequest shuttleOutletLocationRequest
    ) {
        return new BaseResponse<>(
                service.editShuttleOutletLocation(UUID.fromString(outletId), shuttleOutletLocationRequest),
                HttpStatus.OK,
                true,
                ""
        );
    }

    @PostMapping()
    public BaseResponse<ShuttleOutletLocation> createOutlet(
            @RequestBody
            ShuttleOutletLocationRequest shuttleOutletLocationRequest
    ) {
        return new BaseResponse<>(
                service.createShuttleOutletLocation(shuttleOutletLocationRequest),
                HttpStatus.OK,
                true,
                ""
        );
    }

    @DeleteMapping("/{outletId}")
    public BaseResponse<ShuttleOutletLocation> deleteOutlet(
            @PathVariable String outletId
    ) {
        service.deleteShuttleOutletLocation(UUID.fromString(outletId));

        return new BaseResponse<>(
                null,
                HttpStatus.OK,
                true,
                ""
        );
    }
}
