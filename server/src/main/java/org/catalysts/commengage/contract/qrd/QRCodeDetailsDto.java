package org.catalysts.commengage.contract.qrd;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Getter
@Setter
@EqualsAndHashCode()
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class QRCodeDetailsDto {
    private int requestcount;
    private List<UserRequestResponse> requests;
}
