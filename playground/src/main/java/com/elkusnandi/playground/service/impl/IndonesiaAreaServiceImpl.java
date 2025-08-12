package com.elkusnandi.playground.service.impl;


import com.elkusnandi.playground.dto.DistrictDto;
import com.elkusnandi.playground.dto.ProvinceDto;
import com.elkusnandi.playground.dto.RegencyDto;
import com.elkusnandi.playground.dto.VillageDto;
import com.elkusnandi.playground.response.DistrictResponse;
import com.elkusnandi.playground.response.ProvinceResponse;
import com.elkusnandi.playground.response.RegencyResponse;
import com.elkusnandi.playground.response.VillageResponse;
import com.elkusnandi.playground.service.IndonesiaAreaService;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Service
public class IndonesiaAreaServiceImpl implements IndonesiaAreaService {

    private final WebClient webClient;

    public IndonesiaAreaServiceImpl(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("https://www.emsifa.com/api-wilayah-indonesia/api/").build();
    }

    @Override
    public List<ProvinceResponse> getProvinces(String query) {
        List<ProvinceResponse> result =
                webClient.get().uri("/provinces.json").retrieve().bodyToFlux(ProvinceDto.class)
                        .map(provinceDto -> new ProvinceResponse(provinceDto.getId(), provinceDto.getName()))
                        .collectList().block();

        if (query == null || query.isBlank() || result == null) {
            return result;
        } else {
            return result.stream()
                    .filter(provinceDto -> provinceDto.getName().toLowerCase().contains(query.toLowerCase()))
                    .toList();
        }
    }

    @Override
    public List<RegencyResponse> getRegencies(String provinceId, String query) {
        List<RegencyResponse> result = webClient.get().uri(String.format("/regencies/%s.json", provinceId)).retrieve()
                .bodyToFlux(RegencyDto.class)
                .map(regencyDto -> new RegencyResponse(
                        regencyDto.getId(),
                        regencyDto.getId().substring(regencyDto.getProvinceId().length()),
                        regencyDto.getName()
                ))
                .collectList().block();

        if (query == null || query.isBlank() || result == null) {
            return result;
        } else {
            return result.stream()
                    .filter(regencyDto -> regencyDto.getName().toLowerCase().contains(query.toLowerCase()))
                    .toList();
        }
    }

    @Override
    public List<DistrictResponse> getDistricts(String provinceId, String regencyId, String query) {
        List<DistrictResponse> result =
                webClient.get().uri(String.format("/districts/%s%s.json", provinceId, regencyId)).retrieve()
                        .bodyToFlux(DistrictDto.class).map(districtDto -> new DistrictResponse(
                                districtDto.getId(),
                                districtDto.getId().substring(districtDto.getRegencyId().length()), districtDto.getName()
                        )).collectList().block();

        if (query == null || query.isBlank() || result == null) {
            return result;
        } else {
            return result.stream()
                    .filter(districtDto -> districtDto.getName().toLowerCase().contains(query.toLowerCase()))
                    .toList();
        }
    }

    @Override
    public List<VillageResponse> getVillages(String provinceId, String regencyId, String districtId, String query) {
        List<VillageResponse> result =
                webClient.get().uri(String.format("/villages/%s%s%s.json", provinceId, regencyId, districtId))
                        .retrieve()
                        .bodyToFlux(VillageDto.class)
                        .map(villageDto -> new VillageResponse(
                                villageDto.getId(),
                                villageDto.getId().substring(villageDto.getDistrictId().length()),
                                villageDto.getName()
                        )).collectList().block();

        if (query == null || query.isBlank() || result == null) {
            return result;
        } else {
            return result.stream()
                    .filter(villageDto -> villageDto.getName().toLowerCase().contains(query.toLowerCase()))
                    .toList();
        }
    }
}
