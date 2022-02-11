package org.catalysts.commengage.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GoogleReverseGeoResponse {
    private List<Result> results = new ArrayList<>();
    @JsonIgnore
    private HashMap<String, String> data;

    public List<Result> getResults() {
        return results;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Result {
        @JsonProperty("address_components")
        private List<AddressComponent> addressComponents = new ArrayList<>();

        public List<AddressComponent> getAddressComponents() {
            return addressComponents;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class AddressComponent {
        @JsonProperty("long_name")
        private String name;
        private List<String> types = new ArrayList<>();

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<String> getTypes() {
            return types;
        }
    }

    public String getCountry() {
        return this.getField("country");
    }

    public String getState() {
        return this.getField("administrative_area_level_1");
    }

    public String getDistrict() {
        return this.getField("administrative_area_level_2");
    }

    public String getSubDistrict() {
        return this.getField("administrative_area_level_3");
    }

    public String getCityVillage() {
        return this.getField("locality");
    }

    public String getPinCode() {
        return this.getField("postal_code");
    }

    private String getField(String fieldName) {
        if (data == null) {
            List<AddressComponent> addressComponents = this.getResults().parallelStream().flatMap(result -> result.addressComponents.parallelStream()).collect(Collectors.toList());
            data = new HashMap<>();
            addressComponents.forEach(addressComponent -> {
                addressComponent.types.stream().filter(type -> !type.equals("political")).forEach(type -> {
                    data.put(type, addressComponent.name);
                });
            });
        }
        return data.get(fieldName);
    }

    public String getSublocality() {
        return getField("sublocality");
    }
}
