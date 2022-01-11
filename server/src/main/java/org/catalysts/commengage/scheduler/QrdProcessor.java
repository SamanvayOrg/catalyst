package org.catalysts.commengage.scheduler;

import lombok.extern.slf4j.Slf4j;
import org.catalysts.commengage.contract.qrd.QRCodeDto;
import org.catalysts.commengage.contract.qrd.UserRequestDto;
import org.catalysts.commengage.domain.QRCode;
import org.catalysts.commengage.domain.UserRequest;
import org.catalysts.commengage.repository.MapMyIndiaApiRepository;
import org.catalysts.commengage.repository.QRCodeRepository;
import org.catalysts.commengage.repository.QrdApiRepository;
import org.catalysts.commengage.repository.UserRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@Slf4j
public class QrdProcessor {

    private static final int QRD_PAGE_LIMIT = 1000;

    @Autowired
    private QrdApiRepository qrdApi;

    @Autowired
    private MapMyIndiaApiRepository mapMyIndiaApi;

    @Autowired
    private QRCodeRepository qrCodeRepository;

    @Autowired
    private UserRequestRepository userRequestRepository;

    public void processQrCodes() {
        qrdApi.getQRCodes().forEach(this::processQRCode);
    }

    private void processQRCode(QRCodeDto qrCodeDto) {
        QRCode qrCodeEntity = createOrUpdateQRCodeEntity(qrCodeDto);
        int requestsOffset = qrCodeEntity.getRequestsOffset();
        log.info("Current offset for {} is {}", qrCodeEntity.getQrdId(), requestsOffset);

        while(requestsOffset < qrCodeDto.getScans()) {
            var requests = qrdApi.getQRCodeDetails(qrCodeDto.getQrdid(), QRD_PAGE_LIMIT, requestsOffset);
            log.info("Requests count for qr {} is {}", qrCodeDto.getQrdid(), requests.size());
            for (int i = 0; i < requests.size(); i++) {
                UserRequestDto userRequestDto = requests.get(i);
                requestsOffset = createUserRequest(qrCodeEntity, userRequestDto, requestsOffset);
                log.info("QrCode: {}. Processed {} scans.", qrCodeEntity.getQrdId(), requestsOffset);
            }
        }

        qrCodeEntity.setRequestsOffset(requestsOffset);
        qrCodeRepository.save(qrCodeEntity);
    }

    private int createUserRequest(QRCode qrCodeEntity, UserRequestDto userRequestDto,
                                  int requestsOffset) {
        var userRequest = userRequestDto.createEntity(qrCodeEntity);
        if (userRequestDto.getAccuracy() <= 5000) {
            setReverseGeoCodeDataOnUserRequest(userRequestDto.getLat(), userRequestDto.getLng(), userRequest);
        }
        userRequestRepository.save(userRequest);
        return requestsOffset + 1;
    }

    private void setReverseGeoCodeDataOnUserRequest(double lat, double lng, UserRequest entity) {
        var reverseGeoCode = mapMyIndiaApi.getReverseGeoCode(lat, lng);
        log.info("Lat {} Lng {} Reverse GeoCode: {}", lat, lng, reverseGeoCode);
        entity.setState(reverseGeoCode.getState());
        entity.setDistrict(reverseGeoCode.getDistrict());
        entity.setSubDistrict(reverseGeoCode.getSubDistrict());
        entity.setCity(reverseGeoCode.getCity());
        entity.setVillage(reverseGeoCode.getVillage());
        entity.setPinCode(reverseGeoCode.getPincode());
    }

    private QRCode createOrUpdateQRCodeEntity(QRCodeDto qrCodeDto) {
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
