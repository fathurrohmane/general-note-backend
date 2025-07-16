package com.elkusnandi.generalnote.service.impl;

import com.elkusnandi.generalnote.entity.ShuttleOutletLocation;
import com.elkusnandi.generalnote.entity.Travel;
import com.elkusnandi.generalnote.entity.TravelRoute;
import com.elkusnandi.generalnote.exception.UserFaultException;
import com.elkusnandi.generalnote.mapper.TravelMapper;
import com.elkusnandi.generalnote.mapper.TravelRouteMapper;
import com.elkusnandi.generalnote.repository.ShuttleOutletLocationRepository;
import com.elkusnandi.generalnote.repository.TravelRepository;
import com.elkusnandi.generalnote.repository.TravelRouteRepository;
import com.elkusnandi.generalnote.request.TravelRequest;
import com.elkusnandi.generalnote.request.TravelRouteEditRequest;
import com.elkusnandi.generalnote.request.TravelRouteRequest;
import com.elkusnandi.generalnote.response.TravelResponse;
import com.elkusnandi.generalnote.response.TravelRouteResponse;
import com.elkusnandi.generalnote.service.TravelService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
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
        return travelRepository.findAll().stream().map(TravelMapper.INSTANCE::entityToResponse).toList();
    }

    @Override
    @PreAuthorize("hasRole('admin')")
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
    @PreAuthorize("hasRole('admin')")
    public void toggleTravelVisibility(UUID travelId, Boolean visible) {
        Travel travel = travelRepository.findById(travelId).orElseThrow(() -> new UserFaultException(
                HttpStatus.BAD_REQUEST,
                "Travel not found"
        ));

        travel.setVisible(visible);

        travelRepository.save(travel);
    }

    @Override
    @PreAuthorize("hasRole('admin')")
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
            travelRoute.setId(UUID.randomUUID());
            travelRoute.setTravel(travel);
            travelRoute.setOrder(i);

            ShuttleOutletLocation currentLocation = locations.get(travelRoutes.get(i).getShuttleLocationId());
            if (currentLocation == null) {
                throw new UserFaultException(HttpStatus.BAD_REQUEST, "Location not found");
            }

            travelRoute.setLocation(currentLocation);
            travelRoute.setIsPickupLocation(travelRoutes.get(i).getIsPickupLocation());
            travelRoute.setIsDropLocation(travelRoutes.get(i).getIsDropLocation());
            travelRouteArrayList.add(travelRoute);
        }

        List<TravelRoute> newTravelList = travelRouteRepository.saveAll(travelRouteArrayList);

        return newTravelList.stream().map(TravelRouteMapper.INSTANCE::entityToResponse).toList();
    }

    @Override
    @Transactional
    @PreAuthorize("hasRole('admin')")
    public List<TravelRouteResponse> editTravelRoute(UUID travelId, List<TravelRouteEditRequest> travelRoutes) {
        Map<UUID, ShuttleOutletLocation> locations = shuttleOutletLocationRepository.findAllById(travelRoutes.stream()
                .map(TravelRouteEditRequest::getShuttleLocationId).toList()).stream().collect(
                Collectors.toMap(ShuttleOutletLocation::getId, Function.identity()));
        travelRouteRepository.deleteByTravelIdOrderByOrder(travelId);

        Travel travel = travelRepository.findById(travelId).orElseThrow(() -> new UserFaultException(
                HttpStatus.BAD_REQUEST,
                "Travel not found"
        ));

        ArrayList<TravelRoute> travelRouteArrayList = new ArrayList<>();
        for (int i = 0; i < travelRoutes.size(); i++) {
            TravelRoute travelRoute = new TravelRoute();
            travelRoute.setId(UUID.randomUUID());
            travelRoute.setOrder(i);
            travelRoute.setTravel(travel);

            ShuttleOutletLocation currentLocation = locations.get(travelRoutes.get(i).getShuttleLocationId());
            if (currentLocation == null) {
                throw new UserFaultException(HttpStatus.BAD_REQUEST, "Location not found");
            }

            travelRoute.setLocation(currentLocation);
            travelRoute.setIsPickupLocation(travelRoutes.get(i).getIsPickupLocation());
            travelRoute.setIsDropLocation(travelRoutes.get(i).getIsDropLocation());
            travelRouteArrayList.add(travelRoute);
        }
        List<TravelRoute> newTravelList = travelRouteRepository.saveAll(travelRouteArrayList);

        return newTravelList.stream().map(TravelRouteMapper.INSTANCE::entityToResponse).toList();
    }

    @Override
    @PreAuthorize("hasRole('admin')")
    public void deleteTravel(UUID travelId) {
        travelRepository.deleteById(travelId);
    }
}
