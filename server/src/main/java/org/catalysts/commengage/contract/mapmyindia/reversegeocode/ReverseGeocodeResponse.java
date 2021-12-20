package org.catalysts.commengage.contract.mapmyindia.reversegeocode;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class ReverseGeocodeResponse {
    private List<ReverseGeocode> results;
}
