package org.catalysts.commengage.scheduler;

import lombok.extern.slf4j.Slf4j;
import org.catalysts.commengage.service.HealthCheckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class JobScheduler {
    private final QrdProcessor qrdProcessor;
    private final HealthCheckService healthCheckService;
    private final CodedLocationProcessor codedLocationProcessor;

    @Autowired
    public JobScheduler(QrdProcessor qrdProcessor, HealthCheckService healthCheckService, CodedLocationProcessor codedLocationProcessor) {
        this.qrdProcessor = qrdProcessor;
        this.healthCheckService = healthCheckService;
        this.codedLocationProcessor = codedLocationProcessor;
    }

    @Scheduled(cron = "${commengage.qrd.cron}")
    public void qrdJob() {
        try {
            log.info("Qrd background job started");
            qrdProcessor.processQrCodes();
            healthCheckService.verifyMainJob();
            log.info("Qrd background job completed");
        } catch (Exception e) {
            log.error("Job Failed", e);
        }
    }

    @Scheduled(cron = "${commengage.google.cron}")
    public void googleJob() {
        try {
            log.info("Google background job started");
            codedLocationProcessor.process();
            healthCheckService.verifyGoogleJob();
            log.info("Qrd background job completed");
        } catch (Exception e) {
            log.error("Job Failed", e);
        }
    }
}
