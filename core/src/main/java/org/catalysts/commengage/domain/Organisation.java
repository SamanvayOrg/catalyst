package org.catalysts.commengage.domain;

import org.catalysts.commengage.domain.framework.AbstractEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

//@Entity
//@Table(name = "organisation")
public class Organisation extends AbstractEntity {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
