package org.catalysts.commengage.repository;

import org.catalysts.commengage.client.RequestHelper;
import org.catalysts.commengage.service.QrdAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.LinkedHashMap;
import java.util.Map;

@Repository
public class QrdRepository {

    @Value("${qrd.api.baseUrl}")
    private String baseUrl;

    @Autowired
    private QrdAuthService qrdAuthService;

    @Autowired
    private RestTemplate restTemplate;

    public LinkedHashMap<String, Object> getQRCodes() {
        URI uri = RequestHelper.createUri(baseUrl + "/api/qrcodes", authParams());
        ResponseEntity<LinkedHashMap<String, Object>> responseEntity =
                restTemplate.exchange(uri,
                        HttpMethod.GET,
                        HttpEntity.EMPTY,
                        new ParameterizedTypeReference<>() {
                        });
        return responseEntity.getBody();
    }

    private Map<String, String> authParams() {
        return Map.of("key", qrdAuthService.obtainAccessToken());
    }
}