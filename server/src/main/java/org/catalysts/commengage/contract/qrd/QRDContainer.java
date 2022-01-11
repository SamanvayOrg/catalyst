package org.catalysts.commengage.contract.qrd;

import lombok.*;

@Getter
@Setter
@EqualsAndHashCode()
@ToString
public class QRDContainer<T> {
    private T result;
}