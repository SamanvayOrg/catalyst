package org.catalysts.commengage.domain.security;

import org.catalysts.commengage.domain.framework.AbstractEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "privilege")
public class Privilege extends AbstractEntity {
    @Column(name = "name")
    private String name;
}
