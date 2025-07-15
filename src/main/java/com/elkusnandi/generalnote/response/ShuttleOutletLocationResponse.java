package com.elkusnandi.generalnote.response;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class ShuttleOutletLocationResponse {

    private UUID id;

    private String name;

    private Double latitude;

    private Double longitude;

    private ShuttleOutletLocationResponse.Address address;

    @Data
    @AllArgsConstructor
    public static class Address {
        private String provinceId;
        private String province;

        private String regencyId;
        private String regency;

        private String districtId;
        private String district;

        private String villageId;
        private String village;

        private String street;
    }
}
