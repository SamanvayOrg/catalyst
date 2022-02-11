package org.catalysts.commengage.scheduler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import org.catalysts.commengage.contract.qrd.*;
import org.catalysts.commengage.repository.CodedLocationRepository;
import org.catalysts.commengage.repository.QRCodeRepository;
import org.catalysts.commengage.repository.QrdApiRepository;
import org.catalysts.commengage.repository.UserRequestRepository;
import org.catalysts.commengage.util.FileUtil;
import org.catalysts.commengage.util.ObjectMapperFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.List;

@SpringBootTest
class EndToEndTest {
    @Autowired
    private QRCodeRepository qrCodeRepository;
    @Autowired
    private UserRequestRepository userRequestRepository;
    @Autowired
    private CodedLocationRepository codedLocationRepository;

    @Test
    public void processQrCodes() {
        QrdProcessor qrdProcessor = new QrdProcessor(new QrdApiRepositoryStub(), qrCodeRepository, userRequestRepository, codedLocationRepository);
        qrdProcessor.processQrCodes();
    }

    public static class QrdApiRepositoryStub extends QrdApiRepository {
        private final HashMap<String, List<UserRequestResponse>> userRequestsMap;

        public QrdApiRepositoryStub() {
            userRequestsMap = new HashMap<>();
            userRequestsMap.put("iec1", getUserRequests("/userRequests-iec1.json"));
            userRequestsMap.put("nregaiec", getUserRequests("/userRequests-nregaiec.json"));
        }

        private List<UserRequestResponse> getUserRequests(String fileName) {
            try {
                String s = FileUtil.readFile(fileName);
                QRDContainer<QRCodeDetailsDto> response = ObjectMapperFactory.OBJECT_MAPPER.readValue(s, new TypeReference<>() {
                });
                return response.getResult().getRequests();
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }

        @Override
        public List<QRCodeResponse> getQRCodes() {
            try {
                String s = FileUtil.readFile("/qrCodes.json");
                QRDContainer<QRCodesListingDto> response = ObjectMapperFactory.OBJECT_MAPPER.readValue(s, new TypeReference<>() {
                });
                return response.getResult().getQrcodes();
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }

        @Override
        public List<UserRequestResponse> getQRCodeDetails(String qrCodeId, int limit, int requestsOffset) {
            return userRequestsMap.get(qrCodeId);
        }
    }
}
