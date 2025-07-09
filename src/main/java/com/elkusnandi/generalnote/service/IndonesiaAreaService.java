package com.elkusnandi.generalnote.service;

import com.elkusnandi.generalnote.response.DistrictResponse;
import com.elkusnandi.generalnote.response.ProvinceResponse;
import com.elkusnandi.generalnote.response.RegencyResponse;
import com.elkusnandi.generalnote.response.VillageResponse;

import java.util.List;

public interface IndonesiaAreaService {

    List<ProvinceResponse> getProvinces(String query);

    List<RegencyResponse> getRegencies(String provinceId, String query);

    List<DistrictResponse> getDistricts(String provinceId, String regencyId, String query);

    List<VillageResponse> getVillages(String provinceId, String regencyId, String districtId, String query);

}
