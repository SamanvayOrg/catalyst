package org.catalysts.commengage.contract.qrd;

import lombok.Getter;
import lombok.Setter;
import org.catalysts.commengage.domain.UserRequest;
import org.catalysts.commengage.domain.geo.Point;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
public class UserRequestDto {

    private String uniqueid;

    private String anonymizedip;

    private LocalDate requestdate;

    private LocalTime localscantime;

    private double lat;

    private double lng;

    private double accuracy;

    private String browser;

    private String browserversion;

    private String os;

    private String osversion;

    private String timezone;

    private String model;

    private String brand;

    public UserRequest createEntity() {
        UserRequest entity = new UserRequest();
        entity.setUniqueRequestId(this.uniqueid);
        entity.setAnonymizedIp(this.anonymizedip);
        entity.setRequestDate(this.requestdate);
        entity.setLocalScanTime(this.localscantime);
        entity.setLatLong(new Point(this.lat, this.lng));
        entity.setAccuracy(this.accuracy);
        entity.setBrowser(this.browser);
        entity.setBrowserVersion(this.browserversion);
        entity.setOs(this.os);
        entity.setOsVersion(this.osversion);
        entity.setTimezone(this.timezone);
        entity.setModel(this.model);
        entity.setBrand(this.brand);
        return entity;
    }
}