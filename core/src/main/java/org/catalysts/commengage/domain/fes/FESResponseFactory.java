package org.catalysts.commengage.domain.fes;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.catalysts.commengage.util.ObjectMapperFactory;

public class FESResponseFactory {
    public static final String UNKNOWN_REGION_ERROR = "Error: No region found for current location.";

    public static FESReverseGeoResponse getResponse(String json) throws JsonProcessingException {
        FESReverseGeoMetaResponse meta = ObjectMapperFactory.OBJECT_MAPPER.readValue(json, FESReverseGeoMetaResponse.class);
        if (!meta.getResponseType().equals("-1"))
            return ObjectMapperFactory.OBJECT_MAPPER.readValue(json, FESReverseGeoSuccessResponse.class);
        else if (meta.getText().equals(UNKNOWN_REGION_ERROR))
            return new FESReverseGeoOutsideIndiaResponse();
        else
            throw new RuntimeException("Unknown type of response from FES");
    }
}
