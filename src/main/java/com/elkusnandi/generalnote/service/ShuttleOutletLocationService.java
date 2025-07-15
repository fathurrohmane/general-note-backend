package com.elkusnandi.generalnote.service;

import com.elkusnandi.generalnote.entity.ShuttleOutletLocation;
import com.elkusnandi.generalnote.request.ShuttleOutletLocationRequest;

import java.util.List;
import java.util.UUID;

public interface ShuttleOutletLocationService {

    List<ShuttleOutletLocation> getShuttleOutletLocations();

    List<ShuttleOutletLocation> getAvailableOutletLocation(UUID departureLocationId);

    ShuttleOutletLocation getShuttleOutletLocationDetail(UUID outletId);

    ShuttleOutletLocation createShuttleOutletLocation(ShuttleOutletLocationRequest outlet);

    ShuttleOutletLocation editShuttleOutletLocation(UUID outletId, ShuttleOutletLocationRequest outlet);

    void deleteShuttleOutletLocation(UUID outletId);

}
