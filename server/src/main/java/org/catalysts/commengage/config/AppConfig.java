package org.catalysts.commengage.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AppConfig {
    @Value("${google.api.key}")
    private String apiKey;

    @Value("${google.cache.days}")
    private int cacheDays;

    public String getApiKey() {
        return apiKey;
    }

    public int getCacheDays() {
        return cacheDays;
    }

    public void setCacheDays(int cacheDays) {
        this.cacheDays = cacheDays;
    }
}
