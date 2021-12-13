package org.catalysts.commengage.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class QrdAuthService {

    @Value("${qrd.auth.token}")
    private String qrdAuthToken;


    public String obtainAccessToken() {
        return qrdAuthToken;
    }
}