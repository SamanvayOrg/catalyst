package org.catalysts.commengage.contract.qrd;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.catalysts.commengage.domain.QRCode;

import java.time.LocalDateTime;

@Getter
@Setter
@EqualsAndHashCode()
@ToString
@Builder
public class QRCodeDto {
    @JsonProperty("id")
    private String qrdid;

    private String shorturl;

    private String folder;

    private int scans;

    private int uniquevisitors;

    private String tags;

    private String title;

    private String description;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime creationdate;

    @JsonIgnore
    public QRCode toEntity() {
        QRCode qrCode = new QRCode();
        qrCode.setQrdId(this.qrdid);
        qrCode.setShortUrl(this.shorturl);
        qrCode.setUniqueVisitors(this.uniquevisitors);
        qrCode.setScans(this.scans);
        qrCode.setFolder(this.folder);
        qrCode.setCreationDate(this.creationdate);
        qrCode.setTitle(this.title);
        qrCode.setTags(this.tags);
        qrCode.setDescription(this.description);
        return qrCode;
    }

    @JsonIgnore
    public void updateEntity(QRCode qrCode) {
        qrCode.setShortUrl(this.shorturl);
        qrCode.setUniqueVisitors(this.uniquevisitors);
        qrCode.setScans(this.scans);
        qrCode.setFolder(this.folder);
        qrCode.setTitle(this.title);
        qrCode.setTags(this.tags);
        qrCode.setDescription(this.description);
    }
}