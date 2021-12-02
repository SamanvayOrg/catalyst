package org.catalysts.commengage.domain.framework;

import com.sun.istack.NotNull;
import org.catalysts.commengage.domain.Organisation;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class OrganisationalEntity extends AbstractEntity {
    @ManyToOne(targetEntity = Organisation.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "organisation_id")
    @NotNull
    private Organisation organisation;

    public Organisation getOrganisation() {
        return organisation;
    }

    public void setOrganisation(Organisation organisation) {
        this.organisation = organisation;
    }
}
