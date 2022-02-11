package org.catalysts.commengage.repository;

import org.catalysts.commengage.domain.UserRequest;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRequestRepository extends CrudRepository<UserRequest, Integer> {
    UserRequest findByUniqueQrdRequestId(String uniqueRequestId);
    List<UserRequest> findAllBy();
}
