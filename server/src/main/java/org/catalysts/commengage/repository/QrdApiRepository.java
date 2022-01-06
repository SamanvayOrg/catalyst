package org.catalysts.commengage.repository;

import org.catalysts.commengage.client.RequestHelper;
import org.catalysts.commengage.contract.qrd.QRCodeDetailsDto;
import org.catalysts.commengage.contract.qrd.QRCodeDto;
import org.catalysts.commengage.contract.qrd.QRCodesListingDto;
import org.catalysts.commengage.contract.qrd.QRDContainer;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class QrdApiRepository {

    private static final int QR_CODE_DETAILS_API_LIMIT = 1000;

    @Value("${qrd.api.baseUrl}")
    private String baseUrl;

    @Autowired
    private QrdAuthService qrdAuthService;

    @Autowired
    private RestTemplate restTemplate;

    public List<QRCodeDto> getQRCodes() {
        URI uri = RequestHelper.createUri(baseUrl + "/api/qrcodes", authParams());
        ResponseEntity<QRDContainer<QRCodesListingDto>> responseEntity =
                restTemplate.exchange(uri,
                        HttpMethod.GET,
                        HttpEntity.EMPTY,
                        new ParameterizedTypeReference<>() {
                        });
        return responseEntity.getBody().getResult().getQrcodes();
    }

    public QRDContainer<QRCodeDetailsDto> getQRCodeDetails(String qrCodeId, int requestsOffset) {
        URI uri = RequestHelper.createUri(baseUrl + "/api/details",
                qrCodeDetailsParams(qrCodeId, requestsOffset));
        ResponseEntity<QRDContainer<QRCodeDetailsDto>> responseEntity =
                restTemplate.exchange(uri,
                        HttpMethod.GET,
                        HttpEntity.EMPTY,
                        new ParameterizedTypeReference<>() {
                        });
        return responseEntity.getBody();
    }

    private Map<String, String> qrCodeDetailsParams(String qrCodeId, int requestsOffset) {
        var params = new HashMap<String, String>();
        params.putAll(authParams());
        params.put("id", qrCodeId);
        params.put("limit", String.valueOf(QR_CODE_DETAILS_API_LIMIT));
        params.put("offset", String.valueOf(requestsOffset));
        return params;
    }

    private Map<String, String> authParams() {
        Map<String, String> params = new HashMap<>();
        params.put("key", qrdAuthService.obtainAccessToken());
        return params;
    }
}