package com.elkusnandi.generalnote.service.impl;

import com.elkusnandi.generalnote.entity.ShuttleOutletLocation;
import com.elkusnandi.generalnote.entity.Travel;
import com.elkusnandi.generalnote.entity.TravelRoute;
import com.elkusnandi.generalnote.exception.UserFaultException;
import com.elkusnandi.generalnote.repository.ShuttleOutletLocationRepository;
import com.elkusnandi.generalnote.repository.TravelRepository;
import com.elkusnandi.generalnote.repository.TravelRouteRepository;
import com.elkusnandi.generalnote.request.TravelRequest;
import com.elkusnandi.generalnote.request.TravelRouteEditRequest;
import com.elkusnandi.generalnote.request.TravelRouteRequest;
import com.elkusnandi.generalnote.response.ShuttleOutletLocationResponse;
import com.elkusnandi.generalnote.response.TravelResponse;
import com.elkusnandi.generalnote.response.TravelRouteResponse;
import com.elkusnandi.generalnote.service.TravelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class TravelServiceImpl implements TravelService {

    private final ShuttleOutletLocationRepository shuttleOutletLocationRepository;

    private final TravelRepository travelRepository;

    private final TravelRouteRepository travelRouteRepository;

    @Autowired
    public TravelServiceImpl(
            ShuttleOutletLocationRepository shuttleOutletLocationRepository,
            TravelRepository travelRepository,
            TravelRouteRepository travelRouteRepository
    ) {
        this.shuttleOutletLocationRepository = shuttleOutletLocationRepository;
        this.travelRepository = travelRepository;
        this.travelRouteRepository = travelRouteRepository;
    }

    @Override
    public List<TravelResponse> getTravel() {
        return travelRepository.findAll().stream().map(
                travel -> new TravelResponse(
                        travel.getId(),
                        travel.getName(),
                        travel.getVisible(),
                        travel.getRoutes().stream().map(travelRoute -> {
                                    ShuttleOutletLocation location = travelRoute.getLocation();
                                    ShuttleOutletLocation.Address address = travelRoute.getLocation().getAddress();
                                    return new TravelRouteResponse(
                                            travelRoute.getId(),
                                            new ShuttleOutletLocationResponse(
                                                    location.getId(),
                                                    location.getName(),
                                                    location.getLatitude(),
                                                    location.getLongitude(),
                                                    new ShuttleOutletLocationResponse.Address(
                                                            address.getProvinceId(),
                                                            address.getProvince(),
                                                            address.getRegencyId(),
                                                            address.getRegency(),
                                                            address.getDistrictId(),
                                                            address.getDistrict(),
                                                            address.getVillageId(),
                                                            address.getVillage(),
                                                            address.getStreet()
                                                    )
                                            ),
                                            travelRoute.getOrder(),
                                            travelRoute.getIsPickupLocation(),
                                            travelRoute.getIsDropLocation()
                                    );
                                }
                        ).toList()
                )
        ).toList();
    }

    @Override
    public TravelResponse createNewTravel(TravelRequest travelRequest) {
        Travel travel = travelRepository.save(new Travel(UUID.randomUUID(), travelRequest.getName(), false));
        return new TravelResponse(
                travel.getId(),
                travel.getName(),
                travel.getVisible(),
                Collections.emptyList()
        );
    }

    @Override
    public void toggleTravelVisibility(UUID travelId, Boolean visible) {
        Travel travel = travelRepository.findById(travelId).orElseThrow(() -> new UserFaultException(
                HttpStatus.BAD_REQUEST,
                "Travel not found"
        ));

        travel.setVisible(visible);

        travelRepository.save(travel);
    }

    @Override
    public List<TravelRouteResponse> createTravelRoute(UUID travelId, List<TravelRouteRequest> travelRoutes) {
        Travel travel = travelRepository.findById(travelId).orElseThrow(() -> new UserFaultException(
                HttpStatus.BAD_REQUEST,
                "Travel not found"
        ));

        Map<UUID, ShuttleOutletLocation> locations = shuttleOutletLocationRepository.findAllById(travelRoutes.stream()
                .map(TravelRouteRequest::getShuttleLocationId).toList()).stream().collect(
                Collectors.toMap(ShuttleOutletLocation::getId, Function.identity()));

        ArrayList<TravelRoute> travelRouteArrayList = new ArrayList<>();
        for (int i = 0; i < travelRoutes.size(); i++) {
            TravelRoute travelRoute = new TravelRoute();
            travelRoute.setTravel(travel);
            travelRoute.setOrder(i);
            travelRoute.setLocation(locations.get(travelRoute.getLocation().getId()));
            travelRouteArrayList.add(travelRoute);
        }

        List<TravelRoute> newTravelList = travelRouteRepository.saveAll(travelRouteArrayList);

        return newTravelList.stream().map(travelRoute -> {
            ShuttleOutletLocation location = travelRoute.getLocation();
            ShuttleOutletLocation.Address address = travelRoute.getLocation().getAddress();

            return new TravelRouteResponse(
                    travelRoute.getId(),
                    new ShuttleOutletLocationResponse(
                            location.getId(),
                            location.getName(),
                            location.getLatitude(),
                            location.getLongitude(),
                            new ShuttleOutletLocationResponse.Address(
                                    address.getProvinceId(),
                                    address.getProvince(),
                                    address.getRegencyId(),
                                    address.getRegency(),
                                    address.getDistrictId(),
                                    address.getDistrict(),
                                    address.getVillageId(),
                                    address.getVillage(),
                                    address.getStreet()
                            )
                    ),
                    travelRoute.getOrder(),
                    travelRoute.getIsPickupLocation(),
                    travelRoute.getIsDropLocation()
            );
        }).toList();
    }

    @Override
    public List<TravelRouteResponse> editTravelRoute(UUID travelId, List<TravelRouteEditRequest> travelRoutes) {
        Travel travel = travelRepository.findById(travelId).orElseThrow(() -> new UserFaultException(
                HttpStatus.BAD_REQUEST,
                "Travel not found"
        ));

        Map<UUID, ShuttleOutletLocation> locations = shuttleOutletLocationRepository.findAllById(travelRoutes.stream()
                .map(TravelRouteEditRequest::getShuttleLocationId).toList()).stream().collect(
                Collectors.toMap(ShuttleOutletLocation::getId, Function.identity()));
        List<TravelRoute> currentTravelRoutes = travelRouteRepository.findByTravelId(travelId);

        ArrayList<TravelRoute> travelRouteArrayList = new ArrayList<>();
        for (int i = 0; i < travelRoutes.size(); i++) {
            TravelRoute travelRoute = new TravelRoute();
            travelRoute.setId(travelRoute.getId());
            travelRoute.setTravel(travel);
            travelRoute.setOrder(i);
            travelRoute.setLocation(locations.get(travelRoute.getLocation().getId()));
            travelRouteArrayList.add(travelRoute);
        }
        List<TravelRoute> newTravelList = travelRouteRepository.saveAll(travelRouteArrayList);

        travelRouteArrayList.clear();
        for (int i = currentTravelRoutes.size() - 1; i < travelRoutes.size(); i++) {
            TravelRoute travelRoute = new TravelRoute();
            travelRouteArrayList.add(travelRoute);
        }
        travelRouteRepository.deleteAllById(travelRouteArrayList.stream().map(TravelRoute::getId).toList());

        return newTravelList.stream().map(travelRoute -> {
            ShuttleOutletLocation location = travelRoute.getLocation();
            ShuttleOutletLocation.Address address = travelRoute.getLocation().getAddress();

            return new TravelRouteResponse(
                    travelRoute.getId(),
                    new ShuttleOutletLocationResponse(
                            location.getId(),
                            location.getName(),
                            location.getLatitude(),
                            location.getLongitude(),
                            new ShuttleOutletLocationResponse.Address(
                                    address.getProvinceId(),
                                    address.getProvince(),
                                    address.getRegencyId(),
                                    address.getRegency(),
                                    address.getDistrictId(),
                                    address.getDistrict(),
                                    address.getVillageId(),
                                    address.getVillage(),
                                    address.getStreet()
                            )
                    ),
                    travelRoute.getOrder(),
                    travelRoute.getIsPickupLocation(),
                    travelRoute.getIsDropLocation()
            );
        }).toList();
    }
}
