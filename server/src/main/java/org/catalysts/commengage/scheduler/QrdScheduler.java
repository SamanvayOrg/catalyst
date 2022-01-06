package org.catalysts.commengage.scheduler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class QrdScheduler {
    @Autowired
    private QrdProcessor qrdProcessor;

    @Scheduled(cron = "${commengage.qrd.cron}")
    public void qrdJob() {
        log.debug("Qrd background job started");
        qrdProcessor.processQrCodes();
    }
}