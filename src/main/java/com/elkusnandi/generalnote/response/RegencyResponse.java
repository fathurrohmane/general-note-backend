package com.elkusnandi.generalnote.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RegencyResponse {
    @JsonProperty("id")
    private String id;

    @JsonProperty("short_id")
    private String shortId;

    @JsonProperty("name")
    private String name;
}
