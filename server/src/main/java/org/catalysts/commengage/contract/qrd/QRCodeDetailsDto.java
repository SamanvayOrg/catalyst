package org.catalysts.commengage.contract.qrd;

import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class QRCodeDetailsDto {
    private int requestcount;
    private List<UserRequestDto> requests;
}