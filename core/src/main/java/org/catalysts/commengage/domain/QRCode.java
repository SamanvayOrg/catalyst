package org.catalysts.commengage.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "qr_code")
public class QRCode extends AuditedEntity {
    @Column
    private String qrdId;

    @Column
    private String shortUrl;

    @Column
    private String folder;

    @Column
    private int scans;

    @Column
    private int uniqueVisitors;

    @Column
    private LocalDateTime creationDate;

    @Column
    private int requestsOffset;

    @Column
    private String tags;

    @Column
    private String title;

    @Column
    private String description;
}
