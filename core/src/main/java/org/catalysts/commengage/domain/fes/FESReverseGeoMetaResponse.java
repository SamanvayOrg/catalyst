package org.catalysts.commengage.domain.fes;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class FESReverseGeoMetaResponse {
    @JsonProperty
    private String responseType;
    @JsonProperty
    private Object text;

    public String getResponseType() {
        return responseType;
    }

    public void setResponseType(String responseType) {
        this.responseType = responseType;
    }

    public Object getText() {
        return text;
    }

    public void setText(Object text) {
        this.text = text;
    }
}
