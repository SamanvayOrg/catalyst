package org.catalysts.commengage.contract.mapmyindia.reversegeocode;

import lombok.*;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class ReverseGeocode {
    private String city;
    private String village;
    private String subDistrict;
    private String district;
    private String state;
    private int pincode;
}
