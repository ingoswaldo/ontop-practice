/*
 * @creator: Oswaldo Montes
 * @date: December 19, 2023
 *
 */
package com.koombea.web.app.ontoppractice.services;

import com.koombea.web.app.ontoppractice.models.entities.UserIntegrationEntity;
import com.koombea.web.app.ontoppractice.repositories.UserIntegrationRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserIntegrationService {

    private final UserIntegrationRepository userIntegrationRepository;

    public UserIntegrationService(UserIntegrationRepository userIntegrationRepository) {
        this.userIntegrationRepository = userIntegrationRepository;
    }

    public List<UserIntegrationEntity> findAllByCategoryNameAndServiceName(String categoryName, String serviceName) {
        return userIntegrationRepository.findAllByCategoryNameAndServiceNameEquals(categoryName, serviceName);
    }

    public Optional<UserIntegrationEntity> findByAccountToken(String accountToken) {
        return userIntegrationRepository.findFirstByPayloadDataAccountToken(accountToken);
    }
}
