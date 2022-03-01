package org.catalysts.commengage.domain;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.catalysts.commengage.util.FileUtil;
import org.catalysts.commengage.util.ObjectMapperFactory;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FESReverseGeoResponseTest {
    @Test
    public void deserializeVillage() throws JsonProcessingException {
        String s = FileUtil.readFile("/fesVillageResponse.json");
        FESReverseGeoResponse response = ObjectMapperFactory.OBJECT_MAPPER.readValue(s, FESReverseGeoResponse.class);
        assertEquals("India", response.getCountry());
        assertEquals("Odisha", response.getState());
        assertEquals("Khordha", response.getDistrict());
        assertEquals("Balianta", response.getSubDistrict());
        assertEquals("Bhelurihat", response.getVillageCity());
        assertEquals("Bainchua", response.getPanchayat());
        assertEquals("Balianta", response.getBlock());
    }
}
