package org.catalysts.commengage.repository;

import org.catalysts.commengage.domain.CodedLocation;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@Transactional
class CodedLocationRepositoryTest {
    @Autowired
    private CodedLocationRepository codedLocationRepository;

    @Test
    public void findByCodedLocationLatLong() {
        CodedLocation codedLocation = new CodedLocation();
        codedLocation.setLat(20.19);
        codedLocation.setLng(70.35);
        codedLocationRepository.save(codedLocation);
        CodedLocation savedLocation = codedLocationRepository.findByLatAndLng(codedLocation.getLat(), codedLocation.getLng());
        assertNotNull(savedLocation);
    }
}
