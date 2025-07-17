package com.elkusnandi.generalnote.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TravelRequest {

    @NotBlank
    private String name;

}
