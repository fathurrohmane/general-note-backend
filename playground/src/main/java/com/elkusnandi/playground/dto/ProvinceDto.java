package com.elkusnandi.playground.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ProvinceDto {

    @JsonProperty("id")
    private String id;

    @JsonProperty("name")
    private String name;

}
