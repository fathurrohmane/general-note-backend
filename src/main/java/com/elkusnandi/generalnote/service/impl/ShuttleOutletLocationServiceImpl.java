package com.elkusnandi.generalnote.service.impl;

import com.elkusnandi.generalnote.entity.ShuttleOutletLocation;
import com.elkusnandi.generalnote.entity.ShuttleOutletLocation.Address;
import com.elkusnandi.generalnote.exception.UserFaultException;
import com.elkusnandi.generalnote.repository.ShuttleOutletLocationRepository;
import com.elkusnandi.generalnote.request.ShuttleOutletLocationRequest;
import com.elkusnandi.generalnote.response.DistrictResponse;
import com.elkusnandi.generalnote.response.ProvinceResponse;
import com.elkusnandi.generalnote.response.RegencyResponse;
import com.elkusnandi.generalnote.response.VillageResponse;
import com.elkusnandi.generalnote.service.IndonesiaAreaService;
import com.elkusnandi.generalnote.service.ShuttleOutletLocationService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
public class ShuttleOutletLocationServiceImpl implements ShuttleOutletLocationService {

    private final ShuttleOutletLocationRepository outletRepository;

    private final IndonesiaAreaService indonesiaAreaService;

    public ShuttleOutletLocationServiceImpl(
            ShuttleOutletLocationRepository outletRepository,
            IndonesiaAreaService indonesiaAreaService
    ) {
        this.outletRepository = outletRepository;
        this.indonesiaAreaService = indonesiaAreaService;
    }

    @Override
    public List<ShuttleOutletLocation> getShuttleOutletLocations() {
        return outletRepository.findAll();
    }

    @Override
    public List<ShuttleOutletLocation> getAvailableOutletLocation(UUID departureLocationId) {
        if (departureLocationId == null) {
            return outletRepository.findDepartureLocations();
        } else {
            return outletRepository.findArrivalLocation(departureLocationId);
        }
    }

    @Override
    public ShuttleOutletLocation getShuttleOutletLocationDetail(UUID outletId) {
        return outletRepository.findById(outletId).orElseThrow(() -> new UserFaultException(
                HttpStatus.BAD_REQUEST,
                "Shuttle Outlet Location not found"
        ));
    }

    @Override
    public ShuttleOutletLocation createShuttleOutletLocation(ShuttleOutletLocationRequest outlet) {
        return outletRepository.save(createShuttleOutletLocation(null, outlet));
    }

    @Override
    public ShuttleOutletLocation editShuttleOutletLocation(UUID outletId, ShuttleOutletLocationRequest outlet) {
        return outletRepository.save(createShuttleOutletLocation(outletId, outlet));
    }

    private ShuttleOutletLocation createShuttleOutletLocation(UUID outletId, ShuttleOutletLocationRequest outlet) {
        Optional<ProvinceResponse> province = indonesiaAreaService.getProvinces(null).stream()
                .filter(provinceResponse -> provinceResponse.getId().equals(outlet.getProvinceId())).findFirst();

        if (province.isEmpty()) {
            throw new UserFaultException(HttpStatus.BAD_REQUEST, "Province not found");
        }

        Optional<RegencyResponse> regency = indonesiaAreaService.getRegencies(province.get().getId(), null).stream()
                .filter(regencyResponse -> regencyResponse.getShortId().equals(outlet.getRegencyId())).findFirst();

        if (regency.isEmpty()) {
            throw new UserFaultException(HttpStatus.BAD_REQUEST, "Regency not found");
        }

        Optional<DistrictResponse> district =
                indonesiaAreaService.getDistricts(province.get().getId(), regency.get().getShortId(), null).stream()
                        .filter(districtResponse -> districtResponse.getShortId().equals(outlet.getDistrictId()))
                        .findFirst();

        if (district.isEmpty()) {
            throw new UserFaultException(HttpStatus.BAD_REQUEST, "District not found");
        }

        Optional<VillageResponse> village =
                indonesiaAreaService.getVillages(
                                province.get().getId(),
                                regency.get().getShortId(),
                                district.get().getShortId(),
                                null
                        ).stream()
                        .filter(villageResponse -> villageResponse.getShortId().equals(outlet.getVillageId()))
                        .findFirst();

        if (village.isEmpty()) {
            throw new UserFaultException(HttpStatus.BAD_REQUEST, "Village not found");
        }

        UUID id = Objects.requireNonNullElseGet(outletId, UUID::randomUUID);
        return new ShuttleOutletLocation(
                id,
                outlet.getName(),
                outlet.getLatitude(),
                outlet.getLongitude(),
                new Address(
                        province.get().getId(),
                        province.get().getName(),
                        regency.get().getId(),
                        regency.get().getName(),
                        district.get().getId(),
                        district.get().getName(),
                        village.get().getId(),
                        village.get().getName(),
                        outlet.getStreet()
                )
        );
    }

    @Override
    public void deleteShuttleOutletLocation(UUID outletId) {
        outletRepository.deleteById(outletId);
    }
}
