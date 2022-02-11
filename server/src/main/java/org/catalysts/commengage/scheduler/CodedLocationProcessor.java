package org.catalysts.commengage.scheduler;

import org.catalysts.commengage.config.AppConfig;
import org.catalysts.commengage.domain.CodedLocation;
import org.catalysts.commengage.domain.GoogleReverseGeoResponse;
import org.catalysts.commengage.repository.CodedLocationRepository;
import org.catalysts.commengage.repository.GoogleReverseGeoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@Service
public class CodedLocationProcessor {
    private final CodedLocationRepository codedLocationRepository;
    private final GoogleReverseGeoRepository googleReverseGeoRepository;
    private final AppConfig appConfig;

    @Autowired
    public CodedLocationProcessor(CodedLocationRepository codedLocationRepository, GoogleReverseGeoRepository googleReverseGeoRepository, AppConfig appConfig) {
        this.codedLocationRepository = codedLocationRepository;
        this.googleReverseGeoRepository = googleReverseGeoRepository;
        this.appConfig = appConfig;
    }

    public void process() {
        while (true) {
            LocalDate localDate = LocalDate.now().minusDays(appConfig.getCacheDays());
            Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
            List<CodedLocation> codedLocations = codedLocationRepository.findAllByPopulatedOnceFalseOrLastModifiedDateBeforeOrderByLastModifiedDateAsc(date);
            if (codedLocations.size() == 0) break;

            codedLocations.forEach(this::saveCodedLocation);
        }
    }

    @Transactional
    protected void saveCodedLocation(CodedLocation codedLocation) {
        GoogleReverseGeoResponse reverseGeocode = googleReverseGeoRepository.getReverseGeocode(codedLocation);
        codedLocation.setPopulatedOnce(true);
        codedLocation.setCountry(reverseGeocode.getCountry());
        codedLocation.setState(reverseGeocode.getState());
        codedLocation.setDistrict(reverseGeocode.getDistrict());
        codedLocation.setVillageCity(reverseGeocode.getVillageCity());
        codedLocation.setSubLocality(reverseGeocode.getSublocality());
        codedLocation.setPinCode(reverseGeocode.getPinCode());
    }
}
