package org.catalysts.commengage.contract.qrd;

import lombok.*;

import java.util.List;

@Getter
@Setter
@EqualsAndHashCode()
@ToString
public class QRCodeDetailsDto {
    private int requestcount;
    private List<UserRequestDto> requests;
}