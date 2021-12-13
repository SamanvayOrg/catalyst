package org.catalysts.commengage.scheduler;

import org.apache.log4j.Logger;
import org.catalysts.commengage.config.WebSecurityConfig;
import org.catalysts.commengage.repository.QrdRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;

@Component
public class QrdScheduler {

    @Autowired
    private QrdRepository qrdRepository;

    private static final Logger logger = Logger.getLogger(WebSecurityConfig.class);

    @Scheduled(cron = "${commengage.qrd.cron}")
    public void qrdJob() {
        logger.debug("In qrd job");
        LinkedHashMap<String, Object> qrCodes = qrdRepository.getQRCodes();
        logger.debug(String.format("QRCodes = %s", qrCodes));
    }
}