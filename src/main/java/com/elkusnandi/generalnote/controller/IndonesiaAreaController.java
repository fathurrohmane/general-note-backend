package com.elkusnandi.generalnote.controller;

import com.elkusnandi.generalnote.common.base.BaseResponse;
import com.elkusnandi.generalnote.response.DistrictResponse;
import com.elkusnandi.generalnote.response.ProvinceResponse;
import com.elkusnandi.generalnote.response.RegencyResponse;
import com.elkusnandi.generalnote.response.VillageResponse;
import com.elkusnandi.generalnote.service.IndonesiaAreaService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/area")
public class IndonesiaAreaController {

    private final IndonesiaAreaService indonesiaAreaService;

    public IndonesiaAreaController(IndonesiaAreaService indonesiaAreaService) {
        this.indonesiaAreaService = indonesiaAreaService;
    }

    @GetMapping("/province")
    public BaseResponse<List<ProvinceResponse>> getProvince(@RequestParam(name = "q", required = false) String query) {
        return new BaseResponse<>(indonesiaAreaService.getProvinces(query), HttpStatus.OK, true, "");
    }

    @GetMapping("/province/{provinceId}/regency")
    public BaseResponse<List<RegencyResponse>> getRegencies(
            @PathVariable(name = "provinceId") String provinceId,
            @RequestParam(name = "q", required = false) String query
    ) {
        return new BaseResponse<>(indonesiaAreaService.getRegencies(provinceId, query), HttpStatus.OK, true, "");
    }

    @GetMapping("/province/{provinceId}/regency/{regencyId}/district")
    public BaseResponse<List<DistrictResponse>> getDistricts(
            @PathVariable(name = "provinceId") String provinceId,
            @PathVariable(name = "regencyId") String regencyId,
            @RequestParam(name = "q", required = false) String query
    ) {
        return new BaseResponse<>(
                indonesiaAreaService.getDistricts(provinceId, regencyId, query),
                HttpStatus.OK,
                true,
                ""
        );
    }

    @GetMapping("/province/{provinceId}/regency/{regencyId}/district/{districtId}/village")
    public BaseResponse<List<VillageResponse>> getVillages(
            @PathVariable(name = "provinceId") String provinceId,
            @PathVariable(name = "regencyId") String regencyId,
            @PathVariable(name = "districtId") String districtId,
            @RequestParam(name = "q", required = false) String query
    ) {
        return new BaseResponse<>(
                indonesiaAreaService.getVillages(provinceId, regencyId, districtId, query),
                HttpStatus.OK,
                true,
                ""
        );
    }
}
