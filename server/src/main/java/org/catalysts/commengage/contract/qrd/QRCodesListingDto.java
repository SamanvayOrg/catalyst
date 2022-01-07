package org.catalysts.commengage.contract.qrd;

import lombok.*;

import java.util.List;

@Getter
@Setter
@EqualsAndHashCode()
@ToString
@Builder
public class QRCodesListingDto {
    private List<QRCodeDto> qrcodes;
}