package org.catalysts.commengage.contract.qrd;

import lombok.Getter;
import lombok.Setter;

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
}