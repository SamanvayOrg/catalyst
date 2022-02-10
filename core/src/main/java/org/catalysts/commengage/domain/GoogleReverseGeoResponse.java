package org.catalysts.commengage.domain;

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

    public List<Result> getResults() {
        return results;
    }

    public void setResults(List<Result> results) {
        this.results = results;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Result {
        @JsonProperty("address_components")
        private List<AddressComponent> addressComponents = new ArrayList<>();

        public List<AddressComponent> getAddressComponents() {
            return addressComponents;
        }

        public void setAddressComponents(List<AddressComponent> addressComponents) {
            this.addressComponents = addressComponents;
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

        public void setTypes(List<String> types) {
            this.types = types;
        }
    }

    public Map<String, String> getData() {
        List<AddressComponent> addressComponents = this.getResults().parallelStream().flatMap(result -> result.addressComponents.parallelStream()).collect(Collectors.toList());
        HashMap<String, String> data = new HashMap<>();
        addressComponents.forEach(addressComponent -> {
            addressComponent.types.stream().filter(type -> !type.equals("political")).forEach(type -> {
                data.put(type, addressComponent.name);
            });
        });
        return data;
    }
}
