package com.elkusnandi.generalnote.controller;

import com.elkusnandi.generalnote.common.base.BaseResponse;
import com.elkusnandi.generalnote.dto.DistrictDto;
import com.elkusnandi.generalnote.dto.ProvinceDto;
import com.elkusnandi.generalnote.dto.RegencyDto;
import com.elkusnandi.generalnote.dto.VillageDto;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Controller
@RequestMapping("/area")
public class IndonesiaAreaController {

    private final WebClient webClient;

    public IndonesiaAreaController(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("https://www.emsifa.com/api-wilayah-indonesia/api/").build();
    }

    @GetMapping("/province")
    public BaseResponse<List<ProvinceDto>> getProvince(@RequestParam(name = "q", required = false) String query) {
        List<ProvinceDto> result =
                webClient.get().uri("/provinces.json").retrieve().bodyToFlux(ProvinceDto.class).collectList().block();
        if (query.isBlank() || result == null) {
            return new BaseResponse<>(result, HttpStatus.OK, true, "");
        } else {
            return new BaseResponse<>(
                    result.stream().filter(provinceDto -> provinceDto.getName().toLowerCase().contains(query.toLowerCase())).toList(),
                    HttpStatus.OK,
                    true,
                    ""
            );
        }
    }

    @GetMapping("/province/{provinceId}/regency")
    public BaseResponse<List<RegencyDto>> getRegencies(
            @PathVariable(name = "provinceId") String provinceId,
            @RequestParam(name = "q", required = false) String query
    ) {
        List<RegencyDto> result = webClient.get().uri(String.format("/regencies/%s.json", provinceId)).retrieve()
                .bodyToFlux(RegencyDto.class).collectList().block();
        if (query.isBlank() || result == null) {
            return new BaseResponse<>(result, HttpStatus.OK, true, "");
        } else {
            return new BaseResponse<>(
                    result.stream().filter(regencyDto -> regencyDto.getName().toLowerCase().contains(query.toLowerCase())).toList(),
                    HttpStatus.OK,
                    true,
                    ""
            );
        }
    }

    @GetMapping("/province/{provinceId}/regency/{regencyId}/district")
    public BaseResponse<List<DistrictDto>> getDistricts(
            @PathVariable(name = "provinceId") String provinceId,
            @PathVariable(name = "regencyId") String regencyId,
            @RequestParam(name = "q", required = false) String query
    ) {
        List<DistrictDto> result =
                webClient.get().uri(String.format("/districts/%s%s.json", provinceId, regencyId)).retrieve()
                        .bodyToFlux(DistrictDto.class).collectList().block();
        if (query.isBlank() || result == null) {
            return new BaseResponse<>(result, HttpStatus.OK, true, "");
        } else {
            return new BaseResponse<>(
                    result.stream().filter(districtDto -> districtDto.getName().toLowerCase().contains(query.toLowerCase())).toList(),
                    HttpStatus.OK,
                    true,
                    ""
            );
        }
    }

    @GetMapping("/province/{provinceId}/regency/{regencyId}/district/{districtId}/village")
    public BaseResponse<List<VillageDto>> getVillages(
            @PathVariable(name = "provinceId") String provinceId,
            @PathVariable(name = "regencyId") String regencyId,
            @PathVariable(name = "districtId") String districtId,
            @RequestParam(name = "q", required = false) String query
    ) {
        List<VillageDto> result =
                webClient.get().uri(String.format("/villages/%s%s%s.json", provinceId, regencyId, districtId))
                        .retrieve()
                        .bodyToFlux(VillageDto.class).collectList().block();
        if (query.isBlank() || result == null) {
            return new BaseResponse<>(result, HttpStatus.OK, true, "");
        } else {
            return new BaseResponse<>(
                    result.stream().filter(villageDto -> villageDto.getName().toLowerCase().contains(query.toLowerCase())).toList(),
                    HttpStatus.OK,
                    true,
                    ""
            );
        }
    }
}
