package org.catalysts.commengage.repository;

import org.catalysts.commengage.client.RequestHelper;
import org.catalysts.commengage.contract.mapmyindia.reversegeocode.ReverseGeocode;
import org.catalysts.commengage.contract.mapmyindia.reversegeocode.ReverseGeocodeResponse;
import org.catalysts.commengage.contract.qrd.QRCodeDetailsDto;
import org.catalysts.commengage.contract.qrd.QRCodesListingDto;
import org.catalysts.commengage.contract.qrd.QRDContainer;
import org.catalysts.commengage.service.MapMyIndiaAuthService;
import org.catalysts.commengage.service.QrdAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class MapMyIndiaApiRepository {

    @Value("${mapmyindia.api.baseUrl}")
    private String baseUrl;

    @Autowired
    private MapMyIndiaAuthService mapMyIndiaAuthService;

    @Autowired
    private RestTemplate restTemplate;

    private final static String REGION = "IND";
    private final static String LANG = "en";

    public ReverseGeocode getReverseGeoCode(double lat, double lng) {
        String accessToken = mapMyIndiaAuthService.obtainAccessToken();
        URI uri = RequestHelper.createUri("%s/advancedmaps/v1/%s/rev_geocode".formatted(baseUrl, accessToken),
                reverseGeoCodeParams(lat, lng));
        ResponseEntity<ReverseGeocodeResponse> responseEntity =
                restTemplate.exchange(uri,
                        HttpMethod.GET,
                        HttpEntity.EMPTY,
                        new ParameterizedTypeReference<>() {
                        });
        return pickAndExpectOne(responseEntity.getBody().getResults());
    }

    private Map<String, String> reverseGeoCodeParams(double lat, double lng) {
        var params = new HashMap<String, String>();
        params.put("region", REGION);
        params.put("lang", LANG);
        params.put("lat", String.valueOf(lat));
        params.put("lng", String.valueOf(lng));
        return params;
    }

    protected <T> T pickAndExpectOne(List<T> list) {
        if (list.size() == 0) return null;
        if (list.size() > 1)
            throw new MultipleResultsFoundException("More than one entity found");
        return list.get(0);
    }

}
