package org.catalysts.commengage.contract.qrd;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@Getter
@Setter
@EqualsAndHashCode()
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class QRDContainer<T> {
    private T result;
}
