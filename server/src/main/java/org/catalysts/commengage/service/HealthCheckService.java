package org.catalysts.commengage.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@Component
public class HealthCheckService {
    @Value("${healthcheck.qrdJob}")
    private String qrdJob;

    @Value("${healthcheck.googleJob}")
    private String googleJob;

    private static final String PING_BASE_URL = "https://hc-ping.com/";

    private final RestTemplate restTemplate;

    @Autowired
    public HealthCheckService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public void verifyMainJob() {
        verify(qrdJob);
    }

    public void verify(String uuid) {
        restTemplate.exchange(URI.create(String.format("%s%s", PING_BASE_URL, uuid)), HttpMethod.GET, null, String.class);
    }
}
