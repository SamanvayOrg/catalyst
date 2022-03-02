package org.catalysts.commengage.domain.fes;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.catalysts.commengage.util.ObjectMapperFactory;

public class FESResponseFactory {
    public static FESReverseGeoResponse getResponse(String json) throws JsonProcessingException {
        FESReverseGeoMetaResponse meta = ObjectMapperFactory.OBJECT_MAPPER.readValue(json, FESReverseGeoMetaResponse.class);
        if (meta.getResponseType().equals("-1"))
            return new FESReverseGeoOutsideIndiaResponse();
        else {
            return ObjectMapperFactory.OBJECT_MAPPER.readValue(json, FESReverseGeoSuccessResponse.class);
        }
    }
}
