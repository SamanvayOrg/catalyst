package org.catalysts.commengage.repository;

import org.catalysts.commengage.domain.CodedLocation;
import org.catalysts.commengage.domain.fes.FESReverseGeoResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class FESReverseGeoRepositoryTest {
    @Autowired
    private FESReverseGeoRepository repository;

    @Test
    void getReverseGeocode() {
        CodedLocation codedLocation = new CodedLocation();
        codedLocation.setLat(20.264);
        codedLocation.setLng(85.891);
        FESReverseGeoResponse reverseGeocode = repository.getReverseGeocode(codedLocation);
        assertEquals("Odisha", reverseGeocode.getState());
    }
}
