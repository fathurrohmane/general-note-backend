package com.elkusnandi.generalnote.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DistrictResponse {
    @JsonProperty("id")
    private String id;

    @JsonProperty("shortId")
    private String shortId;

    @JsonProperty("name")
    private String name;
}
