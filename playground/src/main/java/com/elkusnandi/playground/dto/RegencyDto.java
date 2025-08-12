package com.elkusnandi.playground.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class RegencyDto {

    @JsonProperty("id")
    private String id;

    @JsonProperty("province_id")
    private String provinceId;

    @JsonProperty("name")
    private String name;
}