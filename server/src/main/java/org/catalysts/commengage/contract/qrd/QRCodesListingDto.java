package org.catalysts.commengage.contract.qrd;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@EqualsAndHashCode()
@ToString
public class QRCodesListingDto {
    private List<QRCodeDto> qrcodes;
}