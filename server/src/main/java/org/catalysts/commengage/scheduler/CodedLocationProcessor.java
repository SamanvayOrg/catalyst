package org.catalysts.commengage.scheduler;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.catalysts.commengage.config.AppConfig;
import org.catalysts.commengage.domain.CodedLocation;
import org.catalysts.commengage.domain.fes.FESReverseGeoResponse;
import org.catalysts.commengage.domain.fes.FESReverseGeoSuccessResponse;
import org.catalysts.commengage.repository.CodedLocationRepository;
import org.catalysts.commengage.repository.FESReverseGeoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
public class CodedLocationProcessor {
    private final CodedLocationRepository codedLocationRepository;
    private final FESReverseGeoRepository fesReverseGeoRepository;
    private final AppConfig appConfig;

    @Autowired
    public CodedLocationProcessor(CodedLocationRepository codedLocationRepository, FESReverseGeoRepository fesReverseGeoRepository, AppConfig appConfig) {
        this.codedLocationRepository = codedLocationRepository;
        this.fesReverseGeoRepository = fesReverseGeoRepository;
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
        FESReverseGeoResponse reverseGeocode = fesReverseGeoRepository.getReverseGeocode(codedLocation);
        codedLocation.setCountry(reverseGeocode.getCountry());
        codedLocation.setState(reverseGeocode.getState());
        codedLocation.setDistrict(reverseGeocode.getDistrict());
        codedLocation.setSubDistrict(reverseGeocode.getSubDistrict());
        codedLocation.setBlock(reverseGeocode.getBlock());
        codedLocation.setPanchayat(reverseGeocode.getPanchayat());
        codedLocation.setVillageCity(reverseGeocode.getVillageCity());
        codedLocation.incrementNumberOfTimesLookedUp();
        codedLocationRepository.save(codedLocation);
    }
}
