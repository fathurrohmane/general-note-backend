package com.elkusnandi.playground.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class DistrictDto {
    @JsonProperty("id")
    private String id;

    @JsonProperty("regency_id")
    private String regencyId;

    @JsonProperty("name")
    private String name;
}
