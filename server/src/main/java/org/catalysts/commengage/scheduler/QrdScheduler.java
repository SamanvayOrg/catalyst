package org.catalysts.commengage.scheduler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;
import org.catalysts.commengage.config.WebSecurityConfig;
import org.catalysts.commengage.contract.qrd.QRCodeDto;
import org.catalysts.commengage.contract.qrd.UserRequestDto;
import org.catalysts.commengage.domain.QRCode;
import org.catalysts.commengage.domain.UserRequest;
import org.catalysts.commengage.repository.QRCodeRepository;
import org.catalysts.commengage.repository.QrdApiRepository;
import org.catalysts.commengage.repository.UserRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class QrdScheduler {

    @Autowired
    private QrdApiRepository qrdApi;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private QRCodeRepository qrCodeRepository;

    @Autowired
    private UserRequestRepository userRequestRepository;

    private static final Logger logger = Logger.getLogger(WebSecurityConfig.class);

    @Scheduled(cron = "${commengage.qrd.cron}")
    public void qrdJob() throws JsonProcessingException {
        logger.debug("In qrd job");
        var qrCodeListings = qrdApi.getQRCodes();
        logger.debug(String.format("QRCodes = %s", objectMapper.writeValueAsString(qrCodeListings)));
        for (QRCodeDto qrCodeDto : qrCodeListings.getResult().getQrcodes()) {
            QRCode qrCodeEntity = createOrUpdateQRCode(qrCodeDto);
            int requestsOffset = qrCodeEntity.getRequestsOffset();
            try {
                var qrCodeDetails = qrdApi.getQRCodeDetails(qrCodeDto.getQrdid(), requestsOffset);
                for (UserRequestDto userRequestDto : qrCodeDetails.getResult().getRequests()) {
                    requestsOffset = createUserRequest(userRequestDto, qrCodeEntity.getRequestsOffset());
                }
            } catch (Exception e) {
                logger.error("Exception", e);
                throw e;
            } finally {
                qrCodeEntity.setRequestsOffset(requestsOffset);
                qrCodeRepository.save(qrCodeEntity);
            }
            break;
        }
    }

    private int createUserRequest(UserRequestDto userRequestDto,
                                  int requestsOffset) {
        UserRequest entity = userRequestDto.createEntity();
        userRequestRepository.save(entity);
        return requestsOffset + 1;
    }

    private QRCode createOrUpdateQRCode(QRCodeDto qrCodeDto) {
        QRCode entity = qrCodeRepository.findByQrdId(qrCodeDto.getQrdid());
        if (entity != null) {
            qrCodeDto.updateEntity(entity);
        } else {
            entity = qrCodeDto.toEntity();
        }
        qrCodeRepository.save(entity);
        return entity;
    }
}