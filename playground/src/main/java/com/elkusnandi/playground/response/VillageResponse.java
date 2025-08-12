package com.elkusnandi.playground.response;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class VillageResponse {
    @JsonProperty("id")
    private String id;

    @JsonProperty("shortId")
    private String shortId;

    @JsonProperty("name")
    private String name;
}
