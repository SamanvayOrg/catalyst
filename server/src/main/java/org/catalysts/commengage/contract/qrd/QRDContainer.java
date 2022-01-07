package org.catalysts.commengage.contract.qrd;

import lombok.*;

@Getter
@Setter
@EqualsAndHashCode()
@ToString
@Builder
public class QRDContainer<T> {
    private T result;
}