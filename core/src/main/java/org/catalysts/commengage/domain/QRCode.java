package org.catalysts.commengage.domain;

import org.catalysts.commengage.domain.framework.AbstractEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "qr_code")
public class QRCode extends AbstractEntity {

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
