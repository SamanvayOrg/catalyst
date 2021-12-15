package org.catalysts.commengage.domain;

import lombok.Getter;
import lombok.Setter;
import org.catalysts.commengage.domain.framework.AbstractEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "location")
public class Location extends AbstractEntity {
    @Column
    private String village;

    @Column
    private String district;

    @Column
    private String subDistrict;

    @Column
    private String city;

    @Column
    private String state;

    @Column
    private String pinCode;
}
