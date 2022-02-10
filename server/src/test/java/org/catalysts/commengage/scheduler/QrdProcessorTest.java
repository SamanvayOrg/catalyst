package org.catalysts.commengage.scheduler;

import org.catalysts.commengage.contract.qrd.QRCodeResponse;
import org.catalysts.commengage.contract.qrd.UserRequestResponse;
import org.catalysts.commengage.repository.QRCodeRepository;
import org.catalysts.commengage.repository.QrdApiRepository;
import org.catalysts.commengage.repository.UserRequestRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class QrdProcessorTest {

    private final String CODE = "abc";

    @Mock
    private QrdApiRepository qrdApi;

    @Mock
    private QRCodeRepository qrCodeRepository;

    @Mock
    private UserRequestRepository userRequestRepository;

    @InjectMocks
    private QrdProcessor qrdProcessor;

    @Test
    void processQrCodes() {
        when(qrdApi.getQRCodes()).thenReturn(generateCodes());
        when(qrdApi.getQRCodeDetails(CODE, 1000, 0)).thenReturn(generateRequests(1000));
        when(qrdApi.getQRCodeDetails(CODE, 1000, 1000)).thenReturn(generateRequests(1000));
        when(qrdApi.getQRCodeDetails(CODE, 1000, 2000)).thenReturn(generateRequests(200));

        qrdProcessor.processQrCodes();

        //Verify that getQrCodeDetails api was called only three times
        verify(qrdApi, times(3)).getQRCodeDetails(anyString(), anyInt(), anyInt());

        //Verify that the correct api calls were made and in the required order
        InOrder inOrder = inOrder(qrdApi);
        inOrder.verify(qrdApi).getQRCodeDetails(CODE, 1000, 0);
        inOrder.verify(qrdApi).getQRCodeDetails(CODE, 1000, 1000);
        inOrder.verify(qrdApi).getQRCodeDetails(CODE, 1000, 2000);

    }

    private List<QRCodeResponse> generateCodes() {
        QRCodeResponse qrCodeResponse = new QRCodeResponse();
        qrCodeResponse.setQrdid(CODE);
        qrCodeResponse.setScans(2200);
        var codes = List.of(qrCodeResponse);
        return codes;
    }

    private List<UserRequestResponse> generateRequests(int numberOfRequests) {
        var requests = new ArrayList<UserRequestResponse>();
        for (int i = 0; i < numberOfRequests; i++) {
            requests.add(new UserRequestResponse());
        }
        return requests;
    }
}
