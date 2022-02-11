package org.catalysts.commengage.repository;

import org.catalysts.commengage.client.RequestHelper;
import org.catalysts.commengage.config.AppConfig;
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
public class GoogleReverseGeoRepository {
    public static final String RESULT_TYPE = "administrative_area_level_1|administrative_area_level_2|administrative_area_level_3|administrative_area_level_4|administrative_area_level_5|locality|sublocality|postal_code|plus_code";

    private final RestTemplate restTemplate;
    private final Map<String, String> params;

    @Autowired
    public GoogleReverseGeoRepository(RestTemplate restTemplate, AppConfig appConfig) {
        this.restTemplate = restTemplate;
        params = new HashMap<>();
        params.put("key", appConfig.getApiKey());
        params.put("result_type", RESULT_TYPE);
    }

    public GoogleReverseGeoResponse getReverseGeocode() {
        URI uri = RequestHelper.createUri("https://maps.googleapis.com/maps/api/geocode/json", params);
        ResponseEntity<GoogleReverseGeoResponse> responseEntity =
                restTemplate.exchange(uri,
                        HttpMethod.GET,
                        HttpEntity.EMPTY,
                        new ParameterizedTypeReference<>() {
                        });
        return responseEntity.getBody();
    }
}
