package org.catalysts.commengage.domain;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.catalysts.commengage.util.FileUtil;
import org.catalysts.commengage.util.ObjectMapperFactory;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GoogleReverseGeoResponseTest {
    @Test
    public void deserialize() throws JsonProcessingException {
        String s = FileUtil.readFile("/googleTestResponse.json");
        GoogleReverseGeoResponse response = ObjectMapperFactory.OBJECT_MAPPER.readValue(s, GoogleReverseGeoResponse.class);
        assertNotEquals(0, response.getResults().size());
        assertNotEquals(0, response.getResults().get(0).getAddressComponents().size());

        assertEquals("India", response.getCountry());
        assertEquals("Odisha", response.getState());
        assertEquals("Khordha", response.getDistrict());
        assertNull(response.getSubDistrict());
        assertEquals("Rathipur", response.getVillage());
        assertEquals("752050", response.getPinCode());
    }
}
