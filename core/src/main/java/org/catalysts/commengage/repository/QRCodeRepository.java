package org.catalysts.commengage.repository;

import org.catalysts.commengage.domain.QRCode;
import org.catalysts.commengage.domain.security.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QRCodeRepository extends CrudRepository<QRCode, Integer> {
    QRCode findByQrdId(String qrdId);
}
