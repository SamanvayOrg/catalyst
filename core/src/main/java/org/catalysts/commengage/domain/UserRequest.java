package org.catalysts.commengage.domain;

import lombok.Getter;
import lombok.Setter;
import org.catalysts.commengage.domain.framework.AbstractEntity;
import org.catalysts.commengage.domain.geo.Point;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@Entity
@Table(name = "user_request")
public class UserRequest extends AbstractEntity {

    @ManyToOne()
    @JoinColumn(name = "qr_code_id")
    private QRCode qrCode;

//    @ManyToOne()
//    @JoinColumn(name = "location_id")
//    private Location location;

    @Column
    private double lat;

    @Column
    private double lng;

    @Column
    private double accuracy;

    @Column(name = "unique_id")
    private String uniqueRequestId;

    @Column
    private LocalDate requestDate;

    @Column
    private LocalTime localScanTime;

    @Column
    private String anonymizedIp;

    @Column
    private String browser;

    @Column
    private String browserVersion;

    @Column
    private String os;

    @Column
    private String osVersion;

    @Column
    private String timezone;

    @Column
    private String model;

    @Column
    private String brand;

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
