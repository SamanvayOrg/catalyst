package org.catalysts.commengage.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class FESReverseGeoResponse {
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

    public String getCountry() {
        return responseType.equals(WITHIN_INDIA_RESPONSE_TYPE) ? "India" : "Unknown";
    }

    public String getState() {
        return this.getField("State");
    }

    public String getDistrict() {
        return this.getField("District");
    }

    public String getSubDistrict() {
        return this.getField("Sub district");
    }

    public String getBlock() {
        return this.getField("Block");
    }

    public String getVillageCity() {
        return this.getField("Village");
    }

    public String getPanchayat() {
        return this.getField("Panchayat");
    }

    private String getField(String fieldName) {
        Result result = this.text.stream().filter(x -> x.getType().equals(fieldName)).findFirst().orElse(null);
        return result == null ? null : result.getName();
    }
}
