package org.catalysts.commengage.domain;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.catalysts.commengage.domain.fes.FESResponseFactory;
import org.catalysts.commengage.domain.fes.FESReverseGeoResponse;
import org.catalysts.commengage.domain.fes.FESReverseGeoSuccessResponse;
import org.catalysts.commengage.util.FileUtil;
import org.catalysts.commengage.util.ObjectMapperFactory;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FESReverseGeoResponseTest {
    @Test
    public void deserializeVillage() throws JsonProcessingException {
        String s = FileUtil.readFile("/fesVillageResponse.json");
        FESReverseGeoResponse response = FESResponseFactory.getResponse(s);
        assertEquals("India", response.getCountry());
        assertEquals("Odisha", response.getState());
        assertEquals("Khordha", response.getDistrict());
        assertEquals("Balianta", response.getSubDistrict());
        assertEquals("Bhelurihat", response.getVillageCity());
        assertEquals("Bainchua", response.getPanchayat());
        assertEquals("Balianta", response.getBlock());
    }

    @Test
    public void deserializeOutsideIndiaLocation() throws JsonProcessingException {
        String s = FileUtil.readFile("/fesOutsideIndiaResponse.json");
        FESReverseGeoResponse response = FESResponseFactory.getResponse(s);
        assertEquals("Unknown", response.getCountry());
        assertNull(response.getState());
        assertNull(response.getDistrict());
        assertNull(response.getSubDistrict());
        assertNull(response.getVillageCity());
        assertNull(response.getPanchayat());
        assertNull(response.getBlock());
    }
}
