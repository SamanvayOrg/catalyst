package org.catalysts.commengage.repository;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class GoogleReverseGeoRepository {
    @Value("${google.api.key}")
    private String apiKey;
}
