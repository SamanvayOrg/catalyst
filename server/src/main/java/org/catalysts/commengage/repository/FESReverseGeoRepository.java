package org.catalysts.commengage.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.catalysts.commengage.client.RequestHelper;
import org.catalysts.commengage.config.AppConfig;
import org.catalysts.commengage.domain.CodedLocation;
import org.catalysts.commengage.domain.fes.FESResponseFactory;
import org.catalysts.commengage.domain.fes.FESReverseGeoResponse;
import org.catalysts.commengage.domain.fes.FESReverseGeoSuccessResponse;
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
@Slf4j
public class FESReverseGeoRepository {
    private final RestTemplate restTemplate;
    private AppConfig appConfig;

    @Autowired
    public FESReverseGeoRepository(RestTemplate restTemplate, AppConfig appConfig) {
        this.restTemplate = restTemplate;
        this.appConfig = appConfig;
    }

    public FESReverseGeoResponse getReverseGeocode(CodedLocation codedLocation) {
        try {
            URI uri = RequestHelper.createUri("https://adminhierarchy.indiaobservatory.org.in/API/getRegionDetailsByLatLon", getParams(codedLocation));

            log.info("Calling URL: {}", uri.toString());
            ResponseEntity<String> responseJson =
                    restTemplate.exchange(uri,
                            HttpMethod.GET,
                            HttpEntity.EMPTY,
                            new ParameterizedTypeReference<>() {
                            });
            String body = responseJson.getBody();
            return FESResponseFactory.getResponse(body);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private Map<String, String> getParams(CodedLocation codedLocation) {
        Map<String, String> params = new HashMap<>();
        params.put("lat", Double.toString(codedLocation.getLat()));
        params.put("lon", Double.toString(codedLocation.getLng()));
        return params;
    }
}
