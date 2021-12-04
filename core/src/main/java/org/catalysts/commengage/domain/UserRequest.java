package org.catalysts.commengage.domain;

import org.catalysts.commengage.domain.geo.Point;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "user_request")
public class UserRequest {

    @ManyToOne()
    @JoinColumn(name = "qr_code_id")
    private QrCode qrCode;

    @ManyToOne()
    @JoinColumn(name = "location_id")
    private Location location;

    @Type(type = "org.catalysts.commengage.domain.geo")
    @Column
    private Point lat;

    @Type(type = "org.catalysts.commengage.domain.geo")
    @Column
    private Point lng;

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
    private String browser_version;

    @Column
    private String os;

    @Column
    private String os_version;

    @Column
    private String timezone;

    @Column
    private String model;

    @Column
    private String brand;

    public QrCode getQrCode() {
        return qrCode;
    }

    public void setQrCode(QrCode qrCode) {
        this.qrCode = qrCode;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Point getLat() {
        return lat;
    }

    public void setLat(Point lat) {
        this.lat = lat;
    }

    public Point getLng() {
        return lng;
    }

    public void setLng(Point lng) {
        this.lng = lng;
    }

    public String getUniqueRequestId() {
        return uniqueRequestId;
    }

    public void setUniqueRequestId(String uniqueRequestId) {
        this.uniqueRequestId = uniqueRequestId;
    }

    public LocalDate getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(LocalDate requestDate) {
        this.requestDate = requestDate;
    }

    public LocalTime getLocalScanTime() {
        return localScanTime;
    }

    public void setLocalScanTime(LocalTime localScanTime) {
        this.localScanTime = localScanTime;
    }

    public String getAnonymizedIp() {
        return anonymizedIp;
    }

    public void setAnonymizedIp(String anonymizedIp) {
        this.anonymizedIp = anonymizedIp;
    }

    public String getBrowser() {
        return browser;
    }

    public void setBrowser(String browser) {
        this.browser = browser;
    }

    public String getBrowser_version() {
        return browser_version;
    }

    public void setBrowser_version(String browser_version) {
        this.browser_version = browser_version;
    }

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public String getOs_version() {
        return os_version;
    }

    public void setOs_version(String os_version) {
        this.os_version = os_version;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }
}
