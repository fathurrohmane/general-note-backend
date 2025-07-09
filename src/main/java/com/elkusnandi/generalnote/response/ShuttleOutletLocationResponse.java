package com.elkusnandi.generalnote.response;


import lombok.Data;

import java.util.UUID;

@Data
public class ShuttleOutletLocationResponse {

    private UUID id;

    private String name;

    private Long latitude;

    private Long longitude;

    private ShuttleOutletLocationResponse.Address address;

    @Data
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
