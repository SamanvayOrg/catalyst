package org.catalysts.commengage.controller.web;

import org.catalysts.commengage.contract.ApplicationStatus;
import org.catalysts.commengage.contract.UserCreateRequest;
import org.catalysts.commengage.controller.web.framework.AbstractController;
import org.catalysts.commengage.domain.Organisation;
import org.catalysts.commengage.domain.security.User;
import org.catalysts.commengage.domain.security.UserType;
import org.catalysts.commengage.repository.OrganisationRepository;
import org.catalysts.commengage.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;

@RestController
public class SecurityController extends AbstractController {
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserRepository userRepository;
    private final OrganisationRepository organisationRepository;

    @Autowired
    public SecurityController(BCryptPasswordEncoder bCryptPasswordEncoder, UserRepository userRepository, OrganisationRepository organisationRepository) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userRepository = userRepository;
        this.organisationRepository = organisationRepository;
    }

    private User saveUser(Organisation organisation, String userName, String email, String phone, UserType userType, String password) {
        User user = new User();
        user.setName(userName);
        user.setEmail(email);
        user.setPhone(phone);
        user.setUserType(userType);
        user.setPassword(bCryptPasswordEncoder.encode(password));
        user.setOrganisation(organisation);
        return userRepository.save(user);
    }

    @RequestMapping(value = "/web/user", method = {RequestMethod.POST, RequestMethod.PUT})
    @PreAuthorize("hasAnyRole('OrgAdmin', 'Admin')")
    @Transactional
    public ResponseEntity<ApplicationStatus> createUser(UserCreateRequest request) {
        Organisation organisation = organisationRepository.findEntity(request.getOrganisationId());
        saveUser(organisation, toString(), request.getEmail(), request.getPhone(), request.getUserType(), request.getPassword());
        return createSuccessResponse();
    }
}
