package org.catalysts.commengage.contract.qrd;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode()
@ToString
public class QRDContainer<T> {
    private T result;
}