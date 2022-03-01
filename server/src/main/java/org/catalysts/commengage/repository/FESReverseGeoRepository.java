package org.catalysts.commengage.repository;

import org.catalysts.commengage.client.RequestHelper;
import org.catalysts.commengage.config.AppConfig;
import org.catalysts.commengage.domain.CodedLocation;
import org.catalysts.commengage.domain.FESReverseGeoResponse;
import org.catalysts.commengage.domain.GoogleReverseGeoResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

@Component
public class FESReverseGeoRepository {
    public static final String RESULT_TYPE = "administrative_area_level_1|administrative_area_level_2|administrative_area_level_3|administrative_area_level_4|administrative_area_level_5|locality|sublocality|postal_code|plus_code";

    private final RestTemplate restTemplate;
    private AppConfig appConfig;

    @Autowired
    public FESReverseGeoRepository(RestTemplate restTemplate, AppConfig appConfig) {
        this.restTemplate = restTemplate;
        this.appConfig = appConfig;
    }

    public FESReverseGeoResponse getReverseGeocode(CodedLocation codedLocation) {
        URI uri = RequestHelper.createUri("https://adminhierarchy.indiaobservatory.org.in/API/getRegionDetailsByLatLon", getParams(codedLocation));
        ResponseEntity<FESReverseGeoResponse> responseEntity =
                restTemplate.exchange(uri,
                        HttpMethod.GET,
                        HttpEntity.EMPTY,
                        new ParameterizedTypeReference<>() {
                        });
        return responseEntity.getBody();
    }

    public Map<String, String> getParams(CodedLocation codedLocation) {
        Map<String, String> params = new HashMap<>();
        params.put("lat", Double.toString(codedLocation.getLat()));
        params.put("lng", Double.toString(codedLocation.getLng()));
        return params;
    }
}
