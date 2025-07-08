package com.elkusnandi.generalnote.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class VillageDto {
    @JsonProperty("id")
    private String id;

    @JsonProperty("district_id")
    private String districtId;

    @JsonProperty("name")
    private String name;
}
