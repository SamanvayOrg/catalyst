package org.catalysts.commengage.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class MapMyIndiaAuthService implements AuthService {

    @Value("${mapmyindia.auth.token}")
    private String mapMyIndiaAuthToken;


    public String obtainAccessToken() {
        return mapMyIndiaAuthToken;
    }
}