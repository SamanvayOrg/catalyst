package org.catalysts.commengage.contract.qrd;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@EqualsAndHashCode()
@ToString
public class QRCodeDto {
    @JsonProperty("id")
    private String qrdid;

    private String shorturl;

    private String folder;

    private int scans;

    private int uniquevisitors;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime creationdate;
}