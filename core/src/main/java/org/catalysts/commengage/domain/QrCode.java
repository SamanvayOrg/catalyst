package org.catalysts.commengage.domain;

import org.catalysts.commengage.domain.framework.AbstractEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "qr_code")
public class QrCode extends AbstractEntity {

    @Column
    private String qrd_id;

    @Column
    private String short_url;

    @Column
    private String folder;

    @Column
    private int scans;

    @Column
    private int unique_visitors;

    @Column
    private LocalDateTime creationDate;

    public String getQrd_id() {
        return qrd_id;
    }

    public void setQrd_id(String qrd_id) {
        this.qrd_id = qrd_id;
    }

    public String getShort_url() {
        return short_url;
    }

    public void setShort_url(String short_url) {
        this.short_url = short_url;
    }

    public String getFolder() {
        return folder;
    }

    public void setFolder(String folder) {
        this.folder = folder;
    }

    public int getScans() {
        return scans;
    }

    public void setScans(int scans) {
        this.scans = scans;
    }

    public int getUnique_visitors() {
        return unique_visitors;
    }

    public void setUnique_visitors(int unique_visitors) {
        this.unique_visitors = unique_visitors;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }
}
