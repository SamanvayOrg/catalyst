package org.catalysts.commengage.scheduler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import org.catalysts.commengage.config.AppConfig;
import org.catalysts.commengage.contract.qrd.*;
import org.catalysts.commengage.domain.CodedLocation;
import org.catalysts.commengage.domain.GoogleReverseGeoResponse;
import org.catalysts.commengage.repository.*;
import org.catalysts.commengage.util.FileUtil;
import org.catalysts.commengage.util.ObjectMapperFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

@SpringBootTest
class EndToEndTest {
    @Autowired
    private QRCodeRepository qrCodeRepository;
    @Autowired
    private UserRequestRepository userRequestRepository;
    @Autowired
    private CodedLocationRepository codedLocationRepository;
    @Autowired
    private AppConfig appConfig;

    @Test
    public void processQrCodes() {
        QrdProcessor qrdProcessor = new QrdProcessor(new QrdApiRepositoryStub(), qrCodeRepository, userRequestRepository, codedLocationRepository);
        qrdProcessor.processQrCodes();
        assertNotEquals(0, codedLocationRepository.findAllBy().size());

        CodedLocationProcessor codedLocationProcessor = new CodedLocationProcessor(codedLocationRepository, new GoogleReverseGeoRepositoryStub(appConfig), appConfig);
        codedLocationProcessor.process();
    }

    public static class GoogleReverseGeoRepositoryStub extends GoogleReverseGeoRepository {
        public GoogleReverseGeoRepositoryStub(AppConfig appConfig) {
            super(null, appConfig);
        }

        @Override
        public GoogleReverseGeoResponse getReverseGeocode(CodedLocation codedLocation) {
            try {
                String s = FileUtil.readFile("/stubbedGoogleResponse.json");
                return ObjectMapperFactory.OBJECT_MAPPER.readValue(s, GoogleReverseGeoResponse.class);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }
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
