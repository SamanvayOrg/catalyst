package org.catalysts.commengage.scheduler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;
import org.catalysts.commengage.config.WebSecurityConfig;
import org.catalysts.commengage.repository.QrdRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class QrdScheduler {

    @Autowired
    private QrdRepository qrdRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private static final Logger logger = Logger.getLogger(WebSecurityConfig.class);

    @Scheduled(cron = "${commengage.qrd.cron}")
    public void qrdJob() throws JsonProcessingException {
        logger.debug("In qrd job");
        var qrCodes = qrdRepository.getQRCodes();
        logger.debug(String.format("QRCodes = %s", objectMapper.writeValueAsString(qrCodes)));
    }
}