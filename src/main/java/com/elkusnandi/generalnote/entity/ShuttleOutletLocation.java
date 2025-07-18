package com.elkusnandi.generalnote.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Data
@Table(name = "shuttle_outlet_location")
@AllArgsConstructor
@NoArgsConstructor
public class ShuttleOutletLocation {

    @Id
    @Column(name = "id")
    private UUID id;

    @Column(name = "name")
    private String name;

    @Column(name = "latitude")
    private Double latitude;

    @Column(name = "longitude")
    private Double longitude;

    @Embedded
    @Column(name = "address")
    private Address address;

    @Data
    @Embeddable
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Address {
        @Column(name = "provinceId")
        private String provinceId;
        @Column(name = "province")
        private String province;

        @Column(name = "regencyId")
        private String regencyId;
        @Column(name = "regency")
        private String regency;

        @Column(name = "districtId")
        private String districtId;
        @Column(name = "district")
        private String district;

        @Column(name = "villageId")
        private String villageId;
        @Column(name = "village")
        private String village;

        @Column(name = "street")
        private String street;
    }
}

