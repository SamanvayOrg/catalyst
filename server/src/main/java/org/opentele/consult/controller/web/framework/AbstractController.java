package org.catalysts.commengage.controller.web.framework;

import org.catalysts.commengage.contract.ApplicationStatus;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public abstract class AbstractController {
    protected ResponseEntity<ApplicationStatus> createFailedResponse(String errorMessage) {
        return new ResponseEntity<>(ApplicationStatus.createFailure(errorMessage), HttpStatus.CONFLICT);
    }
    protected ResponseEntity<ApplicationStatus> createSuccessResponse() {
        return new ResponseEntity<>(new ApplicationStatus(), HttpStatus.OK);
    }
}
