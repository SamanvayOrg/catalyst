package org.catalysts.commengage.scheduler;

import lombok.extern.slf4j.Slf4j;
import org.catalysts.commengage.service.HealthCheckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class QrdScheduler {
    private final QrdProcessor qrdProcessor;
    private final HealthCheckService healthCheckService;

    @Autowired
    public QrdScheduler(QrdProcessor qrdProcessor, HealthCheckService healthCheckService) {
        this.qrdProcessor = qrdProcessor;
        this.healthCheckService = healthCheckService;
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
}
