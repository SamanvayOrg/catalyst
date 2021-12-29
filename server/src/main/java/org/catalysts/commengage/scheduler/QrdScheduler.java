package org.catalysts.commengage.scheduler;

import org.apache.log4j.Logger;
import org.catalysts.commengage.config.WebSecurityConfig;
import org.catalysts.commengage.contract.qrd.QRCodeDto;
import org.catalysts.commengage.contract.qrd.UserRequestDto;
import org.catalysts.commengage.domain.QRCode;
import org.catalysts.commengage.repository.MapMyIndiaApiRepository;
import org.catalysts.commengage.repository.QRCodeRepository;
import org.catalysts.commengage.repository.QrdApiRepository;
import org.catalysts.commengage.repository.UserRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component
public class QrdScheduler {

    @Autowired
    private QrdApiRepository qrdApi;

    @Autowired
    private MapMyIndiaApiRepository mapMyIndiaApi;

    @Autowired
    private QRCodeRepository qrCodeRepository;

    @Autowired
    private UserRequestRepository userRequestRepository;

    private static final Logger logger = Logger.getLogger(WebSecurityConfig.class);

    @Scheduled(cron = "${commengage.qrd.cron}")
    public void qrdJob() {
        logger.debug("Qrd background job started");
        try {
            var qrCodeListings = qrdApi.getQRCodes();
            for (QRCodeDto qrCodeDto : qrCodeListings.getResult().getQrcodes()) {
                processQRCode(qrCodeDto);
            }
        }
        catch (Exception e) {
            logger.error("Exception", e);
            throw e;
        }
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    protected void processQRCode(QRCodeDto qrCodeDto) {
        QRCode qrCodeEntity = createOrUpdateQRCode(qrCodeDto);
        int requestsOffset = qrCodeEntity.getRequestsOffset();
        logger.debug(String.format("Current offset for %s is %d", qrCodeEntity.getQrdId(), requestsOffset));

        var qrCodeDetails = qrdApi.getQRCodeDetails(qrCodeDto.getQrdid(), requestsOffset);
        logger.debug("Got back qrCodeDetails");
        for (UserRequestDto userRequestDto : qrCodeDetails.getResult().getRequests()) {
            requestsOffset = createUserRequest(qrCodeEntity, userRequestDto, requestsOffset);
            logger.debug(String.format("QrCode: %s. Processed %s scans.", qrCodeEntity.getQrdId(), requestsOffset));
        }
        qrCodeEntity.setRequestsOffset(requestsOffset);
        qrCodeRepository.save(qrCodeEntity);
    }

    private int createUserRequest(QRCode qrCodeEntity, UserRequestDto userRequestDto,
                                  int requestsOffset) {
        var reverseGeoCode = mapMyIndiaApi.getReverseGeoCode(userRequestDto.getLat(), userRequestDto.getLng());
        logger.debug(String.format("Lat %f Lng %f Reverse GeoCode: %s", userRequestDto.getLat(), userRequestDto.getLng(), reverseGeoCode));
        var entity = userRequestDto.createEntity(qrCodeEntity);
        entity.setState(reverseGeoCode.getState());
        entity.setDistrict(reverseGeoCode.getDistrict());
        entity.setSubDistrict(reverseGeoCode.getSubDistrict());
        entity.setCity(reverseGeoCode.getCity());
        entity.setVillage(reverseGeoCode.getVillage());
        entity.setPinCode(reverseGeoCode.getPincode());
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