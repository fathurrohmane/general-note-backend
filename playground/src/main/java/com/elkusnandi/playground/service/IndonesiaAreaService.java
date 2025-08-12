package com.elkusnandi.playground.service;

import com.elkusnandi.playground.response.DistrictResponse;
import com.elkusnandi.playground.response.ProvinceResponse;
import com.elkusnandi.playground.response.RegencyResponse;
import com.elkusnandi.playground.response.VillageResponse;

import java.util.List;

public interface IndonesiaAreaService {

    List<ProvinceResponse> getProvinces(String query);

    List<RegencyResponse> getRegencies(String provinceId, String query);

    List<DistrictResponse> getDistricts(String provinceId, String regencyId, String query);

    List<VillageResponse> getVillages(String provinceId, String regencyId, String districtId, String query);

}
