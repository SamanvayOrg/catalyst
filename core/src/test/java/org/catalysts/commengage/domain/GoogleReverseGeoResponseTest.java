package org.catalysts.commengage.domain;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.catalysts.commengage.util.FileUtil;
import org.catalysts.commengage.util.ObjectMapperFactory;
import org.junit.jupiter.api.Test;

class GoogleReverseGeoResponseTest {
    @Test
    public void deserialize() throws JsonProcessingException {
        String s = FileUtil.readFile("/googleTestResponse.json");
        ObjectMapperFactory.OBJECT_MAPPER.readValue(s, GoogleReverseGeoResponse.class);
    }
}
