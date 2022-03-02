package org.catalysts.commengage.domain.fes;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class FESReverseGeoSuccessResponse implements FESReverseGeoResponse {
    @JsonProperty
    private String responseType;
    @JsonProperty
    private List<Result> text = new ArrayList<>();

    private static final String WITHIN_INDIA_RESPONSE_TYPE = "1";

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Result {
        private String id;
        private String name;
        private String type;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }

    @Override
    public String getCountry() {
        return responseType.equals(WITHIN_INDIA_RESPONSE_TYPE) ? "India" : "Unknown";
    }

    @Override
    public String getState() {
        return this.getField("State");
    }

    @Override
    public String getDistrict() {
        return this.getField("District");
    }

    @Override
    public String getSubDistrict() {
        return this.getField("Sub district");
    }

    @Override
    public String getBlock() {
        return this.getField("Block");
    }

    @Override
    public String getVillageCity() {
        return this.getField("Village");
    }

    @Override
    public String getPanchayat() {
        return this.getField("Panchayat");
    }

    private String getField(String fieldName) {
        FESReverseGeoSuccessResponse.Result result = this.text.stream().filter(x -> x.getType().equals(fieldName)).findFirst().orElse(null);
        return result == null ? null : result.getName();
    }
}
