package org.catalysts.commengage.scheduler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;
import org.catalysts.commengage.config.WebSecurityConfig;
import org.catalysts.commengage.contract.qrd.QRCodeDto;
import org.catalysts.commengage.contract.qrd.UserRequestDto;
import org.catalysts.commengage.domain.*;
import org.catalysts.commengage.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class QrdScheduler {

    @Autowired
    private QrdApiRepository qrdApi;

    @Autowired
    private MapMyIndiaApiRepository mapMyIndiaApi;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private QRCodeRepository qrCodeRepository;

//    @Autowired
//    private LocationRepository locationRepository;
//
//    @Autowired
//    private CityRepository cityRepository;
//
//    @Autowired
//    private VillageRepository villageRepository;
//
//    @Autowired
//    private DistrictRepository districtRepository;
//
//    @Autowired
//    private SubDistrictRepository subDistrictRepository;

    @Autowired
    private UserRequestRepository userRequestRepository;

    private static final Logger logger = Logger.getLogger(WebSecurityConfig.class);

    @Scheduled(cron = "${commengage.qrd.cron}")
    public void qrdJob() throws JsonProcessingException {
        logger.debug("In qrd job");
        var qrCodeListings = qrdApi.getQRCodes();
        logger.debug(String.format("QRCodescreateUserRequest = %s", objectMapper.writeValueAsString(qrCodeListings)));
        for (QRCodeDto qrCodeDto : qrCodeListings.getResult().getQrcodes()) {
            QRCode qrCodeEntity = createOrUpdateQRCode(qrCodeDto);
            int requestsOffset = qrCodeEntity.getRequestsOffset();
            try {
                var qrCodeDetails = qrdApi.getQRCodeDetails(qrCodeDto.getQrdid(), requestsOffset);
                logger.debug("Got back qrCodeDetails");
                for (UserRequestDto userRequestDto : qrCodeDetails.getResult().getRequests()) {
                    if (requestsOffset >= 50) {
                        logger.debug(String.format("Stopping scan processing. QrCode: %s. Processed %s scans.", qrCodeEntity.getQrdId(), requestsOffset));
                        break;
                    }
                    requestsOffset = createUserRequest(qrCodeEntity, userRequestDto, requestsOffset);
                    logger.debug(String.format("QrCode: %s. Processed %s scans.", qrCodeEntity.getQrdId(), requestsOffset));
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