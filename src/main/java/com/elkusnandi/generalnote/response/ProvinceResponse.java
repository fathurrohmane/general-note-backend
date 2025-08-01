package com.elkusnandi.generalnote.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProvinceResponse {
    @JsonProperty("id")
    private String id;

    @JsonProperty("name")
    private String name;
}