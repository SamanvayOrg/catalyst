package org.catalysts.commengage.domain;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.catalysts.commengage.util.FileUtil;
import org.catalysts.commengage.util.ObjectMapperFactory;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GoogleReverseGeoResponseTest {
    @Test
    public void deserializeVillage() throws JsonProcessingException {
        String s = FileUtil.readFile("/googleVillageResponse.json");
        GoogleReverseGeoResponse response = ObjectMapperFactory.OBJECT_MAPPER.readValue(s, GoogleReverseGeoResponse.class);
        assertNotEquals(0, response.getResults().size());
        assertNotEquals(0, response.getResults().get(0).getAddressComponents().size());

        assertEquals("India", response.getCountry());
        assertEquals("Odisha", response.getState());
        assertEquals("Khordha", response.getDistrict());
        assertNull(response.getSubDistrict());
        assertEquals("Rathipur", response.getCityVillage());
        assertEquals("752050", response.getPinCode());
    }

    @Test
    public void deserializeCity() throws JsonProcessingException {
        String s = FileUtil.readFile("/googleCityResponse.json");
        GoogleReverseGeoResponse response = ObjectMapperFactory.OBJECT_MAPPER.readValue(s, GoogleReverseGeoResponse.class);
        assertNotEquals(0, response.getResults().size());
        assertNotEquals(0, response.getResults().get(0).getAddressComponents().size());

        assertEquals("India", response.getCountry());
        assertEquals("Karnataka", response.getState());
        assertEquals("Bangalore Urban", response.getDistrict());
        assertNull(response.getSubDistrict());
        assertEquals("Bengaluru", response.getCityVillage());
        assertEquals("Jayamahal", response.getSublocality());
        assertEquals("560006", response.getPinCode());
    }
}
