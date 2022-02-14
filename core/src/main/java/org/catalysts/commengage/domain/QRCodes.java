package org.catalysts.commengage.domain;

import java.util.List;

public class QRCodes {
    public static QRCode findByQrCodeId(String qrCodeId, List<QRCode> qrCodeList) {
        return qrCodeList.stream().filter(qrCode -> qrCode.getQrdId().equals(qrCodeId)).findFirst().orElse(null);
    }
}
