package org.catalysts.commengage.repository;

import org.catalysts.commengage.domain.QRCode;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QRCodeRepository extends CrudRepository<QRCode, Integer> {
    QRCode findByQrdId(String qrdId);
    List<QRCode> findAllBy();
}
