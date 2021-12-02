package org.catalysts.commengage.repository;

import org.catalysts.commengage.domain.Organisation;
import org.catalysts.commengage.repository.framework.AbstractRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrganisationRepository extends AbstractRepository<Organisation> {
    Organisation findByName(String name);
}
