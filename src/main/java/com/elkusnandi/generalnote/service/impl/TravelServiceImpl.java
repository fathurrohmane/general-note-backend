package com.elkusnandi.generalnote.service.impl;

import com.elkusnandi.generalnote.entity.ShuttleOutletLocation;
import com.elkusnandi.generalnote.entity.Travel;
import com.elkusnandi.generalnote.entity.TravelRoute;
import com.elkusnandi.generalnote.exception.UserFaultException;
import com.elkusnandi.generalnote.repository.ShuttleOutletLocationRepository;
import com.elkusnandi.generalnote.repository.TravelRepository;
import com.elkusnandi.generalnote.repository.TravelRouteRepository;
import com.elkusnandi.generalnote.service.TravelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class TravelServiceImpl implements TravelService {

    private final ShuttleOutletLocationRepository shuttleOutletLocationRepository;

    private final TravelRepository travelRepository;

    private final TravelRouteRepository travelRouteRepository;

    public TravelServiceImpl(
            ShuttleOutletLocationRepository shuttleOutletLocationRepository,
            TravelRepository travelRepository,
            TravelRouteRepository travelRouteRepository
    ) {
        this.shuttleOutletLocationRepository = shuttleOutletLocationRepository;
        this.travelRepository = travelRepository;
        this.travelRouteRepository = travelRouteRepository;
    }

    @Autowired


    @Override
    public String createNewTravel(String name) {
        travelRepository.save(new Travel(UUID.randomUUID(), name, false));
        return "";
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
    public String createTravelRoute(UUID travelId, List<TravelRoute> travelRoutes) {
        Travel travel = travelRepository.findById(travelId).orElseThrow(() -> new UserFaultException(
                HttpStatus.BAD_REQUEST,
                "Travel not found"
        ));

        Map<UUID, ShuttleOutletLocation> locations = shuttleOutletLocationRepository.findAllById(travelRoutes.stream()
                .map(travelRoute -> travelRoute.getLocation().getId()).toList()).stream().collect(
                Collectors.toMap(ShuttleOutletLocation::getId, Function.identity()));

        for (int i = 0; i < travelRoutes.size(); i++) {
            TravelRoute travelRoute = travelRoutes.get(i);
            travelRoute.setTravel(travel);
            travelRoute.setOrder(i);
            ShuttleOutletLocation shuttleOutletLocation =
                    locations.get(travelRoute.getLocation().getId());
            travelRoute.setLocation(shuttleOutletLocation);
        }

        travelRouteRepository.saveAll(travelRoutes);

        return "";
    }

    @Override
    public String editTravelRoute(UUID travelId, List<TravelRoute> travelRoutes) {
        Travel travel = travelRepository.findById(travelId).orElseThrow(() -> new UserFaultException(
                HttpStatus.BAD_REQUEST,
                "Travel not found"
        ));

        List<TravelRoute> travelRouteList =
                travelRouteRepository.findAllById(travelRoutes.stream().map(TravelRoute::getId).toList());
        Map<UUID, ShuttleOutletLocation> locations = shuttleOutletLocationRepository.findAllById(travelRoutes.stream()
                .map(travelRoute -> travelRoute.getLocation().getId()).toList()).stream().collect(
                Collectors.toMap(ShuttleOutletLocation::getId, Function.identity()));

        for (int i = 0; i < travelRouteList.size(); i++) {
            TravelRoute travelRoute = travelRouteList.get(i);
            travelRoute.setTravel(travel);
            travelRoute.setOrder(i);
            ShuttleOutletLocation shuttleOutletLocation = locations.get(travelRoute.getLocation().getId());
            travelRoute.setLocation(shuttleOutletLocation);
        }

        travelRouteRepository.saveAll(travelRoutes);

        return "";
    }
}
