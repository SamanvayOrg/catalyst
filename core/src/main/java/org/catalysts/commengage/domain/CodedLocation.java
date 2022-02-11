package org.catalysts.commengage.domain;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@Table
@Entity
public class CodedLocation extends AuditedEntity {
    @Column
    private String country;

    @Column
    private String state;

    @Column
    private String district;

    @Column
    private String subDistrict;

    @Column
    private String villageCity;

    @Column
    private String subLocality;

    @Column
    private String pinCode;

    @Column
    @NotNull
    private double lat;

    @Column
    @NotNull
    private double lng;

    @Column
    private int numberOfTimesLookedUp;

    public String getLatLng() {
        return String.format("%f,%f", lat, lng);
    }

    public void incrementNumberOfTimesLookedUp() {
        numberOfTimesLookedUp++;
    }
}
