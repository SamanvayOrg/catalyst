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

    @Scheduled(cron = "${commengage.main.cron}")
    public void mainJob() {
        try {
            log.info("Background job started");
            qrdProcessor.processQrCodes();
            codedLocationProcessor.process();
            healthCheckService.verifyMainJob();
            log.info("Background job completed");
        } catch (Exception e) {
            log.error("Job Failed", e);
        }
    }
}
