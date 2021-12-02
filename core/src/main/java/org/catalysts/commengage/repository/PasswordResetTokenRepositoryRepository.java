package org.catalysts.commengage.repository;

import org.catalysts.commengage.domain.security.PasswordResetToken;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PasswordResetTokenRepositoryRepository extends CrudRepository<PasswordResetToken, Integer> {
    PasswordResetToken findByToken(String token);
}
