package org.catalysts.commengage.repository;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class GoogleReverseGeoRepository {
    public static final String RESULT_TYPE = "country|administrative_area_level_1|administrative_area_level_2|administrative_area_level_3|administrative_area_level_4|administrative_area_level_5|administrative_area_level_6|administrative_area_level_7|colloquial_area|postal_code|locality";

    @Value("${google.api.key}")
    private String apiKey;

    public void getReverseGeocode() {

    }
}