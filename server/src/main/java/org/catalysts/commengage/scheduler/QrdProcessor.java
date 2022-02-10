package org.catalysts.commengage.scheduler;

import lombok.extern.slf4j.Slf4j;
import org.catalysts.commengage.contract.qrd.QRCodeResponse;
import org.catalysts.commengage.contract.qrd.UserRequestResponse;
import org.catalysts.commengage.domain.QRCode;
import org.catalysts.commengage.domain.UserRequest;
import org.catalysts.commengage.repository.QRCodeRepository;
import org.catalysts.commengage.repository.QrdApiRepository;
import org.catalysts.commengage.repository.UserRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class QrdProcessor {
    private static final int QRD_PAGE_LIMIT = 1000;

    private final QrdApiRepository qrdApi;
    private final QRCodeRepository qrCodeRepository;
    private final UserRequestRepository userRequestRepository;

    @Autowired
    public QrdProcessor(QrdApiRepository qrdApi, QRCodeRepository qrCodeRepository, UserRequestRepository userRequestRepository) {
        this.qrdApi = qrdApi;
        this.qrCodeRepository = qrCodeRepository;
        this.userRequestRepository = userRequestRepository;
    }

    public void processQrCodes() {
        qrdApi.getQRCodes().forEach(this::processQRCode);
    }

    protected void processQRCode(QRCodeResponse qrCodeResponse) {
        QRCode qrCode = createOrUpdateQRCode(qrCodeResponse);
        int requestsOffset = qrCode.getRequestsOffset();
        log.info("Current offset for {} is {}", qrCode.getQrdId(), requestsOffset);

        while(requestsOffset < qrCodeResponse.getScans()) {
            var requests = qrdApi.getQRCodeDetails(qrCodeResponse.getQrdid(), QRD_PAGE_LIMIT, requestsOffset);
            log.info("Requests count for qr {} is {}", qrCodeResponse.getQrdid(), requests.size());
            for (int i = 0; i < requests.size(); i++) {
                UserRequestResponse userRequestResponse = requests.get(i);
                requestsOffset = addNewRequest(qrCode, requestsOffset, userRequestResponse);
                log.info("QrCode: {}. Processed {} scans.", qrCode.getQrdId(), requestsOffset);
            }
        }
    }

    @Transactional
    protected int addNewRequest(QRCode qrCode, int requestsOffset, UserRequestResponse userRequestResponse) {
        int newOffset = createUserRequest(qrCode, userRequestResponse, requestsOffset);
        qrCode.setRequestsOffset(requestsOffset);
        qrCodeRepository.save(qrCode);
        return newOffset;
    }

    private int createUserRequest(QRCode qrCodeEntity, UserRequestResponse userRequestResponse,
                                  int requestsOffset) {
        UserRequest userRequest = userRequestResponse.createEntity(qrCodeEntity);
        if (userRequestResponse.getAccuracy() <= 5000) {
//            setReverseGeoCodeDataOnUserRequest(userRequestDto.getLat(), userRequestDto.getLng(), userRequest);
        }
        userRequestRepository.save(userRequest);
        return requestsOffset + 1;
    }

    @Transactional
    protected QRCode createOrUpdateQRCode(QRCodeResponse qrCodeResponse) {
        QRCode entity = qrCodeRepository.findByQrdId(qrCodeResponse.getQrdid());
        if (entity != null) {
            qrCodeResponse.updateEntity(entity);
        } else {
            entity = qrCodeResponse.toEntity();
        }
        qrCodeRepository.save(entity);
        return entity;
    }
}
