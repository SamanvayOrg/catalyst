package org.catalysts.commengage.scheduler;

import lombok.extern.slf4j.Slf4j;
import org.catalysts.commengage.config.AppConfig;
import org.catalysts.commengage.domain.CodedLocation;
import org.catalysts.commengage.domain.GoogleReverseGeoResponse;
import org.catalysts.commengage.repository.CodedLocationRepository;
import org.catalysts.commengage.repository.GoogleReverseGeoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
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
            int count = codedLocationRepository.countNearExpiringAndNewLocations(appConfig.getCacheDays());
            if (count == 0) {
                log.info("No coded locations to be renewed or to be fetched");
                break;
            }

            List<CodedLocation> codedLocations = codedLocationRepository.getNearExpiringAndNewLocations(appConfig.getCacheDays());
            log.info("Found {} coded locations to be renewed or to be fetched", count);
            codedLocations.forEach(this::saveCodedLocation);
            log.info("Completed renewing/fetching coded locations");
        }
    }

    @Transactional
    protected void saveCodedLocation(CodedLocation codedLocation) {
        GoogleReverseGeoResponse reverseGeocode = googleReverseGeoRepository.getReverseGeocode(codedLocation);
        codedLocation.setCountry(reverseGeocode.getCountry());
        codedLocation.setState(reverseGeocode.getState());
        codedLocation.setDistrict(reverseGeocode.getDistrict());
        codedLocation.setVillageCity(reverseGeocode.getVillageCity());
        codedLocation.setSubLocality(reverseGeocode.getSublocality());
        codedLocation.setPinCode(reverseGeocode.getPinCode());
        codedLocation.incrementNumberOfTimesLookedUp();
        codedLocationRepository.save(codedLocation);
    }
}
