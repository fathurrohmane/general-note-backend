package com.elkusnandi.generalnote.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ShuttleOutletLocationRequest {

    @NotBlank
    private String name;

    @NotBlank
    private Long latitude;

    @NotBlank
    private Long longitude;

    @NotBlank
    private String provinceId;

    @NotBlank
    private String regencyId;

    @NotBlank
    private String districtId;

    @NotBlank
    private String villageId;

    @NotBlank
    private String street;

}

