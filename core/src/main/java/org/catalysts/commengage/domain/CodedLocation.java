package org.catalysts.commengage.domain;

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
    private int pinCode;

    @Column
    private double lat;

    @Column
    private double lng;
}
