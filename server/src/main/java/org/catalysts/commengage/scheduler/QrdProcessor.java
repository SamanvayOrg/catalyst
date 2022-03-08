package org.catalysts.commengage.scheduler;

import lombok.extern.slf4j.Slf4j;
import org.catalysts.commengage.QrdUserRequestUtil;
import org.catalysts.commengage.config.AppConfig;
import org.catalysts.commengage.contract.qrd.QRCodeResponse;
import org.catalysts.commengage.contract.qrd.UserRequestResponse;
import org.catalysts.commengage.domain.CodedLocation;
import org.catalysts.commengage.domain.QRCode;
import org.catalysts.commengage.domain.UserRequest;
import org.catalysts.commengage.repository.CodedLocationRepository;
import org.catalysts.commengage.repository.QRCodeRepository;
import org.catalysts.commengage.repository.QrdApiRepository;
import org.catalysts.commengage.repository.UserRequestRepository;
import org.catalysts.commengage.util.GeolocationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
public class QrdProcessor {

    private final QrdApiRepository qrdApi;
    private final QRCodeRepository qrCodeRepository;
    private final UserRequestRepository userRequestRepository;
    private final CodedLocationRepository codedLocationRepository;
    private final AppConfig appConfig;

    @Autowired
    public QrdProcessor(QrdApiRepository qrdApi, QRCodeRepository qrCodeRepository, UserRequestRepository userRequestRepository, CodedLocationRepository codedLocationRepository, AppConfig appConfig) {
        this.qrdApi = qrdApi;
        this.qrCodeRepository = qrCodeRepository;
        this.userRequestRepository = userRequestRepository;
        this.codedLocationRepository = codedLocationRepository;
        this.appConfig = appConfig;
    }

    public void processQrCodes() {
        String qrCodeToProcess = appConfig.getQrCodeToProcess();
        List<QRCodeResponse> qrCodes = qrdApi.getQRCodes();
        if (qrCodeToProcess == null || qrCodeToProcess.isEmpty())
            qrCodes.forEach(this::processQRCode);
        else
            qrCodes.stream().filter(qrCodeResponse -> qrCodeResponse.getQrdid().equals(qrCodeToProcess)).forEach(this::processQRCode);
    }

    protected void processQRCode(QRCodeResponse qrCodeResponse) {
        QRCode qrCode = createOrUpdateQRCode(qrCodeResponse);
        int requestsOffsetProcessed = qrCode.getRequestsOffset();
        int totalScans = qrCodeResponse.getScans();
        log.info("Current offset for {} is {}. Total scans: {}", qrCode.getQrdId(), requestsOffsetProcessed, totalScans);

        while(requestsOffsetProcessed < totalScans) {
            int requestOffset = QrdUserRequestUtil.getRequestOffset(totalScans, requestsOffsetProcessed);
            int requestLimit = QrdUserRequestUtil.getRequestLimit(totalScans, requestsOffsetProcessed);
            List<UserRequestResponse> requests = qrdApi.getQRCodeUserRequests(qrCodeResponse.getQrdid(), requestLimit, requestOffset);
            log.info("Offset requested is {} with limit {}. Requests count for qr {} is {}.", requestOffset, requestLimit, qrCodeResponse.getQrdid(), requests.size());
            for (int i = requests.size() - 1; i >= 0; i--) {
                UserRequestResponse userRequestResponse = requests.get(i);
                requestsOffsetProcessed = addNewRequest(qrCode, requestsOffsetProcessed, userRequestResponse);
                log.info("QrCode: {}. Processed {} scans.", qrCode.getQrdId(), requestsOffsetProcessed);
            }
        }
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    protected int addNewRequest(QRCode qrCode, int requestsOffset, UserRequestResponse userRequestResponse) {
        int newOffset = createUserRequest(qrCode, userRequestResponse, requestsOffset);
        qrCode.setRequestsOffset(newOffset);
        qrCodeRepository.save(qrCode);
        return newOffset;
    }

    private int createUserRequest(QRCode qrCodeEntity, UserRequestResponse userRequestResponse,
                                  int requestsOffset) {
        UserRequest userRequest = userRequestResponse.createEntity(qrCodeEntity);
        if (userRequestResponse.getAccuracy() <= 5000 && userRequest.getLat() != 0) {
            CodedLocation codedLocation = codedLocationRepository.findByLatAndLng(GeolocationUtil.round(userRequest.getLat()), GeolocationUtil.round(userRequest.getLng()));
            if (codedLocation == null) {
                codedLocation = codedLocationRepository.save(userRequest.createCodedLocation());
            }
            userRequest.setCodedLocation(codedLocation);
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
