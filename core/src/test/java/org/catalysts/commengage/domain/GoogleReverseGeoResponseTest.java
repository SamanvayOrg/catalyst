package org.catalysts.commengage.domain;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.catalysts.commengage.util.FileUtil;
import org.catalysts.commengage.util.ObjectMapperFactory;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class GoogleReverseGeoResponseTest {
    @Test
    public void deserialize() throws JsonProcessingException {
        String s = FileUtil.readFile("/googleTestResponse.json");
        GoogleReverseGeoResponse googleReverseGeoResponse = ObjectMapperFactory.OBJECT_MAPPER.readValue(s, GoogleReverseGeoResponse.class);
        assertNotEquals(0, googleReverseGeoResponse.getResults().size());
        assertNotEquals(0, googleReverseGeoResponse.getResults().get(0).getAddressComponents().size());
        Map<String, String> allData = googleReverseGeoResponse.getData();
        assertEquals("Junia", allData.get("locality"));
        assertEquals("Ajmer", allData.get("administrative_area_level_2"));
    }
}
